package com.esosa.uni.services.implementations

import com.esosa.uni.data.models.User
import com.esosa.uni.email.EmailService
import com.esosa.uni.services.interfaces.IUserService
import com.esosa.uni.data.repositories.ConfirmationRepository
import com.esosa.uni.data.models.Confirmation
import com.esosa.uni.services.interfaces.IConfirmationService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.UUID

@Service
class ConfirmationService (
    private val confirmationRepository: ConfirmationRepository,
    private val userService: IUserService,
    private val emailService: EmailService
): IConfirmationService {

    override fun saveConfirmation(confirmation: Confirmation) {
        confirmationRepository.addConfirmationByToken(confirmation.token, confirmation)
    }

    override fun getConfirmation(token: String) =
        confirmationRepository.findConfirmationByToken(token)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Confirmation token does not exists")

    override fun generateConfirmation(user: User) =
        Confirmation(
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
        ).also { confirmationToken ->
            saveConfirmation(confirmationToken)
            confirmationToken.sendConfirmationTokenEmail()
        }

    override fun enableUserFromConfirmation(token: String) =
        with(getConfirmation(token)) {
            saveConfirmation(
                this.validateToken()
                    .apply { confirmedAt = LocalDateTime.now() }
            ).also { userService.enableUser(this.user) }
        }

    override fun resendConfirmationToUser(username: String) {
        userService.findUserByUsernameOrThrowException(username).also { user ->
            if (user.enabled)
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token already confirmed")
            expireActiveConfirmationTokenFromUser(user)
            generateConfirmation(user)
        }
    }

    private fun expireActiveConfirmationTokenFromUser(user: User) {
        confirmationRepository.findNotConfirmedByUser(user)
            ?.let { confirmationToken ->
                saveConfirmation(
                    confirmationToken.apply { revoked = true }
                )
            }
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no unconfirmed confirmation tokens")
    }

    private fun Confirmation.sendConfirmationTokenEmail() =
        emailService.sendEmail(
            user.email,
            "Confirm User",
            "To enable your user, access to the following link: http://localhost:8080/confirm?token=$token"
        )

    private fun Confirmation.validateToken(): Confirmation {
        if (confirmedAt != null)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token already confirmed")

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token expired")

        if (revoked)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token revoked")

        return this
    }
}