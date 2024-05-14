package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.models.User
import java.time.LocalDate
import java.util.UUID

interface IUserService {
    fun getUserInscriptions(id: UUID): List<InscriptionResponse>
    fun getUserExams(id: UUID, dateFrom: LocalDate?, dateTo: LocalDate?, minGrade: Double?, maxGrade: Double?): List<ExamResponse>
    fun findUserByIdOrThrowException(id: UUID): User
    fun findUserByUsernameOrThrowException(username: String): User
}