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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wodezhengshu.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="<%=path%>/static/js/hideWXShare.js"> </script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.location.replace('<%=basePath%>index')"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>我的证书</p>
    </header>
    <%--<main>--%>
        <%--<div id="box">--%>
            <%--<c:forEach items="${pfUserCertificates}" var="cet">--%>
                <%--<c:choose>--%>
                    <%--<c:when test="${cet.agentLevelId==1}">--%>
                        <%--<div class="big">--%>
                            <%--<div class="sec1 sec">--%>
                                <%--<p>合伙产品</p>--%>
                                <%--<h1>${cet.skuName}</h1>--%>
                            <%--</div>--%>
                            <%--<div class="small">--%>
                                <%--<div class="left">--%>
                                    <%--<p>上级合伙人：<span><a href="<%=path%>/userCertificate/userInfo.list/?uskId=${cet.pid}">${cet.upperName}</a></span></p>--%>
                                    <%--<h1>合伙人等级：<span>高级合伙人</span>  保证金：<span>${cet.bail}</span>元</h1>--%>
                                <%--</div>--%>
                                <%--<div class="right">--%>
                                    <%--<p><img src="<%=path%>/static/images/see.png" alt=""><a href="<%=path%>/userCertificate/detail/?pfuId=${cet.id}">查看证书</a></p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</c:when>--%>
                    <%--<c:when test="${cet.agentLevelId==2}">--%>
                        <%--<div class="big">--%>
                            <%--<div class="sec1 sec2">--%>
                                <%--<p>合伙产品</p>--%>
                                <%--<h1>${cet.skuName}</h1>--%>
                            <%--</div>--%>
                            <%--<div class="small">--%>
                                <%--<div class="left">--%>
                                    <%--<p>上级合伙人：<span><a href="<%=path%>/userCertificate/userInfo.list/?uskId=${cet.pid}">${cet.upperName}</a></span></p>--%>
                                    <%--<h1>合伙人等级：<span>中级合伙人</span>  保证金：<span>${cet.bail}</span>元</h1>--%>
                                <%--</div>--%>
                                <%--<div class="right">--%>
                                    <%--<p><img src="<%=path%>/static/images/see.png" alt=""><a href="<%=path%>/userCertificate/detail/?pfuId=${cet.id}">查看证书</a></p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</c:when>--%>
                    <%--<c:when test="${cet.agentLevelId==3}">--%>
                        <%--<div class="big">--%>
                            <%--<div class="sec1 sec3">--%>
                                <%--<p>合伙产品</p>--%>
                                <%--<h1>${cet.skuName}</h1>--%>
                            <%--</div>--%>
                            <%--<div class="small">--%>
                                <%--<div class="left">--%>
                                    <%--<p>上级合伙人：<span><a href="<%=path%>/userCertificate/userInfo.list/?uskId=${cet.pid}">${cet.upperName}</a></span></p>--%>
                                    <%--<h1>合伙人等级：<span>初级合伙人</span>  保证金：<span>${cet.bail}</span>元</h1>--%>
                                <%--</div>--%>
                                <%--<div class="right">--%>
                                    <%--<p><img src="<%=path%>/static/images/see.png" alt=""><a href="<%=path%>/userCertificate/detail/?pfuId=${cet.id}">查看证书</a></p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</c:when>--%>
                <%--</c:choose>--%>
            <%--</c:forEach>--%>
        <%--</div>--%>
    <%--</main>--%>
    <c:forEach items="${pfUserCertificates}" var="cet">
        <div class="sec1">
            <img src="<%=path%>/static/images/icon_61.png" alt="">
            <div>
                <p>合伙产品</p>
                <h1>${cet.skuName}</h1>
                <h2>等级：<span>${cet.agentLevelId} A级合伙人</span>保证金：<span>${cet.bail}</span>元</h2>
                <h3>上级合伙人：<span><a href="<%=path%>/userCertificate/userInfo.list/?uskId=${cet.pid}">${cet.upperName}</a></span><b><img src="<%=path%>/static/images/see.png" alt=""><a href="<%=path%>/userCertificate/detail/?pfuId=${cet.id}">查看证书</a></b></h3>
                <img src="<%=path%>${cet.backimg}" alt="">
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>