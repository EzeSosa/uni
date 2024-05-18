package com.esosa.uni.data.models

import com.esosa.uni.data.enums.Role
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.UUID

@Entity
data class User(
    val username: String,
    val password: String,
    val email: String,
    val name: String,
    val role: Role = Role.USER,
    @OneToMany(mappedBy = "user")
    val inscriptions: List<Inscription> = emptyList(),
    @Id
    val id: UUID = UUID.randomUUID()
)