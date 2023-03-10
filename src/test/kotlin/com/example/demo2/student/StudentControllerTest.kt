package com.example.demo2.student

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class StudentControllerTest @Autowired constructor (
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
)
{
    val baseUrl = "/api/students"

    @Test
    fun `should return all students`() {
        mockMvc.get(baseUrl)
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                jsonPath("$[0].name") { value("Lee")}
            }
    }

    @Test
    fun `should return a student`() {
        val studentId = 1
        mockMvc.get("$baseUrl/$studentId")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }

                jsonPath("$.name") { value("Lee")}
            }
    }

    @Test
    fun `should return NOT FOUND if a student does not exist`() {
        val studentId = 3
        mockMvc.get("$baseUrl/$studentId")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.message") { value("student with id $studentId does not exist")}
            }
    }

    @Test
    fun `should add new student`() {
        val newStudent = Student( name = "Jack", email = "jack@gmail.com", age = 22 )

        val performPost = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newStudent)
        }

        performPost.andDo { print() }
            .andExpect { status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.name") {value(newStudent.name)}
            }
    }

    @Test
    fun `add new student with empty name`() {
        val newStudent = Student( name = "", email = "jack@gmail.com", age = 22 )

        val performPost = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newStudent)
        }

        performPost.andDo { print() }
            .andExpect { status { isBadRequest() }
            }
    }

    @Test
    fun `add new student with empty email`() {
        val newStudent = Student( name = "Jack", email = "", age = 22 )

        val performPost = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newStudent)
        }

        performPost.andDo { print() }
            .andExpect { status { isBadRequest() }
            }
    }

    @Test
    fun `add new student with null age`() {
        val newStudent = Student( name = "Jack", email = "jack@gmail.com", age = null )

        val performPost = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newStudent)
        }

        performPost.andDo { print() }
            .andExpect { status { isBadRequest() }
            }
    }

    @Test
    fun `add new student with age = 0`() {
        val newStudent = Student( name = "Jack", email = "jack@gmail.com", age = 0 )

        val performPost = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newStudent)
        }

        performPost.andDo { print() }
            .andExpect { status { isBadRequest() }
            }
    }

    @Test
    fun `update student with empty name`() {
        val updatedStudentId = 2
        val updatedStudent = Student( name = "", email = "leo@gmail.com", age = 22 )

        val performPut = mockMvc.put("$baseUrl/$updatedStudentId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedStudent)
        }

        performPut.andDo { print() }
            .andExpect { status { isBadRequest() }
            }
    }

    @Test
    fun `update student with empty email`() {
        val updatedStudentId = 2
        val updatedStudent = Student( name = "Leo", email = "", age = 22 )

        val performPut = mockMvc.put("$baseUrl/$updatedStudentId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedStudent)
        }

        performPut.andDo { print() }
            .andExpect { status { isBadRequest() }
            }
    }

    @Test
    fun `update student with null age`() {
        val updatedStudentId = 2
        val updatedStudent = Student( name = "Leo", email = "leo@gmail.com", age = null )

        val performPut = mockMvc.put("$baseUrl/$updatedStudentId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedStudent)
        }

        performPut.andDo { print() }
            .andExpect { status { isBadRequest() }
            }
    }

    @Test
    fun `update student with age = 0`() {
        val updatedStudentId = 2
        val updatedStudent = Student( name = "Leo", email = "leo@gmail.com", age = 0 )

        val performPut = mockMvc.put("$baseUrl/$updatedStudentId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedStudent)
        }

        performPut.andDo { print() }
            .andExpect { status { isBadRequest() }
            }
    }

    @Test
    fun `should update a student`() {
        val updatedStudentId = 2
        val updatedStudent = Student(name = "Leo", email = "leo@gmail.com", age = 20)

        val performPut = mockMvc.put("$baseUrl/$updatedStudentId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedStudent)
        }
        performPut.andDo { print() }
            .andExpect { status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") { value(updatedStudentId)}
                jsonPath("$.name") { value(updatedStudent.name)}
                jsonPath("$.email") { value(updatedStudent.email)}
                jsonPath("$.age") { value(updatedStudent.age)}
            }
    }

    @Test
    fun `update with studentId not exist`() {
        val updatedStudentId = 5
        val updatedStudent = Student(name = "Leo", email = "leo@gmail.com", age = 20)
        val performPut = mockMvc.put("$baseUrl/$updatedStudentId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedStudent)
        }
        performPut.andDo { print() }
            .andExpect { status { isNotFound() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.message") { value("student with id $updatedStudentId does not exist")}
            }
    }

    @Test
    fun `update with email exist`() {
        val updatedStudentId = 1
        val updatedStudent = Student(name = "Mike2", email = "mike@gmail.com", age = 20)
        val performPut = mockMvc.put("$baseUrl/$updatedStudentId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedStudent)
        }
        performPut.andDo { print() }
            .andExpect { status { isBadRequest() } }
    }
    @Test
    fun `delete a student studentId not exist`() {
        val deletedStudentId = 6
        mockMvc.delete("$baseUrl/$deletedStudentId")
            .andDo { print() }
            .andExpect { status { isNotFound() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.message") { value("student with id $deletedStudentId does not exist")}
            }
    }

    @Test
    fun `should delete a student`() {
        val deletedStudentId = 1
        mockMvc.delete("$baseUrl/$deletedStudentId")
            .andDo { print() }
            .andExpect { status { isOk() }
            }

        mockMvc.get("$baseUrl/$deletedStudentId")
            .andDo { print() }
            .andExpect {
                status { isNotFound() } }
    }
}