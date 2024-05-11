package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.RegisterRequest
import com.esosa.uni.data.model.User
import com.esosa.uni.data.repository.IUserRepository
import com.esosa.uni.services.interfaces.IAuthService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class AuthService(
    private val userRepository: IUserRepository
) : IAuthService {

    override fun register(registerRequest: RegisterRequest): UUID =
        with(registerRequest) {
            validateExistsUsername(username)
            userRepository.save(
                createUser()
            ).id
        }

    private fun validateExistsUsername(username: String) {
        if (userRepository.existsByUsername(username))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists")
    }

    private fun RegisterRequest.createUser() =
        User(username, password, name)
}