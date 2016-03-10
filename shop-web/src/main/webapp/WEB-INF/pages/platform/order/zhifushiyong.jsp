<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zhifushiyong.css">
</head>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script>
    $(document).ready(function () {
            var addressId = $("#addressId").val();
            if (addressId==""){
                $("#xz").show();
                $("#sec1").hide();
            }
    })
    function toChooseAddressPage(){
        var addressId = $("#addressId").val();
        var pfCorderId = $("#pfCorderId").val();
        window.location.href = "<%=path%>/userAddress/toChooseAddressPage.html?addressId="+addressId+"&pfCorderId="+pfCorderId;
    }
</script>
<body>
    <header class="xq_header">
          <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>确认订单</p>            
    </header>
    <main>
        <div id="xz">
            <div class="xinz">
                <p>新增收货地址</p>
            </div>
        </div>
        <div id="sec1">
            <section class="sec1">
               <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
               <div>
                    <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.mobile}</span></h2></a>
                    <a href="#"><p>收货地址：
                        <input id="addressId" style="display:none;" value="${comUserAddress.id}"/>
                        <input id="pfCorderId" style="display:none;" value="${pfCorderId}"/>
                        <span>${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}
                        </span><img onclick="toChooseAddressPage()" src="<%=path%>/static/images/next.png" alt=""></p></a>
                </div>
            </section>
        </div>
        <section class="sec2">
            <p class="photo">
                    <img src="${skuDefaultImg}" alt="${skuImgAlt}">
            </p>
            <div>
                <h2>${product.name}<span>x1000</span></h2>
                <h3>规格：<span>默认</span></h3>
                <p>￥0</p>
            </div>
        </section>
        <section class="sec3">
            <p>运费<span>${product.priceRetail}</span></p>
            <p>库存<b>545454</b></p>
            <p>留言：<input type="text"></p>
            <h1>共<b style="font-size:12px">800</b>件商品　运费：<span>￥300</span></h1>
        </section>
        
        <section class="sec4">
            <p>合计：<span>￥3200</span></p>
            <p>运费：<b>${product.priceRetail}</b></p>
            <p>需付：<span>￥2508.00</span></p>
        </section>
        
        <a href="javascript:;" class="weixin">微信支付</a>
        <a href="javascript:;" class="xianxia">线下支付</a>
    </main>
    
</body>
</html>