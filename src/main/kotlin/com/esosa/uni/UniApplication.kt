package com.esosa.uni

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UniApplication

fun main(args: Array<String>) {
	runApplication<UniApplication>(*args)
}