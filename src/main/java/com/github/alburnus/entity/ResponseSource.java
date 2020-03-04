package com.github.alburnus.entity;

import com.github.alburnus.service.SourceMapper;
import com.github.alburnus.service.WhereMedicineMapper;

public enum ResponseSource {

    GDZIE_PO_LEK(new WhereMedicineMapper());

    private SourceMapper sourceMapper;

    ResponseSource(SourceMapper sourceMapper) {
        this.sourceMapper = sourceMapper;
    }

    public SourceMapper getSourceMapper() {
        return sourceMapper;
    }
}
