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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/tijiaodingdan.css">
</head>
<script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
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
        var shopId = $("#shopId").val();
        window.location.href = "<%=path%>/userAddress/toChooseAddressPage.html?pageType=mallConfirmOrder&selectedAddressId=" + selectedAddressId + "&shopId=" + shopId;
    }
    function submitOrder(){
        var selectedAddressId = $("#addressId").val();
        var shopId = $("#shopId").val();
        var message = $("#userMessageId").val();
        if(selectedAddressId ==null || selectedAddressId =="" ){
            alert("未选择收货地址");
            return false ;
        }
        var paramJson = {
            "message": message,
            "shopId": shopId,
            "selectedAddressId": selectedAddressId
        }
        $.ajax({
            url: '/orderPurchase/submitOrder.do',
            type: 'post',
            async: false,
            data: paramJson,
            dataType:"json",
            success: function (data) {
                if (data.isSubmitOrder == "false") {
                    if(data.sfOrderId==-1){
                        alert("库存不足");
                    }else if (data.sfOrderId==-2){
                        alert("请返回重新选择商品");
                    }else{
                        alert("提交订单失败");
                    }
                } else {
                    window.location.href = "<%=path%>/orderPay/getOrderInfo.html?orderId="+data.sfOrderId;
                }
            },
            error: function () {
                alert("提交订单失败");
            }
        })
    }
    function returnPage(){
        var skuId = $("#skuId").val();
        window.location.href = "<%=basePath%>shop/detail.shtml/?skuId="+skuId+"&shopId=${shopId}";
    }
</script>
<body>
    <header>
          <a href="#" onclick="returnPage()"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>确认订单</p>
    </header>
    <div class="wrap">
       <div id="xz">
            <div class="xinz" onclick = "toChooseAddressPage()">
                <p>选择收货地址</p>
            </div>
        </div>
        <div id="sec1">
            <section class="sec1">
               <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
               <div onclick="toChooseAddressPage()">
                    <input style="display: none" type="text" id="addressId" value="${comUserAddress.id}"/>
                    <input style="display: none" type="text" id="shopId" value="${shopId}"/>
                    <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.mobile}</span></h2></a>
                    <a href="#"><p>收货地址： <span>
                        ${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}
                    </span></p></a>
                </div>
                <img src="<%=path%>/static/images/next.png" alt="" class="next">
            </section>
        </div>
        <c:forEach items="${shopCartSkuDetails}" var="skuDetail">
            <input id="skuId" type="hidden" value="${skuDetail.comSku.id}"/>
            <section class="sec2">
                <p class="photo">
                    <img src="<%=path%>/static/images/shenqing_1.png" alt="">
                </p>
                <div>
                    <h2>${skuDetail.comSku.name}<span>x${skuDetail.quantity}</span></h2>
                    <h3>规格：<span>默认</span></h3>
                    <p>￥${skuDetail.comSku.priceRetail}</p>
                </div>
            </section>
        </c:forEach>
        <section class="sec3">
            <p><em>运费：</em><span>包邮</span></p>
            <p><em>留言：</em><input id="userMessageId" type="text"></p>
            <h1>共支付：<span>￥ ${totalPrice}</span></h1>
        </section>
        <section class="sec4">

            <p>（共<b style="font-size:12px">${totalQuantity}</b>件商品　合计：<span>￥${skuTotalPrice}</span>）</p>
        </section>
        <a  onclick="submitOrder()" class="weixin">提交订单</a>
    </div>
    
</body>
</html>