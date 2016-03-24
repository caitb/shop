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
        <p>证书详情</p>
    </header>
    <main>
        <div id="box">
            <div class="sec1">
                <h1>所属人信息</h1>

                <p>姓名：<span>${comUser.realName}</span></p>

                <p>合作产品：<span>${comSku.name}</span></p>

                <p>合伙人等级：<span>
                    <c:choose>
                    <c:when test="${cdetail.agentLevelId==1}">
                        <em>高级合伙人</em>
                    </c:when>
                    <c:when test="${cdetail.agentLevelId==2}">
                        <em>中级合伙人</em>
                    </c:when>
                    <c:when test="${cdetail.agentLevelId==3}">
                        <em>初级合伙人</em>
                    </c:when>
                </c:choose></span></p>
                <p>上级合伙人：<span>${cdetail.sjName}</span></p>
            </div>
            <div class="sec2">
                <h1>证书图片</h1>
                <p>
                    <img src="${cdetail.imgUrl}" alt="">
                </p>
            </div>
        </div>
    </main>
 </div>
</body>
</html>