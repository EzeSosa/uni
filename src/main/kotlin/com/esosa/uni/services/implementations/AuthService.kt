package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.LoginRequest
import com.esosa.uni.controllers.requests.RefreshTokenRequest
import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.controllers.responses.AuthResponse
import com.esosa.uni.controllers.responses.RefreshTokenResponse
import com.esosa.uni.data.models.User
import com.esosa.uni.data.repositories.IUserRepository
import com.esosa.uni.security.jwt.JWTProperties
import com.esosa.uni.security.jwt.JWTService
import com.esosa.uni.security.repositories.RefreshTokenRepository
import com.esosa.uni.security.services.CustomUserDetailsService
import com.esosa.uni.services.interfaces.IAuthService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID
import java.util.Date

@Service
class AuthService(
    private val userDetailsService: CustomUserDetailsService,
    private val authManager: AuthenticationManager,
    private val jwtService: JWTService,
    private val jwtProperties: JWTProperties,
    private val userRepository: IUserRepository,
    private val encoder: PasswordEncoder,
    private val userService: UserService,
    private val refreshTokenRepository: RefreshTokenRepository
) : IAuthService {

    override fun register(registerRequest: RegisterRequest): Unit =
        with(registerRequest) {
            validateExistsUsername(username)
            validateExistsEmail(email)
            userRepository.save(
                createUser()
            )
        }

    override fun login(loginRequest: LoginRequest): AuthResponse =
        with(loginRequest) {
            authManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

            val user = userDetailsService.loadUserByUsername(username)
            val accessToken = generateAccessToken(user)
            val refreshToken = generateRefreshToken(user)

            refreshTokenRepository.addUserDetailByToken(refreshToken, user)

            AuthResponse(username.extractId(), accessToken, refreshToken)
        }

    override fun refresh(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse? =
        with(refreshTokenRequest) {
            jwtService.extractUsernameFromToken(refreshToken)?.let { username ->
                val currentUserDetails = userDetailsService.loadUserByUsername(username)
                val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken)

                if (!jwtService.isTokenExpired(refreshToken) && currentUserDetails.username == refreshTokenUserDetails?.username)
                    generateAccessToken(currentUserDetails)
                        .buildRefreshTokenResponse(username.extractId())
                else null
            }
        }

    private fun String.buildRefreshTokenResponse(userId: UUID): RefreshTokenResponse =
        RefreshTokenResponse(userId, this)

    private fun String.extractId(): UUID =
        userService.findUserByUsernameOrThrowException(this).id

    private fun generateRefreshToken(user: UserDetails): String =
        jwtService.generateToken(
            user,
            Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
        )

    private fun generateAccessToken(user: UserDetails): String =
        jwtService.generateToken(
            user,
            Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

    private fun validateExistsUsername(username: String) {
        if (userRepository.existsByUsername(username))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists")
    }


    private fun validateExistsEmail(email: String) {
        if (userRepository.existsByEmail(email))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists")
    }

    private fun RegisterRequest.createUser() =
        User(username, encoder.encode(password), name, email)
}