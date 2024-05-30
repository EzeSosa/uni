package com.esosa.uni.services.interfaces

import com.esosa.uni.data.models.User
import com.esosa.uni.data.models.Confirmation

interface IConfirmationService {
    fun saveConfirmation(confirmation: Confirmation)
    fun getConfirmation(token: String): Confirmation
    fun generateConfirmation(user: User): Confirmation
    fun enableUserFromConfirmation(token: String)
    fun resendConfirmationToUser(username: String)
}