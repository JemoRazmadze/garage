package com.example.garage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.Map;

@Getter
@Setter
public class PageResponse {
    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_elements")
    private long totalElements;

    private Map<String, CarResponse> content;

    public PageResponse(Map<String, CarResponse> content, Pageable pageable, long total) {
        this.currentPage = pageable.getPageNumber();
        this.totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
        this.totalElements = total;
        this.content = content;
    }
}
