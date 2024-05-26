package com.esosa.uni.controllers.responses

import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

data class InscriptionResponse(
    val id: UUID,
    val date: LocalDate,
    val courseName: String
) : Serializable