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
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/jinhuoxiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css"/>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/commonAjax.js"/>
    <script src="<%=path%>/static/js/iscroll.js"></script>
    <script>
        function toProduct(skuId){
            fullShow();
            window.location.href = "<%=basePath%>product/"+skuId;
        }
        <%--function toIndex(){--%>
            <%--fullShow();--%>
            <%--window.location.href = "<%=basePath%>index";--%>
        <%--}--%>
        <%--function toPersonCenter(){--%>
            <%--fullShow();--%>
            <%--window.location.href = "<%=basePath%>personalInfo/personalHomePageInfo.html";--%>
        <%--}--%>
    </script>
</head>
<body>
<div class="wrap">
    <main>
        <header class="xq_header">
            <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>订单详情</p>
        </header>
        <div class="tai">
            <img src="<%=path%>/static/images/icon_68.png" alt="">
            <c:if test="${pfBorder.sendType == 1}">
                <h1>已完成</h1>
                <p>您的订单已完成，库存已更新</p>
            </c:if>
            <c:if test="${pfBorder.sendType == 2}">
                <h1>待发货</h1>
                <p>您的订单已提交，请耐心等待收获</p>
            </c:if>
        </div>
        <div class="kuaidi">
            <c:if test="${pfBorder.sendType == 1}">
                <p>发货方式：<span>平台代发</span></p>
            </c:if>
            <c:if test="${pfBorder.sendType == 2}">
                <p>发货方式：<span>自己发货</span></p>
                <p>承运公司：<span></span></p>
                <p>运单编号：<span></span></p>
            </c:if>
            <c:if test="${pfBorder.orderType == 1}">
                <p>类型：<span>补货</span></p>
            </c:if>
            <c:if test="${pfBorder.orderType == 2}">
                <p>类型：<span>拿货</span></p>
            </c:if>
        </div>
        <c:if test="${pfBorder.sendType==2 || pfBorder.orderType==2}">
            <section class="sec1">
                <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                <div>
                    <a href="#"><h2>收货人：<b>${pfBorderConsignee.consignee}</b> <span>${pfBorderConsignee.mobile}</span></h2></a>
                    <a href="#"><p>收货地址： <span>${pfBorderConsignee.address}</span><img src="<%=path%>/static/images/next.png" alt=""></p></a>
                </div>
            </section>
        </c:if>
        <c:if test="${pfBorder.sendType == 1}">
            <c:forEach var="pfBorderItem" items="${pfBorderItems}">
                <div class="paidan">
                    <h1><img src="<%=path%>/static/images/kucun.png" alt=""><b>平台在线库存增加<span>${pfBorderItem.quantity}</span>件</b></h1>
                    <p>当前平台在线库存量为<span>${pfBorderItem.realStock}</span>件</p>
                </div>
                <section class="sec2">
                    <p class="photo">
                        <a href="#" onclick="toProduct(${pfBorderItem.skuId})">
                            <img src="${skuImg}${pfBorderItem.logoUrl}" alt="">
                        </a>
                    </p>
                    <div>
                        <h2>${pfBorderItem.skuName}</h2>
                        <h3>规格：<span>默认</span><b>x${pfBorderItem.quantity}</b></h3>
                        <p>零售价：${pfBorderItem.unitPrice}</p>
                        <h1><b style="color:#333333">合计：</b><span>￥${pfBorderItem.totalPrice}</span></h1>
                    </div>
                </section>
            </c:forEach>
        </c:if>

        <section class="sec3">
            <p>留言： <input type="text" value="${pfBorder.userMessage}"></p>
        </section>
        <section class="sec4">
            <p>商品合计：<span>￥${pfBorder.productAmount}元</span></p>
            <c:if test="${pfBorder.sendType == 1}">
                <p>运费：<span>￥${pfBorder.shipAmount}元</span></p>
            </c:if>
            <c:if test="${pfBorder.sendType == 2}">
                <p>运费：<span>到付</span></p>
            </c:if>
            <h1>共<b>${sumQuantity}</b>件商品　运费：<span>￥${pfBorder.shipAmount}</span>　<b style="color:#333333">合计：</b><span>￥${pfBorder.orderAmount}</span></h1>
        </section>
        <div class="sec5">
            <p>订单编号：<span>${pfBorder.orderCode}</span></p>
            <%--<p>订单编号：<span>1290301293</span><span>1290301293</span></p>--%>
            <p>创建时间：<span><fmt:formatDate value="${pfBorder.createTime}"  type="time" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
            <p>付款时间：<span><fmt:formatDate value="${pfBorder.payTime}"  type="time" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
            <p>发货时间：<span><fmt:formatDate value="${pfBorder.shipTime}"  type="time" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
        </div>
        <botton class="btn">
            返回市场
        </botton>
        <h3></h3>
    </main>
</div>
</body>
</html>