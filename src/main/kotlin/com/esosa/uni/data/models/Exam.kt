package com.esosa.uni.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDate
import java.util.UUID

@Entity
data class Exam(
    var date: LocalDate,
    var grade: Double,
    @ManyToOne
    val inscription: Inscription,
    @Id
    val id: UUID = UUID.randomUUID()
)