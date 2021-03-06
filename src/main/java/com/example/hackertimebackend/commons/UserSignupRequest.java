package com.example.hackertimebackend.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequest {
    private String name;
    private String email;
    private String password;
    private String companyName;
}
