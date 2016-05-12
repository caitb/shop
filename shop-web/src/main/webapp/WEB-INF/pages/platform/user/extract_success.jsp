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
    <style>
        a{
            background: url("<%=path%>/static/images/icon_37.png") no-repeat 0;
            background-size: 13px 13px;
            padding-left: 18px;
            margin-top: 2rem;
        }
        h1{
            margin: 0 10%;
            margin-bottom: 8rem;
            line-height: 15px;
            margin-top: 2rem;
        }
    </style>
</head>
<body>
<div class="wrap">
    <img src="<%=path%>/static/images/icon_66.png" alt="">
    <p>提现申请提交成功</p>
    <h1>您的提现申请提交成功，工作人员会在一个工作日内处理，提现结果会通过短信和微信的方式通知您，请注意查收。</h1>
    <a href="<%=path%>/index">返回首页</a>
</div>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
</body>
</html>
