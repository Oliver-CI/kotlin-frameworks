package be.oml.springkotlinintro.boundary

import be.oml.springkotlinintro.application.DragonService
import be.oml.springkotlinintro.application.DungeonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/v1/iska")
class IskaController {

    @GetMapping("/")
    fun welcome() = "Welcome to the iska today (${DateUtil.getCurrentDay()})"

    internal class DateUtil {

        companion object {
            private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

            fun getCurrentDay(): String {
                return LocalDateTime.now().format(FORMATTER)
            }
        }
    }
}

@RestController
@RequestMapping("/api/v1/dungeon")
class DungeonController(private val dungeonService: DungeonService) {

    @GetMapping("/all")
    fun allDungeons() = dungeonService.activeDungeons()

    @PostMapping("/create")
    fun createDungeon(@RequestBody dungeonDto: DungeonDto): ResponseEntity<Long> {
        val newDungeonId = dungeonService.generateAKillRoom(dungeonDto.roomIds)
        return if (newDungeonId == null) {
            ResponseEntity.badRequest().build()
        } else ResponseEntity.ok(newDungeonId)
    }
}

class DungeonDto(val roomIds: List<Long>)

@RestController
@RequestMapping("/api/v1/dragon")
class DragonController(private val dragonService: DragonService) {

    @GetMapping("/all")
    fun allDragons() = dragonService.getAllTyrants()

    @GetMapping("/")
    fun getDragon() = dragonService.getABigOne()
}
