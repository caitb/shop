<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/zhifu.css">
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="javascript:;" onClick="javascript:history.go(-1);">
                <img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>提交订单</p>
        </header>
        <main>
            <c:if test="${isQueuing==true}">
                <p class="row">本次订单将进入排单期，在您前面有<span>${count}</span>人排单。</p>
            </c:if>
            <c:if test="${bOrderConfirm.sendType==2}">
                <div class="Type">
                    <p>拿货方式：<span>自己发货</span></p>
                </div>
                <div class="xinz" onclick="toChooseAddressPage()">
                    <p><a>选择收货地址</a></p>
                </div>
                <section class="sec1">
                    <img src="${path}/static/images/zhifu_ad.png" alt="">
                    <div onclick="toChooseAddressPage()">
                        <input style="display: none" type="text" id="addressId"
                               value="${bOrderConfirm.comUserAddress.id}"/>
                        <a href="#"><h2>收货人：<b>${bOrderConfirm.comUserAddress.name}</b>
                            <span>${bOrderConfirm.comUserAddress.mobile}</span></h2></a>
                        <a href="#"><p>收货地址：
                            <span>${bOrderConfirm.comUserAddress.provinceName}  ${bOrderConfirm.comUserAddress.cityName}  ${bOrderConfirm.comUserAddress.regionName} ${bOrderConfirm.comUserAddress.address}
                            </span><img src="${path}/static/images/next.png" alt=""></p></a>
                    </div>
                </section>
            </c:if>
            <c:if test="${bOrderConfirm.sendType==1}">
                <div class="Type2">
                    <p>拿货方式：<span>平台代发</span></p>
                        <%--<h1>支付成功后，您的在线库存将会增加</h1>--%>
                </div>
            </c:if>
            <section class="sec2">
                <p class="photo">
                    <img src="${bOrderConfirm.skuImg}" alt=\"\">
                </p>
                <div>
                    <h2> ${bOrderConfirm.skuName}
                        <b style="float:right;margin-right:10px;font-size:12px;">x${bOrderConfirm.skuQuantity}</b>
                    </h2>
                    <h3>规格：<span>默认</span></h3>
                    <p>商品总价:<span>￥${bOrderConfirm.productTotalPrice} </span>
                    </p>
                </div>
            </section>
            <section class="sec3">
                <p>留言：<input type="text" id="userMessage" name="userMessage"></p>
            </section>
            <div>
                <p><img src="${path}/static/images/lirun.png" alt=""></p>
                <h1>
                    <span>预计您的利润</span>
                    <c:choose>
                        <c:when test="${bOrderConfirm.lowProfit.equals(bOrderConfirm.highProfit)}">
                            <span>￥${bOrderConfirm.lowProfit}</span>
                        </c:when>
                        <c:otherwise>
                            <span>￥${bOrderConfirm.lowProfit}~￥${bOrderConfirm.highProfit}</span>
                        </c:otherwise>
                    </c:choose>
                </h1>
            </div>
            <section class="sec4">
                <p>共需支付：￥${bOrderConfirm.orderTotalPrice}</p>
            </section>
            <a href="javascript:;" onclick="submit(this)" class="weixin">下一步</a>
        </main>
    </div>
</div>
<div class="paidanqi">
    <div class="Modal" style="display: block"></div>
    <div class="back_q" style="display: block;">
        <h1>什么是排单期？</h1>
        <p>
            由于商品过于火爆，导致库存量不足。申请合伙人或补货我们将记录付款的先后顺序，待产能提升，麦链商城将按照付款顺序发货
        </p>
        <button class="kNow">我知道了</button>
    </div>
</div>
<div class="fangshi">
    <div class="back_q">
        <p>
            <img src="${path}/static/images/fangshi.png" alt="">
        </p>
        <button class="kNow">我知道了</button>
    </div>
    <div class="Modal"></div>
</div>
</body>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/hideWXShare.js"></script>
<script>
    $(document).ready(function () {
        var addressId = $("#addressId").val();
        if (addressId == "") {
            $(".xinz").show();
            $(".sec1").hide();
        } else {
            $(".xinz").hide();
            $(".sec1").attr("style", "display:-webkit-box;");
        }
    })
    function toChooseAddressPage() {
        var selectedAddressId = $("#addressId").val();
        window.location.href = "${path}/userAddress/toChooseAddressPage.html?pageType=supplementOrder&selectedAddressId=" + selectedAddressId + "&supplementOrderParamForAddress=" + JSON.stringify(${supplementOrderParamForAddress});
    }

    function submit(para) {
        if ($(para).html() == "正在提交...") {
            return;
        }
        var sendType =${bOrderConfirm.sendType};
        var orderType =${bOrderConfirm.orderType};
        if (orderType == 2 || sendType == 2 && ($("#addressId").val() == null || $("#addressId").val() == "")) {
            alert("请填写收货地址");
            return;
        }
        var paraData = {};
        paraData.skuId = "${bOrderConfirm.skuId}";
        paraData.quantity = "${bOrderConfirm.skuQuantity}";
        paraData.userMessage = $("#userMessage").val();
        paraData.userAddressId = $("#addressId").val();
        $.ajax({
            url: "${basePath}BOrderAdd/supplementBOrder/add.do",
            type: "post",
            data: paraData,
            dataType: "json",
            success: function (data) {
                if (data.isError == false) {
                    $(para).html("正在提交...");
                    window.location.href = "${basePath}border/goToPayBOrder.shtml?bOrderId=" + data.bOrderId;
                }
            }
        });
    }
    $(".row").on("click", function () {
        $(".paidanqi").show();
    });
    $(".kNow").on("click", function () {
        $(".paidanqi").hide();
    });
    $(".Type2").on("click", function () {
        $(".fangshi").show();
    });
    $(".kNow").on("click", function () {
        $(".fangshi").hide();
    });
</script>
</html>