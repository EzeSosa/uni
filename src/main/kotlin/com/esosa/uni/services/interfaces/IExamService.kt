package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.ExamRequest
import com.esosa.uni.controllers.responses.ExamResponse

interface IExamService {
    fun createExam(examRequest: ExamRequest) : ExamResponse
}