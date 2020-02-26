package com.github.alburnus.scheduler;

import com.github.alburnus.service.MedicineReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    @Scheduled(initialDelay = 0, fixedDelay = Long.MAX_VALUE)
    public void firstRun() {
        log.info("First run for scheduler.");
        medicineReportService.getAndSaveReport();
        log.info("Finished first run for scheduler.");
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void serviceAlive() {
        log.info("serviceAlive at [{}]", LocalDateTime.now());
    }
}
