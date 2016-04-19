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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wodedingdan.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
       <div class="wrap">
           <div class="box">
                <header class="xq_header"><a href="<%=path%>/borderManage/borderManagement.html">
                    <img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>我的订单</p>  
                </header>
                <nav>
                    <ul>
                        <li><a href="javascript:;" class="on">全部</a></li>
                        <li><a href="javascript:;">待付款</a></li>
                        <li><a href="javascript:;">待发货</a></li>
                        <li><a href="javascript:;">待收货</a></li>
                        <li><a href="javascript:;">已完成</a></li>
                        <li><a href="javascript:;">排单中</a></li>
                    </ul>
                </nav>
                <main>
                    <%--<c:forEach items="${pfBorders}" begin="0" end="${pfBorders.size()}" var="pbs">--%>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                        <section class="sec1">
                           <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="fahuo_${pb.id}" >排单中</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">等待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">已发货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                            </h2>
                            <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                   <a href="<%=path%>/static/html/xiangqing.html">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}</h2>
                                    <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                    <p class="defult">实收款： <span style="float:none;color:#FF6A2A;">￥${pbi.totalPrice}</span><c:if test="${pb.orderType==0}">(包含保证金 运费：到付)</c:if></p>
                                </div>
                            </div> </c:forEach>
                                <h1>
                                    <b>发货方:</b>
                                    <span><c:if test="${pb.sendType==1}">平台发货</c:if><c:if test="${pb.sendType==0 ||pb.sendType==null}">
                                    未选择</c:if><c:if test="${pb.sendType==2}">自己发货</c:if>
                                    </span>
                                    <b>类型:</b>
                                   <span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">申请拿货</c:if>
                                    <c:if test="${pb.orderType==0}">下级合伙订单</c:if>
                                    <c:if test="${pb.orderType==1}">下级补货</c:if>
                                    </span>
                                </h1>
                            <div class="ding">
                                <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                <p class="sh" onclick="shouhuorenxinxi('${pb.pfBorderConsignee.consignee}','${pb.pfBorderConsignee.provinceName} ${pb.pfBorderConsignee.cityName} ${pb.pfBorderConsignee.regionName} ${pb.pfBorderConsignee.address}','${pb.pfBorderConsignee.mobile}','${pb.pfBorderConsignee.zip}')">收货人信息</p></c:if>
                                <c:choose><c:when test="${pb.orderStatus ==6}"><p>处理排单</p></c:when>
                                <c:when test="${pb.orderStatus ==7 && pb.sendType==2}">
                                    <span class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</span></c:when></c:choose>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="fahuo_${pb.id}" >排单中</b ></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">等待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">已发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="<%=path%>/static/html/xiangqing.html">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                            <p class="defult">实收款： <span style="float:none;color:#FF6A2A;">￥${pbi.totalPrice}</span><c:if test="${pb.orderType==0}">(包含保证金 运费：到付)</c:if></p>
                                        </div>
                                    </div> </c:forEach>
                                <h1>
                                    <b>发货方:</b>
                                    <span><c:if test="${pb.sendType==1}">平台发货</c:if><c:if test="${pb.sendType==0||pb.sendType==null}">
                                        未选择</c:if><c:if test="${pb.sendType==2}">自己发货</c:if>
                                    </span>
                                    <b>类型:</b>
                                   <span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">申请拿货</c:if>
                                    <c:if test="${pb.orderType==0}">下级合伙订单</c:if>
                                    <c:if test="${pb.orderType==1}">下级补货</c:if>
                                    </span>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                    <p class="sh" onclick="shouhuorenxinxi('${pb.pfBorderConsignee.consignee}','${pb.pfBorderConsignee.provinceName} ${pb.pfBorderConsignee.cityName} ${pb.pfBorderConsignee.regionName} ${pb.pfBorderConsignee.address}','${pb.pfBorderConsignee.mobile}','${pb.pfBorderConsignee.zip}')">收货人信息</p></c:if>
                                    <c:choose><c:when test="${pb.orderStatus ==6}"><p>处理排单</p></c:when>
                                        <c:when test="${pb.orderStatus ==7 && pb.sendType==2}"><span class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</span></c:when></c:choose>
                                </div>
                            </section></c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="fahuo_${pb.id}" >排单中</b ></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">等待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">已发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="<%=path%>/static/html/xiangqing.html">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                            <p class="defult">实收款： <span style="float:none;color:#FF6A2A;">￥${pbi.totalPrice}</span><c:if test="${pb.orderType==0}">(包含保证金 运费：到付)</c:if></p>
                                        </div>
                                    </div> </c:forEach>
                                <h1>
                                    <b>发货方:</b>
                                    <span><c:if test="${pb.sendType==1}">平台发货</c:if><c:if test="${pb.sendType==0||pb.sendType==null}">
                                        未选择</c:if><c:if test="${pb.sendType==2}">自己发货</c:if>
                                    </span>
                                    <b>类型:</b>
                                   <span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">申请拿货</c:if>
                                    <c:if test="${pb.orderType==0}">下级合伙订单</c:if>
                                    <c:if test="${pb.orderType==1}">下级补货</c:if>
                                    </span>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                    <p class="sh" onclick="shouhuorenxinxi('${pb.pfBorderConsignee.consignee}','${pb.pfBorderConsignee.provinceName} ${pb.pfBorderConsignee.cityName} ${pb.pfBorderConsignee.regionName} ${pb.pfBorderConsignee.address}','${pb.pfBorderConsignee.mobile}','${pb.pfBorderConsignee.zip}')">收货人信息</p></c:if>
                                    <c:choose><c:when test="${pb.orderStatus ==6}"><p>处理排单</p></c:when>
                                        <c:when test="${pb.orderStatus ==7 && pb.sendType==2}">
                                            <span class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</span></c:when></c:choose>
                                </div>
                            </section></c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="fahuo_${pb.id}" >排单中</b ></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">等待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">已发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="<%=path%>/static/html/xiangqing.html">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                            <p class="defult">实收款： <span style="float:none;color:#FF6A2A;">￥${pbi.totalPrice}</span><c:if test="${pb.orderType==0}">(包含保证金 运费：到付)</c:if></p>
                                        </div>
                                    </div> </c:forEach>
                                <h1>
                                    <b>发货方:</b>
                                    <span><c:if test="${pb.sendType==1}">平台发货</c:if><c:if test="${pb.sendType==0||pb.sendType==null}">
                                        未选择</c:if><c:if test="${pb.sendType==2}">自己发货</c:if>
                                    </span>
                                    <b>类型:</b>
                                   <span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">申请拿货</c:if>
                                    <c:if test="${pb.orderType==0}">下级合伙订单</c:if>
                                    <c:if test="${pb.orderType==1}">下级补货</c:if>
                                    </span>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                    <p class="sh" onclick="shouhuorenxinxi('${pb.pfBorderConsignee.consignee}','${pb.pfBorderConsignee.provinceName} ${pb.pfBorderConsignee.cityName} ${pb.pfBorderConsignee.regionName} ${pb.pfBorderConsignee.address}','${pb.pfBorderConsignee.mobile}','${pb.pfBorderConsignee.zip}')">收货人信息</p></c:if>
                                    <c:choose><c:when test="${pb.orderStatus ==6}"><p>处理排单</p>
                                    </c:when><c:when test="${pb.orderStatus ==7 && pb.sendType==2}"><span class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</span></c:when></c:choose>
                                </div>
                            </section></c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="fahuo_${pb.id}" >排单中</b ></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">等待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">已发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="<%=path%>/static/html/xiangqing.html">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                            <p class="defult">实收款： <span style="float:none;color:#FF6A2A;">￥${pbi.totalPrice}</span><c:if test="${pb.orderType==0}">(包含保证金 运费：到付)</c:if></p>
                                        </div>
                                    </div> </c:forEach>
                                <h1>
                                    <b>发货方:</b>
                                    <span><c:if test="${pb.sendType==1}">平台发货</c:if><c:if test="${pb.sendType==0||pb.sendType==null}">
                                        未选择</c:if><c:if test="${pb.sendType==2}">自己发货</c:if>
                                    </span>
                                    <b>类型:</b>
                                   <span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">申请拿货</c:if>
                                    <c:if test="${pb.orderType==0}">下级合伙订单</c:if>
                                    <c:if test="${pb.orderType==1}">下级补货</c:if>
                                    </span>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                    <p class="sh" onclick="shouhuorenxinxi('${pb.pfBorderConsignee.consignee}','${pb.pfBorderConsignee.provinceName} ${pb.pfBorderConsignee.cityName} ${pb.pfBorderConsignee.regionName} ${pb.pfBorderConsignee.address}','${pb.pfBorderConsignee.mobile}','${pb.pfBorderConsignee.zip}')">收货人信息</p></c:if>
                                    <c:choose><c:when test="${pb.orderStatus ==6}"><p>处理排单</p></c:when>
                                        <c:when test="${pb.orderStatus ==7 && pb.sendType==2}"><span class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</span></c:when></c:choose>
                                </div>
                            </section></c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1">
                                <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                                <h2>
                                    订单号：<span>${pb.orderCode}</span>
                                    <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                    <c:if test="${pb.orderStatus ==6 && pb.sendType==1}"><b class="fahuo_${pb.id}" >排单中</b ></c:if>
                                    <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">等待发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">已发货</b></c:if>
                                    <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <p class="photo">
                                            <a href="<%=path%>/static/html/xiangqing.html">
                                                <img src="${pbi.skuUrl}" alt="">
                                            </a>
                                        </p>
                                        <div>
                                            <h2>${pbi.skuName}</h2>
                                            <h3><span>￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></h3>
                                            <p class="defult">实收款： <span style="float:none;color:#FF6A2A;">￥${pbi.totalPrice}</span><c:if test="${pb.orderType==0}">(包含保证金 运费：到付)</c:if></p>
                                        </div>
                                    </div> </c:forEach>
                                <h1>
                                    <b>发货方:</b>
                                    <span><c:if test="${pb.sendType==1}">平台发货</c:if><c:if test="${pb.sendType==0||pb.sendType==null}">
                                        未选择</c:if><c:if test="${pb.sendType==2}">自己发货</c:if>
                                    </span>
                                    <b>类型:</b>
                                   <span>
                                    <c:if test="${pb.orderType==2 && pb.sendType==1}">申请拿货</c:if>
                                    <c:if test="${pb.orderType==0}">下级合伙订单</c:if>
                                    <c:if test="${pb.orderType==1}">下级补货</c:if>
                                    </span>
                                </h1>
                                <div class="ding">
                                    <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                    <p class="sh" onclick="shouhuorenxinxi('${pb.pfBorderConsignee.consignee}','${pb.pfBorderConsignee.provinceName} ${pb.pfBorderConsignee.cityName} ${pb.pfBorderConsignee.regionName} ${pb.pfBorderConsignee.address}','${pb.pfBorderConsignee.mobile}','${pb.pfBorderConsignee.zip}')">收货人信息</p></c:if>
                                    <c:choose><c:when test="${pb.orderStatus ==6}"><p>处理排单</p></c:when>
                                        <c:when test="${pb.orderStatus ==7 && pb.sendType==2}"><span class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</span></c:when></c:choose>
                                </div>
                            </section></c:forEach>
                    </div>
                </main>
           </div>
       </div>
       <div class="back_que">
           <p>确认发货?</p>
           <h4>快递公司:<select id="select"><option value="1">顺风</option><option value="2">EMS</option></select></h4>
           <h4>快递单号:<input type="text" id="input"/></h4>
           <h3 id="faHuo">发货</h3>
       </div>
       <div class="shouhuo">
           <p>收货人信息</p>
           <h4><span>姓　名:</span><span id="1"></span></h4>
           <h4><span>地　址:</span><span id="2">阿斯科利的阿</span></h4>
           <h4><span>手机号:</span><span id="3"></span></h4>
           <h4><span>邮　编:</span><span id="4"></span></h4>
           <h3 class="close">关闭</h3>
       </div>
           <div class="back">

           </div>
       <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
       <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
       <script src="<%=path%>/static/js/commonAjax.js"></script>
       <script src="<%=path%>/static/js/jinhuoshijian.js"></script>
       <script src="<%=path%>/static/js/definedAlertWindow.js"></script>
       <script>
//               $('body').on('touchmove', function (event) {
//                   var event=event||event.window;
//                   event.prevtDefault();
//               });
               function shouhuorenxinxi(a,b,c,d){
                   $(".back").css("display","-webkit-box");
                   $(".shouhuo").css("display","-webkit-box");
                   $("#1").html(a);
                   $("#2").html(b);
                   $("#3").html(c);
                   $("#4").html(d);
               }
               $(document).ready(function(){
                   var index=${index};
                   $("li").children("a").removeClass("on")
                   $("li").eq(index).children("a").addClass("on");
                   $(".all").eq(index).show().siblings().hide();
               });
               $(function(){
                   $("li").on("click",function(){
                       var index=$(this).index();
                       $("li").children("a").removeClass("on")
                       $(this).children("a").addClass("on");
                       $(".all").eq(index).show().siblings().hide();
                       $.ajax({
                           type:"POST",
                           url : "<%=path%>/borderManage/clickdeliverType.do",
                           data:{index:index},
                           dataType:"Json",
                           success:function(data){
                               var trHtml = "";
                               var StatusName="";
                               var orderTypeName="";
                               $.each(data, function(i, pfBorder) {
                                   var time2 = new Date(pfBorder.createTime).Format("yyyy-MM-dd hh:mm");
                                   trHtml+="<section class=\"sec1\">";
                                   trHtml+="<p>时间: <span>"+time2 +"</span></p>";
                                   if(pfBorder.orderStatus==0){
                                       StatusName="待付款";
                                   }else if(pfBorder.orderStatus ==6 && pfBorder.sendType==1){
                                       StatusName="排单中";
                                   }else if(pfBorder.orderStatus ==7){
                                       StatusName="待发货";
                                   }else if(pfBorder.orderStatus ==8){
                                       StatusName="已发货";
                                   }else if(pfBorder.orderStatus ==3){
                                       StatusName="交易成功";
                                   }
                                   trHtml+="<h2>订单号：<span>"+pfBorder.orderCode+"</span><b class=\"fahuo_"+pfBorder.id+"\">"+StatusName+"</b ></h2>";
                                   $.each(pfBorder.pfBorderItems, function(i, pfBorderItem) {
                                       trHtml+="<div class=\"shangpin\">";
                                       trHtml+=" <p class=\"photo\">";
                                       trHtml+="<a href=\"<%=path%>/static/html/xiangqing.html\">";
                                       trHtml+="<img src=\""+pfBorderItem.skuUrl+"\" alt=\"\"></a></p>";
                                       trHtml+="<div><h2>"+pfBorderItem.skuName+"</h2><h3><span>￥"+pfBorderItem.unitPrice+"</span><b>x"+pfBorderItem.quantity+"</b></h3>";
                                       trHtml+="<p class=\"defult\">实收款：<span style=\"float:none;color:#FF6A2A;font-size: 12px\">￥"+pfBorderItem.totalPrice+"</span></p></div></div>";
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
                                   trHtml+="<b>类型：</b><span>"+pfBorder.pidUserName+"</span></h1>";
                                   trHtml+="<div class=\"ding\"><p><a href=\"<%=path%>/borderManage/deliveryBorderDetils.html?id="+pfBorder.id+"\">查看订单详情</a></p>";
                                   if(pfBorder.payStatus ==1 ){
                                       if(pfBorder.pfBorderConsignee != null){
                                           trHtml+="<p class=\"sh\" onclick=\"shouhuorenxinxi(\""+pfBorder.pfBorderConsignee.consignee+"\",\""+pfBorder.pfBorderConsignee.provinceName+" "+pfBorder.pfBorderConsignee.cityName+" "+pfBorder.pfBorderConsignee.regionName+" "+pfBorder.pfBorderConsignee.address+" \",\""+pfBorder.pfBorderConsignee.mobile+"\",\""+pfBorder.pfBorderConsignee.zip+"\")\">收货人信息</p>";
                                       }else{
                                           trHtml+="";
                                       }
                                   }else{
                                       trHtml+="";
                                   }
                                   if(pfBorder.orderStatus ==6){
                                       trHtml+="<p>处理排单</p>";
                                   }else if(pfBorder.orderStatus ==7 && pfBorder.sendType==2){
                                       trHtml+="<span class=\"fa\" name=\"fahuo_"+pfBorder.id+"\" onclick=\"fahuo('"+pfBorder.id+"')\">发货</span>";
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


               function fahuo(id){
                   $(".back").css("display","-webkit-box");
                   $(".back_que").css("display","-webkit-box");
                   $("#faHuo").on("click",function(){
                       $(".back_que").hide();
                       $(".back").hide();
                       var shipManId = $("#select option:selected").val();
                       var shipManName = $("#select option:selected").text();
                       var freight = $("#input").val();
                       var aa="fahuo_"+id;
                       $.ajax({
                           type:"POST",
                           url : "<%=path%>/borderManage/deliver.do",
                           data:{shipManName:shipManName,freight:freight,orderId:id,shipManId:shipManId},
                           dataType:"Json",
                           success:function(date){
                                   $("span[name=" + aa + "]").attr("style", "display:none");
                                   $("b." + aa + "").html("待收货");
                                   location.reload(true);
                           }
                       })
                   })
               }

               $(".close").on("click",function(){
                   $(".shouhuo").hide();
                   $(".back").hide();
               })
               $(".back").on("click",function(){
                   $(".back_que").hide();
                   $(".back").hide();
                   $(".shouhuo").hide();
               })
       </script>
</body>
</html>