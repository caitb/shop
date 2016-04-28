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
<c:if test="${forcusSF !=true}">
    <div class="addb">
        <p>关注麦链公众微信号“<span class="add">麦链商城</span>”，查佣金，查订单。</p>
        <label class="close">×</label>
    </div>
</c:if>
<div class="wrap">
    <header>
        <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>商品详情</p>
    </header>
    <c:if test=" ${fromUserId !=null && fromUserId != loginUser.id && fromUser != null} ">
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

    <p>快递：
            <c:if test="${empty skuInfo.shipAmount}">
            <span>包邮</span></p>
        </c:if>
        <c:if test="${ not empty skuInfo.shipAmount}">
            <span>${skuInfo.shipAmount}</span></p>
        </c:if>
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
    <span class="close">×</span>
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
    <img id="skuPoster" src="<%=path%>/static/images/asd.JPG" alt="">
    <canvas id="canvasOne" style="display: none;">
        Your browser does not support HTML5 Canvas.
    </canvas>
    <b id="downloadPoster">长按图片保存海报</b>
    <span class="close">×</span>
</div>
<div class="back">
</div>
<div class="back_g">
    <span class="close">×</span>
    <img src="${path}/static/images/duan.png" alt="">
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
            <input type="tel" value="1" class="number">
            <label class="jia">+</label>
        </p>
    </h1>
    <c:if test="${skuInfo.isSale==0}">
        <button>该商品已下架</button>
    </c:if>
    <c:if test="${skuInfo.isSale==1}">
        <button onclick="buy()">下一步</button>
    </c:if>
    <span class="close">×</span>
</div>
<footer>
    <section class="sec3">
        <p class="shi" id="share"><a>分享</a></p>
        <p style="background: #fff;color:#333;"onclick="validateCodeJS.applyTrial('buy')">立即购买</p>
    </section>
</footer>
<script src="<%=path%>/static/js/plugins/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script src="<%=path%>/static/js/plugins/validateCode.js"></script>
<%--<script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>--%>
<script src="<%=path%>/static/js/plugins/zepto.min.js"></script>
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
    $(".jian").on("touchend",function(){
        if(i<=1){
            return false;
        }
        i--;
        $(".number").val(i);
        event.preventDefault();
    })
    $(".jia").on("touchend",function(){
        i++;
        $(".number").val(i);
        event.preventDefault();
    })
    $(".add").on("tap",function () {
        $(".back_g").show()
        $(".back").show()
    })
    $(".close").on("tap",function(){
        $(this).parent().hide();
        $(".back").hide();
        $(".back_g").hide()
    })
    function buy(){
        var cartData = {};
        cartData.shopId = "${shopId}";
        cartData.skuId = "${skuInfo.comSku.id}";
        cartData.quantity = i;
        $.ajax({
            url: "<%=basePath%>cart/addCart.do",
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
<script src="//cdn.bootcss.com/modernizr/2010.07.06dev/modernizr.min.js"></script>
<script src="<%=basePath%>static/js/plugins/canvas2image.js"></script>
<script src="<%=basePath%>static/js/plugins/base64.js"></script>
<script type="text/javascript">

    function canvasSupport() {
        return Modernizr.canvas;
    }

    function canvasApp(userName,skuName,imgSrcs) {

        if(!canvasSupport()) {
            return;
        }
        var theCanvas = document.getElementById("canvasOne");
        theCanvas.width  = 904;
        theCanvas.height = 1200;
        var context = theCanvas.getContext("2d");
        context.fillStyle = "#EEEEEE";
        context.fillRect(0, 0, theCanvas.width, theCanvas.height);


        var oImgs = [];
        for(var i in imgSrcs){
            oImgs[i] = new Image();
            oImgs[i].src = imgSrcs[i];
            oImgs[i].isLoaded = false;

            oImgs[i].addEventListener('load', function(){
                this.isLoaded = true;
            }, false);

        }

        var drawTimer = setInterval(function(){
            var isAllLoaded = true;
            for(var i in oImgs){
                if(!oImgs[i].isLoaded) isAllLoaded = false;
            }

            if(isAllLoaded){
                context.drawImage(oImgs[0], 46, 44, 90, 90);
                context.drawImage(oImgs[1], 0, 0);
                context.drawImage(oImgs[2], 304, 314);

                context.font = 'normal 28px Microsoft YaHei';
                context.textBaseline = 'top';
                context.strokeStyle = '#333333';
                context.strokeText('我是'+userName,170, 56);
                context.strokeText('我为'+skuName+'代言!',170, 90);

                clearInterval(drawTimer);
            }
        },100);

    }

    $('#share').on('tap', function(){
        $.ajax({
            url: '<%=basePath%>shop/getSkuPoster',
            data: {shopId: ${shopId}, skuId: ${skuInfo.comSku.id}},
            success: function(data){
                data = window.eval('(' + data + ')');
                <%--var baseUrl = '<%=basePath%>';--%>
                <%--var imgSrcs = [baseUrl+data['userImg'], baseUrl+data['skuImg'], baseUrl+data['shopQRCode']];--%>
                <%--var userName = data['userName'];--%>
                <%--var skuName = data['skuName'];--%>
                <%--canvasApp(userName,skuName,imgSrcs);--%>

                $('#skuPoster').attr('src', data['skuPoster']);
                $('.back_f').show();
                $('.back').show();
            }
        });
    });

    document.getElementById('downloadPoster').onclick = function(){
        Canvas2Image.saveAsPNG(document.getElementById("canvasOne"));
    }
</script>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    /*
     * 注意：
     * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
     * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
     * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
     *
     * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
     * 邮箱地址：weixin-open@qq.com
     * 邮件主题：【微信JS-SDK反馈】具体问题
     * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
     */
    wx.config({
        debug: false,
        appId: '${shareMap.appId}',
        timestamp: ${shareMap.timestamp},
        nonceStr: '${shareMap.nonceStr}',
        signature: '${shareMap.signature}',
        jsApiList: [
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone',
            'hideMenuItems',
            'showMenuItems',
            'hideAllNonBaseMenuItem',
            'showAllNonBaseMenuItem',
            'translateVoice',
            'startRecord',
            'stopRecord',
            'onVoiceRecordEnd',
            'playVoice',
            'onVoicePlayEnd',
            'pauseVoice',
            'stopVoice',
            'uploadVoice',
            'downloadVoice',
            'chooseImage',
            'previewImage',
            'uploadImage',
            'downloadImage',
            'getNetworkType',
            'openLocation',
            'getLocation',
            'hideOptionMenu',
            'showOptionMenu',
            'closeWindow',
            'scanQRCode',
            'chooseWXPay',
            'openProductSpecificView',
            'addCard',
            'chooseCard',
            'openCard'
        ]
    });

    var shareData = {
        title: '${shareMap.shareTitle}',
        desc: '${shareMap.shareDesc}',
        link: '${shareMap.shareLink}',
        imgUrl: '${shareMap.shareImg}'
    };
</script>
<script src="<%=basePath%>static/js/pageJs/zepto.min.js"></script>
<script src="<%=basePath%>static/js/pageJs/share.js"> </script>
</html>