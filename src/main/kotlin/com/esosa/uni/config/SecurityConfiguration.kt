package com.esosa.uni.config

import com.esosa.uni.data.enums.Role
import com.esosa.uni.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpMethod.PATCH
import org.springframework.http.HttpMethod.DELETE
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

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { httpRequests ->
                httpRequests
                    .requestMatchers("/auth/**", "/confirm/**", "/error").permitAll()
                    .requestMatchers(POST, "v1/courses").hasRole(Role.ADMIN.name)
                    .requestMatchers(PATCH, "v1/courses/**").hasRole(Role.ADMIN.name)
                    .requestMatchers(DELETE, "v1/courses/**").hasRole(Role.ADMIN.name)
                    .anyRequest().authenticated()
            }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic { basic -> basic.disable() }
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}