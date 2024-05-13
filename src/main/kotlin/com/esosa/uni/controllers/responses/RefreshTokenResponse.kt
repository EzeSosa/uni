package com.esosa.uni.controllers.responses

import java.util.UUID

data class RefreshTokenResponse(
    val userId: UUID,
    val newAccessToken: String
)