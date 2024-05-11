package com.esosa.uni.data.repository

import com.esosa.uni.data.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface IUserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String) : Optional<User>
    fun existsByUsername(username: String) : Boolean
}