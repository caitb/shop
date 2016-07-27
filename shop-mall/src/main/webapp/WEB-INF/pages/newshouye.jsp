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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/newshouye.css">
</head>
<body>
<div class="wrap">
    <c:if test="${userPid!=user.id && userPid != sfShop.userId && userPid !=null && userPid !=0}">
        <div class="na">
            <p style="background: url('${pUser.wxHeadImg}') no-repeat;background-size: 100% 100%"></p>
            <h1>
                <span>我是${pUser.wxNkName}</span><br>我为好友代言，跟我一起分享赚佣金！
            </h1>
        </div>
    </c:if>
    <div class="banner">
        <div class="swiper-container">
            <div class="swiper-wrapper">
            </div>
            <div class="swiper-pagination"></div>
            <div class="banner_b"></div>
        </div>
        <div class="banner_1">
            <div class="br_1">
                <h1>${sfShop.name}</h1>
                <p>${sfShop.explanation}</p>
                <p id="icon">
                </p>
            </div>
            <div class="br_2">
                <h1><b>已有</b><img src="<%=path%>/static/images/zuo.png" alt=""><span>${allSfSpokesManNum}</span><img src="<%=path%>/static/images/you.png" alt=""><b>人</b></h1>
                <p>成为TA店铺粉丝</p>
            </div>
            <div class="br_3">
                <div onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${sfShop.id}');"><img src="<%=path%>/static/images/woyao.png" alt=""></div>
                <p>&nbsp;&nbsp;</p>
                <div onclick="javascript:window.location.replace('http://mp.weixin.qq.com/s?__biz=MzI1OTIxNzgwNA==&mid=2247483656&idx=1&sn=555876e87000a8b289d535fb12ce4333&scene=0#wechat_redirect');"><img src="<%=path%>/static/images/daiyan.png" alt=""></div>
                    <div class="tallme" onclick="showNowxcode(${isUpload})">联系我</div>
            </div>
        </div>

    </div>
    <main id="main">
    </main>
    <c:if test="${not empty userPromotions}">
        <img src="${path}/static/images/activity.png" onclick="javascript:window.location.replace('<%=path%>/showPromotion/getAllPromoDetail.html');" alt="">
    </c:if>
</div>
<footer>
    <div>
        <p onclick="javascript:window.location.replace('<%=basePath%>${shopId}/${userPid}/shop.shtml');">
            <span><img src="<%=path%>/static/images/dibu11.png" alt=""></span>
            <span class="active">首页</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${sfShop.id}');">
            <span><img src="<%=path%>/static/images/dibu2.png" alt=""></span>
            <span>二维码</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/toBorderManagement?fm=1');">
            <span><img src="<%=path%>/static/images/dibu3.png" alt=""></span>
            <span>个人中心</span>
        </p>
    </div>
</footer>
<div class="black">
    <div class="back_b"></div>
    <div class="b_t">
        <img src="${sfShop.wxQrCode}" alt="">
        <p>
            ${sfShop.wxQrCodeDescription}
        </p>
        <b class="off" onclick="clickHide()"><i>×</i></b>
    </div>
</div>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script src="<%=path%>/static/js/plugins/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
<script>
    $(function () {
        $.ajax({
            type:"POST",
            url : "<%=path%>/findSfSkuLevelImage.do",
            dataType:"Json",
            success:function(data){
                var trHtml1 = "";
                trHtml1+="<p id=\"icon\">";
                trHtml1+="<img src=\"<%=path%>/static/images/1.png\" alt=\"\">";
                trHtml1+="<img src=\"<%=path%>/static/images/3.png\" alt=\"\">";
                $.each(data, function(i, String) {
                    trHtml1+="<img src=\""+String+"\" alt=\"\">";
                })
                trHtml1+="</p>";
                $("#icon").html(trHtml1);
            }
        })

        $.ajax({
            type:"POST",
            url : "<%=path%>/product.do",
            dataType:"Json",
            success:function(data){
                var trHtml = "";
                $.each(data.skuUrlList, function(i, skuimg) {
                    if(i<5){
                        trHtml+="<div class=\"swiper-slide\"><img src=\""+skuimg+"\" alt=\"\"></div>";
                    }
                })
                $(".swiper-wrapper").html(trHtml);
                var trHtml2 = "";
                var shipName="";
                var shopId= ${sfShop.id};
                var tubiaoHtml ="";
                $.each(data.sfShopDetailList, function(i, SfShopDetails) {
                    trHtml2+="<div class=\"sec1\" onclick=\"javascript:window.location.replace('<%=basePath%>shop/detail.shtml/?skuId="+SfShopDetails.skuId+"&shopId="+shopId+"&isOwnShip="+SfShopDetails.isWunShip+"');\">";
                    trHtml2+="<div><img src=\""+SfShopDetails.skuImageUrl+"\" alt=\"\"></div>";
                    if(SfShopDetails.isWunShip==1){
                        shipName="店主发货";
                        tubiaoHtml ="<div><h2><img src='${path}/static/images/dianzhu.png' alt=''>";
                    }else if(SfShopDetails.isWunShip==0){
                        shipName="平台发货";
                        tubiaoHtml ="<div><h2><img src='${path}/static/images/ping.png' alt=''>";
                    }
                    trHtml2+=tubiaoHtml+shipName+"</h2><h1>"+SfShopDetails.skuAssia+"</h1> <p>-"+SfShopDetails.slogan+"-</p> <h3><b>￥</b>"+SfShopDetails.priceRetail+"<span>"+SfShopDetails.priceMarket+"<img src='${path}/static/images/xie.png'/></span></h3>";
                    trHtml2+="</div> </div>";
                })
                $("#main").html(trHtml2);
                var bWidth=$(".swiper-slide").width(),
                        bHeight=$(".swiper-slide").height();
                $(".banner_b").width(bWidth).height(bHeight);
                var mySwiper = new Swiper ('.swiper-container', {
                    direction: 'horizontal',
                    loop: true,
                    autoplay: 3000,
                    // 如果需要分页器
                    pagination: '.swiper-pagination'
                })
            }
        })
    })

    function clickHide(){
        $(".black").hide();
        $(".b_t").hide();
        $(".back_b").hide();
    }
    function showNowxcode(value){
       if(value){
           alert("店主还没有上传二维码");
       }else{
           $(".black").show();
           $(".b_t").show();
           $(".back_b").show();
       }
    }
</script>
</body>
</html>