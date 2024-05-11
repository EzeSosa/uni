package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IInscriptionController
import com.esosa.uni.controllers.requests.InscriptionRequest
import com.esosa.uni.controllers.responses.InscriptionResponse
import com.esosa.uni.services.interfaces.IInscriptionService
import org.springframework.web.bind.annotation.RestController

@RestController
class InscriptionController (private val inscriptionService: IInscriptionService) : IInscriptionController {
    override fun createInscription(inscriptionRequest: InscriptionRequest): InscriptionResponse =
        inscriptionService.createInscription(inscriptionRequest)
}