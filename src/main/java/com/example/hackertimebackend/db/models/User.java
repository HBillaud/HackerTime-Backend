package com.example.hackertimebackend.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String salt;
    private Boolean verified;

    @Override
    public String toString() {
        return String.format(
                "User[email=%s, name=%s, companyName=%s", email, name, companyName
        );
    }
}
