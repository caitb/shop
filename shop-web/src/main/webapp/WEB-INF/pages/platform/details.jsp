<%-- 详细信息 --%>

<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>麦链商城</title>
        <%@include file="/WEB-INF/pages/common.jsp"%>
        <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
        <link rel="stylesheet" href="<%=path%>/static/css/xiangqing.css">
        <link rel="stylesheet" href="<%=path%>/static/css/header.css">
        <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
        <link rel="stylesheet" href="<%=path%>/static/plugins/font-awesome/font-awesome.min.css">
        <script src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
    </head>
    <body>
    <header class="xq_header" style="background:white url('<%=path%>/static/images/xq_rt.png') no-repeat;
               background-position:10px;background-size:3%">
        <p>我要成为合伙人</p>

    </header>
    <div class="banner">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide"><img src="<%=path%>/static/images/chanpin%20(1).jpg" alt=""></div>
                <div class="swiper-slide"><img src="<%=path%>/static/images/chanpin%20(1).png" alt=""></div>
                <div class="swiper-slide"><img src="<%=path%>/static/images/chanpin%20(2).png" alt=""></div>
                <div class="swiper-slide"><img src="<%=path%>/static/images/chanpin%20(3).png" alt=""></div>
            </div>
            <!-- 如果需要分页器 -->
            <div class="swiper-pagination"></div>
        </div>
    </div>
    <div class="price">
        <p>抗引力—快速瘦脸精华</p>
        <p><b>￥298</b><span>成为合伙人可查看利润</span></p>
    </div>
    <div class="dlpople">
        <p>快递：<span>0</span></p>
        <p>代理人数：<b>超过<span>9999</span>人</b></p>
    </div>
    <nav>
        <ul>
            <li>
                <h1><img src="<%=path%>/static/images/xq_nav.png" alt=""></h1>
                <span>正品保障</span>
            </li>
            <li>
                <h1><img src="<%=path%>/static/images/xq_nav2.png" alt=""></h1>
                <span>合伙人优惠</span>
            </li>
            <li>
                <h1><img src="<%=path%>/static/images/xq_nav3.png" alt=""></h1>
                <span>七天退换</span>
            </li>
            <li>
                <h1><img src="<%=path%>/static/images/xq_nav4.png" alt=""></h1>
                <span>创业补贴</span>
            </li>
        </ul>
    </nav>
    <div class="tuw">
        <h1>图文详情</h1>
    </div>
    <main>
        <section class="sec1">
            <img src="<%=path%>/static/images/chanpin%20(1).jpg" alt="">
            <h2>
                抗引力纤颜奢养光感精华液
            </h2>
            <p>产地：中国 规格：50ml 保质期：见标示</p>
            <p>产品主要成分：虞美人花提取物、透明质酸钠、五肽—3、茉莉提取物、葡萄果提取物等</p>
            <p>产品功效：分解面部脂肪、改善面部松弛、增强肌肤弹性、除皱舒缓</p>
            <p>适合肌肤：各种肤质适用，尤其适合面部脂肪囤积、松弛、衰老的肌肤</p>
            <p>使用方法：使用前将本品上下轻摇几次，均匀喷洒于全脸，约5分钟后喷洒第二次，重复喷洒三次，效果更佳。</p>
        </section>
        <section class="sec1">
            <img src="<%=path%>/static/images/chanpin%20(1).jpg" alt="">
        </section>
        <section class="sec2">
            <p>Copyright2005-2016 iimai.com 版权所有</p>
            <p>京ICP证080047号[京ICP备08010314号-6]</p>
            <p>文网文[2009]024号 新出网证（京）字069号</p>
            <p>京公网安备 11000002000006号</p>
        </section>

    </main>
    <footer>
        <section class="sec3">
            <p><a href="javascript:;">申请试用</a></p>
            <p><a href="<%=path%>/lo/quote">申请合伙人</a></p>
        </section>
    </footer>
    <script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
    <script>
        var mySwiper = new Swiper ('.swiper-container', {
            direction: 'horizontal',
            loop: true,
            autoplay: 3000,
            // 如果需要分页器
            pagination: '.swiper-pagination'
        })
    </script>
    </body>
</html>
