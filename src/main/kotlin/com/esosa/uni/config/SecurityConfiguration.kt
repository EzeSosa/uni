package com.esosa.uni.config

import com.esosa.uni.data.enums.Role
import com.esosa.uni.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authProvider: AuthenticationProvider
) {

    private val WHITE_LIST_URL = arrayOf(
        "/auth/*",
        "/auth/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/error",
        "/",
    )

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*WHITE_LIST_URL).permitAll()
                    .requestMatchers(POST, "/courses").hasRole(Role.ADMIN.name)
                    .requestMatchers(PATCH, "/courses/**").hasRole(Role.ADMIN.name)
                    .requestMatchers(DELETE, "/courses/**").hasRole(Role.ADMIN.name)
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .httpBasic {
                it.disable()
            }
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}