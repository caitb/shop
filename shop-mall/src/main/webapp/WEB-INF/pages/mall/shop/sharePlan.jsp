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
    <title>麦链商城</title>
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
            margin-bottom: 100px;
        }
    </style>
</head>
<body>
<div class="wrap">
    <img src="<%=basePath%>static/images/fenx.png" alt="">
    <button onclick="javascript:window.location.replace('<%=basePath%>shop/getPoster?shopId=${shopId}');">
        获取我的专属海报
    </button>
</div>
<footer>
    <div>
        <p onclick="javascript:window.location.replace('<%=basePath%>${shopId}/${userPid}/shop.shtml');">
            <span><img src="<%=path%>/static/images/footer%20(3).png" alt=""></span>
            <span>首页</span>
        </p>
        <p class="active" onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${shopId}');">
            <span><img src="<%=path%>/static/images/footer_x%20(1).png" alt=""></span>
            <span>分享计划</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/toBorderManagement?fm=1');" >
            <span><img src="<%=path%>/static/images/footer%20(2).png" alt=""></span>
            <span>个人中心</span>
        </p>
    </div>
</footer>
</body>
</html>