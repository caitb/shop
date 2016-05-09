<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    <link rel="stylesheet" href="<%=basePath%>static/css/fazhanhehuo.css">
    <script src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<div class="wrap">
    <div id="box">
        <header class="xq_header">
            <a href="<%=basePath%>index"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
            <p>发展合伙人</p>
        </header>
        <div class="banner">
            <img src="<%=basePath%>static/images/icon_58.png" alt="">
        </div>
        <main>
            <p>选择您需要发展合伙人的商品</p>
            <c:forEach items="${agentMaps}" var="agentMap">
                <c:if test="${agentMap.canDeveloping == 'yes'}">
                    <div class="sec1 toShare" onclick="javascript:window.location.replace('<%=basePath%>developing/sharelink?skuId=${agentMap.skuId}');">
                        <img src="${agentMap.brandLogo}" alt="">
                        <div>
                            <p>合伙产品</p>
                            <p><b>${agentMap.skuName}</b></p>
                            <h1>合伙人等级:<span style="font-size: 12px">${agentMap.levelName}</span></h1>
                                <%--<h2>介绍介绍介绍介绍介绍介绍介绍介绍</h2>--%>
                        </div>
                        <botton>我要推广</botton>
                    </div>
                </c:if>
                <c:if test="${agentMap.canDeveloping == 'no'}">
                    <div class="sec1 toShare">
                        <img src="${agentMap.brandLogo}" alt="">
                        <div>
                            <p>合伙产品</p>
                            <p><b>${agentMap.skuName}</b></p>
                            <h1>合伙人等级:<span style="font-size: 12px">${agentMap.levelName}</span></h1>
                                <%--<h2>介绍介绍介绍介绍介绍介绍介绍介绍</h2>--%>
                        </div>
                        <p class="wu">此产品无推广功能<img src="${path}/static/images/icon_70.png"/></p>
                    </div>
                </c:if>
            </c:forEach>
        </main>
    </div>
    <div class="paidanqi">
        <div class="back_q">
            <p style="padding: 20px">非常遗憾，麦链合伙人不支持最低一级合伙人发展下级。
            </p>
            <button class="kNow" style="font-size: 12px;">我知道了</button>
        </div>
        <div class="Modal"></div>
    </div>
</div>
<script>
    $(".wu").on("click", function () {
        $(".paidanqi").show();
    });
    $(".kNow").on("click", function () {
        $(".paidanqi").hide();
    });
</script>
</body>
<%--<script>--%>
    <%--$('.toShare').on('click', function(){--%>
        <%--var userSkuId = $(this).attr('userSkuId');--%>
        <%--var skuId = $(this).attr('skuId');--%>
        <%--$.ajax({--%>
            <%--url: '<%=basePath%>developing/isAudit',--%>
            <%--data: {userSkuId: userSkuId},--%>
            <%--success: function(msg){--%>
                <%--if(msg == 'yes'){--%>
                    <%--window.location.replace('<%=basePath%>developing/sharelink?skuId='+skuId);--%>
                <%--}else{--%>
                    <%--alert('此产品的代理证书未审核!');--%>
                <%--}--%>
            <%--}--%>
        <%--})--%>
    <%--});--%>
<%--</script>--%>
</html>
