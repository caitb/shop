<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/zhifuchenggong.css">
    <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="${path}/static/js/pageJs/hideWXShare.js"></script>
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <p>支付失败</p>
        </header>
        <div class="tai">
            <img src="${path}/static/images/icon_65.png" alt="">
            <h1>支付失败！</h1>
            <p>您的订单支付失败</p>
        </div>
        <section class="sec1">

            <img src="${path}/static/images/zhifu_ad.png" alt="">
            <div>
                <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.mobile}</span></h2></a>
                <a href="#"><p>收货地址： <span>${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}</span></p></a>
            </div>

        </section>
        <section class="sec2">
            <p class="photo">
                <a href="${path}/static/html/xiangqing.html">
                    <img src="${path}/static/images/shenqing_1.png" alt="">
                </a>
            </p>
            <div>
                <h2>${product.name}</h2>
                <h3>规格：<span>试用装</span><b>x1</b></h3>
                <p>零售价： 非卖品</p>
                <h1><b style="color:#333333">合计：</b><span>￥0</span></h1>
            </div>
        </section>
        <section class="sec3">
            <p>备注信息： ${pfCorder.userMassage}</p>
        </section>
        <section class="sec4">
            <p>商品合计：<span>￥0</span></p>
            <p>运费：<span>￥${product.shipAmount}</span></p>
            <h1>共<b>1</b>件商品　运费：<span>￥${product.shipAmount}</span>　<b style="color:#333333">合计：</b><span>￥${product.shipAmount}</span></h1>
        </section>
        <button style="display:block">继续支付</button>
    </div>
</div>
</body>
</html>