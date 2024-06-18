package com.gamification.gamificationbackend.service.impl;

import com.gamification.gamificationbackend.enumeration.ErrorType;
import com.gamification.gamificationbackend.exception.InternalServerErrorException;
import com.gamification.gamificationbackend.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final Environment environment;

    @Override
    public void sendMail(String receiver, String subject, String message) {
        try {
            MimeMessage mimeMessage = buildMimeMessage(receiver, subject);
            mimeMessage.setContent(message, "text/html; charset=UTF-8");
            log.info("Trying to send email to {} with subject: {}", receiver, subject);
            mailSender.send(mimeMessage);
            log.info("Successfully sent email to {} with subject: {}", receiver, subject);
        } catch (MessagingException ex) {
            log.error("An error occurred while sending email to {} with subject: {}", receiver, subject);
            throw new InternalServerErrorException(ErrorType.TECH, ex.getMessage());
        }
    }

    private MimeMessage buildMimeMessage(String receiver, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, "utf-8");
        mailMessage.setFrom(environment.getProperty("spring.mail.username"));
        mailMessage.setTo(receiver);
        mimeMessage.setSubject(subject);
        return mimeMessage;
    }
}
