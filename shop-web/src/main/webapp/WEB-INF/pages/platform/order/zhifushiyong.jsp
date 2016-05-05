<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/zhifushiyong.css">
</head>
<body>
<header class="xq_header">
    <a href="javascript:;" onClick="javascript:history.back(-1);"><img src="${path}/static/images/xq_rt.png" alt=""></a>
    <p>确认订单</p>
</header>
<main>
    <div id="xz" onclick="toChooseAddressPage()">
        <div class="xinz">
            <p>选择收货地址</p>
        </div>
    </div>

    <div id="sec1">
        <section class="sec1">
            <img src="${path}/static/images/zhifu_ad.png" alt="" class="j">
            <div onclick="toChooseAddressPage()">
                <input id="skuId" style="display: none" type="text" value="${product.id}"/>
                <input style="display: none" type="text" id="addressId" value="${comUserAddress.id}"/>
                <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.mobile}</span></h2></a>
                <a href="#"><p>收货地址：
                        <span>${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}
                        </span></p></a>
            </div>
            <img src="${path}/static/images/next.png" alt="" class="ju">
        </section>
    </div>
    <section class="sec2">
        <p class="photo">
            <img src="${skuDefaultImg}" alt="${skuImgAlt}">
        </p>
        <div>
            <h2>${product.name}<span>x1</span></h2>
            <h3>规格：<span>试用装</span></h3>
            <p>￥0</p>
        </div>
    </section>
    <section class="sec3">
        <p>运费：<span>￥${product.shipAmount}</span></p>
        <p><em>试用理由：</em><input type="text" id="trialReasonId" ></p>
        <h1>共<b style="font-size:12px">1</b>件商品　运费：<span>￥${product.shipAmount}</span></h1>
    </section>
    <section class="sec4">
        <p>运费：<b>￥${product.shipAmount}</b></p>
        <p>共支付：<span>￥${product.shipAmount}</span></p>
    </section>
    <a href="javascript:weChatPay();" class="weixin">微信支付</a>
</main>
</body>

<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script src="${path}/static/js/checkUtil.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script>
    $(document).ready(function () {
        var addressId = $("#addressId").val();
        if (addressId == "") {
            $("#xz").show();
            $("#sec1").hide();
        }
    })
    function toChooseAddressPage() {
        var selectedAddressId = $("#addressId").val();
        var skuId = $("#skuId").val();
        window.location.href = "${path}/userAddress/toChooseAddressPage.html?pageType=zhifushiyong&selectedAddressId=" + selectedAddressId + "&skuId=" + skuId;
    }
</script>
<script>
    function weChatPay() {
        var addressId = $("#addressId").val();
        var skuId = $("#skuId").val();
        if(addressId==null||addressId==""){
           alert("请填写收货地址");
            return;
        }
        if(getStrLen($("#trialReasonId").val())>100){
            alert("试用理由不能超过100字");
            return;
        }

        if (!isTrial(skuId)){
            var trialReason = $("#trialReasonId").val();
            window.location.href = "/corder/trialApplyPay.do?skuId="+skuId+"&addressId="+addressId+"&reason="+trialReason;
        }
    }
    function isTrial(skuId){
        var bl = false;
        $.ajax({
            url: '/corder/isApplyTrial.do',
            type: 'post',
            async: false,
            data: {"skuId": skuId},
            success: function (data) {
                var dataObj = eval("(" + data + ")");//转换为json对象
                if (dataObj!=null&&dataObj!="") {
                    if (dataObj[0].payStatus==1){
                        alert("订单已支付无需再次支付");
                        bl = true;
                    }
                }
            }
        });
        return bl;
    }
</script>
</html>