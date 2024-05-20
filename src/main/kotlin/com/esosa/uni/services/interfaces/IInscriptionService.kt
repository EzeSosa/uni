package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.models.Inscription
import com.esosa.uni.data.models.User
import java.time.LocalDate
import java.util.UUID

interface IInscriptionService {
    fun createInscription(inscriptionRequest: InscriptionRequest): InscriptionResponse
    fun deleteInscription(id: UUID)
    fun findInscriptionByIdOrThrowException(id: UUID): Inscription
    fun findUserInscriptions(
        user: User,
        dateFrom: LocalDate? = null,
        dateTo: LocalDate? = null
    ): List<Inscription>
}