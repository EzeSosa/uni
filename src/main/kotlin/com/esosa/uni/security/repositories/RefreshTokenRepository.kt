package com.esosa.uni.security.repositories

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepository {
    private val userDetailsByToken = mutableMapOf<String, UserDetails>()

    fun findUserDetailsByToken(token: String) =
        userDetailsByToken[token]

    fun addUserDetailByToken(token: String, userDetails: UserDetails) {
        userDetailsByToken[token] = userDetails
    }
}