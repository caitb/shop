<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/Paysuccess.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css"/>
    <script>
        function toProduct(skuId){
            fullShow();
            window.location.href = "<%=basePath%>product/skuDetails.shtml?skuId="+skuId;
        }
        function toMarket(){
            fullShow();
            window.location.href = "<%=basePath%>marketGood/market";
        }
    </script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <p>拿货成功</p>
    </header>
    <div class="tai">
        <img src="<%=path%>/static/images/icon_64.png" alt="">
        <h1>拿货成功</h1>
        <p>您的拿货订单申请成功，请耐心等待收货</p>
    </div>
    <div class="content">
        <section class="sec1">
            <div>
                <a href="#"><h2>收货人：<b>${pfBorderConsignee.consignee}</b></h2>
                </a>
                <a href="#"><p>收货地址： <span>${pfBorderConsignee.provinceName} ${pfBorderConsignee.cityName} ${pfBorderConsignee.regionName} ${pfBorderConsignee.address}</span></p></a>
            </div>
        </section>
    </div>
    <h1>
        您还可以
    </h1>
    <div class="oPeration">
        <a href="<%=path%>/borderManage/borderDetils.html?id=${pfBorder.id}">查看订单</a>
        <a href="#" onclick="toMarket()">返回市场</a>
    </div>
</div>
</body>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"/>
<script src="<%=path%>/static/js/iscroll.js"></script>
</html>