package com.esosa.uni.controllers.responses

import java.time.LocalDate

data class ExamResponse(
    val date: LocalDate,
    val grade: Double,
    val courseName: String
)