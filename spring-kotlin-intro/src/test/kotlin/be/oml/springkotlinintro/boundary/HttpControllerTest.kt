package be.oml.springkotlinintro.boundary

import be.oml.springkotlinintro.domain.Dragon
import be.oml.springkotlinintro.domain.Dungeon
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

private const val BASE_URI = "/api/v1"

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class HttpControllerTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun getAllDungeons() {
        val entity = restTemplate.getForEntity<Array<Dungeon>>("$BASE_URI/dungeon/all")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).hasSize(4)
    }

    @Test
    fun getAllDragons() {
        val entity = restTemplate.getForEntity<Array<Dragon>>("$BASE_URI/dragon/all")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isNotEmpty.hasSize(3)
    }

    @Test
    fun getAnDragon() {
        val entity = restTemplate.getForEntity<Dragon>("$BASE_URI/dragon/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        val dragon = entity.body!!
        assertThat(dragon.hp).isGreaterThan(0)
        assertThat(dragon.name).isNotBlank
        assertThat(dragon.id).isGreaterThan(0)
    }

    @Test
    fun createDungeon() {
        val listResponseEntity = restTemplate.getForEntity<Array<Dungeon>> ("$BASE_URI/dungeon/all")
        val dungeons = listResponseEntity.body!!
        val rooms: List<Long> = dungeons.map { dungeon -> dungeon.rooms[0].id!! }
        val dungeonDto = DungeonDto(rooms)
        val entity = restTemplate.postForEntity<Long>("$BASE_URI/dungeon/create", dungeonDto)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isNotNull
    }

    @Test
    fun createFaultyDungeon() {
        val dungeonDto = DungeonDto(emptyList())
        val entity = restTemplate.postForEntity<Long>("$BASE_URI/dungeon/create", dungeonDto)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }
}
