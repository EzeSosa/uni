package com.esosa.uni.controllers.requests

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CourseRequest(
    @field:NotBlank(message = "Name must not be blank")
    @field:Size(max = 30, message = "Field name size must be less than 20 characters")
    val name: String,
    @field:Min(value = 2024, message = "Year must be bigger than 2024")
    val year: Int
)