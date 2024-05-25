package com.esosa.uni.services.interfaces

import com.esosa.uni.controllers.requests.CourseRequest
import com.esosa.uni.controllers.responses.CourseResponse
import com.esosa.uni.data.models.Course
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import java.util.UUID

interface ICourseService {
    @Cacheable(cacheNames = ["courses"], key = "'#key'")
    fun getCourses(): List<CourseResponse>

    @CacheEvict(cacheNames = ["courses"], key = "'#key'", allEntries = true)
    fun createCourse(courseRequest: CourseRequest): CourseResponse

    @CacheEvict(cacheNames = ["courses"], key = "'#key'", allEntries = true)
    fun updateCourse(courseRequest: CourseRequest, id: UUID): CourseResponse

    @CacheEvict(cacheNames = ["courses"], key = "'#key'", allEntries = true)
    fun deleteCourse(id: UUID)

    fun findCourseByIdOrThrowException(courseId: UUID): Course
}