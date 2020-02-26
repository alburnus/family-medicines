package com.github.alburnus.repository;

import com.github.alburnus.entity.MedicineReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineReportRepository extends JpaRepository<MedicineReport, Long> {
}
