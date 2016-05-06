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
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/commonAjax.js"/>
    <script src="<%=path%>/static/js/iscroll.js"></script>
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
        <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>支付成功</p>
    </header>
    <div class="tai">
        <img src="<%=path%>/static/images/icon_64.png" alt="">
        <h1>支付成功</h1>
        <p>您的订单支付成功，请耐心等待收货</p>
    </div>
    <div class="content">
        <c:if test="${pfBorder.sendType == 1 && pfBorder.orderStatus == 1}">
            <c:forEach var="pfBorderItem" items="${pfBorderItems}">
            <div class="paidan">
                <h1 style="border:none"><img src="<%=path%>/static/images/kucun.png" alt=""><b>平台在线库存增加<span>${pfBorderItem.quantity}</span>件</b></h1>
                <p>当前平台在线库存量为<span>${pfBorderItem.realStock}</span>件</p>
            </div>
        </c:forEach>
        </c:if>
        <c:if test="${pfBorder.sendType == 1 && pfBorder.orderStatus == 6}">
            <div class="paidan">
            <p>您的订单正在排单，请耐心等待</p>
            </div>
        </c:if>
        <c:if test="${pfBorder.sendType==2 || pfBorder.orderType==2}">
            <section class="sec1">
                <div>
                    <a href="#"><h2>收货人：<b>${pfBorderConsignee.consignee}</b> <span>${pfBorderConsignee.mobile}</span></h2></a>
                    <a href="#"><p>收货地址： <span>${pfBorderConsignee.address}</span></p></a>
                </div>
            </section>
        </c:if>
        <h1>总价：<span>￥${pfBorder.productAmount}元</span></h1>
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
</html>