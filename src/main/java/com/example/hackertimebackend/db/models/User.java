package com.example.hackertimebackend.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("user")
public class User {
    @Id
    private String email;
    private String name;
    private String companyName;
    private String password;
    private ObjectId[] reportIds;
    private Boolean verified;
    private String verificationCode;
    @CreatedDate
    private Date createdDate;

    @Override
    public String toString() {
        return String.format(
                "User[email=%s, name=%s, companyName=%s]", email, name, companyName);
    }

    public ObjectId[] addReportId(ObjectId id) {
        List<ObjectId> list = new ArrayList<>();
        Collections.addAll(list, reportIds);
        list.add(id);

        ObjectId[] reportList = new ObjectId[list.size()];
        for (int i = 0; i < list.size(); i++) {
            reportList[i] = list.get(i);
        }

        this.reportIds = reportList;
        return this.reportIds;
    }
}
