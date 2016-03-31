<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/dingdanxiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css"/>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/commonAjax.js"/>
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>

<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>订单详情</p>
    </header>
    <div class="tai">
        <img src="<%=path%>/static/images/icon_64.png" alt="">
        <h1>支付成功</h1>
        <c:if test="${pfBorder.sendType==1}">
            <p>您的订单支付成功，请耐心等待收货</p>
        </c:if>
        <c:if test="${pfBorder.sendType==2}">
            <p>您的订单支付成功，在线库存已经变更</p>
        </c:if>
    </div>
    <section class="sec1">
    <c:if test="${pfBorder.sendType==1}">
        <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
        <div>
            <a href="#"><h2>收货人：<b>${pfBorderConsignee.consignee}</b> <span>${pfBorderConsignee.mobile}</span></h2></a>
            <a href="#"><p>收货地址： <span>${pfBorderConsignee.address}</span><img src="<%=path%>/static/images/next.png" alt=""></p></a>
        </div>
    </c:if>
    </section>
    <section class="sec2">
        <c:forEach var="borderItem" items="${pfBorderItems}">
            <p class="photo">
                <a href="<%=path%>/static/html/xiangqing.html">
                    <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                    <img src="${skuImg}${borderItem.logoUrl}" alt="">
                </a>
            </p>
            <div>
                <h2>${borderItem.skuName}</h2>
                <h3>规格：<span>默认</span><b>x${borderItem.quantity}</b></h3>
                <p>零售价：${borderItem.unitPrice}</p>
                <h1><b style="color:#333333">合计：</b><span>￥${borderItem.totalPrice}</span></h1>
            </div>
        </c:forEach>
    </section>
    <section class="sec3">
        <p>留言： <input type="text" value="${pfBorder.userMessage}"></p>
    </section>
    <section class="sec4">
        <p>商品合计：<span>￥${pfBorder.orderAmount}元</span></p>
        <p>运费：<span>￥${pfBorder.shipAmount}元</span></p>
        <h1>共<b>${sumQuantity}</b>件商品　运费：<span>￥${pfBorder.shipAmount}</span>　<b style="color:#333333">合计：</b><span>￥${pfBorder.receivableAmount}</span></h1>
    </section>
    <div class="sec5">
        <p>订单编号：<span>${pfBorder.orderCode}</span></p>
        <%--<p>订单编号：<span>1290301293</span><span>1290301293</span></p>--%>
    </div>
    <div class="sec6">
        <p><a href="index.html">返回首页</a></p>
        <p><a href="gerenzhongxin.html">返回个人中心</a></p>
    </div>
</div>
</body>
</html>