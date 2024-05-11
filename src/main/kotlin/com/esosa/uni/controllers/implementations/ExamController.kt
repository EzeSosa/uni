package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.IExamController
import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.services.interfaces.IExamService
import org.springframework.web.bind.annotation.RestController

@RestController
class ExamController (private val examService: IExamService) : IExamController {
    override fun createExam(examRequest: ExamRequest) =
        examService.createExam(examRequest)
}