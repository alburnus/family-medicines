package com.github.alburnus.scheduler;

import com.github.alburnus.service.MedicineReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReportScheduler {

    private final MedicineReportService medicineReportService;

    public ReportScheduler(MedicineReportService medicineReportService) {
        this.medicineReportService = medicineReportService;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void reportCurrentTime() {
        log.info("Running scheduler.");
        medicineReportService.getAndSaveReport();
        log.info("Stopped running scheduler.");
    }
}
