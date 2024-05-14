package com.esosa.uni.controllers.interfaces

import com.esosa.uni.controllers.requests.CourseRequest
import com.esosa.uni.controllers.responses.CourseResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

@RequestMapping("/courses")
interface ICourseController {
    @GetMapping
    @ResponseStatus(OK)
    fun getCourses() : List<CourseResponse>

    @PostMapping
    @ResponseStatus(CREATED)
    fun createCourse(@RequestBody @Valid courseRequest: CourseRequest): CourseResponse

    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    fun updateCourse(@RequestBody @Valid courseRequest: CourseRequest, @PathVariable id: UUID): CourseResponse

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun deleteCourse(@PathVariable id: UUID)
}