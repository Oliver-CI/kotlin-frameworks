package be.oml.routes

import be.oml.domain.GameDto
import be.oml.service.gameRepo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.gameRouting() {
    route("/game") {
        get {
            val allGames = gameRepo.allGames()
            if (allGames.isNotEmpty()) {
                call.respond(allGames)
            } else {
                call.respondText("No open games found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val game = gameRepo.game(id.toLong()) ?: return@get call.respondText(
                "No game with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(game)
        }
        post {
            val game = call.receive<GameDto>()
            gameRepo.addNewGame(game)
            call.respondText("Game stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (gameRepo.deleteGame(id.toLong())) {
                call.respondText("Game removed correctly", status = HttpStatusCode.NoContent)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}
