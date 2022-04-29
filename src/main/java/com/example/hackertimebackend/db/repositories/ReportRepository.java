package com.example.hackertimebackend.db.repositories;

import com.example.hackertimebackend.db.models.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReportRepository extends MongoRepository<Report, String> {
    @Query("{'roomCode' : ?0}")
    Optional<Report> findReportByRoomCode(String roomCode);
}
