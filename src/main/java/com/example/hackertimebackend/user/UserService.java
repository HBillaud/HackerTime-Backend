package com.example.hackertimebackend.user;

import com.example.hackertimebackend.commons.UserResponse;
import org.bson.types.ObjectId;

public interface UserService {
    UserResponse getUser(String token) throws Exception;
    UserResponse addReport(ObjectId reportId, String token) throws Exception;
}
