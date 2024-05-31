package com.esosa.uni.security.filter

import com.esosa.uni.security.jwt.JWTService
import com.esosa.uni.security.services.CustomUserDetailsService
import com.esosa.uni.services.interfaces.ITokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val jwtService: JWTService,
    private val tokenService: ITokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")

        if (authHeader.doesNotContainBearerToken()) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader!!.extractToken()
        val username = jwtService.extractUsernameFromToken(token)

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val user = userDetailsService.loadUserByUsername(username)

            if (jwtService.isTokenValid(token, user) && !tokenService.isTokenRevoked(token))
                updateContext(user, request)

            filterChain.doFilter(request, response)
        }
    }

    private fun updateContext(user: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }

    private fun String?.doesNotContainBearerToken(): Boolean =
         this == null || !startsWith("Bearer ")

    private fun String.extractToken(): String =
        substringAfter("Bearer ").takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("Invalid JWT format")
}