package com.github.alburnus.service;

import com.github.alburnus.entity.MedicineReport;
import com.github.alburnus.entity.MedicineRequestConfig;
import com.github.alburnus.repository.MedicineReportRepository;
import com.github.alburnus.repository.MedicineRequestConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class MedicineReportService {

    private final MedicineRequestService medicineRequestService;

    private final ResponseAnalyzerService responseAnalyzerService;

    private final MedicineRequestConfigRepository medicineRequestConfigRepository;

    private final MedicineReportRepository medicineReportRepository;

    private final EmailService emailService;

    public MedicineReportService(MedicineRequestService medicineRequestService,
                                 ResponseAnalyzerService responseAnalyzerService,
                                 MedicineRequestConfigRepository medicineRequestConfigRepository,
                                 MedicineReportRepository medicineReportRepository, EmailService emailService) {
        this.medicineRequestService = medicineRequestService;
        this.responseAnalyzerService = responseAnalyzerService;
        this.medicineRequestConfigRepository = medicineRequestConfigRepository;
        this.medicineReportRepository = medicineReportRepository;
        this.emailService = emailService;
    }

    public void getAndSaveReport() {
        List<MedicineRequestConfig> requestConfigs = medicineRequestConfigRepository.findAll();
        log.info("Got [{}] request configurations", requestConfigs.size());
        for (MedicineRequestConfig requestConfig : requestConfigs) {
            String urlToCall = requestConfig.getUrl();
            String response = medicineRequestService.callEndpoint(urlToCall);
            String importantResponse = responseAnalyzerService.getImportantResponse(response);
            long countedResult = responseAnalyzerService.getResultFromResponse(importantResponse);

            MedicineReport report = new MedicineReport();
            report.setCountedResult(countedResult);
            report.setMedicine(requestConfig.getMedicine());
            report.setResponse(response);
            report.setCreatedDate(LocalDateTime.now());
            report.setReportDate(LocalDate.now());
            report.setMedicineRequestConfig(requestConfig);

            String message = requestConfig.getMedicine().getName() + " : " + countedResult;
            log.info("The medicine [{}] is in [{}] pharmacies", requestConfig.getMedicine().getName(), countedResult);
            if (requestConfig.getThreshold() >= countedResult) {
                log.warn("Alert message for [{}]", requestConfig.getMedicine().getName());
                emailService.sendSimpleMessage(
                        requestConfig.getEmail(),
                        "[ALERT] Report",
                        message + ". Threshold is: " + requestConfig.getThreshold());
            }

            medicineReportRepository.save(report);
            log.info("Saved report for medicine [{}]", requestConfig.getMedicine().getName());
        }
    }

    public void sendDailyReport() {
        LocalDate today = LocalDate.now();
        List<MedicineReport> reports = medicineReportRepository.getAllByReportDate(today);
        Map<String, List<MedicineReport>> reportsGroupedByEmail = new HashMap<>();
        reports.forEach(medicineReport -> {
            String keyEmail = medicineReport.getMedicineRequestConfig().getEmail();
            if (!reportsGroupedByEmail.containsKey(keyEmail)) {
                reportsGroupedByEmail.put(medicineReport.getMedicineRequestConfig().getEmail(), new ArrayList<>());
            }
            List<MedicineReport> medicineReports = reportsGroupedByEmail.get(keyEmail);
            medicineReports.add(medicineReport);
            reportsGroupedByEmail.put(keyEmail, medicineReports);
        });

        reportsGroupedByEmail.forEach((s, medicineReports) -> {
            StringBuilder messageBuilder = new StringBuilder();
            medicineReports.forEach(medicineReport -> {
                messageBuilder.append("Medicine: <b>" + medicineReport.getMedicine().getName() + "</b>" +
                        " at: " + medicineReport.getCreatedDate() + " o'clock" +
                        " was available in: <b>" + medicineReport.getCountedResult() + "</b> pharmacies. Threshold is: " +
                        "<b>" + medicineReport.getMedicineRequestConfig().getThreshold() + "</b><br />");
            });
            log.info("Sending daily report");
            emailService.sendMimeMessage(s, "Daily report", messageBuilder.toString());
        });
    }
}
