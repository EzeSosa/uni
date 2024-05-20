package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IExamController
import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.requests.UpdateExamRequest
import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.services.interfaces.IExamService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ExamController (private val examService: IExamService) : IExamController {
    override fun createExam(examRequest: ExamRequest) =
        examService.createExam(examRequest)

    override fun updateExam(@RequestBody @Valid updateExamRequest: UpdateExamRequest, @PathVariable id: UUID): ExamResponse =
        examService.updateExam(updateExamRequest, id)

    override fun deleteExam(id: UUID) =
        examService.deleteExam(id)
}