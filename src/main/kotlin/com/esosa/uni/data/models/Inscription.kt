package com.esosa.uni.data.models

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
data class Inscription(
    val date: LocalDate,
    @ManyToOne
    val course: Course,
    @ManyToOne
    val user: User,
    @OneToMany(mappedBy = "inscription", cascade = [CascadeType.ALL])
    val exams: List<Exam> = emptyList(),
    @Id
    val id: UUID = UUID.randomUUID()
)