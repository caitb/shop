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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/reset.css">
    <style>
        .wrap{
            background: #fff;
        }
        img{
            display: block;
            width: 100%
        }
        button{
            display: block;
            width: 60%;
            line-height: 30px;
            border: 1px solid #F73C8C;
            background: #fff;
            margin: 0 auto;
            margin-top: 2em;
            color: #F73C8C;
        }
    </style>
</head>
<body>
<header>
    <a href="index.html"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>分享计划</p>
</header>
<div class="wrap">
    <img src="<%=basePath%>static/images/fenx.png" alt="">
    <button onclick="javascript:window.location.replace('<%=basePath%>shop/getPoster?shopId=${shopId}');">
        获取我的专属海报
    </button>
</div>
<c:import url="/WEB-INF/pages/common/nav-footer.jsp"></c:import>
</body>
</html>