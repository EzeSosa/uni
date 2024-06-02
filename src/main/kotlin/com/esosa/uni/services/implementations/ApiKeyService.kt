package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.responses.ApiKeyResponse
import com.esosa.uni.data.enums.PricingPlan
import com.esosa.uni.data.models.ApiKey
import com.esosa.uni.data.models.User
import com.esosa.uni.data.repositories.IApiKeyRepository
import com.esosa.uni.services.interfaces.IApiKeyService
import io.github.bucket4j.Bucket
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.concurrent.ConcurrentHashMap

@Service
class ApiKeyService(private val apiKeyRepository: IApiKeyRepository): IApiKeyService {
    private val cache: ConcurrentHashMap<String, Bucket> = ConcurrentHashMap()

    override fun resolveBucket(key: String): Bucket =
        cache.computeIfAbsent(key, this::newBucket)

    override fun newBucket(key: String): Bucket {
        existsApiKeyOrThrowException(key)
        val pricingPlan = PricingPlan.resolvePlanForApiKey(key)
        return Bucket.builder()
            .addLimit(pricingPlan.getLimit())
            .build()
    }

    override fun generateApiKey(user: User): ApiKey =
        ApiKey(user).also { apiKey -> saveApiKey(apiKey) }

    override fun updateApiKeyPlan(key: String): ApiKeyResponse? {
        existsApiKeyOrThrowException(key)
        return apiKeyRepository.findByKey(key)
            ?.let { apiKey -> saveApiKey(apiKey.updateToPaid()) }
            ?.buildApiKeyResponse()
    }

    private fun ApiKey.updateToPaid() =
        this.apply { key = "X001-$key" }

    private fun ApiKey.buildApiKeyResponse() =
        ApiKeyResponse(key)

    private fun saveApiKey(apiKey: ApiKey) =
        apiKeyRepository.save(apiKey)

    private fun existsApiKeyOrThrowException(key: String) {
        if (!apiKeyRepository.existsByKey(key))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "API key not registered")
    }
}