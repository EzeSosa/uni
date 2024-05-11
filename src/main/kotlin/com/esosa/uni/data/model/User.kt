package com.esosa.uni.data.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.UUID

@Entity
data class User(
    val username: String,
    val password: String,
    val name: String,
    @OneToMany(mappedBy = "user")
    val inscriptions: List<Inscription> = emptyList(),
    @Id
    val id: UUID = UUID.randomUUID()
)