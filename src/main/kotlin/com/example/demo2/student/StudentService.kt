package com.example.demo2.student

import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import kotlin.collections.ArrayList

@Service
class StudentService(val repository: StudentRepository) {

    fun getStudents(): List<Student> {
        val students: MutableList<Student> = ArrayList()
        repository.findAll().forEach { student -> students.add(student) }
        return students
    }

    fun getStudent(studentId: Int): Optional<Student> {
        val student = repository.findById(studentId)
        if(!student.isPresent) {
            throw StudentNotFoundException("student with id $studentId does not exist")
        }
        return student
    }

    fun addNewStudent(student: Student): Student {
        val studentOptional: Optional<Student> = repository.findStudentByEmail(student.email!!)
        if(studentOptional.isPresent) { throw IllegalArgumentException("email taken")  }
        return repository.save(student)
    }

    fun deleteStudent(studentId: Int) {
        val student = repository.findById(studentId)
        if(!student.isPresent) {
            throw StudentNotFoundException("student with id $studentId does not exist")
        }
        repository.deleteById(studentId)
    }

    @Transactional
    fun updateStudent(studentId: Int, updatedStudent: Student): Student {
        val student : Student = repository.findById(studentId).orElseThrow {
            StudentNotFoundException(
                "student with id $studentId does not exist"
            )
        }

        if (!Objects.equals(student.name, updatedStudent.name)) {
            student.name = updatedStudent.name
        }

        if (!Objects.equals(student.email, updatedStudent.email)
        ) {
            val studentOptional: Optional<Student> = repository.findStudentByEmail(updatedStudent.email!!)
            if(studentOptional.isPresent) { throw IllegalArgumentException("email taken")  }
            student.email = updatedStudent.email
        }

        if (!Objects.equals(student.age, updatedStudent.age)) {
            student.age = updatedStudent.age
        }

        return student
    }
}