<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <title>到首页</title>
</head>
<body>
<%
//    response.sendRedirect("/index");
    response.sendRedirect("/sfOrderManagerController/borderManagement.html?fm=0");
%>
</body>
</html>
