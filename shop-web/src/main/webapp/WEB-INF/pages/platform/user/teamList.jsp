<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/xiajihehuo.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%=basePath%>index"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>我的团队</p>
    </header>
    <main>
        <div class="nav">
            <p>
                <span>${agentSkuMaps.size()}</span>
                <span>合伙产品数</span>
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
        <div class="sec1">
            <p><img src="${agentSkuMap.brandLogo}" alt=""></p>
            <div>
                <p>${agentSkuMap.skuName}<em>团队</em></p>
                <p>
                    <span>团队人数：<b>${agentSkuMap.countChild}</b></span>
                    <span>销售额：<b>￥${agentSkuMap.countSales}</b></span>
                </p>
            </div>
            <c:if test="${agentSkuMap.isLastLevel == 'no'}"><h1 class="set" onclick="javascript:window.location.replace('<%=basePath%>myteam/teamdetail?userSkuId=${agentSkuMap.userSkuId}');">管理团队</h1></c:if>
            <c:if test="${agentSkuMap.isLastLevel == 'yes'}"><h1 class="team">没有管理团队功能<img class="once" src="${path}/static/images/icon_70.png"/></h1></c:if>
        </div>
        </c:forEach>
    </main>
    <div class="paidanqi">
        <div class="back_q">
            <p style="padding: 20px">您代理的这款产品等级是最后一级,无团队管理功能!
            </p>
            <button class="kNow" style="font-size: 12px;">我知道了</button>
        </div>
        <div class="Modal"></div>
    </div>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script>
    $(".once").on("click", function () {
        $(".paidanqi").show();
    });
    $(".kNow").on("click", function () {
        $(".paidanqi").hide();
    });
</script>
</body>
</html>