package be.oml

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import be.oml.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureRouting()
    }.start(wait = true)
}
