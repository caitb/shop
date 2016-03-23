<%--
  Created by IntelliJ IDEA.
  User: ZhaoLiang
  Date: 2016/3/23
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" href="<%=path%>/static/css/dingdanxiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
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
        <p>您的订单支付成功，请耐心等待收货</p>
    </div>
    <section class="sec1">

        <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
        <div>
            <a href="#"><h2>收货人：<b>王平</b> <span>18611536163</span></h2></a>
            <a href="#"><p>收货地址： <span>北京市 朝阳区 丰联广场A座809A</span><img src="<%=path%>/static/images/next.png" alt=""></p></a>
        </div>

    </section>
    <section class="sec2">
        <p class="photo">
            <a href="<%=path%>/static/html/xiangqing.html">
                <img src="<%=path%>/static/images/shenqing_1.png" alt="">
            </a>
        </p>
        <div>
            <h2>抗引力——快速瘦脸精华</h2>
            <h3>规格：<span>默认</span><b>x1000</b></h3>
            <p>零售价： 非卖品</p>
            <h1><b style="color:#333333">合计：</b><span>￥15500.00</span></h1>
        </div>
    </section>
    <section class="sec3">
        <p>留言： <input type="text"></p>
    </section>
    <section class="sec4">
        <p>商品合计：<span>￥123123元</span></p>
        <p>运费：<span>￥123123元</span></p>
        <h1>共<b>800</b>件商品　运费：<span>￥300</span>　<b style="color:#333333">合计：</b><span>￥15500.00</span></h1>
    </section>
    <div class="sec5">
        <p>订单编号：<span>1290301293890128931290</span></p>
        <p>订单编号：<span>1290301293</span><span>1290301293</span></p>
        <p>订单编号：<span>1290301293</span><span>1290301293</span></p>
        <p>订单编号：<span>1290301293</span><span>1290301293</span></p>
    </div>
    <div class="sec6">
        <p><a href="index.html">返回首页</a></p>
        <p><a href="gerenzhongxin.html">返回个人中心</a></p>
    </div>
</div>
</body>
</html>
