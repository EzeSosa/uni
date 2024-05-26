package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.requests.UpdateExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.data.models.Exam
import com.esosa.uni.data.models.User
import org.springframework.cache.annotation.CacheEvict
import java.time.LocalDate
import java.util.UUID

interface IExamService {

    @CacheEvict(cacheNames = ["userExams"], key = "'#key'", allEntries = true)
    fun createExam(examRequest: ExamRequest): ExamResponse

    @CacheEvict(cacheNames = ["userExams"], key = "'#key'", allEntries = true)
    fun updateExam(updateExamRequest: UpdateExamRequest, id: UUID): ExamResponse

    @CacheEvict(cacheNames = ["userExams"], key = "'#key'", allEntries = true)
    fun deleteExam(id: UUID): Unit

    fun findExamByIdOrThrowException(id: UUID): Exam

    fun findUserExams(
        user: User,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        minGrade: Double?,
        maxGrade: Double?
    ): List<Exam>
}