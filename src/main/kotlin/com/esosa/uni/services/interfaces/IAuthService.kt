package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.LoginRequest
import com.esosa.uni.controllers.requests.RefreshTokenRequest
import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.controllers.responses.AuthResponse
import com.esosa.uni.controllers.responses.RefreshTokenResponse

interface IAuthService {
    fun register(registerRequest: RegisterRequest)
    fun login(loginRequest: LoginRequest): AuthResponse
    fun refresh(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse?
}