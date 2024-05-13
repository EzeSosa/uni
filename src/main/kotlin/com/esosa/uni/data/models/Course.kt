package com.esosa.uni.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.UUID

@Entity
data class Course(
    var name: String,
    var year: Int,
    @OneToMany(mappedBy = "course")
    val inscriptions: List<Inscription> = emptyList(),
    @Id
    val id: UUID = UUID.randomUUID()
)