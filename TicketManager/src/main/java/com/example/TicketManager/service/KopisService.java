package com.example.TicketManager.service;

import com.example.TicketManager.model.Performance;
import com.example.TicketManager.model.PerformanceList;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class KopisService {
    @Value("${kopis.api.url}")
    private String apiUrl;

    @Value("${kopis.api.key}")
    private String apiKey;

    public List<Performance> getPerformances(String showName, String startDate, String endDate) {
        // 날짜 형식 변환 (yyyy-MM-dd -> yyyyMMdd)
        if (startDate != null && startDate.length() == 10) {
            startDate = startDate.replace("-", "");  // 2025-01-12 -> 20250112
        }
        if (endDate != null && endDate.length() == 10) {
            endDate = endDate.replace("-", "");  // 2025-01-12 -> 20250112
        }

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("service", apiKey)
                .queryParam("stdate", startDate)
                .queryParam("eddate", endDate)
                .queryParam("cpage", 1)
                .queryParam("rows", 10)
                .queryParam("shprfnm", showName)
                .toUriString();

        try {
            HttpClient client = HttpClient.newHttpClient();

            // HttpRequest 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            // 요청 보내기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String xmlResponse = response.body();
            System.out.println("API 응답: " + xmlResponse);  // 응답 XML 출력

            // XML 응답 처리
            XmlMapper xmlMapper = new XmlMapper();
            PerformanceList performanceList = xmlMapper.readValue(xmlResponse, PerformanceList.class);

            if (performanceList != null && performanceList.getPerformances() != null) {
                return performanceList.getPerformances();  // 공연 정보 리턴
            } else {
                System.out.println("PerformanceList or performances is null.");
                return new ArrayList<>();  // 빈 리스트 반환
            }
        } catch (Exception e) {
            throw new RuntimeException("API 요청 또는 데이터 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }
}