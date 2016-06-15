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
    <link rel="stylesheet" href="<%=basePath%>static/css/hehuorenhaibao.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>升级信息</p>
    </header>
    <form id="levelIdForm" action="<%=basePath%>developing/sharelink" method="post">
    <input type="hidden" name="skuId" value="${skuId}" >
    <main>
        <h1>
            您的级别：<c:forEach items="${comAgentLevels}" var="agentLevel">
                     <c:if test="${agentLevelId == agentLevel.id}">${agentLevel.name}</c:if>
                    </c:forEach>
        </h1>
        <div class="floor">
            <p>请选择分享后可注册的等级：</p>
            <c:forEach items="${comAgentLevels}" var="agentLevel">
            <input type="checkbox" id="level_${agentLevel.id}" name="levelIds" value="${agentLevel.id}"/>
            <label for="level_${agentLevel.id}">
                    ${agentLevel.name}<c:if test="${agentLevelId == agentLevel.id}"><span>(平级推荐)</span></c:if>
            </label>
            </c:forEach>
        </div>
        <p><b>*</b>您可以多次获取不同等级的分享海报</p>
    </main>
    </form>
    <button id="getPoster">获取分享海报</button>
</div>
</body>
<script src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>static/js/definedAlertWindow.js"></script>
<script>
    $('#getPoster').on('click', function(){
        var checkedCount = $('input[name="levelIds"]:checked').size();
        if(checkedCount <= 0){
            alert('请选择要发展的代理等级!');
            return;
        }
        $('#levelIdForm').submit();
    });
</script>
</html>