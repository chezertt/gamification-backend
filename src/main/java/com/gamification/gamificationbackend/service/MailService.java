package com.gamification.gamificationbackend.service;

public interface MailService {

    void sendMail(String receiver, String subject, String message);
}
