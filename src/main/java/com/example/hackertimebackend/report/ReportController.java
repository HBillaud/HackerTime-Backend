package com.example.hackertimebackend.report;

import com.example.hackertimebackend.commons.ReportRequest;
import com.example.hackertimebackend.commons.ReportRequestRoomCode;
import com.example.hackertimebackend.db.models.Report;
import com.example.hackertimebackend.db.repositories.ReportRepository;
import com.example.hackertimebackend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.hackertimebackend.utils.ApiConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReportRepository reportRepository;

    @PostMapping(END_MEETING_PATH)
    public ResponseEntity generateReport(
            @RequestBody @Valid ReportRequest body, @RequestHeader(value = "Authorization") String bearerToken)
            throws Exception {
        log.info("[POST] meeting ended; generating report: {}", body);
        try {
            Report report = reportService.updateReport(body);
            userService.addReport(report.getId(), bearerToken.substring(7));
            ResponseEntity response = new ResponseEntity(report, HttpStatus.CREATED);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }

    @GetMapping("get-question")
    public ResponseEntity getQuestion(
            @RequestParam @Valid ReportRequestRoomCode roomCode) throws Exception {
        log.info("[GET] request to find Report with roomCode {}", roomCode.getRoomCode());
        try {
            Report report = reportRepository.findReportByRoomCode(roomCode.getRoomCode()).orElseThrow(
                    () -> new Exception("Could not find report!"));
            return new ResponseEntity(report.getQuestion(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }
}
