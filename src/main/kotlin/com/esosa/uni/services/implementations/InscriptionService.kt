package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.models.Course
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
import java.time.LocalDate
import java.util.UUID

@Service
class InscriptionService(
    private val inscriptionRepository: IInscriptionRepository,
    private val courseService: ICourseService,
    @Lazy private val userService: IUserService
) : IInscriptionService {

    override fun createInscription(inscriptionRequest: InscriptionRequest): InscriptionResponse =
        with(inscriptionRequest) {
            val course = courseService.findCourseByIdOrThrowException(courseId)
            val user = userService.findUserByIdOrThrowException(userId)

            inscriptionRepository.save(
                createInscription(course, user)
            ).buildInscriptionResponse()
        }

    override fun deleteInscription(id: UUID) =
        findInscriptionByIdOrThrowException(id).run {
            inscriptionRepository.deleteById(id)
        }

    override fun findInscriptionByIdOrThrowException(id: UUID): Inscription =
        inscriptionRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Inscription not found") }

    override fun findUserInscriptions(
        user: User,
        dateFrom: LocalDate?,
        dateTo: LocalDate?
    ): List<Inscription> =
        inscriptionRepository.findByUser(user, dateFrom, dateTo)

    private fun Inscription.buildInscriptionResponse() =
        InscriptionResponse(id, date, course.name)

    private fun InscriptionRequest.createInscription(course: Course, user: User) =
        Inscription(date, course, user)
}