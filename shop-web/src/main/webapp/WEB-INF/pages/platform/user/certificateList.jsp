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
    <title>我的证书</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wodezhengshu.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>我的证书</p>
    </header>
    <main>
        <div id="box">
            <c:forEach items="${pfUserCertificates}" var="cet">
                <div class="sec1">
                    <p><span>合伙产品</span><b>${cet.comSku.name}</b></p>
                    <p><span>合伙人等级：
                        <c:choose>
                            <c:when test="${cet.agentLevelId==1}">
                                <em>初级合伙人</em>
                            </c:when>
                            <c:when test="${cet.agentLevelId==2}">
                                <em>中级合伙人</em>
                            </c:when>
                            <c:when test="${cet.agentLevelId==3}">
                                <em>高级合伙人</em>
                            </c:when>
                        </c:choose>
                        </span><span>授权书状态：
                    <c:choose>
                        <c:when test="${cet.status==0}">
                            <em>未审核</em>
                        </c:when>
                        <c:when test="${cet.status==1}">
                            <em>审核成功</em>
                        </c:when>
                        <c:otherwise>
                            <em>审核失败</em>
                        </c:otherwise>
                    </c:choose>
                    </span></p>
                     <a href="<%=path%>/userCertificate/userct/${cet.comSku.id}"><img src="<%=path%>/static/images/rightgo.png" alt=""></a>
                </div>
            </c:forEach>
        </div>
    </main>
</div>
</body>
</html>