package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.Inscription
import com.esosa.uni.data.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IInscriptionRepository : JpaRepository<Inscription, UUID> {
    fun findByUser(user: User): List<Inscription>
}