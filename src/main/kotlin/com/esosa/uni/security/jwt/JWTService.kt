package com.esosa.uni.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTService(
    private val jwtProperties: JWTProperties
) {

    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    fun generateToken(
        userDetails: UserDetails,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration))
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    fun extractUsernameFromToken(token: String): String? =
        getAllClaimsFromToken(token)
            .subject

    fun isTokenExpired(token: String): Boolean =
        getAllClaimsFromToken(token)
            .expiration
            .before(Date(System.currentTimeMillis()))

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean =
        !isTokenExpired(token) &&
                tokenSubjectEqualsUsername(token, userDetails)

    private fun tokenSubjectEqualsUsername(token: String, userDetails: UserDetails): Boolean =
        extractUsernameFromToken(token).equals(userDetails.username)

    private fun getAllClaimsFromToken(token: String): Claims =
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
}