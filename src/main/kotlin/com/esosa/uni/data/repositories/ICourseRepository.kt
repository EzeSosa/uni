package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ICourseRepository : JpaRepository<Course, UUID> {
    fun existsByNameAndYear(name: String, year: Int) : Boolean
}