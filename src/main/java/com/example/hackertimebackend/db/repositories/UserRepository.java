package com.example.hackertimebackend.db.repositories;

import com.example.hackertimebackend.db.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
