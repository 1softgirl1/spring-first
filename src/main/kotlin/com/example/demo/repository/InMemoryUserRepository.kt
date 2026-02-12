package com.example.demo.repository

import com.example.demo.dto.UserData
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryUserRepository {
    private val users: MutableMap<UUID, UserData> = ConcurrentHashMap()
    fun save(id: UUID, user: UserData) {
        users[id] = user
    }
    fun findById(id: UUID): UserData? {
        return users[id]
    }

}