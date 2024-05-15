package com.esosa.uni.exceptions

import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(NOT_FOUND)
    fun handleIllegalStateException(ex: IllegalStateException): ExceptionResponse =
        ExceptionResponse(
            ex.message,
            NOT_FOUND.value()
        )

    @ExceptionHandler(ResponseStatusException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handleResponseStatusException(ex: ResponseStatusException): ExceptionResponse =
        ExceptionResponse(
            ex.message,
            BAD_REQUEST.value()
        )

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ExceptionResponse =
        ExceptionResponse(
            "Argument type mismatch. " + ex.message,
            BAD_REQUEST.value()
        )
}