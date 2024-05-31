package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.Token
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class TokenRepository {
    private val tokenByJWT = mutableMapOf<String, Token>()

    fun findTokenByJWT(jwt: String): Token? =
        tokenByJWT[jwt]

    fun addTokenByJWT(token: Token) {
        tokenByJWT[token.token] = token
    }

    fun findTokenByUser(user: UserDetails): Token? =
        tokenByJWT.values
            .find { token -> user.username == token.user.username && !token.revoked }
}