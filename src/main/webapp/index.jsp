<%@ page import="com.example.mission1.dto.wifi.PublicWifiReponseDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.mission1.service.wifi.WifiService" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>와이파이 정보 구하기</title>
        <style>
            .cate button {
                background-color: #FFF;
                cursor: pointer;
                border-style: none none none none;
            }
            .cate button:not(:first-of-type){
                border-left: 1px solid #000;
            }
            #customers {
                font-family: Arial, Helvetica, sans-serif;
                border-collapse: collapse;
                width: 100%;
            }

            #customers td, #customers th {
                border: 1px solid #ddd;
                padding: 8px;
            }

            #customers tr:nth-child(odd){
                background-color: #f2f2f2;
            }

            #customers tr:hover {
                background-color: #ddd;
            }

            #customers th {
                padding-top: 12px;
                padding-bottom: 12px;
                text-align: center;
                background-color: #04AA6D;
                color: white;
            }

            .empty-list {
                text-align: center;
                padding: 20px !important;
            }

            .mb-16 {
                margin-bottom: 16px;
            }
        </style>
    </head>
    <body>
        <h1>와이파이 정보 구하기</h1>
        <div class="cate mb-16">
            <button onclick="location='index.jsp'">홈</button>
            <button onclick="location='history.jsp'">위치 히스토리 목록</button>
            <button onclick="location='load-wifi.jsp'">Open API 와이파이 정보 가져오기</button>
        </div>
        <%
            String lat = request.getParameter("lat") == null ? "0.0" : request.getParameter("lat");
            String lnt = request.getParameter("lnt") == null ? "0.0" :request.getParameter("lnt");

            List<PublicWifiReponseDto> wifi_list = new ArrayList<>();

            if(!lat.equals("0.0") && !"".equals(lat)
                    && !lnt.equals("0.0") && !"".equals(lnt)) {
                WifiService wifiService = new WifiService();
                wifi_list = wifiService.getNearPublicWifiList(lat, lnt);
            }
        %>
        <div class="mb-16">
            <label for="lat">LAT:</label>
            <input type="text" id="lat" name="lat" value="<%=lat%>">,
            <label for="lnt">LNT:</label>
            <input type="text" id="lnt" name="lnt" value="<%=lnt%>">
            <button onclick="currentPosition()">내 위치 가져오기</button>
            <button onclick="getNearPublicWifiList()">근처 WIPI정보 보기</button>
        </div>


        <table id="customers">
            <tr>
                <th>거리(Km)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명주소</th>
                <th>상세주소</th>
                <th>설치위치(층)</th>
                <th>설치유형</th>
                <th>설치기관</th>
                <th>서비스구분</th>
                <th>망구분</th>
                <th>설치년도</th>
                <th>실내외구분</th>
                <th>WIFI접속환경</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>작업일자</th>
            </tr>
            <%
                if (wifi_list.size() == 0) {
            %>
                <tr>
                    <td class="empty-list" colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
                </tr>
            <% } else {
                for (int i = 0; i < wifi_list.size(); i++) {
                    PublicWifiReponseDto publicWifi = wifi_list.get(i);
            %>

                <tr>
                    <td><%=publicWifi.getDistance()%></td>
                    <td><%=publicWifi.getX_swifi_mgr_no()%></td>
                    <td><%=publicWifi.getX_swifi_wrdofc()%></td>
                    <td><%=publicWifi.getX_swifi_main_nm()%></td>
                    <td><%=publicWifi.getX_swifi_adres1()%></td>
                    <td><%=publicWifi.getX_swifi_adres2()%></td>
                    <td><%=publicWifi.getX_swifi_instl_floor()%></td>
                    <td><%=publicWifi.getX_swifi_instl_ty()%></td>
                    <td><%=publicWifi.getX_swifi_instl_mby()%></td>
                    <td><%=publicWifi.getX_swifi_svc_se()%></td>
                    <td><%=publicWifi.getX_swifi_cmcwr()%></td>
                    <td><%=publicWifi.getX_swifi_cnstc_year()%></td>
                    <td><%=publicWifi.getX_swifi_inout_door()%></td>
                    <td><%=publicWifi.getX_swifi_remars3()%></td>
                    <td><%=publicWifi.getLat()%></td>
                    <td><%=publicWifi.getLnt()%></td>
                    <td><%=publicWifi.getWork_dttm()%></td>
                </tr>
            <% }} %>

        </table>

    <script type="text/javascript">
        let $lat;
        let $lnt;
        window.onload = () => {
            $lat = document.getElementById("lat");
            $lnt = document.getElementById("lnt");
        }

        const getNearPublicWifiList = () => {
            let lat_value = $lat.value;
            let lnt_value = $lnt.value;
            if(lat_value !== "0.0" && lat_value != null && lat_value != undefined && lat_value != ""
                && lnt_value !== "0.0" && lnt_value != null && lnt_value != undefined && lnt_value != "") {
                console.log($lat.value, $lnt.value);
                location = "index.jsp?lat=" + lat_value + "&lnt=" + lnt_value;
            } else {
                alert("좌표를 입력해 주세요.")
            }
        }

        const currentPosition = () => {
            if ('geolocation' in navigator) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    console.log(position);
                    $lat.value = position.coords.latitude;
                    $lnt.value = position.coords.longitude;
                }, function (error) {
                    console.log(error)
                })
            } else {
                alert("위치정보확인 서비스를 지원하지 않는 브라우저입니다.")
            }
        }
    </script>
    </body>
</html>