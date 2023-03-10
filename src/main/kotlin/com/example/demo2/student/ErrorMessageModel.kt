package com.example.demo2.student

import org.springframework.http.HttpStatus

data class ErrorMessageModel(
    var status: Int? = null,
    var error: HttpStatus? = null,
    var message: String? = null
)