package com.esosa.uni.controllers.requests

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank(message = "Refresh token must not be empty")
    val refreshToken: String
)