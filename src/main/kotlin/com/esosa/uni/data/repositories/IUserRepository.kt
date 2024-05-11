package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IUserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String) : User?
    fun existsByUsername(username: String) : Boolean
}