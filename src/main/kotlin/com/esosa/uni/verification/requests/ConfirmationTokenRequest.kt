package com.esosa.uni.verification.requests

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.UUID

data class ConfirmationTokenRequest(
    @field:NotBlank(message = "Token must not be blank")
    @field:UUID(message = "Token must be UUID type")
    val token: String
)