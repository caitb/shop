<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/haohuo.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
</head>
<body>

<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="<%=path%>/index"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>好货市场</p>
        </header>
        <div class="banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach items="${urls}" var="url">
                        <div class="swiper-slide"><img src="${url}" alt=""></div>
                    </c:forEach>
                    <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner.png" alt=""></div>--%>
                    <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner2.png" alt=""></div>--%>
                    <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner3.png" alt=""></div>--%>
                </div>
                <!-- 如果需要分页器 -->
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <a href="<%=path%>/productList/showProduct">
            <div class="all">
                <img src="<%=path%>/static/images/all.png" alt="">
                <p>查看所有商品<img src="<%=path%>/static/images/next.png" alt=""></p>
            </div>
        </a>
        <div class="main">
            <div class="title"><img src="<%=path%>/static/images/haohuo_1.png" alt="">好货推荐</div>
            <c:forEach items="${indexComS}" var="Sku">
                <a href="/product/${Sku.skuId}">
                    <section class="sec1">
                        <p class="photo">
                            <img src="${Sku.imgUrl}" alt="">
                        </p>
                        <div>
                            <h2>${Sku.comSku.name}</h2>
                            <c:if test="${Sku.isTrial==1}"><h3>试用费用：<span>${Sku.shipAmount}</span>元</h3></c:if>
                            <h3>保 证 金：<span>${Sku.bailLevel}</span>元</h3>
                            <h3><span style="margin-right:10px;font-size:14px;color:red">￥${Sku.comSku.priceRetail}</span>
                                <b>${Sku.discountLevel}</b>
                            </h3>

                            <h2>
                                超过<span>${Sku.agentNum}</span>人合伙
                                <c:if test="${empty Sku.uid}">
                                    <button>我要合伙</button>
                                </c:if>
                                <c:if test="${not empty Sku.uid}">
                                    <button>您已合伙</button>
                                </c:if>
                            </h2>
                        </div>
                    </section>
                </a>
            </c:forEach>
        </div>
    </div>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script>
    var mySwiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay: 3000,
        autoplayDisableOnInteraction: false,
        // 如果需要分页器
        pagination: '.swiper-pagination'
    })
</script>
</body>
</html>