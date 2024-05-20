package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.util.UUID

@RequestMapping("/users")
interface IUserController {
    @GetMapping("/{id}/inscriptions")
    @ResponseStatus(OK)
    fun getUserInscriptions(
        @PathVariable id: UUID,
        @RequestParam dateFrom: LocalDate?,
        @RequestParam dateTo: LocalDate?
    ): List<InscriptionResponse>

    @GetMapping("/{id}/exams")
    @ResponseStatus(OK)
    fun getUserExams(
        @PathVariable id: UUID,
        @RequestParam dateFrom: LocalDate?,
        @RequestParam dateTo: LocalDate?,
        @RequestParam minGrade: Double?,
        @RequestParam maxGrade: Double?
    ): List<ExamResponse>
}