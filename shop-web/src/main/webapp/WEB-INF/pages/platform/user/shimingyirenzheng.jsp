<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shimingyirenzheng.css">
</head>
<body>
    <div class="wrap">
        <header class="xq_header">
                   <a href="javascript:window.history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                   <p>实名认证</p>
        </header>
        <main>
            <p>您的实名认证已经通过</p>
        <div class="sec1">
            <p><span>姓名:</span><span>${comUser.realName}</span></p>
            <p><span>身份证号:</span><span>${comUser.idCard}</span></p>
            <p><span>身份证照片:</span><span>已上传</span></p>
        </div>
        </main>
    </div>
    
</body>
</html>