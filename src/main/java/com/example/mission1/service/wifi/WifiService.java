package com.example.mission1.service.wifi;

import com.example.mission1.dao.wifi.WifiDao;
import com.example.mission1.domain.wifi.PublicWifi;
import com.example.mission1.domain.wifi.SearchWifi;
import com.example.mission1.dto.wifi.PublicWifiAPIDto;
import com.example.mission1.dto.wifi.PublicWifiReponseDto;
import com.example.mission1.dto.wifi.SearchWifiResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WifiService {
    private static final String API_KEY = "4e7064797a7262733130385855534d68";
    private final WifiDao wifiDao = new WifiDao();

    public int PublicWifiInsert() {
        int result = -1;
        PublicWifiAPIDto publicWifiAPIDto = new PublicWifiAPIDto();
        int publicWifiTotalCount = getPublicWifiTotalCount();
        int loopCnt = publicWifiTotalCount / 1000;

        try {

            for (int i = 0; i <= loopCnt; i++) {
                int start = 1 + (1000 * i);
                int end = (i + 1) * 1000;
                String url = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/TbPublicWifiInfo/" + start + "/" + end + "/";

                // OkHttp 클라이언트 객체 생성
                OkHttpClient client = new OkHttpClient();

                // GET request 객체 생성
                Request.Builder builder = new Request.Builder().url(url).get();

                Request request = builder.build();

                // OkHttp 클라이언트로 GET request 객체 전송
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        Gson gson = new Gson();
                        JsonElement element = JsonParser.parseString(body.string());
                        JsonArray jsonArray = (JsonArray) element.getAsJsonObject().get("TbPublicWifiInfo").getAsJsonObject().get("row");
                        publicWifiAPIDto.addWifiList(gson.fromJson(jsonArray, new TypeToken<List<HashMap<String, Object>>>() {}.getType()));
                    }
                } else {
                    System.out.println(response.code());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        List<PublicWifi> publicWifiList = new ArrayList<>();
        for (int i = 0; i < publicWifiAPIDto.getRow().size(); i++) {
            HashMap<String, String> request = publicWifiAPIDto.getRow().get(i);
            LocalDateTime dateTime = LocalDateTime.parse(request.get("WORK_DTTM"), formatter);

            PublicWifi publicWifi = PublicWifi.builder().
                    x_swifi_mgr_no(request.get("X_SWIFI_MGR_NO"))
                    .x_swifi_wrdofc(request.get("X_SWIFI_WRDOFC"))
                    .x_swifi_main_nm(request.get("X_SWIFI_MAIN_NM"))
                    .x_swifi_adres1(request.get("X_SWIFI_ADRES1"))
                    .x_swifi_adres2(request.get("X_SWIFI_ADRES2"))
                    .x_swifi_instl_floor(request.get("X_SWIFI_INSTL_FLOOR"))
                    .x_swifi_instl_ty(request.get("X_SWIFI_INSTL_TY"))
                    .x_swifi_instl_mby(request.get("X_SWIFI_INSTL_MBY"))
                    .x_swifi_svc_se(request.get("X_SWIFI_SVC_SE"))
                    .x_swifi_cmcwr(request.get("X_SWIFI_CMCWR"))
                    .x_swifi_cnstc_year(request.get("X_SWIFI_CNSTC_YEAR"))
                    .x_swifi_inout_door(request.get("X_SWIFI_INOUT_DOOR"))
                    .x_swifi_remars3(request.get("X_SWIFI_REMARS3"))
                    .lat(new BigDecimal(request.get("LAT")))
                    .lnt(new BigDecimal(request.get("LNT")))
                    .work_dttm(dateTime)
                    .build();

            publicWifiList.add(publicWifi);
        }

        int affectedRows = wifiDao.PublicWifiInsert(publicWifiList);
        if (publicWifiTotalCount == affectedRows) {
            result = affectedRows;
        }

        return result;
    }

    public List<PublicWifiReponseDto> getNearPublicWifiList(String x, String y) {
        BigDecimal b_x = new BigDecimal(x);
        BigDecimal b_y = new BigDecimal(y);

        SearchWifi searchWifi = SearchWifi.builder().lat(b_x).lnt(b_y).build();

        wifiDao.historyWifiInsert(searchWifi);

        return wifiDao.getNearPublicWifiList(b_x, b_y);
    }

    public List<SearchWifiResponseDto> historyList() {
        return wifiDao.histoyList().stream().map(SearchWifiResponseDto::new).collect(Collectors.toList());
    }

    public void deleteHistory(int id) {
        int history_id = wifiDao.selectOneHistory(id);

        if (history_id > 0) {
            wifiDao.deleteHistory(history_id);
        }
    }

    public int getPublicWifiTotalCount() {
        int result = 0;
        try {
            String url = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/TbPublicWifiInfo/1/5/";

            OkHttpClient client = new OkHttpClient();

            Request.Builder builder = new Request.Builder().url(url).get();

            Request request = builder.build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    JsonElement element = JsonParser.parseString(body.string());
                    result =  element.getAsJsonObject().get("TbPublicWifiInfo").getAsJsonObject().get("list_total_count").getAsInt();
                }
            } else {
                System.out.println(response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

     return result;
    }
}
