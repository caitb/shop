<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/dengdaishenhe.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:;"onClick="javascript:history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>证书详情</p>
    </header>
    <main>
        <div id="box">
            <div class="sec1">
                <h1>您的 ${skuName} 授权书申请成功！</h1>
            </div>
            <div class="sec2">
                <img src="<%=path%>/static/images/icon_45.png" alt="">
                <p>审核结果会在1个工作日内完成</p>
            </div>
            <div class="sec6">
                <p><a href="javascript:window.location.replace('<%=basePath%>/index');">返回首页</a></p>
                <p><a href="javascript:window.location.replace('<%=basePath%>profile/profile');">返回个人中心</a></p>
            </div>
        </div>
    </main>
</div>
</body>
</html>