package com.adams.redisstarter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
class RedisStarterApplication

fun main(args: Array<String>) {
	runApplication<RedisStarterApplication>(*args)
}
