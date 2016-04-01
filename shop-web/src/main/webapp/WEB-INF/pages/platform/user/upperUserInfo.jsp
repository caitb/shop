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
    <title>证书详情</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zhengshuxiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%= request.getHeader("REFERER") %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>上级合伙人信息</p>
    </header>
    <main>
        <div id="box">
            <div class="sec1">
                <h1>上级合伙人信息</h1>
                <p>产品：<span>${comSku.name}</span></p>
                <p>等级：
                    <c:choose>
                        <c:when test="${pfUserSku.agentLevelId==1}"><span>高级合伙人</span></c:when>
                        <c:when test="${pfUserSku.agentLevelId==2}"><span>中级合伙人</span></c:when>
                        <c:when test="${pfUserSku.agentLevelId==3}"><span>初级合伙人</span></c:when>
                    </c:choose>
                </p>
                <p>姓名：<span>${userInfo.realName}</span></p>
                <p>手机号：<span>${userInfo.mobile}</span></p>
                <p>微信号：<span>${userInfo.wxId}</span></p>
                <p>实名认证：<span>${userInfo.realName}</span></p>
                <p>证书：<span onclick="javascript:window.location.replace('<%=basePath%>userCertificate/upperDetail/?pfuId=${pfUserSku.id}');">点击查看</span></p>
                <p>加入时间：<span>${comUser.createTime}</span></p>
            </div>
        </div>
    </main>
</div>
</body>
</html>