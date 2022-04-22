package be.oml.springkotlinintro.application

import be.oml.springkotlinintro.domain.Dragon
import be.oml.springkotlinintro.persistence.DragonRepo
import be.oml.springkotlinintro.persistence.DungeonRepo
import be.oml.springkotlinintro.persistence.RoomRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal open class DndServiceTest @Autowired constructor(
    val dungeonRepo: DungeonRepo,
    val dragonRepo: DragonRepo,
    val roomRepo: RoomRepo,
) {
    private lateinit var sut: DndService

    @BeforeEach
    fun setUp() {
        sut = DndService(dragonRepo, dungeonRepo, roomRepo)
    }

    @Test
    fun getABigOne() {
        val aBigOne = sut.getABigOne()
        assertThat(aBigOne).isNotNull.isInstanceOf(Dragon::class.java)
    }

    @Test
    fun generateAKillRoom() {
        val createdDungeon = sut.generateAKillRoom(listOf(1, 2))
        assertThat(createdDungeon).isNotNull
    }
}
