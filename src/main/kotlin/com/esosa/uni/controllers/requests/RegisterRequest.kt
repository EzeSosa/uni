package com.esosa.uni.controllers.requests

import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank(message = "Username must not be empty")
    val username: String,
    @field:NotBlank(message = "Password must not be empty")
    val password: String,
    @field:NotBlank(message = "Name must not be empty")
    val name: String
)