<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/jixuguangguang.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
</head>
<body>
<header class="xq_header" style="margin:0;">
    <p>等待审核</p>
</header>
<div class="drap">
    <img src="<%=path%>/static/images/yes.png" alt=""
         style="width: 20%;margin: 0 auto;display: block;margin-bottom:2em;">
    <p>实名认证已经提交</p>
    <h2>您的实名认证已经提交，审核结果会在1个工作日内下发到您的手机请注意查收。</h2>
</div>
<a href="<%=path%>/static/html/index.html" class="jixu">
    返回市场
</a>
</body>
</html>