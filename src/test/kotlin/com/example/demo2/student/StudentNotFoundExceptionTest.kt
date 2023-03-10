package com.example.demo2.student

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StudentNotFoundExceptionTest {

    @Test
    fun `inherit test`() {
        val exception1 = StudentNotFoundException("student with id 3 does not exist")
        val exception2 = RuntimeException("student with id 3 does not exist")

        assertEquals(exception1.message, exception2.message)
    }
}