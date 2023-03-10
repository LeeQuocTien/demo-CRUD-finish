//package com.example.demo2.student
//
//import org.junit.jupiter.api.Test
//
//import org.junit.jupiter.api.Assertions.*
//import org.mockito.Mockito
//import org.mockito.Mockito.times
//import org.mockito.Mockito.verify
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//
//@SpringBootTest
//internal class StudentConfigTest {
//
//    @Test
//    fun run() {
//        var repository = Mockito.mock(StudentRepository::class.java)
//        var studentConfig = StudentConfig(repository)
//        studentConfig.run()
//
//        verify(repository, times(1)).save(Student(
//            name = "Jerry",
//            email = "jerry@gmail.com",
//            age = 22
//        ))
//        verify(repository, times(1)).save(Student(
//            3,
//            "Tom",
//            "tom@gmail.com",
//            22
//        ))
//    }
//}