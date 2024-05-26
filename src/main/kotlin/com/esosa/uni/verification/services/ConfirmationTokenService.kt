package com.esosa.uni.verification.services

import com.esosa.uni.verification.repositories.IConfirmationTokenRepository
import com.esosa.uni.verification.token.ConfirmationToken
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ConfirmationTokenService (private val confirmationTokenRepository: IConfirmationTokenRepository) {

    fun saveConfirmationToken(token: ConfirmationToken): ConfirmationToken =
        confirmationTokenRepository.save(token)

    fun getToken(token: String): ConfirmationToken =
        confirmationTokenRepository.findByToken(token)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Confirmation token does not exists")
}