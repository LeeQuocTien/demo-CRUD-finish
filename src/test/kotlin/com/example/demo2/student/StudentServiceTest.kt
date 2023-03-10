package com.example.demo2.student

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

internal class StudentServiceTest {

    private val mockStudentRepository = mock(StudentRepository::class.java)
    private val mockStudentService = StudentService(mockStudentRepository)

    @BeforeEach
    fun setUp() {
        val student1 = Student()
        student1.id = 1
        student1.name = "Jerry"
        student1.age = 22
        student1.email = "jerry@gmail.com"

        val student2 = Student()
        student2.id = 2
        student2.name = "Tom"
        student2.email = "tom@gmail.com"
        student2.age = 22

        val students: MutableList<Student> = ArrayList<Student>()
        students.add(student1)
        students.add(student2)

        val newStudent = Student()
        newStudent.name = "Jack"
        newStudent.email = "jack@gmail.com"
        newStudent.age = 20

        val newStudentResponse = Student()
        newStudentResponse.id = 3
        newStudentResponse.name = "Jack"
        newStudentResponse.email = "jack@gmail.com"
        newStudentResponse.age = 20

        val updatedStudent = Student()
        updatedStudent.id = 1
        updatedStudent.name = "Tom3"
        updatedStudent.email = "tom@gmail.com"
        updatedStudent.age = 20

        `when`(mockStudentRepository.findById(student1.id!!)).thenReturn(Optional.of(student1))
        `when`(mockStudentRepository.findAll()).thenReturn(students)
        `when`(mockStudentRepository.findStudentByEmail(updatedStudent.email!!)).thenReturn(Optional.of(student2))
        `when`(mockStudentRepository.save(newStudent)).thenReturn(newStudentResponse)
    }

    @Test
    fun getStudents() {
        val students: List<Student> = mockStudentService.getStudents()
        assertEquals(2, students.size)
    }

    @Test
    fun getStudent() {
        val studentId = 1
        val student: Optional<Student> = mockStudentService.getStudent(studentId)
        assertEquals(studentId, student.get().id)
    }

    @Test
    fun addNewStudent() {
        val newStudent = Student(name = "Jack", email = "jack@gmail.com", age = 20 )
        val student = mockStudentService.addNewStudent(newStudent)


        assertEquals(student.name, newStudent.name)
        assertEquals(student.email, newStudent.email)
        assertEquals(student.age, newStudent.age)
        verify(mockStudentRepository, times(1)).save(newStudent)
    }

    @Test
    fun `addNewStudent() with email is taken`() {
        val student = Student()
        student.name = "Tom"
        student.email = "tom@gmail.com"
        student.age = 22

        val emailExist : Optional<Student> = mockStudentRepository.findStudentByEmail(student.email!!)
        assertTrue(emailExist.isPresent)

        val thrown = assertThrows(IllegalArgumentException::class.java)
        {mockStudentService.addNewStudent(student)}

        assertEquals("email taken", thrown.message)
    }

    @Test
    fun deleteStudent() {
        val studentId = 1

        mockStudentService.deleteStudent(studentId)
        verify(mockStudentRepository, times(1)).deleteById(studentId)
    }

    @Test
    fun updateStudent() {
        val studentId = 1
        val student = Student()
        student.name = "Tom3"
        student.email = "tom3@gmail.com"
        student.age = 18

        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
        assertTrue(willUpdateStudent.isPresent)

        val emailExist : Optional<Student> = mockStudentRepository.findStudentByEmail(student.email!!)
        assertFalse(emailExist.isPresent)

        val updatedStudent : Student = mockStudentService.updateStudent(studentId, student)

        assertEquals(studentId, updatedStudent.id)
        assertEquals(student.name, updatedStudent.name)
        assertEquals(student.email, updatedStudent.email)
        assertEquals(student.age, updatedStudent.age)
    }

    @Test
    fun `updateStudent() with studentId not exist`() {
        val studentId = 4
        val student = Student()
        student.name = "Tom3"
        student.email = "tom3@gmail.com"
        student.age = 18

        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
        assertFalse(willUpdateStudent.isPresent)

        val thrown = assertThrows(StudentNotFoundException::class.java)
        {mockStudentService.updateStudent(studentId, student) }

        assertEquals("student with id $studentId does not exist", thrown.message)
    }

//    @Test
//    fun `updateStudent() with input name is empty`(){
//        val studentId = 1
//        val student = Student()
//        student.name = ""
//        student.email = "tom3@gmail.com"
//        student.age = 18
//
//        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
//        assertTrue(willUpdateStudent.isPresent)
//
//        val emailExist : Optional<Student> = mockStudentRepository.findStudentByEmail(student.email!!)
//        assertFalse(emailExist.isPresent)
//
//        val updatedStudent : Student = mockStudentService.updateStudent(studentId, student.name, student.email, student.age)
//
//        assertEquals(studentId, updatedStudent.id)
//        assertNotEquals(student.name, updatedStudent.name)
//        assertEquals(student.email, updatedStudent.email)
//        assertEquals(student.age, updatedStudent.age)
//    }

    @Test
    fun `updateStudent() with the same input name`(){
        val studentId = 1
        val student = Student()
        student.name = "Jerry"
        student.email = "tom3@gmail.com"
        student.age = 18

        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
        assertTrue(willUpdateStudent.isPresent)

        assertEquals(willUpdateStudent.get().name, student.name)

        val emailExist : Optional<Student> = mockStudentRepository.findStudentByEmail(student.email!!)
        assertFalse(emailExist.isPresent)

        val updatedStudent : Student = mockStudentService.updateStudent(studentId, student)

        assertEquals(studentId, updatedStudent.id)
        assertEquals(student.name, updatedStudent.name)
        assertEquals(student.email, updatedStudent.email)
        assertEquals(student.age, updatedStudent.age)
    }

//    @Test
//    fun `updateStudent() with input email is empty`(){
//        val studentId = 1
//        val student = Student()
//        student.name = "Tom3"
//        student.email = ""
//        student.age = 18
//
//        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
//        assertTrue(willUpdateStudent.isPresent)
//
//        val updatedStudent : Student = mockStudentService.updateStudent(studentId, student.name, student.email, student.age)
//        verify(mockStudentRepository, times(0)).findStudentByEmail(student.email!!)
//
//        assertEquals(studentId, updatedStudent.id)
//        assertEquals(student.name, updatedStudent.name)
//        assertNotEquals(student.email, updatedStudent.email)
//        assertEquals(student.age, updatedStudent.age)
//    }

    @Test
    fun `updateStudent() with the same input email`(){
        val studentId = 1
        val student = Student()
        student.name = "Tom3"
        student.email = "jerry@gmail.com"
        student.age = 18

        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
        assertTrue(willUpdateStudent.isPresent)

        assertEquals(willUpdateStudent.get().email, student.email)

        val updatedStudent : Student = mockStudentService.updateStudent(studentId, student)
        verify(mockStudentRepository, times(0)).findStudentByEmail(student.email!!)

        assertEquals(studentId, updatedStudent.id)
        assertEquals(student.name, updatedStudent.name)
        assertEquals(student.email, updatedStudent.email)
        assertEquals(student.age, updatedStudent.age)
    }

    @Test
    fun `updateStudent() with email is taken`() {
        val studentId = 1
        val student = Student()
        student.name = "Tom3"
        student.email = "tom@gmail.com"
        student.age = 18

        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
        assertTrue(willUpdateStudent.isPresent)

        val emailExist : Optional<Student> = mockStudentRepository.findStudentByEmail(student.email!!)
        assertTrue(emailExist.isPresent)

        val thrown = assertThrows(IllegalArgumentException::class.java)
        {mockStudentService.updateStudent(studentId, student) }

        assertEquals("email taken", thrown.message)
    }

//    @Test
//    fun `updateStudent() with input age is 0`(){
//        val studentId = 1
//        val student = Student()
//        student.name = "Tom3"
//        student.email = "tom3@gmail.com"
//        student.age = 0
//
//        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
//        assertTrue(willUpdateStudent.isPresent)
//
//        val emailExist : Optional<Student> = mockStudentRepository.findStudentByEmail(student.email!!)
//        assertFalse(emailExist.isPresent)
//
//        val updatedStudent : Student = mockStudentService.updateStudent(studentId, student.name!!, student.email!!, student.age!!
//        )
//
//        assertEquals(studentId, updatedStudent.id)
//        assertEquals(student.name, updatedStudent.name)
//        assertEquals(student.email, updatedStudent.email)
//        assertNotEquals(student.age, updatedStudent.age)
//    }

    @Test
    fun `updateStudent() with the same input age`(){
        val studentId = 1
        val student = Student()
        student.name = "Tom3"
        student.email = "tom3@gmail.com"
        student.age = 22

        val willUpdateStudent: Optional<Student> = mockStudentRepository.findById(studentId)
        assertTrue(willUpdateStudent.isPresent)

        assertEquals(willUpdateStudent.get().age, student.age)

        val emailExist : Optional<Student> = mockStudentRepository.findStudentByEmail(student.email!!)
        assertFalse(emailExist.isPresent)

        val updatedStudent : Student = mockStudentService.updateStudent(studentId, student)

        assertEquals(studentId, updatedStudent.id)
        assertEquals(student.name, updatedStudent.name)
        assertEquals(student.email, updatedStudent.email)
        assertEquals(student.age, updatedStudent.age)
    }
}
