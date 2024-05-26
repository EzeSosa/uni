package com.esosa.uni.verification.responses

import java.time.LocalDateTime

data class ConfirmationTokenResponse(
    val token: String,
    val expiredAt: LocalDateTime
)