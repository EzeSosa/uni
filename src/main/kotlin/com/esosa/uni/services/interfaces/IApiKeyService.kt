package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.responses.ApiKeyResponse
import com.esosa.uni.data.models.ApiKey
import com.esosa.uni.data.models.User
import io.github.bucket4j.Bucket

interface IApiKeyService {
    fun resolveBucket(key: String): Bucket
    fun newBucket(key: String): Bucket
    fun generateApiKey(user: User): ApiKey
    fun updateApiKeyPlan(key: String): ApiKeyResponse?
}