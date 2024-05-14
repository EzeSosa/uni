package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.requests.UpdateExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.data.models.Exam
import com.esosa.uni.data.models.Inscription
import java.time.LocalDate
import java.util.UUID

interface IExamService {
    fun createExam(examRequest: ExamRequest): ExamResponse
    fun updateExam(updateExamRequest: UpdateExamRequest, id: UUID): ExamResponse
    fun deleteExam(id: UUID)
    fun findExamByIdOrThrowException(id: UUID): Exam
    fun findInscriptionExams(
        inscription: Inscription,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        minGrade: Double?,
        maxGrade: Double?
    ): List<Exam>
}