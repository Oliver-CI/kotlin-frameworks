package be.oml.springkotlinintro.domain

import javax.persistence.*

@Entity
class Dungeon(
    @Id @GeneratedValue var id: Long? = null,
    @OneToMany
    //to allow Dungeon to use the same Rooms
    @JoinColumn(name = "dungeon_id")
    val rooms: List<Room>
)

@Entity
class Room(
    @Id @GeneratedValue() var id: Long? = null, val enemies: Int, @Enumerated(EnumType.STRING) val level: Level
)

enum class Level {
    EASY, MEDIUM, HARD, DEADLY
}
