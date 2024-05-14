package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.models.Course
import com.esosa.uni.data.models.Exam
import com.esosa.uni.data.models.Inscription
import com.esosa.uni.data.models.User
import com.esosa.uni.data.repositories.IInscriptionRepository
import com.esosa.uni.services.interfaces.ICourseService
import com.esosa.uni.services.interfaces.IExamService
import com.esosa.uni.services.interfaces.IInscriptionService
import com.esosa.uni.services.interfaces.IUserService
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class InscriptionService(
    private val inscriptionRepository: IInscriptionRepository,
    private val courseService: ICourseService,
    @Lazy private val userService: IUserService,
    @Lazy private val examService: IExamService
) : IInscriptionService {

    override fun createInscription(inscriptionRequest: InscriptionRequest): InscriptionResponse =
        with(inscriptionRequest) {
            val course = courseService.findCourseByIdOrThrowException(courseId)
            val user = userService.findUserByIdOrThrowException(userId)

            inscriptionRepository.save(
                createInscription(course, user)
            )
                .buildInscriptionResponse()
        }

    override fun deleteInscription(id: UUID) =
        if (inscriptionRepository.existsById(id))
            inscriptionRepository.deleteById(id)
        else
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Inscription not found")

    override fun findInscriptionByIdOrThrowException(id: UUID): Inscription =
        inscriptionRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Inscription not found") }

    override fun findUserInscriptions(user: User): List<Inscription> =
        inscriptionRepository.findByUser(user)

    override fun findUserExams(user: User): List<Exam> =
        findUserInscriptions(user)
            .flatMap { examService.findInscriptionExams(it) }

    private fun Inscription.buildInscriptionResponse() =
        InscriptionResponse(id, date, course.name)

    private fun InscriptionRequest.createInscription(course: Course, user: User) =
        Inscription(date, course, user)
}