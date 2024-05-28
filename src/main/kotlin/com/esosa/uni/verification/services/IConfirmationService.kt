package com.esosa.uni.verification.services

import com.esosa.uni.data.models.User
import com.esosa.uni.verification.token.ConfirmationToken

interface IConfirmationService {
    fun saveConfirmationToken(token: ConfirmationToken): ConfirmationToken
    fun getToken(token: String): ConfirmationToken
    fun generateConfirmationToken(user: User): ConfirmationToken
    fun enableUserFromToken(token: String): ConfirmationToken
    fun resendConfirmationToken(username: String)
}