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
import com.esosa.uni.verification.requests.ConfirmationTokenRequest
import com.esosa.uni.verification.responses.ConfirmationTokenResponse
import com.esosa.uni.verification.services.ConfirmationTokenService
import com.esosa.uni.verification.token.ConfirmationToken
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
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
    private val refreshTokenRepository: RefreshTokenRepository,
    private val confirmationTokenService: ConfirmationTokenService,
) : IAuthService {

    override fun register(registerRequest: RegisterRequest): ConfirmationTokenResponse =
        with(registerRequest) {
            validateExistsUsername(username)
            validateExistsEmail(email)
            userRepository.save(createUser())
            confirmationTokenService.saveConfirmationToken(generateConfirmationToken())
                .buildConfirmationTokenResponse()
        }

    override fun login(loginRequest: LoginRequest): AuthResponse =
        with(loginRequest) {
            validateConfirmationToken()

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

    override fun enableUser(confirmationTokenRequest: ConfirmationTokenRequest): Unit =
        with(confirmationTokenRequest) {
            confirmationTokenService.saveConfirmationToken(
                confirmationTokenService.getToken(token)
                    .validateToken()
                    .apply { confirmedAt = LocalDateTime.now() }
            )
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

    private fun RegisterRequest.generateConfirmationToken() =
        ConfirmationToken(
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            userService.findUserByUsernameOrThrowException(username)
        )

    private fun LoginRequest.validateConfirmationToken() =
        userService.findUserByUsernameOrThrowException(username).also { user ->
            if (user.confirmationTokens[0].confirmedAt == null)
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token not confirmed")
        }

    private fun ConfirmationToken.validateToken(): ConfirmationToken {
        if (confirmedAt != null)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token already confirmed")

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User token expired")

        return this
    }

    private fun ConfirmationToken.buildConfirmationTokenResponse(): ConfirmationTokenResponse =
        ConfirmationTokenResponse(token, expiredAt)
}