<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>订单详情</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/shouyintai.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <%--<a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>--%>
        <p>支付订单</p>
    </header>
    <div class="xinxi">
        <p>注册信息</p>
        <p>确定拿货方式</p>
        <p>支付订单</p>
    </div>
    <div class="sec1">
        <h1>订单信息：</h1>
        <c:forEach items="${pfBorderItems}" var="pfBorderItem">
            <p><span>商品信息：</span><span>${pfBorderItem.skuName}</span></p>
            <p><span>合伙人套餐：</span><span>${pfBorder.orderAmount}元套餐</span></p>
            <c:if test="${pfBorder.orderType==0}">
                <p><span>保证金：</span><span>￥${pfBorder.bailAmount}</span></p>
                <p><span>拿货门槛：</span><span>￥${pfBorder.productAmount}</span></p>
            </c:if>
            <p><span>数量：</span><span>${pfBorderItem.quantity}</span></p>
        </c:forEach>
        <h1>需付金额：</h1>
        <h2><span>需付款：</span><span>￥${pfBorder.receivableAmount}</span></h2>
    </div>
    <button id="submit" class="wePay"><span><img src="${path}/static/images/icon_36.png" alt="">微信支付</span></button>
    <button class="downPay" id="downPay"><span><img src="${path}/static/images/xianxia.png" alt="">线下支付</span></button>
</div>
</body>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script>
    $("#submit").click(function (event) {
        if ($(this).html() == "正在提交...") {
            return;
        }
        $(this).html("正在提交...");
        window.location.href = "${basePath}border/payBOrder.shtml?bOrderId=${pfBorder.id}";
    })
    $("#downPay").click(function (event) {
        if (${pfBorder.orderStatus==9}) {
            alert("已选择线下支付无需再次选择");
            return false;
        }
        if (${pfBorder.orderStatus!=0}){
            alert("支付状态异常");
            return false;
        }

        if ($(this).html() == "正在提交...") {
            return;
        }
        $(this).html("正在提交...");
        window.location.href = "${basePath}border/offinePayment.html?bOrderId=${pfBorder.id}";
    })
</script>
</html>
