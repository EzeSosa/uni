package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.ILogoutController
import com.esosa.uni.services.interfaces.ITokenService
import org.springframework.web.bind.annotation.RestController

@RestController
class LogoutController(private val tokenService: ITokenService): ILogoutController {
    override fun logout(token: String) =
        tokenService.revokeToken(token)
}