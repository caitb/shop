<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/gerenxinxi.css">
</head>
<body>
<header>
    <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>个人信息</p>
</header>
<div class="wrap">
    <div>
        <p><img src="${comUser.wxHeadImg}" /> </p>
        <h1>
            <span>${comUser.wxNkName}</span>
        </h1>
    </div>
    <div>
        <p>手机号</p>
        <p>${comUser.mobile}</p>
    </div>
</div>
</body>
</html>