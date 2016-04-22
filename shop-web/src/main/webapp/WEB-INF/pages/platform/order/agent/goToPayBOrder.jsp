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
        <p>订单支付</p>
    </header>
    <div class="xinxi">
        <p>注册信息</p>
        <p>确定拿货方式</p>
        <p>支付订单</p>
    </div>
    <div class="sec1">
        <c:forEach items="${pfBorderItems}" var="pfBorderItem">
            <p><span>商品信息：</span><span>${pfBorderItem.skuName}</span></p>
            <p><span>数量：</span><span>${pfBorderItem.quantity}</span></p>
            <p><span>商品总金额：</span><span>￥${pfBorderItem.totalPrice}</span></p>
        </c:forEach>
        <p><span>保证金：</span><span>￥${pfBorder.bailAmount}</span></p>
        <p><span>需付款：</span><span>￥${pfBorder.receivableAmount}</span></p>
    </div>
    <button id="submit">微信支付</button>
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
</script>
</html>
