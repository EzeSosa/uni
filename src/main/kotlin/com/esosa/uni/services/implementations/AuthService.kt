package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.LoginRequest
import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.controllers.responses.AuthResponse
import com.esosa.uni.data.models.User
import com.esosa.uni.data.repositories.IUserRepository
import com.esosa.uni.security.jwt.JWTProperties
import com.esosa.uni.security.jwt.JWTService
import com.esosa.uni.security.services.CustomUserDetailsService
import com.esosa.uni.services.interfaces.IAuthService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.math.log

@Service
class AuthService(
    private val userDetailsService: CustomUserDetailsService,
    private val authManager: AuthenticationManager,
    private val jwtService: JWTService,
    private val jwtProperties: JWTProperties,
    private val userRepository: IUserRepository,
    private val encoder: PasswordEncoder,
    private val userService: UserService
) : IAuthService {

    override fun register(registerRequest: RegisterRequest): Unit =
        with(registerRequest) {
            validateExistsUsername(username)
            userRepository.save(
                createUser()
            )
        }

    override fun login(loginRequest: LoginRequest): AuthResponse =
        with(loginRequest) {
            authManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

            val user = userDetailsService.loadUserByUsername(username)
            val accessToken = jwtService.generateToken(user, Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration))

            AuthResponse(username.extractId(), accessToken)
        }

    private fun String.extractId(): UUID =
        userService.findUserByUsernameOrThrowException(this).id

    private fun validateExistsUsername(username: String) {
        if (userRepository.existsByUsername(username))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists")
    }

    private fun RegisterRequest.createUser() =
        User(username, encoder.encode(password), name)

    private fun extractIdFromUsername(username: String) =
        userService.findUserByUsernameOrThrowException(username).id
}