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
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/zhifuchenggong.css">
</head>
<body>
<header>
    <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>支付成功</p>
</header>
<div class="wrap">

    <div class="tai">
        <img src="<%=path%>/static/images/icon_64.png" alt="">
        <h1>支付成功</h1>
        <p>您的订单支付成功，请耐心等待收货</p>
    </div>
    <section class="sec1">

        <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
        <div>
            <a href="#"><h2>收货人：<b>${orderConsignee.consignee}</b> <span>${orderConsignee.mobile}</span></h2></a>
            <a href="#"><p>收货地址： <span>
                ${orderConsignee.provinceName} ${orderConsignee.cityName} ${orderConsignee.regionName} ${orderConsignee.address}
            </span></p></a>
        </div>

    </section>
    <section class="sec2">
        <p class="photo">
            <a>
                <img src="<%=path%>/static/images/shenqing_1.png" alt="">
            </a>
        </p>
        <c:forEach items="${orderItems}" var="orderItem">
            <div>
                <h2>${orderItem.skuName}</h2>
                <h3>规格：<span>默认</span></h3>
                <h3>￥${orderItem.originalPrice}<b>x${quantity}</b></h3>

            </div>
        </c:forEach>
    </section>
    <section class="sec3">
        <p>买家留言：${order.userMessage}</p>
    </section>
    <section class="sec4">
        <p><b>合计：</b><span>￥${order.productAmount}</span></p>
        <p><b>运费：</b><b style="text-align:left;text-indent:2px;">${order.shipAmount}</b></p>
        <p><b>需付：</b><span>￥${order.orderAmount}</span></p>
    </section>
    <div class="sec6">
        <p><a onclick="contactSeller()">联系卖家</a></p>
        <p><a onclick="askForInvoice()">索要发票</a></p>
    </div>
</div>
<div class="back"></div>
<div id="contactSellerDivId" class="back_l">
    <p>索要发票</p>
    <p>请联系：00000000</p>
    <button  onclick="closeContactSeller()">知道了</button>
    <span class="close" onclick="closeContactSeller()">×</span>
</div>
<div id="askForInvoiceDivId" class="back_l back_s">
    <p>联系卖家</p>
    <p>请联系：00000000</p>
    <button onclick="closeAskForInvoice()">知道了</button>
    <span onclick="closeAskForInvoice()" class="close">×</span>
</div>
<div id="shareDivId"class="back_f">
    <p>支付成功！</p>
    <p>关注麦链商城微信公众账号，查看订单最新状态</p>
    <img src="<%=path%>/static/images/wx.jpg" alt="">
    <p>长按识别二维码</p>
    <p>或微信搜索“麦链商城”公众账号，关注麦链商城微信公众账号</p>
    <span onclick="closeShare()" class="close">×</span>
</div>
<script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
<script>
    function contactSeller(){
        $("#contactSellerDivId").show();
    }
    function closeContactSeller(){
        $("#contactSellerDivId").hide();
    }
    function askForInvoice(){
        $("#askForInvoiceDivId").show();
    }
    function closeAskForInvoice(){
        $("#askForInvoiceDivId").hide();
    }
    function closeShare(){
        $("#shareDivId").hide();
    }

</script>
</body>
</html>
