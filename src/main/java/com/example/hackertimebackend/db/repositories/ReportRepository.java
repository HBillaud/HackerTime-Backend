package com.example.hackertimebackend.db.repositories;

import com.example.hackertimebackend.db.models.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReportRepository extends MongoRepository<Report, String> {
    Optional<Report> findReportByRoomCode(String roomCode) throws Exception;
}
