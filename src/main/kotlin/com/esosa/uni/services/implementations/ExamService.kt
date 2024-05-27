package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.requests.UpdateExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.data.models.Exam
import com.esosa.uni.data.models.Inscription
import com.esosa.uni.data.models.User
import com.esosa.uni.data.repositories.IExamRepository
import com.esosa.uni.services.interfaces.IExamService
import com.esosa.uni.services.interfaces.IInscriptionService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.UUID

@Service
class ExamService(
    private val examRepository: IExamRepository,
    private val inscriptionService: IInscriptionService
) : IExamService {

    override fun createExam(examRequest: ExamRequest): ExamResponse =
        with(examRequest) {
            inscriptionService.findInscriptionByIdOrThrowException(inscriptionId).let { inscription ->
                examRepository.save(
                    createExam(inscription)
                ).buildExamResponse()
            }
        }

    override fun updateExam(updateExamRequest: UpdateExamRequest, id: UUID): ExamResponse =
        findExamByIdOrThrowException(id).also { exam ->
            examRepository.save(
                exam.apply {
                    date = updateExamRequest.date
                    grade = updateExamRequest.grade
                }
            )
        }.buildExamResponse()

    override fun deleteExam(id: UUID) =
        findExamByIdOrThrowException(id).run {
            examRepository.deleteById(id)
        }

    override fun findExamByIdOrThrowException(id: UUID): Exam =
        examRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found") }

    override fun findUserExams(
        user: User,
        dateFrom: LocalDate?,
        dateTo: LocalDate?,
        minGrade: Double?,
        maxGrade: Double?
    ): List<Exam> =
        examRepository.findByUser(user, dateFrom, dateTo, minGrade, maxGrade)

    fun ExamRequest.createExam(inscription: Inscription) =
        Exam(date, grade, inscription)

    private fun Exam.buildExamResponse() =
        ExamResponse(id, date, grade, inscription.course.name)
}