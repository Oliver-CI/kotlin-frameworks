package be.oml

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.*
import javax.ws.rs.core.Response

@QuarkusTest
class PlayerResourceTest {

//    @Test
//    fun testAllPlayersEndpoint() {
//        given()
//            .`when`().get("/player")
//            .then()
//            .statusCode(Response.Status.OK.statusCode)
//            .body(hasSize<String>(greaterThan(0)))
//    }
//
//    @Test
//    fun testPlayerByNameEndpoint() {
//        given()
//            .`when`().get("/player/Hanna")
//            .then()
//            .statusCode(Response.Status.OK.statusCode)
//            .body(`is`(Player("Hanna")))
//    }

}
