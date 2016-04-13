<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>Document</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/wodedianpu.css">
</head>
<body>
<div class="wrap">
    <div class="na">
        <p></p>
        <h1>
            <span>王平，欢迎登陆~</span>
        </h1>
    </div>
    <div class="header">
        <div>
            <p>王平的小店</p>
            <span>分享</span>
        </div>
        <div>
            <p>
                <span>麦链商城光放认证</span>
            </p>
        </div>
        <img src="<%=basePath%>static/images/admin.png" alt="">
        <nav>
            <p><span>1233</span><span>店铺总销售额</span></p>
            <p><span>1233</span><span>店铺总销售额</span></p>
            <p><span>1233</span><span>店铺总销售额</span></p>
        </nav>
    </div>
    <nav>
        <p><span><img src="<%=basePath%>static/images/nav1.jpg" alt=""></span><span>商品管理</span></p>
        <p><span><img src="<%=basePath%>static/images/nav2.jpg" alt=""></span><span>订单管理</span></p>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/manage/setupShop');"><span><img src="<%=basePath%>static/images/nav3.jpg" alt=""></span><span>店铺设置</span></p>
    </nav>
    <nav style="margin:0;">
        <p><span><img src="<%=basePath%>static/images/nav4.jpg" alt=""></span><span>分销记录</span></p>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/manage/setupFreight');"><span><img src="<%=basePath%>static/images/nav1.jpg" alt=""></span><span>运费模板</span></p>
        <p></p>
    </nav>
</div>
<footer>
    <div>
        <a>
            <span><img src="<%=basePath%>static/images/footer1.jpg" alt=""></span>
            <span>首页</span>
        </a>
        <a>
            <span><img src="<%=basePath%>static/images/footer2.jpg" alt=""></span>
            <span>分享计划</span>
        </a>
        <a class="active">
            <span><img src="<%=basePath%>static/images/footer3.jpg" alt=""></span>
            <span>个人中心</span>
        </a>
    </div>
</footer>
</body>
</html>