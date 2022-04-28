package com.example.hackertimebackend.report;

import com.example.hackertimebackend.WebSocket.CodeStruct;
import com.example.hackertimebackend.WebSocket.RoomEndpoint;
import com.example.hackertimebackend.commons.CreateReport;
import com.example.hackertimebackend.commons.ReportRequest;
import com.example.hackertimebackend.db.models.Report;
import com.example.hackertimebackend.db.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private RoomEndpoint roomEndpoint;

    @Override
    public Report updateReport(ReportRequest request) throws Exception {
        CodeStruct codeStruct = new CodeStruct("c", request.getCode());
        Map<String, String> map = roomEndpoint.compile(codeStruct);

        // retrieve report based on room code
        return reportRepository.findReportByRoomCode(request.getRoomCode()).map(
                report -> {
                    report.setCode(request.getCode());
                    report.setOutput(map.get("stdout"));
                    report.setIntervieweeName(request.getIntervieweeName());

                    reportRepository.save(report);
                    return report;
                }
        ).orElseThrow(
                () -> new Exception("Failed to find report!")
        );
    }

    @Override
    public Report generateReport(CreateReport request) throws Exception {
        try {
            Report report = Report.builder()
                    .id(new ObjectId())
                    .question(request.getQuestion())
                    .roomCode(request.getRoomCode())
                    .createdDate(new Date())
                    .build();

            reportRepository.save(report);
            return report;
        } catch (Exception e) {
            throw new Exception("Creation of report failed!");
        }
    }

    @Override
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
