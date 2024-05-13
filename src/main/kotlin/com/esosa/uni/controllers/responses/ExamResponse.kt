package com.esosa.uni.controllers.responses

import java.time.LocalDate
import java.util.*

data class ExamResponse(
    val id: UUID,
    val date: LocalDate,
    val grade: Double,
    val courseName: String
)