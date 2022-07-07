<%@ page import="java.util.List" %>
<%@ page import="com.example.mission1.service.wifi.WifiService" %>
<%@ page import="com.example.mission1.dto.wifi.SearchWifiResponseDto" %><%--
  Created by IntelliJ IDEA.
  User: hangyubin
  Date: 2022/07/02
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            .history-list .btn-delete {
                margin: 0 auto;
                display: block;
            }
        </style>
    </head>
    <body>
    <h1>위치 히스토리 목록</h1>
        <div class="cate mb-16">
            <button onclick="location='index.jsp'">홈</button>
            <button onclick="location='history.jsp'">위치 히스토리 목록</button>
            <button onclick="location='load-wifi.jsp'">Open API 와이파이 정보 가져오기</button>
        </div>

        <%
            WifiService wifiService = new WifiService();
            List<SearchWifiResponseDto> history_list = wifiService.historyList();
        %>
        <table id="customers">
            <tr>
                <th>ID</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>조회일자</th>
                <th>비고</th>
            </tr>
            <% if (history_list.size() == 0) { %>
                <tr>
                    <td class="empty-list" colspan="17">검색한 이력이 없습니다.</td>
                </tr>
            <% } else {
                for (int i = 0; i < history_list.size(); i++) {
                    SearchWifiResponseDto searchWifiResponseDto = history_list.get(i);
             %>
                <tr class="history-list">
                    <td><%=searchWifiResponseDto.getId()%></td>
                    <td><%=searchWifiResponseDto.getLat()%></td>
                    <td><%=searchWifiResponseDto.getLnt()%></td>
                    <td><%=searchWifiResponseDto.getSearch_dttm()%></td>
                    <td><button id="<%=searchWifiResponseDto.getId()%>" class="btn-delete" onclick="deleteHistory(this)">삭제</button></td>
                </tr>
            <% }} %>
        </table>
    <script type="text/javascript">
        const deleteHistory = (e) => {
            location = "delete.jsp?id=" + e.id;
        }
    </script>
    </body>
</html>
