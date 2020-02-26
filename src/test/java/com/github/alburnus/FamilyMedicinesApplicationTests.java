package com.github.alburnus;

import com.github.alburnus.service.EmailService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FamilyMedicinesApplicationTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void shouldReturnNumberOfPharmaciesWhereMedicineIsAvailable() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.gdziepolek.pl/produkty/28209/euthyrox-n-150-tabletki/apteki?pvId=86240&Lng=18.64663840000003&Lat=54.35202520000001&Rad=15000&Loc=Gda%C5%84sk")
                    .get();
            Element meta = doc.select("meta").get(4);
            String content = meta.attr("content");

            String resultToRegex = getStringForPattern(content, "\\s\\d+\\s(aptekach)");
            String result = getStringForPattern(resultToRegex, "\\d+");
            Long count = new Long(result);

            System.out.println(content);
            System.out.println("Count:" + count);

            emailService.sendSimpleMessage("", "", "Count: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStringForPattern(String content, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);
        matcher.find();
        return matcher.group();
    }
}
