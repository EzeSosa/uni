package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.InscriptionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/inscriptions")
@Tag(
    name = "Inscriptions",
    description = "Allows registered users to enroll to a registered course."
)
interface IInscriptionController {
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Registers a new inscription")
    fun createInscription(@RequestBody @Valid inscriptionRequest: InscriptionRequest) : InscriptionResponse
}