package com.example.hackertimebackend.auth;

import com.example.hackertimebackend.commons.UserLoginRequest;
import com.example.hackertimebackend.commons.UserLoginResponse;
import com.example.hackertimebackend.commons.UserSignupRequest;

public interface AuthService {
    UserLoginResponse login(UserLoginRequest request) throws Exception;

    UserLoginResponse signup(UserSignupRequest request) throws Exception;

    void verify(String id, String code) throws Exception;
}
