<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reward.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>领取奖励</p>            
        </header>
        <main>
           <h1>请选择您的收货地址</h1>
            <section class="sec1">
               <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
               <div onclick="toChooseAddressPage()">
                   <input style="display: none" type="text" id="addressId" value="${comUserAddress.id}"/>
                   <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.mobile}</span></h2></a>
                    <a href="#"><p>收货地址： <span>
                         ${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}
                    </span></p></a>
                </div>
                <img src="<%=path%>/static/images/right.png" alt="">
            </section>
            <h1>奖品信息：</h1>
            <c:forEach items="${gifts}" var="gift">
                <section class="sec2">
                    <p>
                        <img src="<%=path%>/static/images/admin.png" alt="">
                    </p>
                </section>
                <section class="sec3">
                    <h1>名称： ${gift.giftName}</h1>
                    <h1>数量： ${gift.giftQuantity}</h1>
                </section>
            </c:forEach>
        </main>
        <button onclick="receiveReward()">领取奖励</button>
    </div>
   <script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
</body>
<script>
    function toChooseAddressPage() {
        var selectedAddressId = $("#addressId").val();
        window.location.href = "<%=path%>/userAddress/toChooseAddressPage.html?pageType=receiveReward&selectedAddressId=" + selectedAddressId +"&promoId=${promoId}&promoRuleId=${promoRuleId}";
    }
    function receiveReward(){
        $.ajax({
            type: "POST",
            url: "/promotionGorder/receiveReward.do",
            async:false,
            data: {selectedAddressId: ${comUserAddress.id}, promoId: ${promoId},promoRuleId: ${promoRuleId}},
            dataType: "Json",
            success: function (result) {
                if (result==1){
                    //领取成功
                    window.location.href="<%=path%>/promotionGorder/skipReceiveRewardSuccessPage.html";
                }else if(result==2){
                    //已领取
                    alert("您已领取不能再重复领取");
                }
            }
        })
    }
</script>
</html>