package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IAuthController
import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.services.interfaces.IAuthService
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class AuthController(private val authService: IAuthService) : IAuthController {
    override fun register(registerRequest: RegisterRequest) : UUID =
        authService.register(registerRequest)
}