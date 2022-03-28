package com.example.hackertimebackend.report;

import com.example.hackertimebackend.commons.ReportRequest;
import com.example.hackertimebackend.db.models.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.hackertimebackend.utils.ApiConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping()
    public ResponseEntity generateReport(
            @RequestBody @Valid ReportRequest body
    ) throws Exception {
        log.info("[POST] meeting ended; generating report: {}", body);
        try {
            Report report = reportService.generateReport();
            ResponseEntity response = new ResponseEntity(report, HttpStatus.CREATED);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }
}
