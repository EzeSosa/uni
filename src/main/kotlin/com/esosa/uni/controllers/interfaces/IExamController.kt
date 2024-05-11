package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/exams")
interface IExamController {
    @PostMapping
    @ResponseStatus(CREATED)
    fun createExam(@RequestBody @Valid examRequest: ExamRequest) : ExamResponse
}