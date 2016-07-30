<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/main.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <%--<link rel="stylesheet" href="<%=path%>/static/shop/css/header.css">--%>
    <link rel="stylesheet" href="<%=path%>/static/shop/css/dingdanxiangqing.css">
</head>
<body>
<header class="xq_header">
    <a href="<%= request.getHeader("REFERER") %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>订单详情</p>
</header>
<div class="wrap">
    <main>
        <div id="box">
            <div class="sec1">
                <c:if test="${orderMallDetail.sfOrder.orderStatus ==7}">
                    <img src="<%=path%>/static/images/icon_40.png" alt=""  style="display: block;width: 35px;height: 25px;top: 18px;">
                    <h1>未发货</h1><p>亲，请及时发货~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus ==0 }">
                    <img src="<%=path%>/static/images/icon_65.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;">
                    <h1>未付款</h1><p>亲，订单还未处理~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus ==8}">
                    <img src="<%=path%>/static/images/icon_68.png" alt="" style="display: block;width: 48px;height: 25px;top: 18px;">
                    <h1>未收货</h1><p>亲，订单还未完结~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==3}">
                    <img src="<%=path%>/static/images/icon_64.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;">
                    <h1>已完成</h1><p>亲，交易成功~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==6}">
                    <img src="<%=path%>/static/images/icon_64.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;">
                    <h1>排单中</h1><p>亲，订单排单中~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==9}">
                    <img src="<%=path%>/static/images/icon_65.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;">
                    <h1>线下支付中</h1><p>您的下级选择的是线下支付，请耐心等待~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==2}">
                    <img src="<%=path%>/static/images/quxiao.png" alt="" style="width: 35px;height: 37px;top: 14px;"><h1>已取消</h1>
                    <p>亲，您的订单已取消~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus ==7 && orderMallDetail.sfOrder.sendType==2}">
                    <button class="fah">发货</button>
                </c:if>
            </div>
            <div class="sec2">
                <p><span>订单编号：</span><span>${orderMallDetail.sfOrder.orderCode}</span></p>
                <p><span>订单状态：</span><span>${orderMallDetail.sfOrder.orderSkuStatus}</span></p>
            </div>
            <div class="sec3">
                <p><span>下单日期：</span>
                    <span><fmt:formatDate value="${orderMallDetail.sfOrder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <p><span>支付日期：</span>
                    <span><fmt:formatDate value="${orderMallDetail.sfOrder.payTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <p><span>支付类型：</span><c:forEach items="${orderMallDetail.sfOrderPayments}" var="pp"> <span>${pp.payTypeName}</span></c:forEach></p>
                <p><span>拿货方式：</span><c:if test="${orderMallDetail.sfOrder.sendType==0}"> <span>未选择</span></c:if><c:if test="${orderMallDetail.sfOrder.sendType==1}"> <span>平台发货</span></c:if><c:if test="${orderMallDetail.sfOrder.sendType==2}"> <span>自己发货</span></c:if></p>
                <p><span>类　　型：</span> <span>店铺订单</span></p>
                <p><span>物流状态：</span>
                    <c:if test="${orderMallDetail.sfOrder.orderStatus==3}">
                        <span>已完成</span>
                    </c:if>
                    <c:if test="${orderMallDetail.sfOrder.orderStatus==8}">
                        <span>待收货</span>
                    </c:if>
                    <c:if test="${orderMallDetail.sfOrder.orderStatus==0}">
                        <span>待处理</span>
                    </c:if>
                    <c:if test="${orderMallDetail.sfOrder.orderStatus==7 && orderMallDetail.sfOrder.sendType==2}">
                        <span>未发货</span>
                    </c:if>
                </p>
                <p><span>配送方式：</span> <span>物流配送</span></p>
                <p><span>发货时间：</span> <span>
                    <fmt:formatDate value="${orderMallDetail.sfOrder.shipTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <c:forEach items="${orderMallDetail.sfOrderFreights}" var="bpf">
                    <c:if test="${not empty bpf.freight}">
                        <p><span>发货单号：</span> <span>${bpf.freight}</span></p>
                    </c:if>
                </c:forEach>
            </div>
                <section class="dizhi">
                    <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                    <div>
                        <a href="#"><h2>收货人：<b>${orderMallDetail.sfOrderConsignee.consignee}</b> <span>${borderDetail.pfBorderConsignee.mobile}</span></h2></a>
                        <a href="#"><p>收货地址： <span>${orderMallDetail.sfOrderConsignee.provinceName} ${orderMallDetail.sfOrderConsignee.cityName} ${orderMallDetail.sfOrderConsignee.regionName} ${orderMallDetail.sfOrderConsignee.address}</span></p></a>
                    </div>
                </section>
            <div class="floor">
                <h1>购买人： ${orderMallDetail.buyerName}</h1>
                <c:forEach items="${orderMallDetail.sfOrderItems}" var="bdpd">
                <div>
                    <img src="${bdpd.skuUrl}" alt=""/>
                    <div>
                        <h2>${bdpd.skuName}</h2>
                        <h3>规格: 默认</h3>
                        <p>
                            零售价：
                            <span>￥${bdpd.unitPrice}</span>
                            <b>× ${bdpd.quantity}</b>
                        </p>
                    </div>
                </div>
                </c:forEach>
                <h4>
                    <span>备注：</span>
                    <span>${orderMallDetail.sfOrder.userMessage}</span>
                </h4>
            </div>
            <div class="sec4">
                <p><span>运费：</span> <span>${orderMallDetail.sfOrder.shipAmount}</span></p>
                <p><span>商品合计：</span> <span>${orderMallDetail.sfOrder.productAmount}</span></p>
                <p><span>实付金额：</span> <span style="color: #f74a11">${orderMallDetail.sfOrder.payAmount}</span></p>
            </div>
            <c:if test="${(orderMallDetail.sfUserBillItemInfo)!=null && fn:length(orderMallDetail.sfUserBillItemInfo)>0 }">
                <div class="sec3 sec5">
                    <h1>分销信息</h1>
                    <c:forEach items="${orderMallDetail.sfUserBillItemInfo}" var="bill">
                        <p><span>微信昵称：${bill.userNameForBill}</span> <span>分销佣金：￥${bill.sfUserBillItem.amount}</span></p>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </main>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="back_que">
        <div class="backt">
            <h1>发货信息</h1>
            <p>
                <span>快递公司：</span>
                <label for="" class="bWidth"><b></b><select class="se">
                    <c:forEach items="${comShipMans}" var="comShipMans">
                        <option value="${comShipMans.id}">${comShipMans.name}</option>
                    </c:forEach>
                </select>
                </label>
            </p>
            <p><span>快递单号：</span><input type="text" id="input"/></p>
            <button id="faHuo">发货</button>
        </div>
    </div>
</div>
<script src="<%=path%>/static/shop/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/shop/js/commonAjax.js"></script>
<script src="<%=path%>/static/shop/js/jinhuoshijian.js"></script>
<script src="<%=path%>/static/shop/js/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script>
$(".fah").on("click", function () {
    $(".black").show();

})
$(".se").on("change",function(){
    var tabVal=$(".se option:selected").text();
    $(".bWidth b").html(tabVal);
})
$("#faHuo").on("click", function () {
    $(".black").hide();
    var shipManId = $(".se option:selected").val();
    var shipManName = $(".se option:selected").text();
    var freight = $("#input").val();
    var borderId = ${orderMallDetail.sfOrder.id};
    $.ajax({
        type: "POST",
        url: "/sfOrderController/deliverOrder.do",
        data:{shipManName:shipManName,freight:freight,orderId:borderId,shipManId:shipManId},
        dataType: "Json",
        success: function () {
            alert("发货成功");
            $(".fah").html("");
            location.reload(true);
        }
    })
})
$(".backb").on("click", function () {
    $(".black").hide();
})
</script>
</body>
</html>