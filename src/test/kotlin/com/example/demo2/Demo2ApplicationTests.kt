package com.example.demo2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class Demo2ApplicationTests {

	@Test
	fun main() {
		assertDoesNotThrow {
			main(listOf("tien").toTypedArray())
		}
	}
}
