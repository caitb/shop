<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/xiangqing.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/swiper.3.1.7.min.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
</head>
<div class="wrap">
    <div class="box">
        <div class="banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach items="${productDetails.comSkuImages}" var="urlImg">
                        <div class="swiper-slide"><img src="${urlImg.fullImgUrl}" alt=""></div>
                    </c:forEach>
                </div>
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <div class="price">
            <p style="padding-right:30px">${productDetails.name}</p>
            <p><span style="padding:0;">${productDetails.slogan}</span></p>
            <p>￥<b>${productDetails.priceRetail}</b><span>最高利润${productDetails.maxDiscount}%
            </span><span style="color: #999999;float:right;margin:4px 10px 0 0;">代理人数：
                <c:if test="${productDetails.agentNum >9999}">
                   超过9999</span>
                </c:if>
                ${productDetails.agentNum}</span>
            </p>
        </div>
        <div class="dlpople">
            <p>运费</p>
            <p><span>到付</span></p>
        </div>
        <nav>
            <ul>
                <li>
                    <h1><img src="${path}/static/images/xq_nav.png" alt=""></h1>
                    <span>正品保障</span>
                </li>
                <li>
                    <h1><img src="${path}/static/images/xq_nav2.png" alt=""></h1>
                    <span>合伙人优惠</span>
                </li>
                <li>
                    <h1><img src="${path}/static/images/xq_nav3.png" alt=""></h1>
                    <span>七天退换</span>
                </li>
                <li>
                    <h1><img src="${path}/static/images/xq_nav4.png" alt=""></h1>
                    <span>创业补贴</span>
                </li>
            </ul>
        </nav>
        <main>
            <h1 style="background:white url('${path}/static/images/xiangqing_1.png') no-repeat 10px;background-size: 15px;">
                <a name="1f">品牌介绍</a></h1>
            <p>${productDetails.brand}</p>
            <h1 style="background:white url('${path}/static/images/xiangqing_2.png') no-repeat 10px;background-size: 15px;">
                <a name="2f">商业政策</a></h1>
            <p>${productDetails.policy}</p>
            <h1 style="background:white url('${path}/static/images/xiangqing_3.png') no-repeat 10px;background-size: 15px;">
                <a name="3f">商品详情</a></h1>
            <p>${productDetails.content}</p>
        </main>
    </div>
</div>
<script src="<%=path%>/static/js/swiper.3.1.7.min.js"></script>
<script>
    var mySwiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay: 3000,
        // 如果需要分页器
        pagination: '.swiper-pagination'
    })
</script>
</body>
</html>