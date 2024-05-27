package com.esosa.uni.email

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    @Async
    fun sendEmail(to: String, subject: String, body: String) {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(body, false)

        mailSender.send(message)
    }
}