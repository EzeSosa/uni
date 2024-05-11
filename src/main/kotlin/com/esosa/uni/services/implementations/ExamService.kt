package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.data.models.Exam
import com.esosa.uni.data.models.Inscription
import com.esosa.uni.data.repositories.IExamRepository
import com.esosa.uni.services.interfaces.IExamService
import com.esosa.uni.services.interfaces.IInscriptionService
import org.springframework.stereotype.Service

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

    private fun ExamRequest.createExam(inscription: Inscription) =
        Exam(date, grade, inscription)

    private fun Exam.buildExamResponse() =
        ExamResponse(date, grade, inscription.course.name)
}