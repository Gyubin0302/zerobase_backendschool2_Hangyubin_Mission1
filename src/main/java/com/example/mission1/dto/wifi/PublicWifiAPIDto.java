package com.example.mission1.dto.wifi;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter // getter
public class PublicWifiAPIDto {
    private List<HashMap<String, String>> row;

    public PublicWifiAPIDto() {
        this.row = new ArrayList<>();
    }

    public void addWifiList(List<HashMap<String, String>> wifi_map) {
        this.row.addAll(wifi_map);
    }

}
