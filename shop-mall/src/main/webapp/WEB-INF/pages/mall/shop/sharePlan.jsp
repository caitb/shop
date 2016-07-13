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
            width: 95%;
            margin:10px auto;
        }
        button{
            display: block;
            width:80%;
            line-height: 30px;
            background: #C8A66A;
            margin: 0 auto;
            margin-top: 2em;
            color: #fff;
            margin-bottom: 100px;
            padding: 4px 0;
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
            <span><img src="<%=path%>/static/images/dibu1.png" alt=""></span>
            <span>首页</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${sfShop.id}');">
            <span><img src="<%=path%>/static/images/dibu22.png" alt=""></span>
            <span class="active" >二维码</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/toBorderManagement?fm=1');">
            <span><img src="<%=path%>/static/images/dibu3.png" alt=""></span>
            <span>个人中心</span>
        </p>
    </div>
</footer>
<script src="<%=basePath%>static/js/plugins/jquery/jquery-1.8.3.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script type="application/javascript" src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
</body>
</html>