package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.Inscription
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IInscriptionRepository : JpaRepository<Inscription, UUID> {}