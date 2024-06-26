package com.esosa.uni.controllers.interfaces

import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/confirm")
interface IConfirmationController {

    @PostMapping
    @ResponseStatus(CREATED)
    fun confirm(@RequestParam @NotNull token: String)

    @PostMapping("/resend")
    @ResponseStatus(CREATED)
    fun resend(@RequestParam @NotNull username: String)
}