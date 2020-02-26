package com.github.alburnus.repository;

import com.github.alburnus.entity.MedicineRequestConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRequestConfigRepository extends JpaRepository<MedicineRequestConfig, Long> {
}
