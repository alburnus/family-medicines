package com.github.alburnus.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MedicineRequestService {


    public String callEndpoint(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .get();
            Element meta = doc.select("meta").get(4);
            return meta.attr("content");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
