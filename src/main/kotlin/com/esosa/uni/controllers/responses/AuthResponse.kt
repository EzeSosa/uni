package com.esosa.uni.controllers.responses

import java.util.UUID

data class AuthResponse(
    val userId: UUID,
    val accessToken: String
)