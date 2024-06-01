package com.esosa.uni.config

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class BucketConfiguration {

    @Bean
    fun generateBucket(): Bucket =
        Bucket.builder()
            .addLimit(generateBandwidth())
            .build()

    fun generateBandwidth(): Bandwidth =
        Bandwidth.builder()
            .capacity(20)
            .refillGreedy(20, Duration.ofHours(1))
            .build()
}