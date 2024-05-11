package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.CourseRequest
import com.esosa.uni.controllers.responses.CourseResponse
import com.esosa.uni.data.model.Course
import com.esosa.uni.data.repository.ICourseRepository
import com.esosa.uni.services.interfaces.ICourseService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class CourseService(
    private val courseRepository: ICourseRepository
) : ICourseService {

    override fun getCourses(): List<CourseResponse> =
        courseRepository.findAll()
            .map { it.buildCourseResponse() }

    override fun createCourse(courseRequest: CourseRequest): CourseResponse =
        courseRepository.save(courseRequest.createCourse())
            .buildCourseResponse()

    override fun findCourseByIdOrThrowException(courseId: UUID): Course =
        courseRepository.findById(courseId)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Course not found") }

    private fun CourseRequest.createCourse() =
        Course(name, year)

    private fun Course.buildCourseResponse() =
        CourseResponse(id, name, year)
}