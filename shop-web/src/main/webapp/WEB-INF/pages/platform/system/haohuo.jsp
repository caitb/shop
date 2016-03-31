<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
               <header class="xq_header">
              <a href="<%=path%>/index"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                <p>我要成为合伙人</p>            
        </header>
                <div class="banner">
                   <div class="swiper-container">
                            <div class="swiper-wrapper">
                                <c:forEach items="${urls}" var="url">
                                    <div class="swiper-slide"><img src="${url}" alt=""></div>
                                </c:forEach>
                                <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner.png" alt=""></div>--%>
                                <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner2.png" alt=""></div>--%>
                                <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner3.png" alt=""></div>--%>
                            </div>
                                <!-- 如果需要分页器 -->
                            <div class="swiper-pagination"></div>
    </div>
                </div><a href="<%=path%>/productList/showProduct">
                <div class="all" style="margin-top:5px;">
                        <img src="<%=path%>/static/images/all.png" alt="">
                        <p>查看所有商品<img src="<%=path%>/static/images/next.png" alt=""></p>
                </div></a>
                <div class="main">
                    <div class="title"><img src="<%=path%>/static/images/haohuo_1.png" alt="">好货推荐</div>
                    <c:forEach items="${indexComS}"  var="Sku">
                    <section class="sec1">
                        <p class="photo"><a href="/product/${Sku.id}">
                                <img src="${Sku.imgUrl}" alt=""></a>
                        </p>
                        <div>
                            <h2>${Sku.comSku.name}</h2>
                            <h3>试用费用：<span>${Sku.shipAmount}</span>元</h3>
                            <h3>保 证 金：<span>${Sku.bail}</span>元<b style="float:right; margin-right:10px;font-size:14px;color:red">￥${Sku.comSku.priceRetail}</b></h3>
                            <h2>超过<span>${Sku.agentNum}</span>人合伙<b>${Sku.discountLevel}</b></h2>
                        </div>
                </section></c:forEach>
                    <%--<section class="sec1">--%>
                        <%--<p class="photo">--%>
                                <%--<img src="<%=path%>/static/images/haohuo.png" alt="">--%>
                        <%--</p>--%>
                        <%--<div>--%>
                            <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                            <%--<h3>试用费用：<span>默认</span>元</h3>--%>
                            <%--<h3>保 证 金：<span>默认</span>元<b style="float:right; margin-right:10px;font-size:14px;color:red">￥298.00</b></h3>--%>
                            <%--<h2>超过<span>9999</span>人合伙<b>最好利润50%</b></h2>--%>
                        <%--</div>--%>
                <%--</section>--%>
                </section>
                </section>
                </section>
                </section>
                <%--<section class="sec1">--%>
                        <%--<p class="photo">--%>
                                <%--<img src="<%=path%>/static/images/haohuo.png" alt="">--%>
                        <%--</p>--%>
                        <%--<div>--%>
                            <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                            <%--<h3>试用费用：<span>默认</span>元</h3>--%>
                            <%--<h3>保 证 金：<span>默认</span>元<b style="float:right; margin-right:10px;font-size:14px;color:red">￥298.00</b></h3>--%>
                            <%--<h2>超过<span>9999</span>人合伙<b>最好利润50%</b></h2>--%>
                        <%--</div>--%>
                <%--</section>--%>
                <%--<section class="sec1">--%>
                        <%--<p class="photo">--%>
                                <%--<img src="<%=path%>/static/images/haohuo.png" alt="">--%>
                        <%--</p>--%>
                        <%--<div>--%>
                            <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                            <%--<h3>试用费用：<span>默认</span>元</h3>--%>
                            <%--<h3>保 证 金：<span>默认</span>元<b style="float:right; margin-right:10px;font-size:14px;color:red">￥298.00</b></h3>--%>
                            <%--<h2>超过<span>9999</span>人合伙<b>最好利润50%</b></h2>--%>
                        <%--</div>--%>
                <%--</section>--%>
                </div>
            </div>
        </div>
    <script>
        var mySwiper = new Swiper ('.swiper-container', {
                direction: 'horizontal',
                loop: true,
                autoplay: 3000,
                 autoplayDisableOnInteraction : false,
                // 如果需要分页器
                pagination: '.swiper-pagination' 
              })
    </script>
</body>
</html>