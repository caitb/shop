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
    <link rel="stylesheet" href="<%=basePath%>static/css/gerenzhongxin.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/xinjiandizhi.css">
    <script src="<%=basePath%>static/js/iscroll.js"></script>
    <script src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<div id="wrap">
    <div class="main">
        <header>
            <div>
                <p><img src="<%=basePath%>static/images/icon_01.png" alt=""></p>
                <h1>
                    <b>用户名</b>
                    <span>您的推荐人是王平</span>
                </h1>
            </div>
            <p>退出</p>
        </header>
        <div id="box">

            <p onclick="javascript:window.location.replace('<%=basePath%>product/user/${comUser.id}');"><span><img src="<%=basePath%>static/images/icon_28.png" alt="">商品管理</span><span><img src="<%=basePath%>static/images/right.png" alt=""></span></p>
            <p onclick="javascript:window.location.replace('<%=basePath%>border/deliveryBorder');"><span><img src="<%=basePath%>static/images/icon_29.png" alt="">出货订单</span><span><img src="<%=basePath%>static/images/right.png" alt=""></span></p>
            <p onclick="javascript:window.location.replace('<%=basePath%>border/stockBorder');"><span><b></b><img src="<%=basePath%>static/images/icon_49.png" alt="">进货订单</span><span><img src="<%=basePath%>static/images/right.png" alt=""></span></p>
            <p onclick="javascript:window.location.replace('<%=basePath%>userCertificate/userList/${comUser.id}');"><span><img src="<%=basePath%>static/images/icon_31.png" alt="">我的授权书</span><span><img src="<%=basePath%>static/images/right.png" alt=""></span></p>
            <p onclick="javascript:window.location.replace('<%=basePath%>developing/ui');"><span><img src="<%=basePath%>static/images/icon_48.png" alt="">发展合伙人</span><span><img src="<%=basePath%>static/images/right.png" alt=""></span></p>
            <p onclick="javascript:window.location.replace('<%=basePath%>myteam/teamlist');"><span><img src="<%=basePath%>static/images/icon_50.png" alt="">我的团队</span><span><img src="<%=basePath%>static/images/right.png" alt=""></span></p>
            <p onclick="javascript:window.location.replace('<%=basePath%>account/home');"><span><img src="<%=basePath%>static/images/icon_51.png" alt="">我的资产</span><span><img src="<%=basePath%>static/images/right.png" alt=""></span></p>
            <p><span><img src="<%=basePath%>static/images/icon_52.png" alt="">个人信息</span><span><img src="<%=basePath%>static/images/right.png" alt=""></span></p>
        </div>
    </div>
</div>
<div class="bottom">
    <footer>
        <div class="btm">
            <a href="javascript:window.location.replace('<%=basePath%>/index');">
                <span><img src="<%=basePath%>static/images/shouye_footer.png" alt=""></span>
                <span>首页</span>
            </a>
        </div>
        <div class="btm">
            <a href="../html/xiaodian.html">
                <span><img src="<%=basePath%>static/images/shouye_footer2.png" alt=""></span>
                <span>我的店铺</span>
            </a>
        </div>
        <div class="btm" style="background: #DA3600;">
            <a href="<%=basePath%>static/html/xiaodianguanli.html">
                <span><img src="<%=basePath%>static/images/shouye_footer3.png" alt=""></span>
                <span>个人中心</span>
            </a>
        </div>
    </footer>
</div>
<script>
    var myScroll = new IScroll("#wrap",{
        preventDefault: false
    })
</script>
</body>
</html>