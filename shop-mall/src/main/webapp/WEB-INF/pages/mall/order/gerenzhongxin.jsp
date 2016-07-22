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
    <script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
    <script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
</head>
<script>
    function skipToPromotionPage(){
        window.location.href="";
    }
</script>
<body>
<div class="wrap">
    <div class="floor">
        <img src="${path}/static/images/qwe%20(2).png" alt="">
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
    </div>
    <div class="jiang">
        <p>我的奖励</p>
        <h1>￥<span><fmt:formatNumber value="${cumulativeFee}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></span></h1>
    </div>
    <nav>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/stockOrder');">
                <span><img src="<%=path%>/static/images/my.png" alt=""><%--<b></b>--%></span>
                <span>我的订单</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/shopview/home.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(3).png" alt=""></span>
                <span>分享赚佣金</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/user/getPersonalInfo.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(2).png" alt=""></span>
                <span>个人信息</span>
            </li>
        </ul>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/sfaccount/rewardHome.shtml');">
                <span><img src="<%=path%>/static/images/geren2%20(4).png" alt=""></span>
                <span>佣金管理</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/distribution/fansHome.shtml');">
                <span><img src="<%=path%>/static/images/fensi.png" alt=""></span>
                <span>粉丝</span>
            </li>
            <li onclick="javascript:window.location.replace('<%=path%>/distribution/spokesManHome.shtml');">
                <span><img src="<%=path%>/static/images/daiyanren.png" alt=""></span>
                <span>代言人</span>
            </li>
        </ul>
        <ul>
            <li onclick="javascript:window.location.replace('<%=path%>/mallmessage/toMessageCenter.shtml?cur=0');">
                <span><img src="<%=path%>/static/images/xiaoxizhongxin.png" alt="">
                    <c:if test="${countMsg>0}">
                        <b></b>
                    </c:if>
                </span>
                <span>消息中心</span>
            </li>
            <li style="background:#f3f4f5;border: none;"></li>
            <li style="background:#f3f4f5;border: none;"></li>
        </ul>
    </nav>
    <c:if test="${not empty userPromotions}">
        <img src="${path}/static/images/activity.png" onclick="javascript:window.location.replace('<%=path%>/showPromotion/getAllPromoDetail.html');" alt="">
    </c:if>
</div><c:if test="${fm!=0}">
    <footer>
        <div>
            <p onclick="javascript:window.location.replace('<%=basePath%>${shopId}/${userPid}/shop.shtml');">
                <span><img src="<%=path%>/static/images/dibu1.png" alt=""></span>
                <span>首页</span>
            </p>
            <p onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${sfShop.id}');">
                <span><img src="<%=path%>/static/images/dibu2.png" alt=""></span>
                <span>二维码</span>
            </p>
            <p onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/toBorderManagement?fm=1');">
                <span><img src="<%=path%>/static/images/dibu33.png" alt=""></span>
                <span class="active" >个人中心</span>
            </p>
        </div>
    </footer>
</c:if>
</body>

</html>