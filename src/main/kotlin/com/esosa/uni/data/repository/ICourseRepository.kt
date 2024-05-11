package com.esosa.uni.data.repository

import com.esosa.uni.data.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ICourseRepository : JpaRepository<Course, UUID> {
    fun existsByNameAndYear(name: String, year: Int) : Boolean
}