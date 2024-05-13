package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.requests.UpdateExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.data.models.Exam
import com.esosa.uni.data.models.Inscription
import com.esosa.uni.data.repositories.IExamRepository
import com.esosa.uni.services.interfaces.IExamService
import com.esosa.uni.services.interfaces.IInscriptionService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class ExamService(
    private val examRepository: IExamRepository,
    private val inscriptionService: IInscriptionService
) : IExamService {

    override fun createExam(examRequest: ExamRequest): ExamResponse =
        with(examRequest) {
            val inscription = inscriptionService.findInscriptionByIdOrThrowException(inscriptionId)

            examRepository.save(
                createExam(inscription)
            )
                .buildExamResponse()
        }

    override fun updateExam(updateExamRequest: UpdateExamRequest, id: UUID): ExamResponse =
        findExamByIdOrThrowException(id).also {
            examRepository.save(
                it.apply {
                    it.date = updateExamRequest.date
                    it.grade = updateExamRequest.grade
                }
            )
        }
            .buildExamResponse()

    override fun findExamByIdOrThrowException(id: UUID): Exam =
        examRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Course not found") }

    fun ExamRequest.createExam(inscription: Inscription) =
        Exam(date, grade, inscription)

    private fun Exam.buildExamResponse() =
        ExamResponse(id, date, grade, inscription.course.name)
}