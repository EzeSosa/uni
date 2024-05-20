package com.esosa.uni.controllers.requests

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.util.UUID

data class ExamRequest(
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    @field:Min(value = 0, message = "Exam grade must be bigger or equal to 0")
    @field:Max(value = 10, message = "Exam grade must be less or equal to 10")
    val grade: Double,
    @field:NotNull(message = "Inscription id must not be null")
    val inscriptionId: UUID
)