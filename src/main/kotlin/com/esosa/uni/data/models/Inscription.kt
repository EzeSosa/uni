package com.esosa.uni.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDate
import java.util.UUID

@Entity
data class Inscription(
    val date: LocalDate,
    @ManyToOne
    val course: Course,
    @ManyToOne
    val user: User,
    @OneToMany(mappedBy = "inscription")
    val exams: List<Exam> = emptyList(),
    @Id
    val id: UUID = UUID.randomUUID()
)