package com.esosa.uni.data.repository

import com.esosa.uni.data.model.Inscription
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IInscriptionRepository : JpaRepository<Inscription, UUID> {}