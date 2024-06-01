package com.esosa.uni.services.implementations

import com.esosa.uni.data.models.ApiKey
import com.esosa.uni.data.models.User
import com.esosa.uni.data.repositories.IApiKeyRepository
import com.esosa.uni.services.interfaces.IApiKeyService
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Service
class ApiKeyService(private val apiKeyRepository: IApiKeyRepository): IApiKeyService {
    private val cache: ConcurrentHashMap<String, Bucket> = ConcurrentHashMap()

    override fun resolveBucket(apiKey: String): Bucket =
        cache.computeIfAbsent(apiKey, this::newBucket)

    override fun newBucket(apiKey: String): Bucket {
        existsApiKeyOrThrowException(apiKey)
        return Bucket.builder()
            .addLimit(generateBandwidth())
            .build()
    }

    override fun generateApiKey(user: User): ApiKey =
        apiKeyRepository.save(ApiKey(user))

    private fun generateBandwidth(): Bandwidth =
        Bandwidth.builder()
            .capacity(20)
            .refillGreedy(20, Duration.ofHours(1))
            .build()

    private fun existsApiKeyOrThrowException(apiKey: String) {
        if (!apiKeyRepository.existsByKey(apiKey))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "API key not registered")
    }
}