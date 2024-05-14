package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.requests.UpdateExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

@RequestMapping("/exams")
interface IExamController {
    @PostMapping
    @ResponseStatus(CREATED)
    fun createExam(@RequestBody @Valid examRequest: ExamRequest): ExamResponse

    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    fun updateExam(@RequestBody @Valid updateExamRequest: UpdateExamRequest, @PathVariable id: UUID): ExamResponse

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun deleteExam(@PathVariable id: UUID)
}