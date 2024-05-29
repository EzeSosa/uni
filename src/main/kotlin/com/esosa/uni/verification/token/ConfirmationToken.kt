package com.esosa.uni.verification.token

import com.esosa.uni.data.models.User
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class ConfirmationToken(
    val token: String,
    val createdAt: LocalDateTime,
    var expiredAt: LocalDateTime,

    @ManyToOne
    val user: User,

    var confirmedAt: LocalDateTime? = null,
    var revoked: Boolean = false,

    @Id
    val id: UUID = UUID.randomUUID()
)