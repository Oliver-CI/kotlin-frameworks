package be.oml

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/player")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PlayerResource(val playerService: PlayerService) {

    @GET
    fun players(): Response {
        return Response.ok(playerService.getAllPlayers()).build()
    }

    @GET
    @Path("/{name}")
    fun getPlayer(name: String): Response {
        val playerByName = playerService.getPlayerByName(name) ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(playerByName).build()
    }

    @PUT
    @Path("/{name}/character")
    fun changeCharacter(name: String, character: Character): Response {
        val playerByName = playerService.updateCharacter(name, character) ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(playerByName).build()
    }

    @PUT
    @Path("/{name}/food")
    fun changeFoodLikes(name: String, foodLikes: FoodLikes): Response {
        val playerByName = playerService.updateFoodLikes(name, foodLikes) ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(playerByName).build()
    }
}
