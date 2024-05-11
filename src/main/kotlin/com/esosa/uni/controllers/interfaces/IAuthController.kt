package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.LoginRequest
import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.controllers.responses.AuthResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/auth")
interface IAuthController {
    @PostMapping("/register")
    @ResponseStatus(CREATED)
    fun register(@RequestBody @Valid registerRequest: RegisterRequest)

    @PostMapping("/login")
    @ResponseStatus(CREATED)
    fun login(@RequestBody @Valid loginRequest: LoginRequest) : AuthResponse
}