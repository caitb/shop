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
    <link rel="stylesheet" href="<%=path%>/static/css/shangjihehuo.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%= request.getHeader("REFERER") %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>证书详情</p>
    </header>
    <main>
        <div>
            <p>上级合伙人信息详情</p>
            <h1><span>${userInfo.realName}</span>${comSku.name}</h1>
            <h2><img src="<%=path%>/static/images/lv.png" alt="">合伙人等级
                <c:choose>
                    <c:when test="${pfUserSku.agentLevelId==1}">
                        <b>高级合伙人</b>
                    </c:when>
                    <c:when test="${pfUserSku.agentLevelId==2}">
                        <b>中级合伙人</b>
                    </c:when>
                    <c:when test="${pfUserSku.agentLevelId==3}">
                        <b>初级合伙人</b>
                    </c:when>
                </c:choose>
            </h2>
        </div>
        <p><span>等级</span>
            <c:choose>
                <c:when test="${pfUserSku.agentLevelId==1}">
                    <span>高级合伙人</span>
                </c:when>
                <c:when test="${pfUserSku.agentLevelId==2}">
                    <span>中级合伙人</span>
                </c:when>
                <c:when test="${pfUserSku.agentLevelId==3}">
                    <span>初级合伙人</span>
                </c:when>
            </c:choose>
        </p>
        <p><span>手机号</span><span>${userInfo.mobile}</span></p>
        <p><span>微信号</span><span>${userInfo.wxId}</span></p>
        <p><span>是否认证</span><span class="four"><img src="<%=path%>/static/images/guanli.png" alt="">已通过</span></p>
        <p><span>授权证书</span><span><b><img src="${pfUserCertificate.imgUrl}" alt=""></b></span></p>
        <p><span>加入时间</span><span>${sDate}</span></p>
    </main>
</div>
</body>
</html>