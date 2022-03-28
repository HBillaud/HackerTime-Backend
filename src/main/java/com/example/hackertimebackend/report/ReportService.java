package com.example.hackertimebackend.report;

import com.example.hackertimebackend.commons.ReportRequest;
import com.example.hackertimebackend.db.models.Report;

public interface ReportService {
    Report generateReport(ReportRequest request) throws Exception;
}
