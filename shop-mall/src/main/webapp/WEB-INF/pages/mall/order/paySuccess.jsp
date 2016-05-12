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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/paySuccess.css">
</head>
<body>
   <div class="wrap">
      <header class="xq_header">
            <p>支付成功</p>            
        </header>
            <div class="tai">
                 <img src="<%=path%>/static/images/icon_64.png" alt="">
                  <h1>支付成功</h1>
                  <p>您的订单支付成功，请耐心等待收货</p>
            </div>
            <div class="content">
                <section class="sec1">

                    <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                    <div>
                        <a href="#"><h2>收货人：<b>${orderConsignee.consignee}</b> <span>${orderConsignee.mobile}</span></h2></a>
                        <a href="#"><p>收货地址： <span>
                ${orderConsignee.provinceName}  ${orderConsignee.cityName}  ${orderConsignee.regionName}  ${orderConsignee.address}
            </span></p></a>
                    </div>

                </section>

                <h1>总价：<span>￥${order.orderAmount}</span></h1>
            </div>
            <h1>
                您还可以
            </h1>
       <div class="oPeration">
           <a href="${path}/shop/getPoster?shopId=${order.shopId}" >分享赚佣金</a>
           <a href="${path}/orderPay/getOrderDetail.html?orderId=${order.id}" >查看订单</a>
           <a onclick="returnHomePage()">返回首页</a>
       </div>
    </div>
</body>
<script>
    function returnHomePage(){
        window.location.href="${mallDomainNameAddress}/${order.shopId}/${userPid}/shop.shtml";
    }
</script>
</html>