package com.esosa.uni.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    info = Info(
        title = "Speficiation - Uni API",
        description = "Uni API that allows a user to manage their courses, inscriptions to courses and exams.",
        version = "1.0.0",
    ),
    servers = [
        Server(
            description = "LOCAL ENV",
            url = "http://localhost:8080/"
        )
    ],
    security = [
        SecurityRequirement(
            name = "BearerAuthorization"
        )
    ]
)
@SecurityScheme(
    name = "BearerAuthorization",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER
)
class OpenAPIConfiguration {}