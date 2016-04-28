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
    <link rel="stylesheet" href="<%=path%>/static/shop/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/shop/css/reset.css">
    <%--<link rel="stylesheet" href="<%=path%>/static/shop/css/header.css">--%>
    <link rel="stylesheet" href="<%=path%>/static/shop/css/dingdanxiangqing.css">
</head>
<body>
<header class="xq_header">
    <a href="<%= request.getHeader("REFERER") %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>订单详情</p>
</header>
<div class="wrap">
    <div id="box">
        <div class="sec2">
            <p><span>订单编号：</span><span>${orderMallDetail.sfOrder.orderCode}</span></p>
            <p><span>订单状态：</span><span>${orderMallDetail.sfOrder.orderSkuStatus}</span></p>
            <p><span>下单日期：</span>
                <span><fmt:formatDate value="${orderMallDetail.sfOrder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
            <p><span>支付日期：</span>
                <span><fmt:formatDate value="${orderMallDetail.sfOrder.payTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
            <p><span>支付类型：</span><c:forEach items="${orderMallDetail.sfOrderPayments}" var="pp"> <span>${pp.payTypeName}</span></c:forEach></p>
            <p><span>拿货方式：</span><c:if test="${orderMallDetail.sfOrder.sendType==0}">未选择</c:if><c:if test="${orderMallDetail.sfOrder.sendType==1}">平台发货</c:if><c:if test="${orderMallDetail.sfOrder.sendType==2}">自己发货</c:if></p>
            <p><span>类　　型：</span>店铺订单</p>
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
                    <span>未发货</span><a class="fah">发货</a>
                </c:if>
            </p>
            <p><span>配送方式：</span><span>物流配送</span></p>
            <p><span>发货时间：</span><span>
                    <fmt:formatDate value="${orderMallDetail.sfOrder.shipTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
            <c:forEach items="${orderMallDetail.sfOrderFreights}" var="bpf">
                <c:if test="${not empty bpf.freight}">
                    <p><span>发货单号：</span><span>${bpf.freight}</span></p>
                </c:if>
            </c:forEach>
        </div>
        <div class="sec3">
            <p>收货人：<b>${orderMallDetail.sfOrderConsignee.consignee}</b></p>
            <p>手机号：<span>${orderMallDetail.sfOrderConsignee.mobile}</span></p>
            <p>收货地址：<span>${orderMallDetail.sfOrderConsignee.provinceName} ${orderMallDetail.sfOrderConsignee.cityName} ${orderMallDetail.sfOrderConsignee.regionName} ${orderMallDetail.sfOrderConsignee.address}</span></p>
            <p> 购物人：<span>${orderMallDetail.buyerName}</span></p>
            <p>   备注：<span>${orderMallDetail.sfOrder.userMessage}</span></p>
        </div>
        <div class="sec3">
            <c:forEach items="${orderMallDetail.sfOrderItems}" var="bdpd">
                <p><span>订单商品：</span><span>${bdpd.skuName}</span></p>
                <p><span>购买数量：</span><span>${bdpd.quantity}瓶</span></p>
                <p><span>商品单价：</span><span>${bdpd.unitPrice}</span></p>
            </c:forEach>
            <p><span>运    费：</span><span>${orderMallDetail.sfOrder.shipAmount}</span></p>
            <p><span>商品总金额：</span><span>${orderMallDetail.sfOrder.productAmount}</span></p>
            <p><span>实付金额：</span><span>${orderMallDetail.sfOrder.payAmount}</span></p>
            <p><span>订单状态：</span>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==0}">
                    <span>未处理</span>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==7}">
                    <span>未发货</span>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==3}">
                    <span>成功交易</span>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==8}">
                    <span>待收货</span>
                </c:if>
            </p>
        </div>
    </div>
</div>
<div class="back_que" style="display: none">
    <p>确认发货?</p>
    <h4>快递公司:<select id="select">
        <c:forEach items="${comShipMans}" var="comShipMans">
            <option value="${comShipMans.id}">${comShipMans.name}</option>
        </c:forEach>
    </select></h4>
    <h4>快递单号:<input type="text" id="input"/></h4>
    <h3 id="faHuo">发货</h3>
</div>
<div class="back" style="display: none">

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
    $(".back").show();
    $(".back_que").css("display", "-webkit-box");
})
$("#faHuo").on("click", function () {
    $(".back_que").hide();
    $(".back").hide();
    var shipManId = $("#select option:selected").val();
    var shipManName = $("#select option:selected").text();
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
$(".close").on("click", function () {
    $(".back_que").hide();
    $(".back").hide();
})
$(".back").on("click", function () {
    $(".back_que").hide();
    $(".back").hide();
})
</script>
</body>
</html>