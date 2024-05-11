package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IUserController
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.services.interfaces.IUserService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UserController (private val userService: IUserService) : IUserController {
    override fun getUserInscriptions(@PathVariable id: UUID): List<InscriptionResponse> =
        userService.getUserInscriptions(id)

    override fun getUserExams(@PathVariable id: UUID): List<ExamResponse> =
        userService.getUserExams(id)
}