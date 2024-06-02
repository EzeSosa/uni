package com.esosa.uni.data.enums

import io.github.bucket4j.Bandwidth
import java.time.Duration

enum class PricingPlan {
    FREE {
        override fun getLimit(): Bandwidth = Bandwidth.builder()
            .capacity(20)
            .refillIntervally(20, Duration.ofHours(1))
            .build()
    },
    PAID {
        override fun getLimit(): Bandwidth = Bandwidth.builder()
            .capacity(50)
            .refillIntervally(50, Duration.ofHours(1))
            .build()
    };

    abstract fun getLimit(): Bandwidth

    companion object {
        fun resolvePlanForApiKey(apiKey: String): PricingPlan {
            if (apiKey.startsWith(("X001-"))) return PAID
            return FREE
        }
    }
}