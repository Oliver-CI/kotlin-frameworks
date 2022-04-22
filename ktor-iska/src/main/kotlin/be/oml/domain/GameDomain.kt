package be.oml.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateComponentSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Game(
    val id: Long,
    val dungeonMaster: String,
    @Serializable(with = LocalDateComponentSerializer::class)
    val date: LocalDate
)

@Serializable
data class GameDto(
    val dungeonMaster: String,
    @Serializable(with = LocalDateComponentSerializer::class)
    val date: LocalDate
)

object Games : Table() {
    val id = long("id").autoIncrement()
    val dungeonMaster = varchar("title", 256)
    val date_year = integer("year")
    val date_month = integer("month")
    val date_day = integer("day")

    override val primaryKey = PrimaryKey(id)
}
