package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.data.model.Course
import com.esosa.uni.data.model.Inscription
import com.esosa.uni.data.model.User
import com.esosa.uni.data.repository.IInscriptionRepository
import com.esosa.uni.services.interfaces.ICourseService
import com.esosa.uni.services.interfaces.IInscriptionService
import com.esosa.uni.services.interfaces.IUserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class InscriptionService(
    private val inscriptionRepository: IInscriptionRepository,
    private val userService: IUserService,
    private val courseService: ICourseService
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

    override fun findInscriptionByIdOrThrowException(id: UUID): Inscription =
        inscriptionRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Inscription not found") }

    private fun Inscription.buildInscriptionResponse() =
        InscriptionResponse(id, date, course.name)

    private fun InscriptionRequest.createInscription(course: Course, user: User) =
        Inscription(date, course, user)
}