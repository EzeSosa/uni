package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.controllers.responses.InscriptionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

@RequestMapping("/users")
@Tag(
    name = "Users",
    description = "Allows registered users to fetch information related to their inscriptions and exams."
)
interface IUserController {
    @GetMapping("/{id}/inscriptions")
    @ResponseStatus(OK)
    @Operation(summary = "Fetches all inscriptions from a registered user")
    fun getUserInscriptions(@PathVariable id: UUID): List<InscriptionResponse>

    @GetMapping("/{id}/exams")
    @ResponseStatus(OK)
    @Operation(summary = "Fetches all exams from a registered user")
    fun getUserExams(@PathVariable id: UUID): List<ExamResponse>
}