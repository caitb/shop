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
            <c:if test="${pfBorder.orderType==0}">
                <p><span>合伙人套餐：</span><span>${pfBorder.orderAmount}元套餐</span></p>
                <p><span>保证金：</span><span>￥${pfBorder.bailAmount}</span></p>
                <p><span>拿货门槛：</span><span>￥${pfBorder.productAmount}</span></p>
            </c:if>
            <c:if test="${pfBorder.orderType==1}">
                <p><span>单价：</span><span>￥${pfBorderItem.unitPrice}</span></p>
            </c:if>
            <p><span>数量：</span><span>${pfBorderItem.quantity}</span></p>
        </c:forEach>
        <h1>需付金额：</h1>
        <h2><span>需付款：</span><span>￥${pfBorder.receivableAmount}</span></h2>
    </div>
    <button id="submit" class="wePay"><span><img src="${path}/static/images/icon_36.png" alt="">微信支付</span></button>
    <button class="downPay" id="downPay"><span><img src="${path}/static/images/xianxia.png" alt="">线下支付</span></button>
    <p><span style="color:red;">*大额转账请选择线下支付</span></p>
</div>
<div class="back_box" style="display: none">
    <div class="back"></div>
    <div class="back_que">
        <p>数据确认</p>
        <h4>您需要在${downPayLatestTime}前将￥${pfBorder.receivableAmount}转到麦链合伙人对公账户。</h4>
        <h4>*请在汇款单的附言处注明“您的手机号+订单号”如：手机18611536163 订单4564646546（非常重要！）</h4>
        <h4>*线下支付到账时间为T+1天到账，审核时间为1个工作日</h4>
        <h3>
            <span class="que_qu" id="downPayCancel">取消</span>
            <span class="que_que" id="downPayConfirm" >确定</span>
        </h3>
    </div>
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
        $(".back_box").show();
    })
    $("#downPayCancel").click(function (event) {
        $(".back_box").hide();
    })
    $("#downPayConfirm").click(function (event) {
        if ($(this).html() == "正在提交...") {
            return;
        }
        $(this).html("正在提交...");
        window.location.href = "${basePath}border/offinePayment.html?bOrderId=${pfBorder.id}";
    })
</script>
</html>
