<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shiyong.css">
</head>
<body>
<header class="xq_header">
    <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>申请试用</p>
</header>
<script type="text/javascript" src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/checkUtil.js"></script>
<script>
    function apply(){
        var name  = $("#nameId")[0].value;
        var phone = $("#phoneId")[0].value;
        var wechat = $("#wechatId")[0].value;
        var applyReason = $("#applyReasonId")[0].value;
        var spuId =$("#spuId")[0].value;
        var skuId = $("#skuId")[0].value;
        var isPhone = checkPhone(phone);
        if (!checkNumber(wechat)){
            alert("微信号格式不对");
            return;
        }
        if(!checkNumber(phone)||!isPhone){
            alert("手机号格式不对");
            return;
        }
        $.post("/corder/trialApply.json",
                {
                    "spuId":spuId,
                    "skuId":skuId,
                    "applyReason":applyReason,
                    "name" : name,
                    "phone" : phone,
                    "wechat" : wechat
                },function(data) {
                    if(data == "success"){
                        window.location.href = "<%=path%>/corder/continueStroll"
                    }
                });
    }

</script>
<main>
    <section class="sec2">
        <p class="photo">
            <a href="../html/xiangqing.html">
                <img src="${skuDefaultImg}" alt="${skuImgAlt}">
            </a>
        </p>
        <div>
            <h2>${product.name}<span>x1</span></h2>
            <h3>规格：<span>默认</span></h3>
            <p>零售价：<span>￥${product.priceRetail}</span></p>
        </div>
    </section>
    <section class="sec3">
        <p>运费<span>${product.priceRetail}</span></p>
        <h1>共<b style="font-size:12px">1</b>件商品　运费：<span>${product.shipAmount}</span>　<b style="font-size:12px">合计：</b><span>￥${product.priceRetail}</span></h1>
        <p>申请理由：<input type="text" id="applyReasonId"></p>
    </section>
    <h1 class="pople">
        申请人信息
    </h1>
    <section class="sec4">
        <input id="skuId" type="hidden" value="${product.id}"/>
        <input id="spuId" type="hidden" value="${product.spuId}"/>
        <p>姓名：<input id="nameId" type="text"></p>
        <p>手机号：<input id="phoneId" type="tel"></p>
        <p>验证码：<input id="validateNumberId" type="text"><span>获取验证码</span></p>
        <p>微信：<input id="wechatId" type="text"></p>
    </section>
    <a href="javascript:apply();"  class="sq">试用申请</a>
</main>
</body>
</html>