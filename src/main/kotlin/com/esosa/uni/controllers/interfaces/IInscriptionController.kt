package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.InscriptionResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@RequestMapping("/inscriptions")
interface IInscriptionController {
    @PostMapping
    @ResponseStatus(CREATED)
    fun createInscription(@RequestBody @Valid inscriptionRequest: InscriptionRequest): InscriptionResponse

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun deleteInscription(@PathVariable id: UUID)
}