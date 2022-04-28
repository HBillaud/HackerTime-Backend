package com.example.hackertimebackend.commons;

import com.example.hackertimebackend.db.models.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String email;
    private String name;
    private String companyName;
    private ArrayList<Report> reports;
}