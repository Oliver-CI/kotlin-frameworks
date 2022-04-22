package be.oml.springkotlinintro.application

import be.oml.springkotlinintro.domain.*
import be.oml.springkotlinintro.persistence.AttackRepo
import be.oml.springkotlinintro.persistence.DragonRepo
import be.oml.springkotlinintro.persistence.DungeonRepo
import be.oml.springkotlinintro.persistence.RoomRepo
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
class DndService(
    private val dragonRepo: DragonRepo,
    private val dungeonRepo: DungeonRepo,
    private val roomRepo: RoomRepo,
) : DragonService, DungeonService {

    override fun getABigOne(): Dragon {
        return dragonRepo.findAll().shuffled().first()
    }

    override fun getAllTyrants(): List<Dragon> = dragonRepo.findAll().toList()

    //TODO: create new one
    override fun generateAKillRoom(roomIds: List<Long>): Long? {
        val existingRooms = roomIds
            .map { id -> roomRepo.findById(id) }
            .filter { opt -> opt.isPresent }
            .map { opt -> opt.get() }
        if (existingRooms.isEmpty()) return null

        return dungeonRepo.save(Dungeon(rooms = existingRooms)).id
    }

    override fun activeDungeons(): List<Dungeon> = dungeonRepo.findAll().toList()
}

@Configuration
open class DnDInitializer {

    @Bean
    open fun databaseInitializer(
        dragonRepo: DragonRepo,
        attackRepo: AttackRepo,
        dungeonRepo: DungeonRepo,
        roomRepo: RoomRepo,
    ) = ApplicationRunner {
        val wingAttack = attackRepo.save(Attack(toHit = 12, damage = 35))
        val clawAttack = attackRepo.save(Attack(toHit = 10, damage = 20))
        val breathAttack = attackRepo.save(Attack(toHit = 19, damage = 75))

        val dragons: List<Dragon> = listOf(
            Dragon(name = "Tyamat", hp = 500, attacks = listOf(wingAttack, breathAttack, clawAttack)),
            Dragon(name = "Raishan", hp = 200, attacks = listOf(clawAttack)),
            Dragon(name = "Barney", hp = 1, attacks = emptyList())
        )
        dragonRepo.saveAll(dragons)

        val room1: Room = roomRepo.save(Room(enemies = 2, level = Level.EASY))
        val room2: Room = roomRepo.save(Room(enemies = 10, level = Level.HARD))
        val room3: Room = roomRepo.save(Room(enemies = 300, level = Level.DEADLY))
        val room4: Room = roomRepo.save(Room(enemies = 5, level = Level.MEDIUM))

        val dungeons: List<Dungeon> = listOf(
            Dungeon(rooms = listOf(room2)),
            Dungeon(rooms = listOf(room1, room4)),
            Dungeon(rooms = listOf(room3)),
        )
        dungeonRepo.saveAll(dungeons)
    }
}
