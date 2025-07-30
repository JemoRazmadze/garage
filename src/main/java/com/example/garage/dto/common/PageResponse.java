package com.example.garage.dto.common;

import com.example.garage.dto.car.CarResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class PageResponse {
    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_elements")
    private long totalElements;

    private Object content;

    public PageResponse(List<CarResponse> response, Pageable pageable, long total) {
        this.currentPage = pageable.getPageNumber();
        this.totalPages = pageable.getPageSize();
        this.totalElements = total;
        this.content = response;
    }
}
