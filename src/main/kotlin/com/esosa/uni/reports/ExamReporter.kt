package com.esosa.uni.reports

import com.esosa.uni.controllers.responses.ExamResponse
import com.esosa.uni.data.models.User
import com.esosa.uni.data.repositories.IUserRepository
import com.esosa.uni.email.EmailService
import com.esosa.uni.services.interfaces.IUserService
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDate

@EnableAsync
@EnableScheduling
@Configuration
class ExamReporter(
    private val userService: IUserService,
    private val userRepository: IUserRepository,
    private val emailService: EmailService
) {

    @Async
    @Scheduled(cron = "0 0 0 1 * *")
    fun reportWeeklyExams() {
        userRepository.findAll().forEach { user ->
            val mailBody = getWeeklyExams(user)
                .joinToString("\n") { it.mapToMailLine() }

            if (mailBody.isNotEmpty())
                emailService.sendEmail(user.email, "Monthly Exam Report", mailBody)
        }
    }

    fun getWeeklyExams(user: User): List<ExamResponse> =
        userService.getUserExams(
            user.id,
            LocalDate.now().minusMonths(1),
            LocalDate.now(),
            null,
            null)

    fun ExamResponse.mapToMailLine() =
        "Course: $courseName | Date: $date | Grade: $grade"
}