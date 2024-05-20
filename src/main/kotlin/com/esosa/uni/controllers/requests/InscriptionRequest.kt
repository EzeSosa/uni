package com.esosa.uni.controllers.requests

import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.util.UUID

data class InscriptionRequest(
    @field:NotNull(message = "Course id must not be null")
    val courseId: UUID,
    @field:NotNull(message = "User id must not be null")
    val userId: UUID,
    val date: LocalDate = LocalDate.now()
)