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
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/gerenzhongxin.css">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
</head>
<body>
<div class="wrap">
    <div class="na">
        <p><img src="${user.wxHeadImg}" alt=""></p>
        <h1>
            <span>${user.wxNkName}</span>
            <c:if test="">
                <span>您的推荐人是<b>${userPid.realName}</b></span>
            </c:if>
            <%--<span>会员：<b><c:if test="${user.isBuy==0 || empty user.isBuy}">否</c:if><c:if test="${user.isBuy==1}">是</c:if></b> <c:if test="${user.isBuy==0}">（需要购买至少一件商品）</c:if></span>--%>
        </h1>
    </div>
    <div class="jiang">
        <p>我的奖励</p>
        <h1>￥<span>${cumulativeFee}</span></h1>
    </div>
    <nav>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/stockOrder');">
                <span><img src="<%=path%>/static/images/my.png" alt="" style="width:23px;"><%--<b></b>--%></span>
                <span>我的订单</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/sfaccount/rewardHome.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(4).png" alt=""></span>
                <span>佣金管理</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/user/getPersonalInfo.do');">
                <span><img src="<%=path%>/static/images/geren2%20(2).png" alt=""></span>
                <span>个人信息</span>
            </li>
        </ul>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/shopview/home.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(3).png" alt=""></span>
                <span>分享赚佣金</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/userAddress/toManageAddressPage.html?addAddressJumpType=1&manageAddressJumpType=1');" >
                <span><img src="<%=path%>/static/images/geren2%20(1).png" alt=""></span>
                <span>地址管理</span>
            </li>
            <li> </li>
        </ul>
    </nav>
</div><c:if test="${fm!=0}">
<footer>
    <div>
        <p onclick="javascript:window.location.replace('<%=basePath%>${shopId}/${userPid}/shop.shtml');">
            <span><img src="<%=path%>/static/images/footer%20(3).png" alt=""></span>
            <span>首页</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${shopId}');">
            <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
            <span>分享计划</span>
        </p>
        <p class="active">
            <span><img src="<%=path%>/static/images/footer_x%20(2).png" alt=""></span>
            <span>个人中心</span>
        </p>
    </div>
</footer></c:if>
</body>
</html>