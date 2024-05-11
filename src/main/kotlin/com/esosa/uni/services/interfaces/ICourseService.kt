package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.CourseRequest
import com.esosa.uni.controllers.responses.CourseResponse
import com.esosa.uni.data.models.Course
import java.util.UUID

interface ICourseService {
    fun getCourses() : List<CourseResponse>
    fun createCourse(courseRequest: CourseRequest) : CourseResponse
    fun findCourseByIdOrThrowException(courseId: UUID) : Course
}