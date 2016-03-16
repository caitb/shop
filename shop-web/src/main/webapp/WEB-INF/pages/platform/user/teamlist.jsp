<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/gerenzhongxin.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/xinjiandizhi.css">
    <script src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
<body>
<ul>
    <c:forEach items="${agentSkus}" var="agentSku">
    <li>${agentSku.name}</li>
    </c:forEach>
</ul>
</body>
</html>