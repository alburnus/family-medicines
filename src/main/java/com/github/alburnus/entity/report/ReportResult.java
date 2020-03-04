package com.github.alburnus.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResult implements Serializable {

    private Long availability;

    private Map<String, String> stockStatistics;

    private List<PharmacyStock> pharmacyStocks;
}
