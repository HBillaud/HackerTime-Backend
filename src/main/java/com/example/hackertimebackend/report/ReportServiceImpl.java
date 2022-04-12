package com.example.hackertimebackend.report;

import com.example.hackertimebackend.commons.ReportRequest;
import com.example.hackertimebackend.db.models.Report;
import com.example.hackertimebackend.db.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public Report generateReport(ReportRequest request) throws Exception {
        try {
            Report report = Report.builder()
                    .id(new ObjectId())
                    .question(request.getQuestion())
                    .code(request.getCode())
                    .intervieweeName("")
                    .createdDate(new Date())
                    .build();

            reportRepository.save(report);
            return report;
        } catch (Exception e) {
            throw new Exception("Generating end-meeting report failed!");
        }
    }

    public ArrayList<Report> getReports(ObjectId[] arr) {
        ArrayList<Report> set = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            reportRepository.findById(arr[i].toString()).ifPresent(
                    Report -> set.add(Report)
            );
        }
        return set;
    }
}
