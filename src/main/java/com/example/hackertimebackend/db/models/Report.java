package com.example.hackertimebackend.db.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("report")
public class Report {
    @Id
    private ObjectId id;
    private String question;
    private String code;
    private String output;
    private String intervieweeName;
    @CreatedDate
    private Date createdDate;
}
