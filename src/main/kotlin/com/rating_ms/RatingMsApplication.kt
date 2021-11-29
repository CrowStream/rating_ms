package com.rating_ms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class RatingMsApplication

@RestController
class GreetingsController {
	@GetMapping("/")
	fun greetings() = "hello rating_ms from a Docker"
}

fun main(args: Array<String>) {
	runApplication<RatingMsApplication>(*args)
}
