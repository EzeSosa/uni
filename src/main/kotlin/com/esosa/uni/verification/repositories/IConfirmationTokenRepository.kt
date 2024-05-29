package com.esosa.uni.verification.repositories

import com.esosa.uni.data.models.User
import com.esosa.uni.verification.token.ConfirmationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface IConfirmationTokenRepository : JpaRepository<ConfirmationToken, UUID> {
    fun findByToken(token: String): ConfirmationToken?

    @Query(
        "SELECT ct FROM ConfirmationToken ct INNER JOIN ct.user u " +
        "WHERE u = ?1 AND ct.confirmedAt is null"
    )
    fun findNotConfirmedByUser(user: User): ConfirmationToken?
}