package com.esosa.uni.controllers.responses

import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

data class ExamResponse(
    val id: UUID,
    val date: LocalDate,
    val grade: Double,
    val courseName: String
) : Serializable