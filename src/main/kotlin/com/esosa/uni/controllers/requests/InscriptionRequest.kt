package com.esosa.uni.controllers.requests

import java.time.LocalDate
import java.util.UUID

data class InscriptionRequest(
    val courseId: UUID,
    val userId: UUID,
    val date: LocalDate = LocalDate.now()
)