package com.esosa.uni.exceptions

import java.time.LocalDateTime

data class ExceptionResponse(
    val message: String? = null,
    val status: Int? = null,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)