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
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wodedingdan.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
       <div class="wrap">
           <div class="box">
                <header class="xq_header">
                    <a href="javascript:;" onClick="javascript:history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>我的订单</p>  
                </header>
                <nav>
                    <ul>
                        <li><a href="javascript:;" class="on">全部</a></li>
                        <li><a href="javascript:;">待付款</a></li>
                        <li><a href="javascript:;">待发货</a></li>
                        <li><a href="javascript:;">待收货</a></li>
                        <li><a href="javascript:;">已完成</a></li>
                    </ul>
                </nav>
                <main><c:forEach items="${pfBorders}" begin="0" end="${pfBorders.size()}" var="pbs">
                    <div class="all">
                        <c:forEach items="${pbs}" var="pb">
                        <section class="sec1">
                           <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if><c:if test="${pb.orderStatus ==1&& pb.shipStatus==0}"> <b class="fahuo_${pb.id}">等待发货</b></c:if><c:if test="${pb.orderStatus ==1&& pb.shipStatus==5}"><b class="fahuo_${pb.id}">等待收货</b></c:if><c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                            </h2>
                            <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                   <a href="<%=path%>/static/html/xiangqing.html">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}</h2>
                                    <h3>规格：<span>默认</span><b>x${pbi.quantity}</b></h3>
                                    <p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                </div>
                            </div>
                            </c:forEach>
                            <h1><b>合计：￥${pb.orderAmount}</b>(共<span>${pb.totalQuantity}</span>件商品 运费<span>￥${pb.shipAmount}</span>)</h1>
                            <div class="ding">
                                <p><a href="<%=path%>/border/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                <p class="sh" onclick="shouhuorenxinxi('${pb.pfBorderConsignee.consignee}','${pb.pfBorderConsignee.provinceName} ${pb.pfBorderConsignee.cityName} ${pb.pfBorderConsignee.regionName} ${pb.pfBorderConsignee.address}','${pb.pfBorderConsignee.mobile}','${pb.pfBorderConsignee.zip}')">收货人信息</p></c:if><c:if test="${pb.payStatus ==1 && pb.shipStatus==0}">
                                <span class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">
                                    发货
                                </span></c:if>
                            </div>
                        </section></c:forEach>
                    </div></c:forEach>
                    <%--<div class="all">           --%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                    <%--</div>--%>
                    <%--<div class="all">           --%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                    <%--</div>--%>
                    <%--<div class="all">           --%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                    <%--</div>--%>
                    <%--<div class="all">           --%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p>查看订单详情</p>--%>
                                <%--<p>收货人信息</p>--%>
                                <%--<span>--%>
                                    <%--发货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                    <%--</div>--%>
                </main>
           </div>
       </div>
       <div class="back_que">
           <p>确认发货?</p>
           <h4>快递公司:<select id="select"><option selected="selected">顺风</option><option >EMS</option></select></h4>
           <h4>快递单号:<input type="text" id="input"/></h4>
           <h3 id="fahuo">发货</h3>
       </div>
       <div class="shouhuo">
           <p>收货人信息</p>
           <h4><span>姓　名:</span><span id="1"></span></h4>
           <h4><span>地　址:</span><span id="2">阿斯科利的asdasdasdasdas将阿</span></h4>
           <h4><span>手机号:</span><span id="3"></span></h4>
           <h4><span>邮　编:</span><span id="4"></span></h4>
           <h3 class="close">关闭</h3>
       </div>
           <div class="back">

           </div>
       <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
       <script>
               $('body').on('touchmove', function (event) {
                   var event=event||event.window;
                   event.prevtDefault();
               });
               function shouhuorenxinxi(a,b,c,d){
                   $(".back").css("display","-webkit-box");
                   $(".shouhuo").css("display","-webkit-box");
                   $("#1").html(a);
                   $("#2").html(b);
                   $("#3").html(c);
                   $("#4").html(d);
               }
               $("li").on("click",function(){
                   var index=$(this).index();
                   $("li").children("a").removeClass("on")
                   $(this).children("a").addClass("on");
                   $(".all").eq(index).show().siblings().hide();
               })

               function fahuo(id){
                   $(".back").css("display","-webkit-box");
                   $(".back_que").css("display","-webkit-box");
                   $("#fahuo").on("click",function(){
                       $(".back_que").hide();
                       $(".back").hide();
                       var shipManName = $("#select").val();
                       var freight = $("#input").val();
                       var aa="fahuo_"+id;

                       $.ajax({
                           type:"POST",
                           url : "<%=path%>/border/deliver.do",
                           data:{shipManName:shipManName,freight:freight,orderId:id},
                           dataType:"Json",
                           success:function(date){
                               if(!date.msgs){
                                   alert(date.msg);
                               }else {
                                   $("span[name=" + aa + "]").attr("style", "display:none");
                                   $("b." + aa + "").html("待收货");
                                   location.reload(true);
                               }
                           }
                       })
                   })
               }

               $(".close").on("click",function(){
                   $(".shouhuo").hide();
                   $(".back").hide();
               })
               $(".back").on("click",function(){
                   $(".back_que").hide();
                   $(".back").hide();
                   $(".shouhuo").hide();
               })
       </script>
</body>
</html>