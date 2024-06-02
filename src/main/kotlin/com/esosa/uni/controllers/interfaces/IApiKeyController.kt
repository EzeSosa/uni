package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.responses.ApiKeyResponse
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/apikey")
interface IApiKeyController {
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    fun updateApiKeyPlan(@RequestParam @NotNull key: String): ApiKeyResponse?
}