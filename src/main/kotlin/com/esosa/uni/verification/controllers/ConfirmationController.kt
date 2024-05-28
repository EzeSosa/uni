package com.esosa.uni.verification.controllers

import com.esosa.uni.verification.services.IConfirmationService
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfirmationController(
    private val confirmationService: IConfirmationService
) : IConfirmationController {

    override fun confirm(token: String): Unit {
        confirmationService.enableUserFromToken(token)
    }
}