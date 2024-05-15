package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.Inscription
import com.esosa.uni.data.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.UUID

interface IInscriptionRepository : JpaRepository<Inscription, UUID> {
    @Query("SELECT i from Inscription i WHERE " +
            "i.user = ?1 " +
            "AND (?2 is null or i.date >= ?2) " +
            "AND (?3 is null or i.date <= ?3)"
    )
    fun findByUser(
        user: User,
        dateFrom: LocalDate?,
        dateTo: LocalDate?
    ): List<Inscription>
}