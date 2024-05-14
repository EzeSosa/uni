package com.esosa.uni.services.implementations

import com.esosa.uni.controllers.requests.CourseRequest
import com.esosa.uni.controllers.responses.CourseResponse
import com.esosa.uni.data.models.Course
import com.esosa.uni.data.repositories.ICourseRepository
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
        with(courseRequest) {
            if (courseRepository.existsByNameAndYear(name, year))
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Course already exists")
            else
                courseRepository.save(this.createCourse())
                    .buildCourseResponse()
        }


    override fun updateCourse(courseRequest: CourseRequest, id: UUID): CourseResponse =
        findCourseByIdOrThrowException(id).also {
            courseRepository.save(
                it.apply {
                    it.name = courseRequest.name
                    it.year = courseRequest.year
                }
            )
        }
            .buildCourseResponse()

    override fun deleteCourse(id: UUID) =
        if (courseRepository.existsById(id))
            courseRepository.deleteById(id)
        else
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Course not found")

    override fun findCourseByIdOrThrowException(courseId: UUID): Course =
        courseRepository.findById(courseId)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Course not found") }

    private fun CourseRequest.createCourse(): Course =
        Course(name, year)

    private fun Course.buildCourseResponse(): CourseResponse =
        CourseResponse(id, name, year)
}