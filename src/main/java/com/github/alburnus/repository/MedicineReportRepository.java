package com.github.alburnus.repository;

import com.github.alburnus.entity.MedicineReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicineReportRepository extends JpaRepository<MedicineReport, Long> {

    List<MedicineReport> getAllByReportDate(LocalDate reportDate);
}
