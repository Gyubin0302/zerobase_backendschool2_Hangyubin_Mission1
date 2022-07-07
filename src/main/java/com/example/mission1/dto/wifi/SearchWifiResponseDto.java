package com.example.mission1.dto.wifi;

import com.example.mission1.domain.wifi.SearchWifi;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchWifiResponseDto {
    private int id;
    private String lat;
    private String lnt;
    private LocalDateTime search_dttm;

    public SearchWifiResponseDto(SearchWifi entity) {
        this.id = entity.getId();
        this.lat = entity.getLat().stripTrailingZeros().toString();
        this.lnt = entity.getLnt().stripTrailingZeros().toString();
        this.search_dttm = entity.getSearch_dttm();
    }
}
