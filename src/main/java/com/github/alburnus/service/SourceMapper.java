package com.github.alburnus.service;

import com.github.alburnus.entity.report.ReportResult;
import org.jsoup.nodes.Document;

public interface SourceMapper {

    ReportResult mapToResult(Document document);
}
