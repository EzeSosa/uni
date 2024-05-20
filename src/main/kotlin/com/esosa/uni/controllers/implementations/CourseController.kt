package com.esosa.uni.controllers.implementations

import com.esosa.uni.controllers.interfaces.ICourseController
import com.esosa.uni.controllers.requests.CourseRequest
import com.esosa.uni.controllers.responses.CourseResponse
import com.esosa.uni.services.interfaces.ICourseService
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class CourseController (private val courseService: ICourseService) : ICourseController {
    override fun getCourses(): List<CourseResponse> =
        courseService.getCourses()

    override fun createCourse(courseRequest: CourseRequest): CourseResponse =
        courseService.createCourse(courseRequest)

    override fun updateCourse(courseRequest: CourseRequest, id: UUID): CourseResponse =
        courseService.updateCourse(courseRequest, id)

    override fun deleteCourse(id: UUID) =
        courseService.deleteCourse(id)
}