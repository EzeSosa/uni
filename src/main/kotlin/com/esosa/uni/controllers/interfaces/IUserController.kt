package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
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