package com.esosa.uni.services.implementations

import com.esosa.uni.data.models.Token
import com.esosa.uni.data.repositories.TokenRepository
import com.esosa.uni.services.interfaces.ITokenService
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TokenService(private val tokenRepository: TokenRepository): ITokenService {
    override fun saveToken(jwt: String, user: UserDetails) =
        tokenRepository.addTokenByJWT(generateTokenEntity(jwt, user))

    override fun getTokenByJwt(jwt: String): Token =
        tokenRepository.findTokenByJWT(jwt)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Token does not exist")

    override fun revokeToken(jwt: String) =
        tokenRepository.addTokenByJWT(
            getTokenByJwt(jwt)
                .apply { revoked = true }
        )

    override fun isTokenRevoked(jwt: String): Boolean =
        getTokenByJwt(jwt).revoked

    override fun revokeTokensFromUser(user: UserDetails) {
        with(tokenRepository) {
            findTokenByUser(user)
                ?.apply { revoked = true }
                ?.let { token ->
                    addTokenByJWT(token)
                }
        }
    }

    private fun generateTokenEntity(jwt: String, user: UserDetails) =
        Token(jwt, user)
}