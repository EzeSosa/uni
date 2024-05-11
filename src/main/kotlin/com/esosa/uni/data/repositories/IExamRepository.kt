package com.esosa.uni.data.repositories

import com.esosa.uni.data.models.Exam
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IExamRepository : JpaRepository<Exam, UUID> {}