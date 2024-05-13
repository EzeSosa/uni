package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.LoginRequest
import com.esosa.uni.controllers.requests.RefreshTokenRequest
import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.controllers.responses.AuthResponse
import com.esosa.uni.controllers.responses.RefreshTokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/auth")
@Tag(
    name = "Authentication",
    description = "Allows a user to register and registered users to authenticate and refresh their tokens."
)
interface IAuthController {
    @PostMapping("/register")
    @ResponseStatus(CREATED)
    @Operation(summary = "Registers a new user")
    fun register(@RequestBody @Valid registerRequest: RegisterRequest)

    @PostMapping("/login")
    @ResponseStatus(CREATED)
    @Operation(summary = "Authenticates a registered user")
    fun login(@RequestBody @Valid loginRequest: LoginRequest) : AuthResponse

    @PostMapping("/refresh")
    @ResponseStatus(CREATED)
    @Operation(summary = "Refreshes a token for a previously authenticated user")
    fun refresh(@RequestBody @Valid refreshTokenRequest: RefreshTokenRequest) : RefreshTokenResponse?
}