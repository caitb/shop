<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/jinhuoxiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
   
    <div class="wrap">
       <main>
            <header class="xq_header">
                  <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>订单详情</p>            
            </header>
            <div class="tai"><c:if test="${borderDetail.pfBorder.orderStatus==0}">
                           <h1>未付款</h1>
                           <p>亲，未付款的订单可以保留7天~~</p></c:if><c:if test="${borderDetail.pfBorder.orderStatus==0}">
            </div>
            <div class="kuaidi"> <c:forEach items="${borderDetail.pfBorderFreights}" var="bdpb">
                <p>承运公司：<span>${bdpb.shipManName}</span></p>
                <p>运单编号：<span>${bdpb.freight}</span></p></c:forEach>
            </div>
            <section class="sec1">
                       <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                       <div>
                            <a href="#"><h2>收货人：<b>${borderDetail.pfBorderConsignee.consignee}</b> <span>${borderDetail.pfBorderConsignee.mobile}</span></h2></a>
                            <a href="#"><p>收货地址： <span>${borderDetail.pfBorderConsignee.provinceName} ${borderDetail.pfBorderConsignee.cityName} ${borderDetail.pfBorderConsignee.regionName} ${borderDetail.pfBorderConsignee.address}</span><img src="<%=path%>/static/images/next.png" alt=""></p></a>
                        </div>

            </section><c:forEach items="${borderDetail.pfBorderItems}" var="bdpi">
            <section class="sec2">
                <p class="photo">
                   <a href="<%=path%>/static/html/xiangqing.html">
                        <img src="${bdpi.skuUrl}" alt="">
                    </a>
                </p>
                <div>
                    <h2>${bdpi.skuName}</h2>
                    <h3>规格：<span>默认</span><b>x${bdpi.quantity}</b></h3>
                    <p>零售价： ${bdpi.unitPrice}</p>
                    <h1><b style="color:#333333">合计：</b><span>￥${bdpi.totalPrice}</span></h1>
                </div>
            </section></c:forEach>
            <section class="sec3">
                <p>留言： <input type="text"value="${borderDetail.pfBorder.userMessage}"></p>
            </section>
            <section class="sec4">
                <p>商品合计：<span>￥${borderDetail.pfBorder.productAmount}元</span></p>
                <p>运费：<span>￥${borderDetail.pfBorder.shipAmount}元</span></p>
                <h1>共<b>${borderDetail.pfBorder.totalQuantity}</b>件商品　运费：<span>￥${borderDetail.pfBorder.orderAmount}</span>　<b style="color:#333333">合计：</b><span>￥${borderDetail.pfBorder.payAmount}</span></h1>
            </section>
            <div class="sec5">
                <p>订单编号：<span>${borderDetail.pfBorder.orderCode}</span></p>
                <p>创建时间：<span><fmt:formatDate value="${borderDetail.pfBorder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <p>付款时间：<span><fmt:formatDate value="${borderDetail.pfBorder.payTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <p>发货时间：<span><fmt:formatDate value="${borderDetail.pfBorder.shipTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
            </div>
            <botton class="btn">
                确认收货
            </botton>
            <h3></h3>
        </main>
        <div class="back">
                <div class="back_que">
                    <p>确认减库存?</p>
                    <h4>亲，请您核对却是厚道商品后在操作确认收货</h4>

                    <h3>
                        <span class="que_qu">取消</span>
                        <span class="que_que">确认</span>
                    </h3>
                </div>
    </div>
    </div>
    
</body>
</html>