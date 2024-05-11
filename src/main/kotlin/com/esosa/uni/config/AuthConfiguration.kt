package com.esosa.uni.config

import com.esosa.uni.data.repositories.IUserRepository
import com.esosa.uni.security.jwt.JWTProperties
import com.esosa.uni.security.services.CustomUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JWTProperties::class)
class AuthConfiguration {

    @Bean
    fun userDetailsService(userRepository: IUserRepository) : CustomUserDetailsService =
        CustomUserDetailsService(userRepository)

    @Bean
    fun passwordEncoder() : PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(userRepository: IUserRepository) : AuthenticationProvider =
        DaoAuthenticationProvider().also {
            it.setUserDetailsService(userDetailsService(userRepository))
            it.setPasswordEncoder(passwordEncoder())
        }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager
}