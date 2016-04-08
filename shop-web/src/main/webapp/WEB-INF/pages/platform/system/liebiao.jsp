<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/haohuo.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
</head>
<body>
    <div class="wrap">
        <div class="box">
                <header class="xq_header" style="margin-bottom:0;">
                        <a href="<%=path%>/marketGood/market"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>所有商品</p>
                    </header>
                <main><div class="main"><c:forEach items="${indexComSkus}"  var="Sku">
                    <section class="sec1">
                        <p class="photo"><a href="/product/${Sku.id}">
                            <img src="${Sku.imgUrl}" alt=""></a>
                        </p>
                        <div>
                            <h2>${Sku.comSku.name}</h2>
                            <h3>试用费用：<span>${Sku.shipAmount}</span>元</h3>
                            <h3><c:if test="${user.isAgent==1}">保 证 金：<span>${Sku.bail}</span>元</c:if><b style="float:right; margin-right:10px;font-size:14px;color:red">￥${Sku.comSku.priceRetail}</b></h3>
                            <h2>超过<span>${Sku.agentNum}</span>人合伙<b>${Sku.discountLevel}</b></h2>
                        </div>
                    </section>
                </c:forEach></div>
                       <!--<section class="sec1">-->
                        <!--<div>-->
                            <!--<a href="xiangqing.html"><img src="<%=path%>/static/images/cp_1.png" alt=""></a>-->
                            <!--<h2>抗引力—快速瘦脸精华</h2>-->
                            <!--<h1>￥328 <span>成为合伙人可查看</span></h1>-->
                            <!--<h3>-->
                                <!--<p>超过<span>9999</span>人代理</p>-->
                                <!--<a href="shenqing.html" class="he">我要合伙</a>-->
                                <!--<a href="javascript:;" class="ok">您已合伙</a>-->
                            <!--</h3>-->
                        <!--</div>-->
                        <!--<div>-->
                            <!--<a href="xiangqing.html"><img src="<%=path%>/static/images/cp_1.png" alt=""></a>-->
                            <!--<h2>抗引力—快速瘦脸精华</h2>-->
                            <!--<h1>￥328 <span>成为合伙人可查看</span></h1>-->
                            <!--<h3>-->
                                <!--<p>超过<span>9999</span>人代理</p>-->
                                <!--<a href="shenqing.html" class="he">我要合伙</a>-->
                                <!--<a href="javascript:;" class="ok">您已合伙</a>-->
                            <!--</h3>-->
                        <!--</div>-->
                    <!--</section>-->
                 </main>
        </div>
    </div>
</body>
</html>