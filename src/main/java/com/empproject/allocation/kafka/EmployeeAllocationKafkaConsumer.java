package com.empproject.allocation.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@EnableKafka
@Service
public class EmployeeAllocationKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JavaMailSender mailSender;

    public EmployeeAllocationKafkaConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "employee-allocation-topic", groupId = "employee-allocations-group")
    public void consumeMessage(String message) {
        log.info("EmployeeAllocationKafkaConsumer: consumeMessage Starts ");
        log.info("Received Kafka message: " + message);
        sendEmailNotification(message);
    }

    private void sendEmailNotification(String message) {
        log.info("EmployeeAllocationKafkaConsumer: sendEmailNotification Starts ");
        String recipientEmail = "ramprasath.r@gmail.com";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientEmail);
            helper.setSubject("Employee Allocation Notification");
            helper.setText("Dear User, \n\n" + message + "\n\nRegards, \nCompany HR");
            mailSender.send(mimeMessage);
            log.info("Email sent to: " + recipientEmail);
        } catch (MessagingException e) {
            log.error("Error sending email: " + e.getMessage());
        }
        log.info("EmployeeAllocationKafkaConsumer: sendEmailNotification Ends ");
    }
}

