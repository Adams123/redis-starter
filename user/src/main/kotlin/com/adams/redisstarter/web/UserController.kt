package com.adams.redisstarter.web

import Utils.Companion.log
import data.User
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.web.bind.annotation.*
import repository.UserRepository
import java.util.*
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("/users")
class UserController(
    private val userRepository: UserRepository
) {

    @Cacheable(value = ["usersList"])
    @GetMapping
    fun findAllUsers(): MutableList<User> {
        log.info("Calling findAll")
        return userRepository.findAll()
    }

    @Cacheable(value = ["user"], key = "#id")
    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: UUID
    ): User {
        log.info { "Finding by id" }
        return userRepository.findById(id).orElseThrow { throw EntityNotFoundException("Failed to find $id") }
    }

    @Caching(evict = [CacheEvict(value = ["usersList"], allEntries = true)], put = [CachePut(value = ["user"])])
    @PostMapping
    fun createUser() = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build().nextObject(User::class.java, "id").let {
        userRepository.save(it)
    }

    @CacheEvict(value = ["usersList", "user"])
    @DeleteMapping
    fun deleteCache() = run {
        log.info { "Deleting caches" }
    }
}