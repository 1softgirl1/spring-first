package com.example.demo.controller

import com.example.demo.dto.GreetingMain
import com.example.demo.dto.GreetingUser
import com.example.demo.dto.UserData
import com.example.demo.repository.InMemoryUserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
@RequestMapping("/greeting")
class HelloController(
    private val userRepository: InMemoryUserRepository
) {

    @GetMapping
    fun hello(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) surname: String?
    ): ResponseEntity<Any> {
        return if (name != null && surname != null) {
            val user = UserData(name = name, surname = surname)
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.ok(GreetingMain())
        }
    }

    @PostMapping
    fun saveUser(@RequestBody(required = true) user: UserData): ResponseEntity<GreetingUser> {
        val id = UUID.randomUUID()
        userRepository.save(id, user)

        val response = GreetingUser(
            text = "Hello, ${user.name} ${user.surname}!",
            id = id
        )
        return ResponseEntity.ok(response)

    }
    @GetMapping("/{id}")
    fun getById(@PathVariable(required = true) id: UUID): ResponseEntity<UserData> {
        val user = userRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(user)
    }

}