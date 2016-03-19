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
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/fazhanhehuo.css">
</head>
<body>
<div class="wrap">
    <div id="box">
        <header class="xq_header">
            <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
            <p>发展合伙人</p>
        </header>
        <div class="banner">
            <img src="<%=basePath%>static/images/icon_58.png" alt="">
        </div>
        <main>
            <p>选择您需要发展合伙人的商品</p>
            <div class="sec1" onclick="javascript:window.location.replace('<%=basePath%>developing/sharelink');">
                <img src="<%=basePath%>static/images/icon_59.png" alt="">
                <div>
                    <p><span>合伙产品</span><b>抗引力-瘦脸精华</b></p>
                    <h1>合伙人等级<span>高级合伙人</span></h1>
                    <h2>介绍介绍介绍介绍介绍介绍介绍介绍</h2>
                </div>
                <botton>我要推广</botton>
            </div>
            <div class="sec1" onclick="javascript:window.location.replace('<%=basePath%>developing/sharelink');">
                <img src="<%=basePath%>static/images/icon_61.png" alt="">
                <div>
                    <p><span>合伙产品</span><b>抗引力-瘦脸精华</b></p>
                    <h1>合伙人等级<span>高级合伙人</span></h1>
                    <h2>介绍介绍介绍介绍介绍介绍介绍介绍</h2>
                </div>
                <botton>我要推广</botton>
            </div>
        </main>
    </div>
</div>

</body>
</html>
