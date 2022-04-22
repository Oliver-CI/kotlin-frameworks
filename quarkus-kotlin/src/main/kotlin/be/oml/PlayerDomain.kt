package be.oml

import javax.persistence.*

@Entity
class Player() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null
    var name: String? = null

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    val character = Character()

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    val foodLikes = FoodLikes()

    /**
     * Overload default jackson constructor to initialize with a name
     */
    constructor(name: String) : this() {
        this.name = name
    }


    fun updateCharacter(character: Character) {
        this.character.level = character.level
        this.character.name = character.name
        this.character.profession = character.profession
    }

    fun updateFoodLikes(foodLikes: FoodLikes) {
        this.foodLikes.alcohol = foodLikes.alcohol
        this.foodLikes.nutAllergy = foodLikes.nutAllergy
        this.foodLikes.vegan = foodLikes.vegan
    }
}

@Entity
class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var character_id: Long? = null
    var name: String = ""
    var level: Int = 1

    @Enumerated
    var profession: Profession = Profession.PEASANT
}

enum class Profession {
    WIZARD, CLERIC, FIGHTER, BARBARIAN, RANGER, PEASANT
}

@Entity
class FoodLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var food_id: Long? = null
    var alcohol: Boolean = false
    var vegan: Boolean = false
    var nutAllergy: Boolean = false
}
