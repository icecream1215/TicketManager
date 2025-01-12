package com.example.TicketManager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="performances")
@JsonIgnoreProperties(ignoreUnknown = true)  // Unknown 필드를 무시
@Data
public class Performance {
    @Id
    @JacksonXmlProperty(localName = "mt20id")
    @Column(name = "id")         // DB 컬럼명 매핑
    private String id;          // 공연 ID
    @JacksonXmlProperty(localName = "prfnm")
    @Column(name = "name")
    private String name;        // 공연명
    @JacksonXmlProperty(localName = "prfpdfrom")
    @Column(name = "start_date")
    private String startDate;   // 공연 시작일
    @JacksonXmlProperty(localName = "prfpdto")
    @Column(name = "end_date")
    private String endDate;     // 공연 종료일
    @JacksonXmlProperty(localName = "fcltynm")
    @Column(name = "location")
    private String location;    // 공연 장소
    @JacksonXmlProperty(localName = "genrenm")
    @Column(name = "genre")
    private String genre;       // 장르
    @JacksonXmlProperty(localName = "poster")
    @Column(name = "poster")
    private String poster;      // 포스터 이미지 URL
}
