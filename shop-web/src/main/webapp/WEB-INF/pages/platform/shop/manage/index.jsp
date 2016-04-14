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
    <title>麦链麦链商城</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/main.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/wodedianpu.css">
</head>
<body>
<div class="wrap">
    <div class="na">
        <p><img src="${comUser.wxHeadImg}" alt=""></p>
        <h1>
            <span>${comUser.realName}，欢迎登陆~</span>
        </h1>
    </div>
    <div class="header">
        <div>
            <p>${sfShop.name}</p>
            <span>分享</span>
        </div>
        <div>
            <p>
                <span>麦链商城光放认证</span>
            </p>
        </div>
        <img src="${sfShop.logo}" alt="">
        <nav>
            <p onclick="javascript:window.location.replace('<%=basePath%>sfOrderController/stockShipOrder');"><span>${orderCount}</span><span>店铺总订单</span></p>
            <p><span>${sfShop.saleAmount}</span><span>店铺总销售额</span></p>
            <p><span>
                <c:if test="${sumLevel == null}">0</c:if>
                <c:if test="${sumLevel != null}">${sumLevel}</c:if>
               </span>
                <span>店铺总参与人数</span>
            </p>
        </nav>
    </div>
    <nav>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/managePro.htmls?shopId='+sfShop+'&&isSale=1');"><span><img src="<%=basePath%>static/images/nav1.jpg" alt=""></span><span>商品管理</span></p>
        <p onclick="javascript:window.location.replace('<%=basePath%>sfOrderController/stockShipOrder');"><span><img src="<%=basePath%>static/images/nav2.jpg" alt=""></span><span>订单管理</span></p>
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
        <a href="<%=basePath%>index">
            <span><img src="<%=path%>/static/images/footer%20(2).png" alt=""></span>
            <span>首页</span>
        </a>
        <a class="active" href="<%=basePath%>shop/manage/index">
            <span><img src="<%=path%>/static/images/footer%20(3).png" alt=""></span>
            <span>我的店铺</span>
        </a>
        <a href="<%=basePath%>personalInfo/personalHomePageInfo.html">
            <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
            <span>个人中心</span>
        </a>
    </div>
</footer>
</body>
</html>