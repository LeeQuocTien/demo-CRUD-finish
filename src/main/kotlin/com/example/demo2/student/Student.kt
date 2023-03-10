package com.example.demo2.student

import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table
data class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @field:NotBlank(message = "Name cannot empty")
    var name: String? = "",
    @field:NotBlank(message = "Email cannot empty")
    var email: String? = "",
    @field:NotNull(message = "Age cannot null")
    @field:Min(value = 1, message = "Age must be greater than 0")
    var age: Int? = null
)