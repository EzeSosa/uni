package com.esosa.uni.data.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDate
import java.util.UUID

@Entity
data class Exam(
    val date: LocalDate,
    val grade: Double,
    @ManyToOne
    val inscription: Inscription,
    @Id
    val id: UUID = UUID.randomUUID()
)