package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.model.User
import java.util.UUID

interface IUserService {
    fun getUserInscriptions(id: UUID) : List<InscriptionResponse>
    fun getUserExams(id: UUID) : List<ExamResponse>
    fun findUserByIdOrThrowException(id: UUID) : User
}