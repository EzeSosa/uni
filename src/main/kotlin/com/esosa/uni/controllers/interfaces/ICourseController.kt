package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.CourseRequest
import com.esosa.uni.controllers.responses.CourseResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/courses")
interface ICourseController {
    @GetMapping
    @ResponseStatus(OK)
    fun getCourses() : List<CourseResponse>

    @PostMapping
    @ResponseStatus(CREATED)
    fun createCourse(@RequestBody @Valid courseRequest: CourseRequest) : CourseResponse
}