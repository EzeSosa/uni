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
class ConfirmationService (
    private val confirmationTokenRepository: IConfirmationTokenRepository,
    private val userService: IUserService,
    private val emailService: EmailService
): IConfirmationService {

    override fun saveConfirmationToken(token: ConfirmationToken): ConfirmationToken =
        confirmationTokenRepository.save(token)

    override fun getToken(token: String) =
        confirmationTokenRepository.findByToken(token)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Confirmation token does not exists")

    override fun generateConfirmationToken(user: User) =
        ConfirmationToken(
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
        ).also { confirmationToken ->
            saveConfirmationToken(confirmationToken)
            confirmationToken.sendConfirmationTokenEmail()
        }

    override fun enableUserFromToken(token: String): ConfirmationToken =
        saveConfirmationToken(
            getToken(token)
                .validateToken()
                .apply { confirmedAt = LocalDateTime.now() }
        ).also { confirmationToken -> userService.enableUser(confirmationToken.user) }

    override fun resendConfirmationToken(username: String) {
        userService.findUserByUsernameOrThrowException(username).also { user ->
            if (user.enabled)
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token already confirmed")
            expireActiveConfirmationTokenFromUser(user)
            generateConfirmationToken(user)
        }
    }

    private fun expireActiveConfirmationTokenFromUser(user: User) {
        confirmationTokenRepository.findNotConfirmedByUser(user)
            ?.let { confirmationToken ->
                confirmationTokenRepository.save(
                    confirmationToken.apply { revoked = true }
                )
            }
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no unconfirmed confirmation tokens")
    }

    private fun ConfirmationToken.sendConfirmationTokenEmail() =
        emailService.sendEmail(
            user.email,
            "Confirm User",
            "To enable your user, access to the following link: http://localhost:8080/confirm?token=$token"
        )

    private fun ConfirmationToken.validateToken(): ConfirmationToken {
        if (confirmedAt != null)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token already confirmed")

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token expired")

        if (revoked)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token revoked")

        return this
    }
}