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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/jinhuodingdan.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="<%=path%>/borderManage/borderManagement.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>我的订单</p>
        </header>
        <nav>
            <ul>
                <li class="order-list" isShipment="${isShipment}"                ><a href="javascript:void(0);" <c:if test="${orderStatus == null}">class="on"</c:if> >全部</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="0"><a href="javascript:void(0);" <c:if test="${orderStatus == 0}">class="on"</c:if> >待付款</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="7"><a href="javascript:void(0);" <c:if test="${orderStatus == 7}">class="on"</c:if> >待发货</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="8"><a href="javascript:void(0);" <c:if test="${orderStatus == 8}">class="on"</c:if> >待收货</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="3"><a href="javascript:void(0);" <c:if test="${orderStatus == 3}">class="on"</c:if> >已完成</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="6"><a href="javascript:void(0);" <c:if test="${orderStatus == 6}">class="on"</c:if> >排单中</a></li>
            </ul>
            <img src="${path}/static/images/youdao.png" alt="" class="you">
        </nav>
        <c:if test="${isShipment == 1}">
        <p><img src="/static/images/laba.png" alt="">您只可以查看直接下级的订单</p>
        </c:if>
        <main>
            <div class="all">
                <c:forEach items="${orderMaps}" var="orderMap">
                    <section class="sec1">
                        <p>时间： <span><fmt:formatDate value="${orderMap.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                        <h2>
                            订单号：<span>${orderMap.orderCode}</span>
                            <c:forEach items="${orderStatuses}" var="orderStatus">
                                <c:if test="${orderStatus.code == orderMap.orderStatus}"><b>${orderStatus.desc}</b></c:if>
                            </c:forEach>
                        </h2>
                        <c:forEach items="${orderMap.bItems}" var="bItem">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="javascript:void(0);">
                                        <img src="${imgUrlPrefix}${bItem.imgUrls.imgUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${bItem.skuName}</h2>
                                    <h3><span>￥${bItem.unitPrice}</span><b>x${bItem.quantity}</b></h3>
                                </div>
                            </div>
                        </c:forEach>

                        <h1>
                            <b style="color:#A5A5A5">合计：￥${orderMap.orderAmount}</b>
                                                            <c:if test="${orderMap.orderType==0}">(保证金：￥${orderMap.bailAmount})</c:if>
                                                            <c:if test="${orderMap.orderType==2}">(运费：到付)</c:if>
                        </h1>

                        <h1>
                            <b>发货方：</b>
                            <span>
                            <c:if test="${orderMap.sendType == 0}">未选择</c:if>
                            <c:if test="${orderMap.sendType == 1}">平台代发</c:if>
                            <c:if test="${orderMap.sendType == 2}">自己发货</c:if>
                            </span>
                            <b>类型：</b>
                            <c:forEach items="${orderTypes}" var="orderType">
                                <c:if test="${orderType.code == orderMap.orderType}"><span>${orderType.desc}</span></c:if>
                            </c:forEach>
                        </h1>

                        <div class="ding">
                            <p><a href="<%=path%>/borderManage/borderDetils.html?id=${orderMap.id}">查看订单详情</a></p>
                            <c:if test="${orderMap.sendType==0 && orderMap.orderStatus !=0}">
                                <span class="jixu">选择拿货方式</span>
                            </c:if>
                            <c:if test="${orderMap.orderStatus ==0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${orderMap.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${orderMap.orderStatus ==9 }">
                                <span class="jixu" onclick="xinxi('${orderMap.orderAmount}','${orderMap.payTime}','${orderMap.orderCode}')">支付信息</span>
                            </c:if>
                            <c:if test="${orderMap.orderStatus ==8}">
                                <span class="fa" name="querenshouhuo_${orderMap.id}" onclick="querenshouhuo('${orderMap.orderStatus}','${orderMap.id}')">确认收货</span>
                            </c:if>
                            <c:if test="${orderMap.orderStatus ==9}">
                                <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${orderMap.id}">改变支付方式</a></span>
                            </c:if>
                        </div>

                    </section>
                </c:forEach>
            </div>
        </main>
    </div>
    <div class="back_shouhuo" style="display: none">
        <p>确认收到货品?</p>
        <h4>亲，请您核对商品后再操作确认收货</h4>

        <h3>
            <span class="que_qu">取消</span>
            <span class="que_que">确认</span>
        </h3>
    </div>

    <div class="back_que" style="display: none">
        <p>确认取消订单？</p>
        <h4>亲，是否确认删除商品抗引力-收敛精华乳液订单？</h4>

        <h3>
            <span class="que_qu">取消</span>
            <span class="que_que">确认</span>
        </h3>
    </div>
    <div class="back_pay">
        <div>
            <h1>订单号：<span id="1">1,000,000</span></h1>
            <p>您需要在<span id="2">2016-4-30</span>前将￥<span id="3">1,000,000.00</span>转到麦链合伙人对公账户。</p>
        </div>
        <%--<div>--%>
        <%--<h1>￥1,000,000元</h1>--%>
        <%--<p>您需要在2016-4-30前将￥1,000,000.00转到麦链合伙人对公账户。</p>--%>
        <%--</div>--%>
        <p>*请在汇款单的附言处注明绑定的订单号”（<span>非常重要！</span>）</p>
        <h1><span></span>麦链对公账户信息</h1>
        <h2><span>开户行：</span><span>${defaultBank.bankName}</span></h2>
        <h2><span>开户名：</span><span>${defaultBank.accountName}</span></h2>
        <h2><span>卡号：</span><span>${defaultBank.cardNumber}</span></h2>
        <button class="xinxn">我知道了</button>
    </div>
    <div class="back" style="display: none">

    </div>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script src="<%=path%>/static/js/jinhuoshijian.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script>
    $(document).on('click', '.order-list', function(){
        var isShipment = $(this).attr('isShipment');
        var orderStatus = $(this).attr('orderStatus');
        var param  = isShipment  == undefined ? '' : '?isShipment=' + isShipment;
            param += orderStatus == undefined ? '' : '&orderStatus=' + orderStatus;
        window.location.replace('<%=basePath%>borderManage/orderList'+param);
    });
</script>
</body>
</html>