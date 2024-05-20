package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IUserController
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.services.interfaces.IUserService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID

@RestController
class UserController (private val userService: IUserService) : IUserController {
    override fun getUserInscriptions(
        @PathVariable id: UUID,
        @RequestParam dateFrom: LocalDate?,
        @RequestParam dateTo: LocalDate?
    ): List<InscriptionResponse> =
        userService.getUserInscriptions(id, dateFrom, dateTo)

    override fun getUserExams(
        @PathVariable id: UUID,
        @RequestParam dateFrom: LocalDate?,
        @RequestParam dateTo: LocalDate?,
        @RequestParam minGrade: Double?,
        @RequestParam maxGrade: Double?
    ): List<ExamResponse> =
        userService.getUserExams(id, dateFrom, dateTo, minGrade, maxGrade)
}