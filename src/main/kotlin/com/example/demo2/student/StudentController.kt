package com.example.demo2.student

import org.apache.catalina.connector.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.validation.Valid


@CrossOrigin( maxAge = 3600)
@RestController
@RequestMapping("/api/students")
@ControllerAdvice
class StudentController(val studentService: StudentService) {

    @ExceptionHandler
    fun handleStudentNotFoundException(ex: StudentNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND,
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @GetMapping
    fun getStudents(): List<Student> {
        return studentService.getStudents()
    }

    @GetMapping("/{studentId}")
    fun getStudent(
        @PathVariable(value = "studentId") studentId: Int
    ): Optional<Student> {
            return studentService.getStudent(studentId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private fun createNewStudent(@Valid @RequestBody student: Student): Student {
        return studentService.addNewStudent(student)
    }

    @DeleteMapping("/{studentId}")
    fun deleteStudent(
        @PathVariable(value = "studentId") studentId: Int
    ) {
        studentService.deleteStudent(studentId)
    }

    @PutMapping("/{studentId}")
    fun updateStudent(
        @PathVariable(value = "studentId") studentId: Int,
        @Valid @RequestBody updatedStudent: Student
    ): Student {
        try {
            return studentService.updateStudent(studentId, updatedStudent)
        } catch (ex: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.localizedMessage)
        }
    }
}