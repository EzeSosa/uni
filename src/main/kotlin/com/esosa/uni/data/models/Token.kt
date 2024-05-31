package com.esosa.uni.data.models

import org.springframework.security.core.userdetails.UserDetails

data class Token(
    val token: String,
    val user: UserDetails,
    var revoked: Boolean = false
)