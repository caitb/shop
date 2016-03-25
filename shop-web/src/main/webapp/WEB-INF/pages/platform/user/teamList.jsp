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
    <link rel="stylesheet" href="<%=basePath%>static/css/wodetuandui.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <script src="<%=basePath%>static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>我的团队</p>
    </header>
    <main>
        <c:forEach items="${agentSkuMaps}" var="agentSkuMap">
        <div class="sec1" onclick="javascript:window.location.replace('<%=basePath%>myteam/teamdetail?userSkuId=${agentSkuMap.userSkuId}&skuId=${agentSkuMap.skuId}');">
            <img src="${agentSkuMap.brandLogo}" alt="">
            <p>合伙产品：<span>${agentSkuMap.skuName}</span></p>
        </div>
        </c:forEach>
    </main>
</div>
</body>
</html>