<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/gerenxinxi.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>个人信息</p>
    </header>
    <main>
        <div class="sec1">
            <p>头像</p>
            <span><img src="${comUser.wxHeadImg}" alt=""></span>
        </div>
        <div class="sec1">
            <p>昵称</p>
            <b>${comUser.wxNkName}</b>
        </div>
        <div class="sec1" style="margin-top:10px;">
            <p>微信号</p>
            <b>${comUser.wxId}</b>
        </div>
        <div class="sec1">
            <p>手机号</p>
            <b>${comUser.mobile}</b>
        </div>
    </main>
</div>
</body>
</html>