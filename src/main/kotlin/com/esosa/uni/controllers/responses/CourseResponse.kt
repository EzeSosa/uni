package com.esosa.uni.controllers.responses

import java.io.Serializable
import java.util.UUID

data class CourseResponse(
    val id: UUID,
    val name: String,
    val year: Int
) : Serializable