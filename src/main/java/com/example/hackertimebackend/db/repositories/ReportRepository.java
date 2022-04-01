package com.example.hackertimebackend.db.repositories;

import com.example.hackertimebackend.db.models.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, String> {
}
