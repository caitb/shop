<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">
    <style>
        .xq_header + img{
            display: block;
            margin: 50px auto;
            width:100px;
        }
        p{
            padding: 0 15px;
            color: #333;
            text-align: center;
        }

    </style>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%=basePath%>index"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>店铺名称</p>
    </header>
    <img src="${path}/static/images/tanhao.png" alt=""/>
    <p>您访问的店铺未进行实名认证，系统已经禁止访问。</p>
</div>
</body>
</html>