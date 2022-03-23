package com.example.hackertimebackend.auth;

import com.example.hackertimebackend.commons.UserLoginRequest;
import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.commons.UserSignupRequest;

public interface AuthService {
    UserResponse login(UserLoginRequest request) throws Exception;

    UserResponse signup(UserSignupRequest request) throws Exception;

    void verify(String id, String code) throws Exception;
}
