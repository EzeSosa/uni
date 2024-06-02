package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.ApiKey
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IApiKeyRepository: JpaRepository<ApiKey, UUID> {
    fun existsByKey(key: String): Boolean
    fun findByKey(key: String): ApiKey?
}