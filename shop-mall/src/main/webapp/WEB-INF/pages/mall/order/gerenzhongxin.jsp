<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/gerenzhongxin.css">
</head>
<body>
<div class="wrap">
    <div class="na">
        <p><img src="${user.wxHeadImg}" alt=""></p>
        <h1>
            <span>${user.wxNkName}</span>
            <span>您的推荐人是<b>${userPid.realName}</b></span>
        </h1>
    </div>
    <div class="Xin">
        <p>我的订单</p>
        <p><a href="<%=path%>/sfOrderManagerController/stockOrder">查看全部订单></a></p>
    </div>
    <div class="dynmic">
        <p><a href="<%=path%>/sfOrderManagerController/stockOrder?orderStatus=0">
            <span><img src="<%=path%>/static/images/geren%20(2).png" alt=""><b>${sfOrders0}</b></span>
            <span>待付款</span></a>
        </p>
        <p><a href="<%=path%>/sfOrderManagerController/stockOrder?orderStatus=7">
            <span><img src="<%=path%>/static/images/geren%20(3).png" alt=""></span>
            <span>待发货</span></a>
        </p>
        <p><a href="<%=path%>/sfOrderManagerController/stockOrder?orderStatus=8">
            <span><img src="<%=path%>/static/images/geren%20(1).png" alt="" style="width: 29px;"></span>
            <span>待收货</span></a>
        </p>
    </div>
    <nav>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/sfaccount/commissionHome.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(4).png" alt=""></span>
                <span>我的佣金</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/user/getPersonalInfo.do');">
                <span><img src="<%=path%>/static/images/geren2%20(2).png" alt=""></span>
                <span>个人信息</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/shopview/home.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(3).png" alt=""></span>
                <span>浏览过的店铺</span>
            </li>
        </ul>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/userAddress/toManageAddressPage.html?addAddressJumpType=1&manageAddressJumpType=1');" >
                <span><img src="<%=path%>/static/images/geren2%20(1).png" alt=""></span>
                <span>地址管理</span>
            </li>
            <li> </li>
            <li></li>
        </ul>
    </nav>
</div>
<footer>
    <div>
        <p onclick="javascript:window.location.replace('<%=path%>/index');">
            <span><img src="<%=path%>/static/images/footer%20(3).png" alt=""></span>
            <span>首页</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=path%>/share.html');">
            <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
            <span>分享计划</span>
        </p>
        <p class="active">
            <span><img src="<%=path%>/static/images/footer_x%20(2).png" alt=""></span>
            <span>个人中心</span>
        </p>
    </div>
</footer>
</body>
</html><%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/gerenzhongxin.css">
</head>
<body>
<div class="wrap">
    <div class="na">
        <p><img src="${user.wxHeadImg}" alt=""></p>
        <h1>
            <span>${user.wxNkName}</span>
            <span>您的推荐人是<b>${userPid.realName}</b></span>
        </h1>
    </div>
    <div class="Xin">
        <p>我的订单</p>
        <p><a href="<%=path%>/sfOrderManagerController/stockOrder">查看全部订单></a></p>
    </div>
    <div class="dynmic">
        <p><a href="<%=path%>/sfOrderManagerController/stockOrder?orderStatus=0">
            <span><img src="<%=path%>/static/images/geren%20(2).png" alt=""><b>${sfOrders0}</b></span>
            <span>待付款</span></a>
        </p>
        <p><a href="<%=path%>/sfOrderManagerController/stockOrder?orderStatus=7">
            <span><img src="<%=path%>/static/images/geren%20(3).png" alt=""></span>
            <span>待发货</span></a>
        </p>
        <p><a href="<%=path%>/sfOrderManagerController/stockOrder?orderStatus=8">
            <span><img src="<%=path%>/static/images/geren%20(1).png" alt="" style="width: 29px;"></span>
            <span>待收货</span></a>
        </p>
    </div>
    <nav>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/sfaccount/commissionHome.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(4).png" alt=""></span>
                <span>我的佣金</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/user/getPersonalInfo.do');">
                <span><img src="<%=path%>/static/images/geren2%20(2).png" alt=""></span>
                <span>个人信息</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/shopview/home.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(3).png" alt=""></span>
                <span>浏览过的店铺</span>
            </li>
        </ul>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/userAddress/toManageAddressPage.html?addAddressJumpType=1&manageAddressJumpType=1');" >
                <span><img src="<%=path%>/static/images/geren2%20(1).png" alt=""></span>
                <span>地址管理</span>
            </li>
            <li> </li>
            <li></li>
        </ul>
    </nav>
</div>
<footer>
    <div>
        <p onclick="javascript:window.location.replace('<%=path%>/index');">
            <span><img src="<%=path%>/static/images/footer%20(3).png" alt=""></span>
            <span>首页</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=path%>/share.html');">
            <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
            <span>分享计划</span>
        </p>
        <p class="active">
            <span><img src="<%=path%>/static/images/footer_x%20(2).png" alt=""></span>
            <span>个人中心</span>
        </p>
    </div>
</footer>
</body>
</html>