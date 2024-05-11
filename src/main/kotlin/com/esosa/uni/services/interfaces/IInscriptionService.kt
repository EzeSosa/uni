package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.model.Inscription
import java.util.*

interface IInscriptionService {
    fun createInscription(inscriptionRequest: InscriptionRequest) : InscriptionResponse
    fun findInscriptionByIdOrThrowException(id: UUID) : Inscription
}