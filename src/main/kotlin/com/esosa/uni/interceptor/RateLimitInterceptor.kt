package com.esosa.uni.interceptor

import com.esosa.uni.services.implementations.ApiKeyService
import com.esosa.uni.utils.WHITE_LIST_URL
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.servlet.HandlerInterceptor

@Component
class RateLimitInterceptor(private val apiKeyService: ApiKeyService) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (WHITE_LIST_URL.any { url -> AntPathMatcher().match(url, request.servletPath) })
            return true

        val apiKey = request.getHeader("x-api-key")

        if (apiKey.isNullOrEmpty()) {
            response.sendError(400, "Missing header: x-api-key")
            return false;
        }

        val consumption = apiKeyService.resolveBucket(apiKey)
            .tryConsumeAndReturnRemaining(1)

        if (!consumption.isConsumed) {
            val retryAfter = consumption.nanosToWaitForReset / 1_000_000_000
            response.sendError(429, "Rate limit exhausted. Try after $retryAfter seconds")
        }

        return true;
    }
}