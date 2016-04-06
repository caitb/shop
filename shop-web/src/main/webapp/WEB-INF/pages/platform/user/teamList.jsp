<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/xiajihehuo.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <script src="../js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>我的团队</p>
    </header>
    <main>
        <div class="nav">
            <p>
                <span>${agentSkuMaps.size()}</span>
                <span>团队产品</span>
            </p>
            <p>
                <span>${totalChild}</span>
                <span>总人数</span>
            </p>
            <p>
                <span>${totalSales}</span>
                <span>总销售额</span>
            </p>
        </div>
        <p>产品团队列表</p>
        <c:forEach items="${agentSkuMaps}" var="agentSkuMap">
        <div class="sec1" onclick="javascript:window.location.replace('<%=basePath%>myteam/teamdetail?userSkuId=${agentSkuMap.userSkuId}');">
            <p><img src="<%=basePath%>static/images/icon_43.png" alt=""></p>
            <div>
                <p>${agentSkuMap.skuName}<em>团队</em></p>
                <p>
                    <span>团队人数：<b>${agentSkuMap.countChild}</b></span>
                    <span>销售额：<b>￥${agentSkuMap.countSales}</b></span>
                </p>
            </div>
        </div>
        </c:forEach>
    </main>
</div>
</body>
</html>