<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/shengjijilu.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>升级记录</p>
    </header>
    <main>
        <c:forEach items="${upgradeRecords}" var="ur">
        <div class="sec1">
            <h1>升级时间：<fmt:formatDate value="${ur.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></h1>
            <p>
                <span>原等级：<b>${ur.orgLevelName}</b></span>
                <span>升级等级：<b>${ur.wishLevelName}</b></span>
            </p>
            <p>
                <span>原上级：<b>${ur.pUserName}</b></span>
                <span>推荐人：　<b>${ur.recommendUserName}</b></span>
            </p>
        </div>
        </c:forEach>
    </main>
</div>
<script src="../js/jquery-1.8.3.min.js"></script>
</body>
</html>