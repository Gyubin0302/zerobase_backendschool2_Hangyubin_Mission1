package com.example.mission1.domain.wifi;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchWifi {
    private int id;
    private BigDecimal lat;
    private BigDecimal lnt;
    private LocalDateTime search_dttm;
    private boolean delete_yn;

    @Builder
    public SearchWifi(int id, BigDecimal lat, BigDecimal lnt, LocalDateTime search_dttm, boolean delete_yn) {
        this.id = id;
        this.lat = lat;
        this.lnt = lnt;
        this.search_dttm = search_dttm;
        this.delete_yn = delete_yn;
    }
}
