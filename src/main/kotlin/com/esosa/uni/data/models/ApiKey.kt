package com.esosa.uni.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
data class ApiKey(
    @ManyToOne
    val user: User,
    @Column(name = "_key")
    var key: String = UUID.randomUUID().toString(),
    @Id
    val id: UUID = UUID.randomUUID()
)