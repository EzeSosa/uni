package com.esosa.uni.verification.token

import com.esosa.uni.data.models.User
import java.time.LocalDateTime

data class Confirmation(
    val token: String,
    val createdAt: LocalDateTime,
    var expiredAt: LocalDateTime,
    val user: User,
    var confirmedAt: LocalDateTime? = null,
    var revoked: Boolean = false,
)