package com.example.TicketManager.service;

import com.example.TicketManager.model.Performance;
import com.example.TicketManager.model.PerformanceList;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class KopisService {
    @Value("${kopis.api.url}")
    private String apiUrl;

    @Value("${kopis.api.key}")
    private String apiKey;

    public List<Performance> getPerformances() {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("service", apiKey)
                .queryParam("stdate", "20241101")
                .queryParam("eddate", "20250630")
                .queryParam("cpage", 1)
                .queryParam("rows", 10)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(url, String.class);
        try {
            XmlMapper xmlMapper = new XmlMapper();
            PerformanceList performanceList = xmlMapper.readValue(xmlResponse, PerformanceList.class);

            // 데이터가 정상적으로 들어왔는지 확인
            if (performanceList != null && performanceList.getPerformances() != null) {
                return performanceList.getPerformances();  // 공연 정보 리턴
            } else {
                System.out.println("PerformanceList or performances is null.");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("API 요청 또는 데이터 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }
}