<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/shiyong.css">
</head>
<body>
<header class="xq_header">
    <a onclick="returnPage(${product.id})" ><img src="${path}/static/images/xq_rt.png" alt=""></a>
    <p>申请试用</p>
</header>
<main>
    <section class="sec2">
        <p class="photo">
            <a onclick="returnPage(${product.id})">
                <img src="${skuDefaultImg}" alt="${skuImgAlt}">
            </a>
        </p>
        <div>
            <h2>${product.name}</h2>
            <h3>规格：<span>默认</span><b>x1</b></h3>
            <p>零售价： 非卖品</p>
            <p style="color:#333;">运费<span>${product.shipAmount}</span></p>
        </div>
    </section>
    <section class="sec3">
        <h1>共<b>1</b>件商品　运费：<span>￥${product.shipAmount}</span>　<b style="color:#333333">合计：</b><span>￥${product.shipAmount}</span></h1>
        <p>申请理由：<input type="text" id="applyReasonId"></p>
    </section>
    <h1 class="pople">
        申请人信息
    </h1>
    <input id="skuId" type="hidden" value="${product.id}"/>
    <input id="spuId" type="hidden" value="${product.spuId}"/>
    <section class="sec4">
        <p>姓名：<input id="nameId" class="name" type="text"></p>
        <p>手机号：<input id="phoneId" class="tel" type="tel"></p>
        <p>验证码：<input id="codeValueId" class="yan" type="text"><botton id="yanzhengma" class="btn">获取验证码</botton></p>
        <p>微信：<input id="wechatId" type="text"></p>
    </section>
    <a id="apply"  class="sq">试用申请</a>
</main>
</body>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/hideWXShare.js"></script>
<script type="text/javascript" src="${path}/static/js/shiyong.js"></script>
<script>
    function returnPage(skuId){
        window.location.href="/product/"+skuId;
    }
</script>
</html>