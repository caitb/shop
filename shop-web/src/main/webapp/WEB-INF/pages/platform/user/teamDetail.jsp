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
    <link rel="stylesheet" href="<%=basePath%>static/css/wodetuandui.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <script src="<%=basePath%>static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%=basePath%>myteam/teamlist"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>${teamMap.skuName}团队</p>
    </header>
    <main>
        <div class="nav">
            <p>
                <span>${teamMap.totalChildren}</span>
                <span>直接下级</span>
            </p>
            <p>
                <span>${teamMap.countChild}</span>
                <span>间接下级</span>
            </p>
            <p>
                <span>${teamMap.countSales}</span>
                <span>销售额</span>
            </p>
        </div>
        <p>团队成员列表</p>
        <c:forEach items="${teamMap.userAgentMaps}" var="userAgentMap">
        <div class="sec1" onclick="javascript:window.location.replace('<%=basePath%>myteam/memberinfo?code=${userAgentMap.code}');">
            <p><img src="<%=basePath%>static/images/yes.png" alt=""></p>
            <p>
                <b style="font-size: 14px;">${userAgentMap.userName}</b>
                <b><img src="<%=basePath%>static/images/lv.png" alt="">合伙人等级:<span>${userAgentMap.agentLevelName}</span></b>
            </p>
        </div>
        </c:forEach>
    </main>
</div>
</body>
</html>