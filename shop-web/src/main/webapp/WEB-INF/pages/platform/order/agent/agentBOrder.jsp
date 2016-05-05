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
            <c:choose>
                <c:when test="${previousPageType==0}">
                    <a href="${basePath}userApply/register.shtml?skuId=${bOrderConfirm.skuId}">
                        <img src="${path}/static/images/xq_rt.png" alt=""></a>
                </c:when>
                <c:otherwise>
                    <a href="${basePath}border/setUserSendType.shtml?skuId=${bOrderConfirm.skuId}&agentLevelId=${bOrderConfirm.agentLevelId}&weiXinId=${bOrderConfirm.weiXinId}">
                        <img src="${path}/static/images/xq_rt.png" alt=""></a>
                </c:otherwise>
            </c:choose>
            <p>提交订单</p>
        </header>
        <main>
            <div class="xinxi">
                <p>注册信息</p>
                <p>确定拿货方式</p>
                <p>支付订单</p>
            </div>
            <c:if test="${isQueuing==true}">
                <p class="row">本次订单将进入排单期。在您前面有<span>${count}</span>人排单。</p>
            </c:if>
            <c:if test="${bOrderConfirm.sendType==2}">
                <div class="Type">
                    <p>拿货方式：<span>自己发货</span>
                        <c:if test="${previousPageType==1}">
                            <a href="${basePath}border/setUserSendType.shtml?skuId=${bOrderConfirm.skuId}&agentLevelId=${bOrderConfirm.agentLevelId}&weiXinId=${bOrderConfirm.weiXinId}">
                                重新选择</a>
                        </c:if>
                    </p>
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
                    <p>拿货方式：<span>平台代发</span>
                        <c:if test="${previousPageType==1}">
                            <a href="${basePath}border/setUserSendType.shtml?skuId=${bOrderConfirm.skuId}&agentLevelId=${bOrderConfirm.agentLevelId}&weiXinId=${bOrderConfirm.weiXinId}">
                                重新选择</a>
                        </c:if>
                    </p>
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
                    <h3>合伙人套餐：<span>${bOrderConfirm.orderTotalPrice}元套餐</span></h3>
                    <p>
                        商品价格:<span>￥${bOrderConfirm.productTotalPrice}</span>保证金:<span>￥${bOrderConfirm.bailAmount}</span>
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
                    <span>￥${bOrderConfirm.lowProfit}~￥${bOrderConfirm.highProfit}</span>
                </h1>
            </div>
            <h1>支付成功后，您的在线库存将会增加</h1>
            <section class="sec4">
                <p><b>商品总价：</b><span>￥${bOrderConfirm.productTotalPrice}</span></p>
                <p><b>保证金：</b><span>￥${bOrderConfirm.bailAmount}</span></p>
                <p><b>共需支付：</b><span style="color:#f74a11">￥${bOrderConfirm.orderTotalPrice}</span></p>
            </section>
            <a href="javascript:;" onclick="submit(this);" class="weixin">下一步</a>
        </main>
    </div>
</div>
<div class="paidanqi">
    <div class="back_q">
        <h1>什么是排单期？</h1>
        <p>
            由于商品过于火爆，导致库存量不足。申请合伙人或补货我们将记录付款的先后顺序，待产能提升，麦链商城将按照付款顺序发货
        </p>
        <button class="kNow">我知道了</button>
    </div>
    <div class="Modal"></div>
</div>
</body>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
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
        window.location.href = "${path}/userAddress/toChooseAddressPage.html?pageType=agentOrder&selectedAddressId=" + selectedAddressId + "&agentOrderparamForAddress=" + JSON.stringify(${agentOrderparamForAddress});
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
        paraData.orderType = "${bOrderConfirm.orderType}";
        paraData.sendType = "${bOrderConfirm.sendType}";
        paraData.skuId = "${bOrderConfirm.skuId}";
        paraData.agentLevelId = "${bOrderConfirm.agentLevelId}";
        paraData.weiXinId = "${bOrderConfirm.weiXinId}";
        paraData.userMessage = $("#userMessage").val();
        paraData.userAddressId = $("#addressId").val();
        $.ajax({
            url: "${basePath}BOrderAdd/agentBOrder/add.do",
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
</script>
</html>