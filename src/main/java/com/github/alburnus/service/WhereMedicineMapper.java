package com.github.alburnus.service;

import com.github.alburnus.entity.report.ReportResult;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class WhereMedicineMapper implements SourceMapper {

    @Override
    public ReportResult mapToResult(Document document) {
        return null;
    }
}
