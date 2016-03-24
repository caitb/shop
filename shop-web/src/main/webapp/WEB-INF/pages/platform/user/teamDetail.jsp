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
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/kangtuandui.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <script src="<%=basePath%>static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>抗引力团队</p>
    </header>
    <nav>
        <ul>
            <li class="s">已审核</li>
            <li class="d on">待审核</li>
        </ul>
    </nav>
    <main>
        <div class="yi">
            <c:forEach items="${teamMaps.isAuditTeamMaps}" var="isAuditTeamMap">
            <div class="sec1" onclick="javascript:window.location.replace('<%=basePath%>myteam/memberinfo?comUserId=${isAuditTeamMap.comUserId}&skuId=${isAuditTeamMap.skuId}&agentLevelId=${isAuditTeamMap.agentLevelId}&userSkuId=${isAuditTeamMap.userSkuId}');">
                <img src="${isAuditTeamMap.comUserImg}" alt="">
                <p>
                    <b>队员姓名：<em>${isAuditTeamMap.comUserName}</em></b>
                    <span>合伙人等级：<em>${isAuditTeamMap.agentLevelName}</em></span>
                </p>
            </div>
            </c:forEach>
        </div>
        <div class="wei">
            <c:forEach items="${teamMaps.noAuditTeamMaps}" var="noAuditTeamMap">
            <div class="sec1"  onclick="javascript:window.location.replace('<%=basePath%>myteam/toaudit?comUserId=${noAuditTeamMap.comUserId}&skuId=${noAuditTeamMap.skuId}&agentLevelId=${noAuditTeamMap.agentLevelId}&userSkuId=${noAuditTeamMap.userSkuId}');">
                <img src="${noAuditTeamMap.comUserImg}" alt="">
                <p>
                    <b>队员姓名：<em>${noAuditTeamMap.comUserName}</em></b>
                    <span>合伙人等级：<em>${noAuditTeamMap.agentLevelName}</em></span>
                </p>
            </div>
            </c:forEach>
        </div>
    </main>
    <script src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
    <script>
        $(".s").on("click",function(){
            $(this).addClass("on").siblings().removeClass("on")
            $(".yi").show().siblings().hide();
        })
        $(".d").on("click",function(){
            $(this).addClass("on").siblings().removeClass("on")
            $(".wei").show().siblings().hide();
        })
    </script>
</div>
</body>
</html>