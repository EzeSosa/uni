package com.esosa.uni.security.services

import com.esosa.uni.data.repositories.IUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException

typealias AppUser = com.esosa.uni.data.models.User

class CustomUserDetailsService(
    private val userRepository: IUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Username not found")

    private fun AppUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(username)
            .password(password)
            .roles(role.toString())
            .build()
}