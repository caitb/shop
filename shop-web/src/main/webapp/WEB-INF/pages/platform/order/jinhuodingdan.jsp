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
                        <p>进货订单</p>
                </header>
                <nav>
                    <ul>
                        <li><a href="javascript:;" class="on">全部</a></li>
                        <li><a href="javascript:;">待付款</a></li>
                        <li><a href="javascript:;">线下支付中</a></li>
                        <li><a href="javascript:;">待发货</a></li>
                        <li><a href="javascript:;">待收货</a></li>
                        <li><a href="javascript:;">已完成</a></li>
                        <li><a href="javascript:;">排单中</a></li>
                    </ul>
                    <img src="${path}/static/images/youdao.png" alt="" class="you">
                </nav>
                <main>
                    <%--<c:forEach items="${pfBorders}" begin="0" end="${pfBorders.size()}" var="pbs">--%>
                    <%--<div class="all">--%>
                        <%--<c:forEach items="${pbs}" var="pb">--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>${pb.orderCode}</span>--%>
                                <%--<c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>--%>
                                <%--<c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="querenshouhuo_${pb.id}" >排单中</b></c:if>--%>
                                <%--<c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>--%>
                                <%--<c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>--%>
                                <%--<c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>--%>
                            <%--</h2>--%>
                            <%--<c:forEach items="${pb.pfBorderItems}" var="pbi">--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="javascript:void(0);">--%>
                                        <%--<img src="${pbi.skuUrl}" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>${pbi.skuName}</h2>--%>
                                    <%--<h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>--%>
                                    <%--<p class="defult">实收款：<span style="float:none;color:#FF6A2A;font-size: 12px">￥${pbi.totalPrice}</span></p>--%>
                                <%--</div>--%>
                            <%--</div></c:forEach>--%>
                               <%--<h1> 共<span>${pb.totalQuantity}</span>件商品 <b style="color:#A5A5A5">合计：￥${pb.orderAmount}</b>( 运费：到付)</h1>--%>
                                <%--<h1><b>发货方：</b>--%>
                                    <%--<span>${pb.pidUserName}</span>--%>
                                    <%--<c:if test="${pb.orderType==2 && pb.sendType==1}">--%>
                                        <%--<b>类型：</b><span>拿货</span></c:if><c:if test="${pb.orderType==0}">--%>
                                        <%--<b>类型：</b><span>合伙订单</span></c:if><c:if test="${pb.orderType==1}">--%>
                                        <%--<b>类型：</b><span>补货</span></c:if>--%>
                                <%--</h1>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="<%=path%>/borderManage/borderDetils.html?id=${pb.id}">查看订单详情</a></p>--%>
                                <%--<span class="jixu"><c:if test="${pb.sendType==0}">选择拿货方式</c:if> <c:if test="${pb.orderStatus ==0 && pb.sendType!=0}">--%>
                                    <%--继续支付</a></c:if>--%>
                                <%--</span><c:if test="${pb.orderStatus ==8}">--%>
                                <%--<span class="fa"  name="querenshouhuo_${pb.id}"  onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">--%>
                                    <%--确认收货--%>
                                <%--</span></c:if>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--</c:forEach>--%>
                    <%--</div>--%>
                    <%--</c:forEach>--%>

                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="querenshouhuo_${pb.id}">排单中</b></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                                    <c:if test="${pb.orderStatus ==9}"><b class="querenshouhuo_${pb.id}">线下支付中</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="javascript:void(0);">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                        </div>
                                    </div></c:forEach>
                                <h1> 共<span style="margin:0">${pb.totalQuantity}</span>件商品 <b style="color:#A5A5A5">合计：￥${pb.orderAmount}</b>(<c:if test="${pb.orderType==0}">包含保证金</c:if> 运费：到付)</h1>
                                <h1><b>发货方：</b>
                                    <span>${pb.pidUserName}</span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">
                                        <b>类型：</b><span>拿货</span></c:if>
                                    <c:if test="${pb.orderType==0}">
                                        <b>类型：</b><span>合伙订单</span></c:if>
                                    <c:if test="${pb.orderType==1}">
                                        <b>类型：</b><span>补货</span></c:if>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                    <c:if test="${pb.sendType==0 && pb.orderStatus !=0}"><span class="jixu">选择拿货方式</span></c:if>
                                    <c:if test="${pb.orderStatus ==0 }">
                                        <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">继续支付</a></span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==9 }">
                                        <span class="jixu" onclick="xinxi('${pb.orderAmount}','${pb.payTimes}')">支付信息</span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa" name="querenshouhuo_${pb.id}" onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">
                                            确认收货</span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==9}">
                                        <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a></span>
                                    </c:if>
                                </div>
                            </section>
                        </c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="querenshouhuo_${pb.id}" >排单中</b></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                                    <c:if test="${pb.orderStatus ==9}"><b class="querenshouhuo_${pb.id}">线下支付中</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="javascript:void(0);">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                        </div>
                                    </div></c:forEach>
                                <h1> 共<span style="margin:0">${pb.totalQuantity}</span>件商品 <b style="color:#A5A5A5">合计：￥${pb.orderAmount}</b>(<c:if test="${pb.orderType==0}">包含保证金</c:if> 运费：到付)</h1>
                                <h1><b>发货方：</b>
                                    <span>${pb.pidUserName}</span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">
                                        <b>类型：</b><span>拿货</span></c:if><c:if test="${pb.orderType==0}">
                                        <b>类型：</b><span>合伙订单</span></c:if><c:if test="${pb.orderType==1}">
                                        <b>类型：</b><span>补货</span></c:if>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                    <c:if test="${pb.sendType==0 && pb.orderStatus !=0}"><span class="jixu">选择拿货方式</span></c:if>
                                    <c:if test="${pb.orderStatus ==0}">
                                        <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}"> 继续支付</a></span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==9 }">
                                        <span class="jixu" onclick="xinxi('${pb.orderAmount}','${pb.payTimes}')">支付信息</span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa"  name="querenshouhuo_${pb.id}"  onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">
                                            确认收货</span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==9}">
                                        <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a></span>
                                    </c:if>
                                </div>
                            </section>
                        </c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="querenshouhuo_${pb.id}" >排单中</b></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                                    <c:if test="${pb.orderStatus ==9}"><b class="querenshouhuo_${pb.id}">线下支付中</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="javascript:void(0);">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                        </div>
                                    </div></c:forEach>
                                <h1> 共<span style="margin:0">${pb.totalQuantity}</span>件商品 <b style="color:#A5A5A5">合计：￥${pb.orderAmount}</b>(<c:if test="${pb.orderType==0}">包含保证金</c:if> 运费：到付)</h1>
                                <h1><b>发货方：</b>
                                    <span>${pb.pidUserName}</span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">
                                        <b>类型：</b><span>拿货</span></c:if><c:if test="${pb.orderType==0}">
                                        <b>类型：</b><span>合伙订单</span></c:if><c:if test="${pb.orderType==1}">
                                        <b>类型：</b><span>补货</span></c:if>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                    <c:if test="${pb.sendType==0 && pb.orderStatus !=0}"><span class="jixu">选择拿货方式</span></c:if>
                                    <c:if test="${pb.orderStatus ==0}">
                                        <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}"> 继续支付</a></span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==9 }">
                                        <span class="jixu" onclick="xinxi('${pb.orderAmount}','${pb.payTimes}')">支付信息</span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==8}">
                                    <span class="fa"  name="querenshouhuo_${pb.id}"  onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">
                                        确认收货
                                    </span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==9}">
                                        <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a></span>
                                    </c:if>
                                </div>
                            </section>
                        </c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="querenshouhuo_${pb.id}" >排单中</b></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                                    <c:if test="${pb.orderStatus ==9}"><b class="querenshouhuo_${pb.id}">线下支付中</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="javascript:void(0);">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                        </div>
                                    </div></c:forEach>
                                <h1> 共<span style="margin:0">${pb.totalQuantity}</span>件商品 <b style="color:#A5A5A5">合计：￥${pb.orderAmount}</b>(<c:if test="${pb.orderType==0}">包含保证金</c:if> 运费：到付)</h1>
                                <h1><b>发货方：</b>
                                    <span>${pb.pidUserName}</span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">
                                        <b>类型：</b><span>拿货</span></c:if><c:if test="${pb.orderType==0}">
                                        <b>类型：</b><span>合伙订单</span></c:if><c:if test="${pb.orderType==1}">
                                        <b>类型：</b><span>补货</span></c:if>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                    <c:if test="${pb.sendType==0 && pb.orderStatus !=0}">
                                        <span class="jixu">选择拿货方式</span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==0}">
                                        <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}"> 继续支付</a></span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==9 }">
                                        <span class="jixu" onclick="xinxi('${pb.orderAmount}','${pb.payTimes}')">支付信息</span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==8}">
                                    <span class="fa"  name="querenshouhuo_${pb.id}"  onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">
                                        确认收货
                                    </span>
                                    </c:if>
                                    <c:if test="${pb.orderStatus ==9}">
                                        <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a></span>
                                    </c:if>
                                </div>
                            </section>
                        </c:forEach>
                    </div>
                        <div class="all">
                            <c:forEach items="${pfBorders}" var="pb">
                                <section class="sec1">
                                    <p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                    <h2>
                                        订单号：<span>${pb.orderCode}</span>
                                        <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                        <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="querenshouhuo_${pb.id}" >排单中</b></c:if>
                                        <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                        <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                        <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                                        <c:if test="${pb.orderStatus ==9}"><b class="querenshouhuo_${pb.id}">线下支付中</b></c:if>
                                    </h2>
                                    <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                        <div class="shangpin">
                                            <p class="photo">
                                                <a href="javascript:void(0);">
                                                    <img src="${pbi.skuUrl}" alt="">
                                                </a>
                                            </p>
                                            <div>
                                                <h2>${pbi.skuName}</h2>
                                                <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                            </div>
                                        </div></c:forEach>
                                    <h1> 共<span style="margin:0">${pb.totalQuantity}</span>件商品 <b style="color:#A5A5A5">合计：￥${pb.orderAmount}</b>(<c:if test="${pb.orderType==0}">包含保证金</c:if> 运费：到付)</h1>
                                    <h1><b>发货方：</b>
                                        <span>${pb.pidUserName}</span>
                                        <c:if test="${pb.orderType==2 && pb.sendType==1}">
                                            <b>类型：</b><span>拿货</span></c:if><c:if test="${pb.orderType==0}">
                                            <b>类型：</b><span>合伙订单</span></c:if><c:if test="${pb.orderType==1}">
                                            <b>类型：</b><span>补货</span></c:if>
                                    </h1>
                                    <div class="ding">
                                        <p><a href="<%=path%>/borderManage/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                        <c:if test="${pb.sendType==0 && pb.orderStatus !=0}"><span class="jixu">选择拿货方式</span></c:if>
                                        <c:if test="${pb.orderStatus ==0}">
                                            <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}"> 继续支付</a></span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==9 }">
                                            <span class="jixu" onclick="xinxi('${pb.orderAmount}','${pb.payTimes}')">支付信息</span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==8}">
                                            <span class="fa"  name="querenshouhuo_${pb.id}"  onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">
                                                确认收货
                                            </span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==9}">
                                            <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a></span>
                                        </c:if>
                                    </div>
                                </section>
                            </c:forEach>
                        </div>
                        <div class="all">
                            <c:forEach items="${pfBorders}" var="pb">
                                <section class="sec1">
                                    <p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                    <h2>
                                        订单号：<span>${pb.orderCode}</span>
                                        <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                        <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="querenshouhuo_${pb.id}" >排单中</b></c:if>
                                        <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                        <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                        <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                                        <c:if test="${pb.orderStatus ==9}"><b class="querenshouhuo_${pb.id}">线下支付中</b></c:if>
                                    </h2>
                                    <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                        <div class="shangpin">
                                            <p class="photo">
                                                <a href="javascript:void(0);">
                                                    <img src="${pbi.skuUrl}" alt="">
                                                </a>
                                            </p>
                                            <div>
                                                <h2>${pbi.skuName}</h2>
                                                <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                            </div>
                                        </div></c:forEach>
                                    <h1> 共<span style="margin:0">${pb.totalQuantity}</span>件商品 <b style="color:#A5A5A5">合计：￥${pb.orderAmount}</b>(<c:if test="${pb.orderType==0}">包含保证金</c:if> 运费：到付)</h1>
                                    <h1><b>发货方：</b>
                                        <span>${pb.pidUserName}</span>
                                        <c:if test="${pb.orderType==2 && pb.sendType==1}">
                                            <b>类型：</b><span>拿货</span></c:if><c:if test="${pb.orderType==0}">
                                            <b>类型：</b><span>合伙订单</span></c:if><c:if test="${pb.orderType==1}">
                                            <b>类型：</b><span>补货</span></c:if>
                                    </h1>
                                    <div class="ding">
                                        <p><a href="<%=path%>/borderManage/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                        <c:if test="${pb.sendType==0 && pb.orderStatus !=0}">
                                            <span class="jixu">选择拿货方式</span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==0 }">
                                            <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}"> 继续支付</a></span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==9 }">
                                            <span class="jixu" onclick="xinxi('${pb.orderAmount}','${pb.payTimes}')">支付信息</span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==8}">
                                            <span class="fa"  name="querenshouhuo_${pb.id}"
                                                  onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">确认收货</span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==9}">
                                            <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a></span>
                                        </c:if>
                                    </div>
                                </section>
                            </c:forEach>
                        </div>
                        <div class="all">
                            <c:forEach items="${pfBorders}" var="pb">
                                <section class="sec1">
                                    <p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                    <h2>
                                        订单号：<span>${pb.orderCode}</span>
                                        <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                        <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="querenshouhuo_${pb.id}" >排单中</b></c:if>
                                        <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                        <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                        <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                                        <c:if test="${pb.orderStatus ==9}"><b class="querenshouhuo_${pb.id}">线下支付中</b></c:if>
                                    </h2>
                                    <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                        <div class="shangpin">
                                            <p class="photo">
                                                <a href="javascript:void(0);">
                                                    <img src="${pbi.skuUrl}" alt="">
                                                </a>
                                            </p>
                                            <div>
                                                <h2>${pbi.skuName}</h2>
                                                <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                            </div>
                                        </div></c:forEach>
                                    <h1> 共<span style="margin:0">${pb.totalQuantity}</span>件商品 <b style="color:#A5A5A5">合计：￥${pb.orderAmount}</b>(<c:if test="${pb.orderType==0}">包含保证金</c:if> 运费：到付)</h1>
                                    <h1><b>发货方：</b>
                                        <span>${pb.pidUserName}</span>
                                        <c:if test="${pb.orderType==2 && pb.sendType==1}">
                                            <b>类型：</b><span>拿货</span></c:if><c:if test="${pb.orderType==0}">
                                            <b>类型：</b><span>合伙订单</span></c:if><c:if test="${pb.orderType==1}">
                                            <b>类型：</b><span>补货</span></c:if>
                                    </h1>
                                    <div class="ding">
                                        <p><a href="<%=path%>/borderManage/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                        <c:if test="${pb.sendType==0 && pb.orderStatus !=0}">
                                            <span class="jixu">选择拿货方式</span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==0 }">
                                            <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}"> 继续支付</a></span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==9 }">
                                            <span class="jixu" onclick="xinxi('${pb.orderAmount}','${pb.payTimes}')">支付信息</span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==8}">
                                            <span class="fa"  name="querenshouhuo_${pb.id}"
                                                  onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">确认收货</span>
                                        </c:if>
                                        <c:if test="${pb.orderStatus ==9}">
                                            <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a></span>
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
                   <h1>￥<span id="1">1,000,000</span>元</h1>
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

           function xinxi(orderAmount,payTimes){
               $(".back").css("display","-webkit-box");
               $(".back_pay").show();
               $("#1").html(orderAmount);
               $("#3").html(orderAmount);
               $("#2").html(payTimes);
           }

           $(".xinxn").on("click",function(){
               $(".back_pay").hide();
               $(".back").hide();
           })

           $(function(){
            $("li").on("click",function(){
                var index=$(this).index();
                $(".all").html("");
                $(".all").eq(index).show().siblings().hide();
                $("li").children("a").removeClass("on");
                $(this).children("a").addClass("on");
                $.ajax({
                    type:"POST",
                    url : "<%=path%>/borderManage/clickType.do",
                    data:{index:index},
                    dataType:"Json",
                    success:function(data){
                        var trHtml = "";
                        var orderStatusName="";
                        var StatusName="";
                        var orderTypeName="";
                        $.each(data, function(i, pfBorder) {
                            var time2 = new Date(pfBorder.createTime).Format("yyyy-MM-dd hh:mm");
                            trHtml+="<section class='sec1'>";
                            trHtml+="<p>时间: <span>"+time2 +"</span></p>";
                            if(pfBorder.orderStatus==0){
                                StatusName="待付款";
                            }else if(pfBorder.orderStatus ==6 && pfBorder.sendType==1){
                                StatusName="排单中";
                            }else if(pfBorder.orderStatus ==7){
                                StatusName="待发货";
                            }else if(pfBorder.orderStatus ==8){
                                StatusName="待收货";
                            }else if(pfBorder.orderStatus ==3){
                                StatusName="交易成功";
                            }else if(pfBorder.orderStatus ==9){
                                StatusName="线下支付中";
                            }
                            trHtml+="<h2>订单号：<span>"+pfBorder.orderCode+"</span><b class='querenshouhuo_"+pfBorder.id+"' >"+StatusName+"</b ></h2>";
                            $.each(pfBorder.pfBorderItems, function(i, pfBorderItem) {
                                trHtml+="<div class='shangpin'>";
                                trHtml+=" <p class=\"photo\">";
                                trHtml+="<a href=\"javascript:void(0);\">";
                                trHtml+="<img src=\""+pfBorderItem.skuUrl+"\" alt=\"\"></a></p>";
                                trHtml+="<div><h2>"+pfBorderItem.skuName+"</h2><h3><span>￥"+pfBorderItem.unitPrice+"</span><b>x"+pfBorderItem.quantity+"</b></h3>";
                                trHtml+="</div></div>";
                            });
                            trHtml+="<h1> 共<span>"+pfBorder.totalQuantity+"</span>件商品 <b style=\"color:#A5A5A5\">合计：￥"+pfBorder.orderAmount+"</b>";
                            if(pfBorder.orderType==0){
                                trHtml+="(包含保证金 运费：到付)";
                            }else{
                                trHtml+="(运费：到付)";
                            }
                            trHtml+="</h1><h1><b>发货方：</b><span>"+pfBorder.pidUserName+"</span>";
                            if(pfBorder.orderType==2 && pfBorder.sendType==1){
                                orderTypeName="拿货";
                            }else if(pfBorder.orderType==0){
                                orderTypeName="合伙订单";
                            }else if(pfBorder.orderType==1){
                                orderTypeName="补货";
                            }
                            trHtml+="<b>类型：</b><span>"+orderTypeName+"</span></h1>";
                            trHtml+="<div class=\"ding\"><p><a href=\"<%=path%>/borderManage/borderDetils.html?id="+pfBorder.id+"\">查看订单详情</a></p>";
                            if(pfBorder.sendType==0 && pfBorder.orderStatus !=0){
                                trHtml+="<span class=\"jixu\">选择拿货方式</span></a>";
                            }else if(pfBorder.orderStatus ==0){
                                trHtml+="<span class=\"jixu\"><a href=\"<%=basePath%>border/goToPayBOrder.shtml?bOrderId="+pfBorder.id+"\">继续支付</a></span></a>";
                            }else if(pfBorder.orderStatus ==9){
                                trHtml+="<span class=\"jixu\" onclick=\"xinxi('"+pfBorder.orderAmount+"','"+pfBorder.payTimes+"')\">支付信息</span></a>";
                            }else{
                                trHtml+="</a>";
                            }
                            if(pfBorder.orderStatus ==8){
                                orderStatusName="确认收货";
                                trHtml+="<span class=\"fa\"  name=\"querenshouhuo_"+pfBorder.id+"\"  onclick=\"querenshouhuo('"+pfBorder.orderStatus+"','"+pfBorder.id+"')\">"+orderStatusName+"</span>";
                            }if(pfBorder.orderStatus ==9){
                                trHtml+="<span><a href=\"<%=basePath%>border/goToPayBOrder.shtml?bOrderId="+pfBorder.id+"\">改变支付方式</a></span>";
                            }else{
                                trHtml+="";
                            }
                            trHtml+="</div></section>";
                        });
                        $(".all").eq(index).html(trHtml);
                    }
                })
            })
           })
            $(document).ready(function(){
                var index=${index};
                $("li").children("a").removeClass("on")
                $("li").eq(index).children("a").addClass("on");
                $(".all").eq(index).show().siblings().hide();
            });


            $(".fa").on("click",function(){
                $(".back").css("display","-webkit-box");
                $(".back_shouhuo").css("display","-webkit-box");
            })

           var oid = "";
            function querenshouhuo(orderStatus,id) {
                $(".back").css("display", "-webkit-box");
                $(".back_shouhuo").css("display", "-webkit-box");
                oid = id;
            }
           $(function(){
                $(".que_que").on("click",function(){
                    $(".back_shouhuo").hide();
                    $(".back").hide();
                    var aa="querenshouhuo_"+oid;
                    $.ajax({
                        type:"POST",
                        url : "<%=path%>/borderManage/closeDeal.do",
                        data:{orderStatus:3,shipStatus:9,orderId:oid},
                        dataType:"Json",
                        success:function(date){
//                            if(date.msgs){
                                $("span[name="+aa+"]").attr("style","display:none");
                                $("b."+aa+"").html("交易成功");
                                location.reload(true);
//                            }else{
//                                alert(date.message);
//                            }
                        }
                    })
                })
           })
            $(".que_qu").on("click",function(){
                $(".back_shouhuo").hide();
                $(".back").hide();
            })

       </script>
</body>
</html>