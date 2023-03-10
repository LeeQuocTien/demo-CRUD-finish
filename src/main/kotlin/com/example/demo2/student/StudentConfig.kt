//package com.example.demo2.student
//
//import org.springframework.boot.CommandLineRunner
//import org.springframework.stereotype.Component
//
//@Component
//class StudentConfig(val repository: StudentRepository) : CommandLineRunner {
//
//    override fun run(vararg args: String) {
//        repository.save(Student(
//            name = "Jerry",
//            email = "jerry@gmail.com",
//            age = 22
//        ))
//        repository.save(Student(
//            3,
//            "Tom",
//            "tom@gmail.com",
//            22
//        ))
//    }
//}
