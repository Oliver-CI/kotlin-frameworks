package be.oml.springkotlinintro.persistence

import be.oml.springkotlinintro.domain.Attack
import be.oml.springkotlinintro.domain.Dragon
import be.oml.springkotlinintro.domain.Dungeon
import be.oml.springkotlinintro.domain.Room
import org.springframework.data.repository.CrudRepository

interface DragonRepo : CrudRepository<Dragon, Long> {
    fun findByName(name: String): Dragon?
}

interface AttackRepo : CrudRepository<Attack, Long>

interface RoomRepo : CrudRepository<Room, Long>

interface DungeonRepo : CrudRepository<Dungeon, Long>
