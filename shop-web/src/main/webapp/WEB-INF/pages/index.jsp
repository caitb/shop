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
    <link rel="stylesheet" href="<%=path%>/static/css/index.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header" style="margin-bottom:0;">
            <a href="<%=path%>/index"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>进货市场</p>
        </header>
        <%--<div class="admin">--%>
            <%--<img src="${user.wxHeadImg}" alt="">--%>
            <%--<h3>${user.wxNkName}—欢迎您登入</h3>--%>
            <%--&lt;%&ndash;<span><a href="<%=path%>/binding/bindingList" style="color:white;">绑定账号</a></span>&ndash;%&gt;--%>
        <%--</div>--%>
        <div class="banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach items="${urls}" var="url">
                        <div class="swiper-slide"><img src="${url}" alt=""></div>
                    </c:forEach>
                    <!--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner.png" alt=""></div>-->
                    <!--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner2.png" alt=""></div>-->
                    <!--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner3.png" alt=""></div>-->
                </div>
                <!-- 如果需要分页器 -->
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <%--<div class="sing">--%>
            <%--<img src="<%=path%>/static/images/shouye_sing.png" alt="">--%>
            <%--<h2>麦链推出全新扶持计划！ ！ ！</h2>--%>
        <%--</div>--%>
        <div class="sing all" style="margin-top:5px;">
            <a href="<%=path%>/productList/showProduct">
                <img src="<%=path%>/static/images/all.png" alt="">
                <h3>所有商品</h3>
                <img src="<%=path%>/static/images/next.png" alt="" class="next">
            </a>
        </div>
        <h1 class="tuij">推荐产品</h1>
        <main>
                <c:forEach begin="0" end="${ComSize}" step="2" var="i">
            <section class="sec1">
                <c:forEach items="${indexComS}"  begin="${i}" end="${i+1}" var="Sku">
                    <div>
                        <a href="/product/${Sku.id}"><img src="${Sku.imgUrl}" alt=""></a>
                        <h2>${Sku.comSku.name}</h2>
                        <h1>￥${Sku.comSku.priceRetail} <span>${Sku.discountLevel}</span></h1>
                        <h3>保证金：${Sku.bail}元</h3>
                        <h4>试用费用：${Sku.shipAmount}元</h4>
                        <h5>
                            <p>超过<span>${Sku.agentNum}</span>人合伙</p>
                            <%--<c:choose><c:when--%>
                                <%--test="${Sku.isPartner==1 && Sku.isPay==1}">--%>
                            <%--<a href="javascript:;">您已合伙</a></c:when>--%>
                            <%--<c:otherwise><a--%>
                                    <%--href="<%=basePath%>userApply/apply.shtml?skuId=${Sku.id}">我要合伙</a></c:otherwise></c:choose>--%>
                        </h5>
                    </div>
                </c:forEach>
            </section>
                </c:forEach>
        </main>
    </div>
</div>
<%--<div class="bottom">--%>
    <%--<footer>--%>
        <%--<div class="btm">--%>
            <%--<span><img src="<%=path%>/static/images/shouye_footer.png" alt=""></span>--%>
            <%--<span>首页</span>--%>
        <%--</div>--%>
        <%--<div class="btm">--%>
            <%--<span><img src="<%=path%>/static/images/shouye_footer2.png" alt=""></span>--%>
            <%--<span>我的店铺</span>--%>
        <%--</div>--%>
        <%--<div class="btm" onclick="javascript:window.location.replace('<%=basePath%>profile/profile');">--%>
            <%--<span><img src="<%=path%>/static/images/shouye_footer3.png" alt=""></span>--%>
            <%--<span>个人中心</span>--%>
        <%--</div>--%>
    <%--</footer>--%>
<%--</div>--%>
</body>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script src="<%=path%>/static/js/iscroll.js"></script>
<script>
    var mySwiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay: 3000,
        autoplayDisableOnInteraction: false,
        // 如果需要分页器
        pagination: '.swiper-pagination'
    })
    var myScroll = new IScroll(".wrap", {
        preventDefault: false
    })

</script>
</html>