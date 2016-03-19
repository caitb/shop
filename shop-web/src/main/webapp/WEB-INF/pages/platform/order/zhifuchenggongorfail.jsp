<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zhifuchenggong.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
   <div class="wrap">
       <div class="box">
            <header class="xq_header">
                    <p>支付成功</p>            
            </header>
            <div class="tai">
                          <img src="../images/icon_64.png" alt="">
                           <h1>使用支付成功</h1>
                           <p>您的订单支付成功，请耐心等待收获</p>
            </div>
            <section class="sec1">

                   <img src="../images/zhifu_ad.png" alt="">
                   <div>
                        <a href="#"><h2>收货人：<b>王平</b> <span>18611536163</span></h2></a>
                        <a href="#"><p>收货地址： <span>北京市 朝阳区 丰联广场A座809A</span></p></a>
                    </div>

                </section>
            <section class="sec2">
                <p class="photo">
                   <a href="../html/xiangqing.html">
                        <img src="../images/shenqing_1.png" alt="">
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
                <p>商品合计：<span>￥123123</span></p>
                <p>运费：<span>￥123123</span></p>
                <h1>共<b>800</b>件商品　运费：<span>￥300</span>　<b style="color:#333333">合计：</b><span>￥15500.00</span></h1>
            </section>
            <p><a href="">返回首页</a></p>
            <button>继续支付</button>
       </div>
</body>
</html>