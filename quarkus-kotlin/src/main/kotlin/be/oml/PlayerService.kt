package be.oml

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.runtime.Startup
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import kotlin.streams.toList


@ApplicationScoped
class PlayerRepository : PanacheRepository<Player> {
    fun findByName(name: String) = find("lower(name)", name.lowercase()).firstResult()
}

@ApplicationScoped
@Transactional
class PlayerService(val playerRepo: PlayerRepository) {

    fun getAllPlayers(): List<String> {
        return playerRepo.streamAll().map { player -> player.name!! }.sorted().toList()
    }

    fun getPlayerByName(name: String): Player? {
        return playerRepo.findByName(name)
    }

    fun addPlayer(name: String): Player {
        val player = Player(name)
        playerRepo.persist(player)
        return player
    }

    fun updateCharacter(name: String, character: Character): Player? {
        val playerByName = getPlayerByName(name) ?: return null
        playerByName.updateCharacter(character)
        return playerByName
    }

    fun updateFoodLikes(name: String, foodLikes: FoodLikes): Player? {
        val playerByName = getPlayerByName(name) ?: return null
        playerByName.updateFoodLikes(foodLikes)
        return playerByName
    }
}

@Startup
@Transactional
@ApplicationScoped
class InitAppBean(playerService: PlayerService) {
    private val names: List<String> = listOf("Hanna", "TimB", "Tom", "TimA", "TimC")

    init {
        names.forEach(playerService::addPlayer)
    }
}
