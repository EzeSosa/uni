package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IApiKeyController
import com.esosa.uni.controllers.responses.ApiKeyResponse
import com.esosa.uni.services.interfaces.IApiKeyService
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiKeyController(private val apiKeyService: IApiKeyService) : IApiKeyController {
    override fun updateApiKeyPlan(key: String): ApiKeyResponse? =
        apiKeyService.updateApiKeyPlan(key)
}