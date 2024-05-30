package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IConfirmationController
import com.esosa.uni.services.interfaces.IConfirmationService
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfirmationController(
    private val confirmationService: IConfirmationService
) : IConfirmationController {

    override fun confirm(token: String) {
        confirmationService.enableUserFromConfirmation(token)
    }

    override fun resend(username: String) =
        confirmationService.resendConfirmationToUser(username)
}