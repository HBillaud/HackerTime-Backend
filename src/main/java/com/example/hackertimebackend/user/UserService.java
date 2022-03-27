package com.example.hackertimebackend.user;

import com.example.hackertimebackend.commons.UserResponse;

public interface UserService {
    UserResponse getUser(String id) throws Exception;
}
