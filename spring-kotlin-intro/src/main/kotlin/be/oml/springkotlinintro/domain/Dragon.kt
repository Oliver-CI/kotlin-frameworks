package be.oml.springkotlinintro.domain

import javax.persistence.*

@Entity
class Dragon(
    @Id @GeneratedValue var id: Long? = null,
    val name: String,
    val hp: Int,
    @OneToMany
    //to allow dragon to use the same attacks
    @JoinColumn(name = "dragon_id")
    val attacks: List<Attack>
)

@Entity
class Attack(
    @Id @GeneratedValue var id: Long? = null,
    val toHit: Int,
    val damage: Int
)

