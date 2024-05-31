package com.esosa.uni.services.interfaces

import com.esosa.uni.data.models.Token
import org.springframework.security.core.userdetails.UserDetails

interface ITokenService {
    fun saveToken(jwt: String, user: UserDetails)
    fun getTokenByJwt(jwt: String): Token?
    fun revokeToken(jwt: String)
    fun isTokenRevoked(jwt: String): Boolean
    fun revokeTokensFromUser(user: UserDetails)
}