package com.adams.redisstarter.web

import Utils.Companion.log
import data.Customer
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.web.bind.annotation.*
import repository.CustomerRepository
import java.util.*
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerRepository: CustomerRepository
) {

    @Cacheable(value = ["customerList"])
    @GetMapping
    fun findAllUsers(): MutableList<Customer> {
        log.info("Calling findAll")
        return customerRepository.findAll()
    }

    @Cacheable(value = ["customer"], key = "#id")
    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: UUID
    ): Customer {
        log.info { "Finding by id" }
        return customerRepository.findById(id).orElseThrow { throw EntityNotFoundException("Failed to find $id") }
    }

    @Caching(evict = [CacheEvict(value = ["customerList"], allEntries = true)], put = [CachePut(value = ["customer"])])
    @PostMapping
    fun createUser() = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build().nextObject(Customer::class.java, "id").let {
        customerRepository.save(it)
    }

    @CacheEvict(value = ["customerList", "customer"])
    @DeleteMapping
    fun deleteCache() = run {
        log.info { "Deleting caches" }
    }

    // Check if clearing cache from this service reflects on cache on other service (should not call find all on UserController)
    @CacheEvict(value = ["usersList", "user"])
    @DeleteMapping("/users")
    fun deleteUsers() = run {
        log.info { "Deleting users" }
    }
}