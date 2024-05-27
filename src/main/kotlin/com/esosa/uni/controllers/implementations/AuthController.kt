package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IAuthController
import com.esosa.uni.controllers.requests.LoginRequest
import com.esosa.uni.controllers.requests.RefreshTokenRequest
import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.controllers.responses.AuthResponse
import com.esosa.uni.controllers.responses.RefreshTokenResponse
import com.esosa.uni.services.interfaces.IAuthService
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authService: IAuthService) : IAuthController {
    override fun register(registerRequest: RegisterRequest) =
        authService.register(registerRequest)

    override fun login(loginRequest: LoginRequest): AuthResponse =
        authService.login(loginRequest)

    override fun refresh(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse? =
        authService.refresh(refreshTokenRequest)

    override fun enableUser(token: String) =
        authService.enableUser(token)
}