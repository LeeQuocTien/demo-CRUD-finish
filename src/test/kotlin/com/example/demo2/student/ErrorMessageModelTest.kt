package com.example.demo2.student

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.http.HttpStatus

internal class ErrorMessageModelTest {

    @Test
    fun getStatus() {
        val errorMessage = ErrorMessageModel()
        errorMessage.status = 404
        errorMessage.error = HttpStatus.NOT_FOUND
        errorMessage.message = "student with id 3 does not exist"

        assertEquals(404, errorMessage.status)
        assertEquals(HttpStatus.NOT_FOUND, errorMessage.error)
        assertEquals("student with id 3 does not exist", errorMessage.message)
    }
}