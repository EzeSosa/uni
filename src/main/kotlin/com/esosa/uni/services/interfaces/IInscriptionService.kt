package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.models.Exam
import com.esosa.uni.data.models.Inscription
import com.esosa.uni.data.models.User
import java.util.*

interface IInscriptionService {
    fun createInscription(inscriptionRequest: InscriptionRequest): InscriptionResponse
    fun deleteInscription(id: UUID)
    fun findInscriptionByIdOrThrowException(id: UUID): Inscription
    fun findUserInscriptions(user: User): List<Inscription>
    fun findUserExams(user: User): List<Exam>
}