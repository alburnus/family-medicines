package com.github.alburnus.service;

import com.github.alburnus.entity.MedicineReport;
import com.github.alburnus.entity.MedicineRequestConfig;
import com.github.alburnus.repository.MedicineReportRepository;
import com.github.alburnus.repository.MedicineRequestConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
            report.setMedicineRequestConfig(requestConfig);

            String message = requestConfig.getMedicine().getName() + " : " + countedResult;
            if (requestConfig.getThreshold() < countedResult) {
                emailService.sendSimpleMessage(
                        requestConfig.getEmail(),
                        "[INFO] Report",
                        message);
            } else {
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
}
