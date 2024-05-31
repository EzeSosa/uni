package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.ICourseController
import com.esosa.uni.controllers.requests.CourseRequest
import com.esosa.uni.controllers.responses.CourseResponse
import com.esosa.uni.services.interfaces.ICourseService
import io.github.bucket4j.Bucket
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
class CourseController (
    private val courseService: ICourseService,
    private val bucket: Bucket
) : ICourseController {

    override fun getCourses(): List<CourseResponse> {
        if (!bucket.tryConsume(1))
            throw ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests")

        return courseService.getCourses()
    }

    override fun createCourse(courseRequest: CourseRequest): CourseResponse =
        courseService.createCourse(courseRequest)

    override fun updateCourse(courseRequest: CourseRequest, id: UUID): CourseResponse =
        courseService.updateCourse(courseRequest, id)

    override fun deleteCourse(id: UUID) =
        courseService.deleteCourse(id)
}