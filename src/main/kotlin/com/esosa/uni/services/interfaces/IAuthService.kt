package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.RegisterRequest
import java.util.UUID

interface IAuthService {
    fun register(registerRequest: RegisterRequest) : UUID
}