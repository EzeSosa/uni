package com.esosa.uni.services.interfaces

import com.esosa.uni.data.models.ApiKey
import com.esosa.uni.data.models.User
import io.github.bucket4j.Bucket

interface IApiKeyService {
    fun resolveBucket(apiKey: String): Bucket
    fun newBucket(apiKey: String): Bucket
    fun generateApiKey(user: User): ApiKey
}