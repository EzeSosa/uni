package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.requests.UpdateExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.data.models.Exam
import java.util.UUID

interface IExamService {
    fun createExam(examRequest: ExamRequest) : ExamResponse
    fun updateExam(updateExamRequest: UpdateExamRequest, id: UUID) : ExamResponse
    fun findExamByIdOrThrowException(id: UUID) : Exam
}