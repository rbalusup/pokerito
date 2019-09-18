package io.toxa108.pokerito

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PokeritoApplication

fun main(args: Array<String>) {
    runApplication<PokeritoApplication>(*args)
}
