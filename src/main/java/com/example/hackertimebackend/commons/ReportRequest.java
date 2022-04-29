package com.example.hackertimebackend.commons;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    private String code;
    private String roomCode;
    private String intervieweeName;
    private String output;
}
