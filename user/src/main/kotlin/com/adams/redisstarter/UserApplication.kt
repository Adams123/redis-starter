package com.adams.redisstarter

import config.CommonConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(CommonConfig::class)
@EnableCaching
class RedisStarterApplication

fun main(args: Array<String>) {
	runApplication<RedisStarterApplication>(*args)
}
