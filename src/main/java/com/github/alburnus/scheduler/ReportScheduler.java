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

    @Scheduled(cron = "${scheduler.cron.checkAvailability}")
    public void checkCurrentAvailability() {
        log.info("Running scheduler for current availability");
        medicineReportService.getAndSaveReport();
        log.info("Finished running scheduler for current availability.");
    }

    @Scheduled(cron = "${scheduler.cron.dailyReport}")
    public void runDailyReport() {
        log.info("Running scheduler for daily report.");
        medicineReportService.sendDailyReport();
        log.info("Finished scheduler for daily report.");
    }

    @Scheduled(initialDelay = 0, fixedDelay = Long.MAX_VALUE)
    public void firstRun() {
        log.info("First run for scheduler.");
        medicineReportService.getAndSaveReport();
        log.info("Finished first run for scheduler.");
    }

    @Scheduled(cron = "${scheduler.cron.serviceAlive}")
    public void serviceAlive() {
        log.info("serviceAlive at [{}]", LocalDateTime.now());
    }
}
