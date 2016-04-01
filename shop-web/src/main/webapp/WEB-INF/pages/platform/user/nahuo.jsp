<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zhifuchenggong.css">
</head>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script src="<%=path%>/static/js/checkUtil.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script>
    $(document).ready(function () {
        var addressId = $("#addressId").val();
        if (addressId == "") {
            $("#xz").show();
            $("#sec1").hide();
        }
    })
    function toChooseAddressPage() {
        var selectedAddressId = $("#addressId").val();
        var skuId = $("#skuId").val();
        window.location.href = "<%=path%>/userAddress/toChooseAddressPage.html?pageType=zhifushiyong&selectedAddressId=" + selectedAddressId + "&skuId=" + skuId;
    }
</script>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <p>拿货信息</p>
        </header>
        <section class="sec1">
            <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
            <div onclick="toChooseAddressPage()">
                <input style="display: none" type="text" id="addressId" value="${comUserAddress.id}"/>
                <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.mobile}</span></h2></a>
                <a href="#"><p>收货地址：
                        <span>${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}
                        </span><img src="<%=path%>/static/images/next.png" alt=""></p></a>
            </div>
        </section>
        <section class="sec2">
            <p class="photo">
                <a>
                    <img src="${sku.comSkuImage.fullImgUrl}" alt="">
                </a>
            </p>
            <div>
                <h2>${comSku.name}</h2>
                <h3>规格：<span>默认</span><b>x1</b></h3>
                <p>零售价： 非卖品</p>
                <h1><b style="color:#333333">合计：</b><span>￥0</span></h1>
            </div>
        </section>
        <section class="sec3">
            <p>试用理由： ${userMessage}</p>
        </section>
        <section class="sec4">
            <p>商品合计：<span>￥0</span></p>
            <p>运费：<span>￥${product.shipAmount}</span></p>
            <h1>共<b>1</b>件商品　运费：<span>￥${product.shipAmount}</span>　<b style="color:#333333">合计：</b><span>￥${product.shipAmount}</span></h1>
        </section>
    </div>
</div>
</body>
<script src="<%=path%>/static/js/iscroll.js"></script>
</html>