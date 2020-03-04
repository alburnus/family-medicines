package com.github.alburnus.service;

import com.github.alburnus.entity.report.ReportResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WhereMedicineMapperIT {

    @Autowired
    private WhereMedicineMapper whereMedicineMapper;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void shouldReturnReportResult() {
        // Given
        String data = "";
        try {
            Resource resource = resourceLoader.getResource("classpath:response/gdziePoLekResponse.html");
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
        }

        Document document = Jsoup.parse(data);

        // When
        ReportResult reportResult = whereMedicineMapper.mapToResult(document);
        // Then

        //assertThat("").isEmpty();
    }
}