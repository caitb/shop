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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/xiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
</head>
<body>
<header>
    <a href="javascript:;"onClick="javascript:history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>商品详情</p>
</header>
<div class="wrap">
    <c:if test=" ${not empty fromUser} ">
        <div class="na">
            <p><img src="${fromUser.wxHeadImg}" alt=""></p>
            <h1>
                <span>我是${fromUser.wxNkName},我为麦链商城呐喊!</span>
                <span>跟我一起：呐喊得红包，分享赚佣金</span>
            </h1>
        </div>
    </c:if>
    <div class="banner">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <c:forEach items="${SkuImageList}" var="img">
                    <div class="swiper-slide"><img src="${img.imgUrl}" alt=""></div>
                </c:forEach>
            </div>
            <!-- 如果需要分页器 -->
            <div class="swiper-pagination"></div>
        </div>
    </div>
    <div class="price">
        <p>${skuInfo.comSku.name}</p>
        <p><span style="padding:0;">${skuInfo.slogan}</span></p>
        <p><b>${skuInfo.comSku.priceRetail}</b></p>
    </div>
    <div class="dlpople">
        <p>快递：<span>${skuInfo.shipAmount}</span></p>
        <p>总销量：<b>${skuInfo.saleNum}</b></p>
        <p>分销量：<b>${skuInfo.shareNum}</b></p>
    </div>
    <div class="dlpople" onclick="validateCodeJS.applyTrial('buy')">
        <p>选择： 数量</p>
        <p><img src="<%=path%>/static/images/next.png" alt=""></p>
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
            ${skuInfo.content}
        </section>
        <section class="sec1">
            <img src="<%=path%>/static/images/chanpin%20(1).jpg" alt="">
        </section>
        <section class="sec2">
            <h2 style="color:#F74A11;">麦链合伙人提供技术支持</h2>
            <p>Copyright2005-2016 iimai.com 版权所有</p>
            <p>京ICP证080047号[京ICP备08010314号-6]</p>
            <p>文网文[2009]024号 新出网证（京）字069号</p>
            <p>京公网安备 11000002000006号</p>
        </section>

    </main>

</div>
<div class="back_j" style="display: none">
    <p class="biao">绑定账号</p>
    <div>
        <p>手机号：<input type="tel" class="phone" id="phoneId"></p>
    </div>
    <div class="d">
        <p>验证码：<input type="tel" id="validateNumberDataId">
            <button id="validateNumberId">获取验证码</button>
        </p>
    </div>
    <p class="tishi" id="errorMessageId"></p>
    <h1 class="j_qu" id="nextPageId">下一步</h1>
</div>
<div class="back_f">
    <p>保存图片到手机，复制文案，发送图文到朋友圈，产生购买后可获得佣金</p>
    <img src="<%=path%>/static/images/asd.JPG" alt="">
    <b>下载图片</b>
    <span class="close">×</span>
</div>
<div class="back">

</div>
<div class="shoping">
    <img src="${defaultSkuImage.fullImgUrl}" alt="">
    <p>
        <span id="skuName">${skuInfo.comSku.name}</span>
        <span id="price">${skuInfo.comSku.priceRetail}</span>
    </p>
    <h1>
        <span>库存：</span>
        <span id="stock">${skuInfo.stock}</span>
    </h1>
    <h1>
        <span style="float:left">数量：</span>
        <p>
            <label class="jian">-</label>
            <input type="text" value="1" class="number">
            <label class="jia">+</label>
        </p>
    </h1>
    <button onclick="buy()">下一步</button>

</div>
<footer>
    <section class="sec3">
        <p class="shi" onclick="validateCodeJS.applyTrial('share')"><a>分享</a></p>
        <p style="background: #DA3600;"onclick="validateCodeJS.applyTrial('buy')">立即购买</p>
    </section>
</footer>
<script src="<%=path%>/static/js/plugins/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script src="<%=path%>/static/js/plugins/validateCode.js"></script>
<script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
<script>
    $(document).ready(function () {
        validateCodeJS.initPage();
    });
    var mySwiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay: 3000,
        // 如果需要分页器
        pagination: '.swiper-pagination'
    })
    var i=1;
    $(".number").on("change",function(){
        i=$(this).val();
    })
    $(".jian").on("click",function(){
        if(i<=1){
            return false;
        }
        i--;
        $(".number").val(i)
    })
    $(".jia").on("click",function(){
        i++;
        $(".number").val(i)
    })
    $(".close").on("click",function(){
        $(this).parent().hide();
        $(".back").hide();
    })
    function buy(){
        var cartData = {};
        cartData.shopId = "${shopId}";
        cartData.skuId = "${skuInfo.comSku.id}";
        cartData.quantity = i;
        $.ajax({
            url: "<%=basePath%>shop/addCart.do",
            type: "post",
            data: cartData,
            dataType: "json",
            success: function (data) {
                if (data.isError == false) {
                    window.location.href = "<%=basePath%>orderPurchase/getShopCartInfo.html?shopId="+cartData.shopId;
                }
                else {
                    alert(data.message);
                }
            }
        });
    }
</script>
</body>
</html>