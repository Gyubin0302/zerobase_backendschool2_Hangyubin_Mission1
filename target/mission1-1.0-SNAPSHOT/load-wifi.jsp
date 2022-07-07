<%@ page import="com.example.mission1.service.wifi.WifiService" %><%--
  Created by IntelliJ IDEA.
  User: hangyubin
  Date: 2022/07/02
  Time: 1:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>와이파이 정보 구하기</title>
        <style>
            p {
                font-size: larger;
                font-weight: bold;
                text-align: center;
            }
            button {
                background-color: #FFF;
                cursor: pointer;
                border-style: none none none none;
                margin: 0 auto;
                display: block;
            }
        </style>
    </head>
    <body>
        <%
            WifiService wifiService = new WifiService();
            int result = wifiService.PublicWifiInsert();
        %>

        <% if (result != -1) { %>
            <p><%=result%>개의 WIFI 정보를 정상적으로 저장하였습니다.</p>
        <% } else { %>
            <p>WIFI 정보 저장에 실패하였습니다.</p>
        <% } %>
        <div>
            <button onclick="location='index.jsp'">홈으로 가기</button>
        </div>
    </body>
</html>
