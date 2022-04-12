package com.example.hackertimebackend.report;

import com.example.hackertimebackend.commons.ReportRequest;
import com.example.hackertimebackend.db.models.Report;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public interface ReportService {
    Report generateReport(ReportRequest request) throws Exception;
    ArrayList<Report> getReports(ObjectId[] arr);
}
