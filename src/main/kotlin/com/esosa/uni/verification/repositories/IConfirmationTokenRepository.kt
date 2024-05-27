package com.esosa.uni.verification.repositories

import com.esosa.uni.verification.token.ConfirmationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IConfirmationTokenRepository : JpaRepository<ConfirmationToken, UUID> {
    fun findByToken(token: String): ConfirmationToken?
}