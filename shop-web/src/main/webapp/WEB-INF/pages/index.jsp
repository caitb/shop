<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/index.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <div class="box">
        <div class="admin">
            <img src="<%=path%>/static/images/admin.png" alt="">
            <h3>王平—欢迎您登入</h3>
            <span><a href="<%=path%>/binding/bindingList" style="color:white;">绑定账号</a></span>
        </div>
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
        </div>
        <div class="sing">
            <img src="<%=path%>/static/images/shouye_sing.png" alt="">
            <h2>麦链推出全新扶持计划！ ！ ！</h2>
        </div>
        <div class="sing all" style="margin-top:5px;">
            <a href="<%=path%>/productList/showProduct">
                <img src="<%=path%>/static/images/all.png" alt="">
                <h3>查看所有商品</h3>
                <img src="<%=path%>/static/images/next.png" alt="" class="next">
            </a>
        </div>
        <!--<nav>
            <ul>
                <li>
                    <a href="javascript:;">
                        <h1><img src="../images/shouye_nav.png" alt=""></h1>
                        <span>男士</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <h1><img src="../images/shouye_nav2.png" alt=""></h1>
                        <span>女士</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <h1><img src="../images/shouye_nav3.png" alt=""></h1>
                        <span>美食</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <h1><img src="../images/shouye_nav4.png" alt=""></h1>
                        <span>美妆</span>
                    </a>
                </li>
            </ul>
        </nav>-->
        <h1 class="tuij">推荐产品</h1>
        <main>
            <section class="sec1">
                    <c:forEach items="${indexComSkus}" var="Sku">
                        <div>
                            <a href="/product/${Sku.id}"><img src="${Sku.imgUrl}" alt="">
                            <h2>${Sku.comSku.name}</h2>
                            <h1>￥${Sku.comSku.priceRetail} <span>${Sku.discountLevel} </span></h1>
                            <p>超过<span>${Sku.agentNum}</span>人代理</p>
                            <a href="<%=path%>/lo/quote">我要合伙</a>
                        </div>
                    </c:forEach>

                <%--<div>--%>
                    <%--<a href="xiangqing.html"><img src="../images/cp_1.png" alt=""></a>--%>
                    <%--<h2>抗引力—快速瘦脸精华</h2>--%>
                    <%--<h1>￥328 <span>成为合伙人可查看</span></h1>--%>
                    <%--<h3>--%>
                        <%--<p>超过<span>9999</span>人代理</p>--%>
                        <%--<a href="shenqing.html">我要合伙</a>--%>
                    <%--</h3>--%>
                <%--</div>--%>
            </section>
            <%--<section class="sec1">--%>
                <%--<div>--%>
                    <%--<a href="xiangqing.html"><img src="../images/cp_1.png" alt=""></a>--%>
                    <%--<h2>抗引力—快速瘦脸精华</h2>--%>
                    <%--<h1>￥328 <span>成为合伙人可查看</span></h1>--%>
                    <%--<h3>--%>
                        <%--<p>超过<span>9999</span>人代理</p>--%>
                        <%--<a href="shenqing.html">我要合伙</a>--%>
                    <%--</h3>--%>
                <%--</div>--%>
                <%--<div>--%>
                    <%--<a href="xiangqing.html"><img src="../images/cp_1.png" alt=""></a>--%>
                    <%--<h2>抗引力—快速瘦脸精华</h2>--%>
                    <%--<h1>￥328 <span>成为合伙人可查看</span></h1>--%>
                    <%--<h3>--%>
                        <%--<p>超过<span>9999</span>人代理</p>--%>
                        <%--<a href="shenqing.html">我要合伙</a>--%>
                    <%--</h3>--%>
                <%--</div>--%>
            <%--</section>--%>
        </main>
        <section class="sec2">
            <h2 style="color:#F74A11;">麦链合伙人提供技术支持</h2>
            <p>Copyright2005-2016 iimai.com 版权所有</p>
            <p>京ICP证080047号[京ICP备08010314号-6]</p>
            <p>文网文[2009]024号 新出网证（京）字069号</p>
            <p>京公网安备 11000002000006号</p>
        </section>
    </div>
</div>
<div class="bottom">
    <footer>
        <div class="btm">
            <span><img src="<%=path%>/static/images/shouye_footer.png" alt=""></span>
            <span>首页</span>
        </div>
        <div class="btm">
            <span><img src="<%=path%>/static/images/shouye_footer2.png" alt=""></span>
            <span>我的店铺</span>
        </div>
        <div class="btm">
            <span><img src="<%=path%>/static/images/shouye_footer3.png" alt=""></span>
            <span>个人中心</span>
        </div>
    </footer>
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
    var myScroll = new IScroll(".wrap",{
        preventDefault: false
    })

</script>
</body>
</html>