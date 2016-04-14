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
    <link rel="stylesheet" href="<%=path%>/css/base.css">
    <link rel="stylesheet" href="<%=path%>/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/css/gerenzhongxin.css">
    <link rel="stylesheet" href="<%=path%>/css/xinjiandizhi.css">
    <script src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<body>
<div id="wrap">
    <header>
        <div>
            <p><img src="<%=path%>/images/icon_01.png" alt=""></p>
            <h1>
                <b>用户名</b>
                <span>您的推荐人是王平</span>
            </h1>
        </div>
        <p>退出</p>
    </header>
    <div id="box">
        <h1><p><span>我的订单</span><span style="padding-right: 20px;">全部订单</span></p></h1>
        <p><span><img src="<%=path%>/images/icon_28.png" alt="">待付款</span><span><img src="<%=path%>/images/right.png" alt=""></span></p>
        <p><span><img src="<%=path%>/images/icon_29.png" alt="">待发货</span><span><img src="<%=path%>/images/right.png" alt=""></span></p>
        <p><span><b>1</b><img src="<%=path%>/images/icon_30.png" alt="">待收货</span><span><img src="<%=path%>/images/right.png" alt=""></span></p>
        <p><span><img src="<%=path%>/images/icon_31.png" alt="">待评价</span><span><img src="<%=path%>/images/right.png" alt=""></span></p>
        <p><span><img src="<%=path%>/images/icon_32.png" alt="">退款/售后</span><span><img src="<%=path%>/images/right.png" alt=""></span></p>

    </div>

</div>
<div class="bottom">
    <footer>
        <div class="btm">
            <a href="<%=path%>/index">
                <span><img src="<%=path%>/images/shouye_footer.png" alt=""></span>
                <span>首页</span>
            </a>
        </div>
        <div class="btm">
            <a href="<%=path%>/shop/manage/index">
                <span><img src="<%=path%>/images/shouye_footer2.png" alt=""></span>
                <span>我的店铺</span>
            </a>
        </div>
        <div class="btm" style="background: #DA3600;">
            <a href="<%=path%>/html/xiaodianguanli.html">
                <span><img src="<%=path%>/images/shouye_footer3.png" alt=""></span>
                <span>个人中心</span>
            </a>
        </div>
    </footer>
</div>
</body>
</html>