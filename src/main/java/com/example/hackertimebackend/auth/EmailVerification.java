package com.example.hackertimebackend.auth;

import com.example.hackertimebackend.db.models.User;

import javax.mail.MessagingException;

public interface EmailVerification {
    void sendVerificationEmail(User user) throws MessagingException;
    boolean verifyUser(String id, String code) throws Exception;
}
