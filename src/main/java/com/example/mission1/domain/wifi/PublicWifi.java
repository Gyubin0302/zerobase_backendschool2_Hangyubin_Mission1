package com.example.mission1.domain.wifi;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter // getter
@NoArgsConstructor // 기본 생성자
public class PublicWifi {

    private String x_swifi_mgr_no; // 관리번호
    private String x_swifi_wrdofc; // 자치구
    private String x_swifi_main_nm; // 와이파이명
    private String x_swifi_adres1; // 도로명주소
    private String x_swifi_adres2; // 상세주소
    private String x_swifi_instl_floor; // 설치위치(층)
    private String x_swifi_instl_ty; // 설치유형
    private String x_swifi_instl_mby; // 설치기관
    private String x_swifi_svc_se; // 서비스구분
    private String x_swifi_cmcwr; // 망구분
    private String x_swifi_cnstc_year; // 설치년도
    private String x_swifi_inout_door; // 실내외구분
    private String x_swifi_remars3; // WIFI접속환경
    private BigDecimal lat; // X좌표
    private BigDecimal lnt; // Y좌표
    private LocalDateTime work_dttm; // 작업일자


    @Builder
    public PublicWifi(String x_swifi_mgr_no, String x_swifi_wrdofc, String x_swifi_main_nm, String x_swifi_adres1, String x_swifi_adres2, String x_swifi_instl_floor, String x_swifi_instl_ty, String x_swifi_instl_mby, String x_swifi_svc_se, String x_swifi_cmcwr, String x_swifi_cnstc_year, String x_swifi_inout_door, String x_swifi_remars3, BigDecimal lat, BigDecimal lnt, LocalDateTime work_dttm) {
        this.x_swifi_mgr_no = x_swifi_mgr_no;
        this.x_swifi_wrdofc = x_swifi_wrdofc;
        this.x_swifi_main_nm = x_swifi_main_nm;
        this.x_swifi_adres1 = x_swifi_adres1;
        this.x_swifi_adres2 = x_swifi_adres2;
        this.x_swifi_instl_floor = x_swifi_instl_floor;
        this.x_swifi_instl_ty = x_swifi_instl_ty;
        this.x_swifi_instl_mby = x_swifi_instl_mby;
        this.x_swifi_svc_se = x_swifi_svc_se;
        this.x_swifi_cmcwr = x_swifi_cmcwr;
        this.x_swifi_cnstc_year = x_swifi_cnstc_year;
        this.x_swifi_inout_door = x_swifi_inout_door;
        this.x_swifi_remars3 = x_swifi_remars3;
        this.lat = lat;
        this.lnt = lnt;
        this.work_dttm = work_dttm;
    }
}
