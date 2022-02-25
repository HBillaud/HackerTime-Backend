package com.example.hackertimebackend.stubs;

import com.example.hackertimebackend.commons.UserResponse;

public class LoginStubs {
    public UserResponse getUserResponse() {
        return new UserResponse(
                "John Wick",
                "hpcbillaud@gmail.com",
                "N/A",
                "fawf"
        );
    }
}
