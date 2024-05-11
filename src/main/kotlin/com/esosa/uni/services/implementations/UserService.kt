package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.model.Exam
import com.esosa.uni.data.model.Inscription
import com.esosa.uni.data.model.User
import com.esosa.uni.data.repository.IUserRepository
import com.esosa.uni.services.interfaces.IUserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class UserService (
    private val userRepository: IUserRepository
) : IUserService {

    override fun getUserInscriptions(id: UUID): List<InscriptionResponse> =
        findUserByIdOrThrowException(id).inscriptions
            .map { it.buildInscriptionResponse() }

    override fun getUserExams(id: UUID): List<ExamResponse> =
        findUserByIdOrThrowException(id).inscriptions
            .flatMap { it.exams }
            .map { it.buildExamResponse() }

    override fun findUserByIdOrThrowException(id: UUID): User =
        userRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found") }

    private fun Inscription.buildInscriptionResponse() =
        InscriptionResponse(id, date, course.name)

    private fun Exam.buildExamResponse() =
        ExamResponse(date, grade, inscription.course.name)
}