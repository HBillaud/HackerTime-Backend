package com.example.hackertimebackend.report;

import com.example.hackertimebackend.db.models.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    public Report generateReport() {
        Report report = Report.builder()


                .build();
        // generate report
        // save report to DB
        // update array of reportIds of corresponding User
        // return newly generated Report
        return null;
    }
}
