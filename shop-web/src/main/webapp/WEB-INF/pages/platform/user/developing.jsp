<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>微信JS-SDK Demo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
    <link rel="stylesheet" href="<%=basePath%>static/js/test/style.css">
</head>
<body ontouchstart="">
<div class="wxapi_container">
        <button class="btn btn_primary" id="chooseWXPay" onclick="javascript:window.location.replace('<%=basePath%>developing/sharelink');">获取分享链接</button>
    </div>
</div>
</body>

</html>
