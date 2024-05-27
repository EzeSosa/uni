package com.esosa.uni.verification.services

import com.esosa.uni.data.models.User
import com.esosa.uni.email.EmailService
import com.esosa.uni.services.interfaces.IUserService
import com.esosa.uni.verification.repositories.IConfirmationTokenRepository
import com.esosa.uni.verification.token.ConfirmationToken
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.UUID

@Service
class ConfirmationTokenService (
    private val confirmationTokenRepository: IConfirmationTokenRepository,
    private val userService: IUserService,
    private val emailService: EmailService
) {

    fun saveConfirmationToken(token: ConfirmationToken): ConfirmationToken =
        confirmationTokenRepository.save(token)

    fun getToken(token: String): ConfirmationToken =
        confirmationTokenRepository.findByToken(token)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Confirmation token does not exists")

    fun generateConfirmationToken(user: User) =
        ConfirmationToken(
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
        ).also { confirmationToken ->
            saveConfirmationToken(confirmationToken)
            confirmationToken.sendConfirmationTokenEmail()
        }

    fun enableUserFromToken(token: String): ConfirmationToken =
        saveConfirmationToken(
            getToken(token)
                .validateToken()
                .apply { confirmedAt = LocalDateTime.now() }
        ).also { confirmationToken -> userService.enableUser(confirmationToken.user) }

    private fun ConfirmationToken.sendConfirmationTokenEmail() =
        emailService.sendEmail(
            user.email,
            "Confirm User",
            "To enable your user, access to the following link: http://localhost:8080/auth/enable?token=$token"
        )

    private fun ConfirmationToken.validateToken(): ConfirmationToken {
        if (confirmedAt != null)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token already confirmed")

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token expired")

        return this
    }
}