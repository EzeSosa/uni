package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

@RequestMapping("/users")
interface IUserController {
    @GetMapping("/{id}/inscriptions")
    @ResponseStatus(OK)
    fun getUserInscriptions(@PathVariable id: UUID): List<InscriptionResponse>

    @GetMapping("/{id}/exams")
    @ResponseStatus(OK)
    fun getUserExams(@PathVariable id: UUID): List<ExamResponse>
}