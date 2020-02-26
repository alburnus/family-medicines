package com.github.alburnus.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResponseAnalyzerService {

    public String getImportantResponse(String responseToAnalyze) {
        return getStringForPattern(responseToAnalyze, "\\s\\d+\\s(aptekach)");

    }

    public Long getResultFromResponse(String responseToAnalyze) {
        String result = getStringForPattern(responseToAnalyze, "\\d+");
        return new Long(result);
    }

    private String getStringForPattern(String content, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);
        matcher.find();
        return matcher.group();
    }
}
