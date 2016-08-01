<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String url = request.getHeader("REFERER");
    if(url ==null || url==""){
        url=path+"/borderManage/deliveryDouckBorder";
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/chakanxiangqing.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%= url %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>订单详情</p>
    </header>
    <main>
        <div id="box">
            <div class="sec1">
                <c:if test="${borderDetail.pfBorder.orderStatus ==7}">
                    <img src="<%=path%>/static/images/icon_40.png" alt=""  style="display: block;width: 35px;height: 25px;top: 18px;">
                    <h1>未发货</h1><p>亲，请及时发货~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus ==0 }">
                    <img src="<%=path%>/static/images/icon_65.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;">
                    <h1>未付款</h1><p>亲，订单还未处理~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus ==8}">
                    <img src="<%=path%>/static/images/icon_68.png" alt="" style="display: block;width: 48px;height: 25px;top: 18px;">
                    <h1>未收货</h1><p>亲，订单还未完结~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus==3}">
                    <img src="<%=path%>/static/images/icon_64.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;">
                    <h1>已完成</h1><p>亲，交易成功~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus==6}">
                    <img src="<%=path%>/static/images/icon_64.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;">
                    <h1>排单中</h1><p>亲，订单排单中~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus==9}">
                    <img src="<%=path%>/static/images/icon_65.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;">
                    <h1>线下支付中</h1><p>您的下级选择的是线下支付，请耐心等待~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus==2}">
                    <img src="<%=path%>/static/images/quxiao.png" alt="" style="width: 35px;height: 37px;top: 14px;"><h1>已取消</h1>
                    <p>亲，您的订单已取消~~</p>
                </c:if>
            </div>
            <div class="sec2">
                <p><span>订单编号：</span><span>${borderDetail.pfBorder.orderCode}</span></p>
                <p><span>下单日期：</span><span>
                    <fmt:formatDate value="${borderDetail.pfBorder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
            </div>
            <div class="sec3">
                <p>
                    <span>订单状态：</span>
                    <c:forEach items="${bOrderStatuses}" var="os">
                        <c:if test="${os.code == borderDetail.pfBorder.orderStatus}"><span>${os.desc}</span></c:if>
                    </c:forEach>
                </p>
                <p><span>支付日期：</span><span>
                    <fmt:formatDate value="${borderDetail.pfBorder.payTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <p><span>支付类型：</span><c:forEach items="${borderDetail.pfBorderPayments}" var="pp"> <span>${pp.payTypeName}</span></c:forEach></p>
                <p><span>拿货方式：</span><c:if test="${borderDetail.pfBorder.sendType==0}"> <span>未选择</span></c:if><c:if test="${borderDetail.pfBorder.sendType==1}"> <span>平台发货</span></c:if><c:if test="${borderDetail.pfBorder.sendType==2}"><span>自己发货</span></c:if></p>
                <p><span>类　　型：</span>
                    <c:forEach items="${bOrderTypes}" var="orderType">
                        <c:if test="${orderType.code == borderDetail.pfBorder.orderType}"><span>${orderType.desc}</span></c:if>
                    </c:forEach>
                </p>
                <p><span>物流状态：</span>
                    <c:if test="${borderDetail.pfBorder.orderStatus==3 &&borderDetail.pfBorder.shipStatus==9}">
                        <span>已完成</span>
                    </c:if>
                    <c:if test="${borderDetail.pfBorder.orderStatus==8}">
                        <span>已发货</span>
                    </c:if>
                    <c:if test="${borderDetail.pfBorder.orderStatus==7}">
                        <span>未发货</span><a class="fah">发货</a>
                    </c:if></p>
                <p> <span>配送方式：</span> <span>物流配送</span></p>
                <p><span>发货时间：</span><span>
                    <fmt:formatDate value="${borderDetail.pfBorder.shipTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <c:forEach items="${borderDetail.pfBorderFreights}" var="bpf">
                    <c:if test="${not empty bpf.freight}">
                        <p><span>发货单号：</span><span>${bpf.freight}</span></p>
                    </c:if>
                </c:forEach>
            </div>
            <c:if test="${borderDetail.pfBorderConsignee!=null}">
                <section class="dizhi">
                    <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                    <div>
                        <a href="#"><h2>收货人：<b>${borderDetail.pfBorderConsignee.consignee}</b> <span>${borderDetail.pfBorderConsignee.mobile}</span></h2></a>
                        <a href="#"><p>收货地址： <span>${borderDetail.pfBorderConsignee.provinceName} ${borderDetail.pfBorderConsignee.cityName} ${borderDetail.pfBorderConsignee.regionName} ${borderDetail.pfBorderConsignee.address}</span></p></a>
                    </div>
                </section>
            </c:if>
            <div class="floor">
                <h1>购买人： ${borderDetail.buyerName}</h1>
                <c:forEach items="${borderDetail.pfBorderItems}" var="bdpd">
                <div>
                    <img src="${bdpd.skuUrl}" alt=""/>
                    <div>
                        <h2>${bdpd.skuName}</h2>
                        <h3>规格：默认</h3>
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
                    <span>${borderDetail.pfBorder.userMessage}</span>
                </h4>
            </div>
            <div class="sec4">
                    <p><span>运费：</span> <span>￥${borderDetail.pfBorder.shipAmount}</span></p>
                    <p><span>商品合计：</span> <span>￥${borderDetail.pfBorder.productAmount}</span></p>
                    <p><span>实付金额：</span> <span style="color: #f74a11">￥${borderDetail.pfBorder.payAmount}</span>(含保证金：￥${borderDetail.pfBorder.bailAmount})</p>
            </div>
            <c:if test="${ not empty borderDetail.rewordUser}">
                <div class="sec3">
                    <h1>推荐信息</h1>
                    <p><span>推荐奖励：</span> <span>￥${borderDetail.pfBorder.recommenAmount}</span></p>
                    <p><span>推荐人：</span> <span>${borderDetail.rewordUser}</span></p>
                </div>
            </c:if>
        </div>
    </main>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<link rel="stylesheet" href="<%=path%>/static/css/loading.css">
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script>
    $("#faHuo").on("click", function () {
        $(".back_que").hide();
        $(".back").hide();
        var shipManId = $("#select option:selected").val();
        var shipManName = $("#select option:selected").text();
        var freight = $("#input").val();
        var borderId = ${borderDetail.pfBorder.id};
        alert("asdas");
        $.ajax({
            type: "POST",
            url: "/borderManage/deliver.do",
            data:{shipManName:shipManName,freight:freight,orderId:borderId,shipManId:shipManId},
            dataType: "Json",
            success: function () {
                alert("asdas");
                $(".fah").html("");
                location.reload(true);
            },
            error: function () {
                alert("66666");
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