package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.models.User
import org.springframework.cache.annotation.Cacheable
import java.time.LocalDate
import java.util.UUID

interface IUserService {

    @Cacheable(cacheNames = ["userInscriptions"], key = "'#key'")
    fun getUserInscriptions(
        id: UUID,
        dateFrom: LocalDate?,
        dateTo: LocalDate?
    ): List<InscriptionResponse>

    @Cacheable(cacheNames = ["userExams"], key = "'#key'")
    fun getUserExams(
        id: UUID,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        minGrade: Double?,
        maxGrade: Double?
    ): List<ExamResponse>

    fun findUserByIdOrThrowException(id: UUID): User

    fun findUserByUsernameOrThrowException(username: String): User
}