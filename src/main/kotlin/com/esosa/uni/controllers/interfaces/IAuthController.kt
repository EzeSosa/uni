package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.LoginRequest
import com.esosa.uni.controllers.requests.RefreshTokenRequest
import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.controllers.responses.AuthResponse
import com.esosa.uni.controllers.responses.RefreshTokenResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/auth")
interface IAuthController {
    @PostMapping("/register")
    @ResponseStatus(CREATED)
    fun register(@RequestBody @Valid registerRequest: RegisterRequest)

    @PostMapping("/login")
    @ResponseStatus(CREATED)
    fun login(@RequestBody @Valid loginRequest: LoginRequest): AuthResponse

    @PostMapping("/refresh")
    @ResponseStatus(CREATED)
    fun refresh(@RequestBody @Valid refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse?

    @PostMapping("/enable")
    @ResponseStatus(CREATED)
    fun enableUser(@RequestParam token: String)
}