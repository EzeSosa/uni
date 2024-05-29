package com.esosa.uni.verification.repositories

import com.esosa.uni.data.models.User
import com.esosa.uni.verification.token.Confirmation
import org.springframework.stereotype.Repository

@Repository
class ConfirmationRepository {
    private val confirmationByToken = mutableMapOf<String, Confirmation>()

    fun findConfirmationByToken(token: String): Confirmation? =
        confirmationByToken[token]

    fun addConfirmationByToken(token: String, confirmation: Confirmation) {
        confirmationByToken[token] = confirmation
    }

    fun findNotConfirmedByUser(user: User): Confirmation? {
        return confirmationByToken.values
            .find { confirmation ->
                confirmation.user.username == user.username && confirmation.confirmedAt == null
            }
    }
}