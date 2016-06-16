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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/tiaozhuan.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
    <script src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/commonAjax.js"></script>
</head>
<body>
   <div class="wrap">
       <input style="display: none" value="${path}" id="pathId"/>
       <img src="<%=path%>/static/images/icon_66.png" alt="">
       <p>绑定成功</p>
       <h1>您的升级申请已经提交，请耐心等待审核。审核通过后需要您继续支付。</h1>
    </div>
</body>
</html>