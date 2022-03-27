package com.example.hackertimebackend.stubs;

import com.example.hackertimebackend.commons.UserLoginResponse;

public class LoginStubs {
    public UserLoginResponse getUserResponse() {
        return new UserLoginResponse(
                "John Wick",
                "hpcbillaud@gmail.com",
                "N/A",
                "fawf"
        );
    }
}
