package be.oml.service

import be.oml.domain.Game
import be.oml.domain.GameDto
import be.oml.domain.Games
import be.oml.service.DatabaseFactory.dbQuery
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.Month


interface GameRepo {
    suspend fun allGames(): List<Game>
    suspend fun game(id: Long): Game?
    suspend fun addNewGame(gameDto: GameDto): Game?
    suspend fun deleteGame(id: Long): Boolean
}

class GameService : GameRepo {
    override suspend fun allGames(): List<Game> = dbQuery {
        Games.selectAll().map(::mapResultRowToGame)
    }

    override suspend fun game(id: Long): Game? = dbQuery {
        Games.select(Games.id eq id)
            .map(::mapResultRowToGame)
            .singleOrNull()
    }

    override suspend fun addNewGame(gameDto: GameDto): Game? = dbQuery {
        val insertStatement = Games.insert {
            it[dungeonMaster] = gameDto.dungeonMaster
            it[date_day] = gameDto.date.dayOfMonth
            it[date_month] = gameDto.date.month.value
            it[date_year] = gameDto.date.year
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::mapResultRowToGame)
    }

    override suspend fun deleteGame(id: Long): Boolean = dbQuery {
        Games.deleteWhere { Games.id eq id } > 0
    }

    private fun mapResultRowToGame(row: ResultRow) = Game(
        id = row[Games.id],
        dungeonMaster = row[Games.dungeonMaster],
        date = LocalDate(
            year = row[Games.date_year],
            monthNumber = row[Games.date_month],
            dayOfMonth = row[Games.date_day]
        )
    )
}

val gameRepo: GameRepo = GameService().apply {
    runBlocking {
        if (allGames().isEmpty()) {
            addNewGame(GameDto("Oliver", LocalDate(2022, Month.APRIL, 10)))
        }
    }
}
