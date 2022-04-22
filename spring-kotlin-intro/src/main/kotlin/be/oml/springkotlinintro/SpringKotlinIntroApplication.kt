package be.oml.springkotlinintro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinIntroApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinIntroApplication>(*args)
}
