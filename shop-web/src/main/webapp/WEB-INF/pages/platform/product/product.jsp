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
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/common.jsp" %>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/xiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/font-awesome/font-awesome.min.css">
    <script src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="index.html"><img src="../images/xq_rt.png" alt=""></a>
            <p>我要成为合伙人</p>
        </header>
        <div class="banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach items="${productDetails.comSkuImages}" var="urlImg">
                        <div class="swiper-slide"><img src="${urlImg.fullImgUrl}" alt=""></div>
                    </c:forEach>
                </div>
                <!-- 如果需要分页器 -->
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <div class="price">
            <p>${productDetails.name}</p>
            <p><span style="padding:0;">${productDetails.slogan}</span></p>
            <p><b>￥${productDetails.priceRetail}</b><span><c:choose><c:when
                    test="${productDetails.isPartner}">${productDetails.discountLevel}</c:when>
                <c:otherwise>成为合伙人后可查看利润</c:otherwise></c:choose>
            </span></p>
        </div>
        <div class="dlpople">
            <p>快递：<span>到付</span></p>
            <p>代理人数：<b><span>${productDetails.agentNum}</span>人</b></p>
        </div>
        <div class="dlpople">
            <p>库存</p>
            <p><span>${productDetails.stock}</span></p>
        </div>
        <nav>
            <ul>
                <li>
                    <h1><img src="<%=path%>/static/images/xq_nav.png" alt=""></h1>
                    <span>正品保障</span>
                </li>
                <li>
                    <h1><img src="<%=path%>/static/images/xq_nav2.png" alt=""></h1>
                    <span>合伙人优惠</span>
                </li>
                <li>
                    <h1><img src="<%=path%>/static/images/xq_nav3.png" alt=""></h1>
                    <span>七天退换</span>
                </li>
                <li>
                    <h1><img src="<%=path%>/static/images/xq_nav4.png" alt=""></h1>
                    <span>创业补贴</span>
                </li>
            </ul>
        </nav>
        <div class="tuw">
            <h1>图文详情</h1>
        </div>
        <main>
            <section class="sec1">
                ${productDetails.content}
            </section>
            <section class="sec2">
                <h2 style="color:#F74A11;">麦链合伙人提供技术支持</h2>
                <p>Copyright2005-2016 iimai.com 版权所有</p>
                <p>京ICP证080047号[京ICP备08010314号-6]</p>
                <p>文网文[2009]024号 新出网证（京）字069号</p>
                <p>京公网安备 11000002000006号</p>
            </section>

        </main>
    </div>
</div>
<footer>
    <section class="sec3">
        <%--   <p><a href="<%=path%>/corder/applyTrialToPage.json?skuId=${productDetails.id}" onclick="applyTrial(${productDetails.id})">申请试用</a></p>--%>
        <p><a href="" onclick="applyTrial(${productDetails.id})">申请试用</a></p>
        <p><a href="<%=basePath%>border/apply.shtml?skuId="${productDetails.id}">申请合伙人</a></p>
    </section>
</footer>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script>
    function applyTrial(skuId) {
        $.post("/corder/isApplyTrial.do",
                {
                    "skuId": skuId
                }, function (data) {
                    if (data == "false") {
                        window.location.href = "<%=path%>/corder/applyTrialToPage.json?skuId=" + skuId;
                    } else {
                        alert("此商品您已试用过，不能再次使用");
                    }
                });
    }

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