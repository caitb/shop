<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

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
            <c:if test="${orderMap.orderType==0}">
                (保证金：￥${orderMap.bailAmount})
            </c:if>
            <c:if test="${orderMap.orderType==2}">(运费：到付)</c:if>
        </h1>

        <h1>
            <b>发货方：</b><span>
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