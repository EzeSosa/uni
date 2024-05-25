package com.esosa.uni.caching

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> =
        RedisTemplate<String, Any>().apply {
            connectionFactory = connectionFactory()
            keySerializer = JdkSerializationRedisSerializer()
            valueSerializer = GenericToStringSerializer(Object::class.java)
        }

    fun connectionFactory(): LettuceConnectionFactory =
        with(redisConfiguration()) {
            this.hostName = redisProperties().host
            this.port = redisProperties().port
            LettuceConnectionFactory(this)
        }

    fun redisConfiguration(): RedisStandaloneConfiguration =
        RedisStandaloneConfiguration()

    fun redisProperties(): RedisProperties =
        RedisProperties()
}