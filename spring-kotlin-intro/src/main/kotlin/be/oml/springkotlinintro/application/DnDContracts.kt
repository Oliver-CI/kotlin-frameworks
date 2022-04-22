package be.oml.springkotlinintro.application

import be.oml.springkotlinintro.domain.Dragon
import be.oml.springkotlinintro.domain.Dungeon

interface DragonService {
    fun getABigOne(): Dragon
    fun getAllTyrants(): List<Dragon>
}

interface DungeonService {
    fun generateAKillRoom(roomIds: List<Long>): Long?
    fun activeDungeons(): List<Dungeon>
}
