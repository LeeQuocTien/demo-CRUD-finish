package com.example.demo2.student

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StudentTest {

    @Test
    fun `test default Student object`() {
        val defaultStudent = Student()
        defaultStudent.id = 1
        defaultStudent.name = ""
        defaultStudent.email = ""
        defaultStudent.age = null

        assertEquals(1, defaultStudent.id)
        assertEquals("", defaultStudent.name)
        assertEquals("", defaultStudent.email)
        assertEquals(null, defaultStudent.age)
    }

    @Test
    fun `should return a Student object`() {
        val student = Student()
        student.id = 1
        student.name = "Jack"
        student.email = "jack@gmail.com"
        student.age = 20

        assertEquals(1, student.id)
        assertEquals("Jack", student.name)
        assertEquals("jack@gmail.com", student.email)
        assertEquals(20, student.age)
    }


}