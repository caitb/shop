<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/trialPaySuccess.css">
    <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="${path}/static/js/hideWXShare.js"></script>
</head>
<body>
   <div class="wrap">
      <header class="xq_header">
            <p>支付成功</p>            
        </header>
            <div class="tai">
                 <img src="<%=path%>/static/images/icon_64.png" alt="">
                  <h1>试用支付成功</h1>
                  <p>您的订单支付成功，请耐心等待收货</p>
            </div>
            <div class="content">
                <p>收货人：<span>${comUserAddress.name}</span></p>
                <p>收货地址：<span>${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}</span></p>
                <h1>总价：<span>￥${product.shipAmount}</span></h1>
            </div>
            <h1>
                您还可以
            </h1>
       <div class="oPeration">
           <a href="${path}/corder/checkTrialOrderInfo.shtml?pfCorderId=${pfCorderId}&skuId=${skuId}&addressId=${addressId}" >查看订单</a>
           <a href="${path}/marketGood/market">返回市场</a>
       </div>
    </div>
</body>
</html>