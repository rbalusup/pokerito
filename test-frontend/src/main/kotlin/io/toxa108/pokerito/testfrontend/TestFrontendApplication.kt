package io.toxa108.pokerito.testfrontend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestFrontendApplication

fun main(args: Array<String>) {
    runApplication<TestFrontendApplication>(*args)
}
