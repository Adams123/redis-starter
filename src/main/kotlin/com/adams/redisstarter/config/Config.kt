package com.adams.redisstarter.config

import com.adams.redisstarter.data.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.util.*


@Configuration
class Config {
    @Bean
    fun redisTemplate(cf: RedisConnectionFactory?): RedisTemplate<UUID, User>? {
        val redisTemplate = RedisTemplate<UUID, User>()
        redisTemplate.setConnectionFactory(cf!!)
        return redisTemplate
    }
}