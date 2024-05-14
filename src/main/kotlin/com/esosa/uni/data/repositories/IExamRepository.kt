package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.Exam
import com.esosa.uni.data.models.Inscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface IExamRepository : JpaRepository<Exam, UUID> {
    @Query("SELECT e from Exam e WHERE " +
            "e.inscription = ?1 " +
            "AND (?2 is null or e.date >= ?2) " +
            "AND (?3 is null or e.date <= ?3) " +
            "AND (?4 is null or e.grade >= ?4) " +
            "AND (?5 is null or e.grade <= ?5)"
    )
    fun findByInscription(
        inscription: Inscription,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        minGrade: Double?,
        maxGrade: Double?
    ): List<Exam>
}