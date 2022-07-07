<%@ page import="com.example.mission1.service.wifi.WifiService" %><%--
  Created by IntelliJ IDEA.
  User: hangyubin
  Date: 2022/07/02
  Time: 8:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
    <%
        int id = Integer.parseInt(request.getParameter("id"));
        WifiService wifiService = new WifiService();
        wifiService.deleteHistory(id);

        response.sendRedirect("history.jsp");
    %>
</body>
</html>
