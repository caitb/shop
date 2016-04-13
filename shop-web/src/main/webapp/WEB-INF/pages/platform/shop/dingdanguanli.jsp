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
    <link rel="stylesheet" href="<%=path%>/static/shop/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/shop/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/shop/css/loading.css">
    <link rel="stylesheet" href="<%=path%>/static/shop/css/dingdanguanli.css">
</head>
<body>
       <div class="wrap">
           <div class="box">
                <header class="xq_header">
                   <a href="zhifu.html"><img src="<%=path%>/static/shop/images/xq_rt.png" alt=""></a>
                        <p>我的订单</p>  
                </header>
                <nav>
                    <ul>
                        <li><a href="javascript:;" class="on">全部</a></li>
                        <li><a href="javascript:;">待付款</a></li>
                        <li><a href="javascript:;">待发货</a></li>
                        <li><a href="javascript:;">待收货</a></li>
                        <li><a href="javascript:;">已完成</a></li>
                    </ul>
                </nav>
               <main>
                    <div class="all">
                        <c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">待收货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                            </h2>
                            <c:forEach items="${pb.sfOrderItems}" var="pbi">
                                <div class="shangpin">
                                    <p class="photo">
                                        <a href="<%=path%>/static/html/xiangqing.html">
                                            <img src="${pbi.skuUrl}" alt="">
                                        </a>
                                    </p>
                                    <div>
                                        <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                        <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                    </div>
                                </div> </c:forEach>
                            <p class="money">实收款：<span>￥${pb.receivableAmount}</span><span>发货方：<b>
                                <c:if test="${pb.sendType==1}">平台发货</c:if>
                                <c:if test="${pb.sendType==0 ||pb.sendType==null}">未选择</c:if>
                                <c:if test="${pb.sendType==2}">自己发货</c:if></b></span></p>
                            <div class="ding">
                                <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                <p class="sh" onclick="shouhuorenxinxi('${pb.sfOrderConsignee.consignee}','${pb.sfOrderConsignee.provinceName} ${pb.sfOrderConsignee.cityName} ${pb.sfOrderConsignee.regionName} ${pb.sfOrderConsignee.address}','${pb.sfOrderConsignee.mobile}','${pb.sfOrderConsignee.zip}')">收货人信息</p></c:if>
                                <c:if test="${pb.orderStatus ==7}">
                                    <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</button>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                   <div class="all">
                       <c:forEach items="${sfOrders}" var="pb">
                           <section class="sec1">
                               <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                               <h2>
                                   订单号：<span>${pb.orderCode}</span>
                                   <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                   <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">待发货</b></c:if>
                                   <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">待收货</b></c:if>
                                   <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                               </h2>
                               <c:forEach items="${pb.sfOrderItems}" var="pbi">
                                   <div class="shangpin">
                                       <p class="photo">
                                           <a href="<%=path%>/static/html/xiangqing.html">
                                               <img src="${pbi.skuUrl}" alt="">
                                           </a>
                                       </p>
                                       <div>
                                           <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                           <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                       </div>
                                   </div> </c:forEach>
                               <p class="money">实收款：<span>￥${pb.receivableAmount}</span><span>发货方：<b>
                                   <c:if test="${pb.sendType==1}">平台发货</c:if>
                                   <c:if test="${pb.sendType==0 ||pb.sendType==null}">未选择</c:if>
                                   <c:if test="${pb.sendType==2}">自己发货</c:if></b></span></p>
                               <div class="ding">
                                   <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                   <p class="sh" onclick="shouhuorenxinxi('${pb.sfOrderConsignee.consignee}','${pb.sfOrderConsignee.provinceName} ${pb.sfOrderConsignee.cityName} ${pb.sfOrderConsignee.regionName} ${pb.sfOrderConsignee.address}','${pb.sfOrderConsignee.mobile}','${pb.sfOrderConsignee.zip}')">收货人信息</p></c:if>
                                   <c:if test="${pb.orderStatus ==7}">
                                       <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</button>
                                   </c:if>
                               </div>
                           </section></c:forEach>
                   </div>
                   <div class="all">
                       <c:forEach items="${sfOrders}" var="pb">
                           <section class="sec1">
                               <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                               <h2>
                                   订单号：<span>${pb.orderCode}</span>
                                   <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                   <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">待发货</b></c:if>
                                   <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">待收货</b></c:if>
                                   <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                               </h2>
                               <c:forEach items="${pb.sfOrderItems}" var="pbi">
                                   <div class="shangpin">
                                       <p class="photo">
                                           <a href="<%=path%>/static/html/xiangqing.html">
                                               <img src="${pbi.skuUrl}" alt="">
                                           </a>
                                       </p>
                                       <div>
                                           <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                           <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                       </div>
                                   </div> </c:forEach>
                               <p class="money">实收款：<span>￥${pb.receivableAmount}</span><span>发货方：<b>
                                   <c:if test="${pb.sendType==1}">平台发货</c:if>
                                   <c:if test="${pb.sendType==0 ||pb.sendType==null}">未选择</c:if>
                                   <c:if test="${pb.sendType==2}">自己发货</c:if></b></span></p>
                               <div class="ding">
                                   <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                   <p class="sh" onclick="shouhuorenxinxi('${pb.sfOrderConsignee.consignee}','${pb.sfOrderConsignee.provinceName} ${pb.sfOrderConsignee.cityName} ${pb.sfOrderConsignee.regionName} ${pb.sfOrderConsignee.address}','${pb.sfOrderConsignee.mobile}','${pb.sfOrderConsignee.zip}')">收货人信息</p></c:if>
                                   <c:if test="${pb.orderStatus ==7}">
                                       <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</button>
                                   </c:if>
                               </div>
                           </section></c:forEach>
                   </div>
                   <div class="all">
                       <c:forEach items="${sfOrders}" var="pb">
                           <section class="sec1">
                               <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                               <h2>
                                   订单号：<span>${pb.orderCode}</span>
                                   <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                   <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">待发货</b></c:if>
                                   <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">待收货</b></c:if>
                                   <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                               </h2>
                               <c:forEach items="${pb.sfOrderItems}" var="pbi">
                                   <div class="shangpin">
                                       <p class="photo">
                                           <a href="<%=path%>/static/html/xiangqing.html">
                                               <img src="${pbi.skuUrl}" alt="">
                                           </a>
                                       </p>
                                       <div>
                                           <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                           <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                       </div>
                                   </div> </c:forEach>
                               <p class="money">实收款：<span>￥${pb.receivableAmount}</span><span>发货方：<b>
                                   <c:if test="${pb.sendType==1}">平台发货</c:if>
                                   <c:if test="${pb.sendType==0 ||pb.sendType==null}">未选择</c:if>
                                   <c:if test="${pb.sendType==2}">自己发货</c:if></b></span></p>
                               <div class="ding">
                                   <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                   <p class="sh" onclick="shouhuorenxinxi('${pb.sfOrderConsignee.consignee}','${pb.sfOrderConsignee.provinceName} ${pb.sfOrderConsignee.cityName} ${pb.sfOrderConsignee.regionName} ${pb.sfOrderConsignee.address}','${pb.sfOrderConsignee.mobile}','${pb.sfOrderConsignee.zip}')">收货人信息</p></c:if>
                                   <c:if test="${pb.orderStatus ==7}">
                                       <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</button>
                                   </c:if>
                               </div>
                           </section></c:forEach>
                   </div>
                   <div class="all">
                       <c:forEach items="${sfOrders}" var="pb">
                           <section class="sec1">
                               <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                               <h2>
                                   订单号：<span>${pb.orderCode}</span>
                                   <c:if test="${pb.orderStatus ==0}"><b class="fahuo_${pb.id}" >待付款</b ></c:if>
                                   <c:if test="${pb.orderStatus ==7}"> <b class="fahuo_${pb.id}">待发货</b></c:if>
                                   <c:if test="${pb.orderStatus ==8}"><b class="fahuo_${pb.id}">待收货</b></c:if>
                                   <c:if test="${pb.orderStatus ==3}"><b class="fahuo_${pb.id}">交易成功</b></c:if>
                               </h2>
                               <c:forEach items="${pb.sfOrderItems}" var="pbi">
                                   <div class="shangpin">
                                       <p class="photo">
                                           <a href="<%=path%>/static/html/xiangqing.html">
                                               <img src="${pbi.skuUrl}" alt="">
                                           </a>
                                       </p>
                                       <div>
                                           <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                           <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                       </div>
                                   </div> </c:forEach>
                               <p class="money">实收款：<span>￥${pb.receivableAmount}</span><span>发货方：<b>
                                   <c:if test="${pb.sendType==1}">平台发货</c:if>
                                   <c:if test="${pb.sendType==0 ||pb.sendType==null}">未选择</c:if>
                                   <c:if test="${pb.sendType==2}">自己发货</c:if></b></span></p>
                               <div class="ding">
                                   <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.payStatus ==1}">
                                   <p class="sh" onclick="shouhuorenxinxi('${pb.sfOrderConsignee.consignee}','${pb.sfOrderConsignee.provinceName} ${pb.sfOrderConsignee.cityName} ${pb.sfOrderConsignee.regionName} ${pb.sfOrderConsignee.address}','${pb.sfOrderConsignee.mobile}','${pb.sfOrderConsignee.zip}')">收货人信息</p></c:if>
                                   <c:if test="${pb.orderStatus ==7}">
                                       <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}')">发货</button>
                                   </c:if>
                               </div>
                           </section></c:forEach>
                   </div>
                </main>
           </div>
       </div>
       <div class="back_que">
                    <p>确认减库存?</p>
                    <h4>快递公司:<select id="select"><option value="1">顺风</option><option value="2">EMS</option></select></h4>
                    <h4>快递单号:<input type="text" id="input"/></h4>
                    <h3 id="faHuo">发货</h3>
                </div>
                <div class="shouhuo">
                    <p>收货人信息</p>
                    <h4><span>姓　名:</span><span id="1"></span></h4>
                    <h4><span>地　址:</span><span id="2">阿斯科利的将阿</span></h4>
                    <h4><span>手机号:</span><span id="3"></span></h4>
                    <h4><span>邮　编:</span><span id="4"></span></h4>
                    <h3 class="close">关闭</h3>
                </div>
           <div class="back">
               
           </div>
       <script src="<%=path%>/static/shop/js/jquery-1.8.3.min.js"></script>
       <script src="<%=path%>/static/shop/js/commonAjax.js"></script>
       <script src="<%=path%>/static/shop/js/jinhuoshijian.js"></script>
       <script src="<%=path%>/static/shop/js/definedAlertWindow.js"></script>
       <script>
//            $("li").on("click",function(){
//                var index=$(this).index();
//                $("li").children("a").removeClass("on")
//                $(this).children("a").addClass("on");
//                $(".all").eq(index).show().siblings().hide();
//            })
            $(document).ready(function(){
                var index=${index};
                $("li").children("a").removeClass("on")
                $("li").eq(index).children("a").addClass("on");
                $(".all").eq(index).show().siblings().hide();
            });
            function shouhuorenxinxi(a,b,c,d){
                $(".back").css("display","-webkit-box");
                $(".shouhuo").css("display","-webkit-box");
                $("#1").html(a);
                $("#2").html(b);
                $("#3").html(c);
                $("#4").html(d);
            }

            $(function(){
                $("li").on("click",function(){
                    var index=$(this).index();
                    $("li").children("a").removeClass("on")
                    $(this).children("a").addClass("on");
                    $(".all").eq(index).show().siblings().hide();
                    $.ajax({
                        type:"POST",
                        url : "<%=path%>/sfOrderController/clickSfOrder.do",
                        data:{index:index},
                        dataType:"Json",
                        success:function(data){
                            var trHtml = "";
                            var StatusName="";
                            $.each(data, function(i, sfOrder) {
                                var time2 = new Date(sfOrder.createTime).Format("yyyy-MM-dd hh:mm");
                                trHtml+="<section class=\"sec1\">";
                                trHtml+="<p>时间: <span>"+time2 +"</span></p>";
                                if(sfOrder.orderStatus==0){
                                    StatusName="待付款";
                                }else if(sfOrder.orderStatus ==7){
                                    StatusName="待发货";
                                }else if(sfOrder.orderStatus ==8){
                                    StatusName="已发货";
                                }else if(sfOrder.orderStatus ==3){
                                    StatusName="交易成功";
                                }
                                trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class=\"fahuo_"+sfOrder.id+"\">"+StatusName+"</b ></h2>";
                                $.each(sfOrder.sfOrderItems, function(i, sfOrderItem) {
                                    trHtml+="<div class=\"shangpin\">";
                                    trHtml+=" <p class=\"photo\">";
                                    trHtml+="<a href=\"<%=path%>/static/html/xiangqing.html\">";
                                    trHtml+="<img src=\""+sfOrderItem.skuUrl+"\" alt=\"\"></a></p>";
                                    trHtml+="<div><h2>"+sfOrderItem.skuName+"<b>x"+sfOrderItem.quantity+"</b></h2><p class=\"defult\"><span style=\"float:none;color:#FF6A2A;\">￥"+sfOrderItem.unitPrice+"</span></p> </div> </div>";
                                });
                                trHtml+="<p class=\"money\">实收款：<span>￥"+sfOrder.receivableAmount+"</span><span>发货方：<b>";
                                if(sfOrder.sendType==1){
                                    trHtml+="平台发货"
                                }else if(sfOrder.sendType==0 ||sfOrder.sendType==null){
                                    trHtml+="未选择"
                                }else if(sfOrder.sendType==2){
                                    trHtml+="自己发货";
                                }
                                trHtml+="</b></span></p>";
                                trHtml+="<div class=\"ding\">";
                                trHtml+="<p><a href=\"<%=path%>/borderManage/deliveryBorderDetils.html?id="+sfOrder.id+"\">查看订单详情</a></p>";
                                if(sfOrder.payStatus ==1 ){
                                    if(sfOrder.sfOrderConsignee != null){
                                        trHtml+="<p class=\"sh\" onclick=\"shouhuorenxinxi('"
                                                + sfOrder.sfOrderConsignee.consignee+"','"
                                                + sfOrder.sfOrderConsignee.provinceName+""
                                                + sfOrder.sfOrderConsignee.cityName+""
                                                + sfOrder.sfOrderConsignee.regionName+""
                                                + sfOrder.sfOrderConsignee.address+"','"
                                                + sfOrder.sfOrderConsignee.mobile+"','"
                                                + sfOrder.sfOrderConsignee.zip+"')\">收货人信息</p>";
                                    }else{
                                        trHtml+="";
                                    }
                                }else{
                                    trHtml+="";
                                }
                                 if(sfOrder.orderStatus ==7){
                                    trHtml+="<button class=\"fa\" name=\"fahuo_"+sfOrder.id+"\" onclick=\"fahuo('"+sfOrder.id+"')\">发货</button>";
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
                        url : "<%=path%>/sfOrderController/deliverOrder.do",
                        data:{shipManName:shipManName,freight:freight,orderId:id,shipManId:shipManId},
                        dataType:"Json",
                        success:function(date){
                            $("button[name=" + aa + "]").attr("style", "display:none");
                            $("b." + aa + "").html("待收货");
                            location.reload(true);
                        }
                    })
                })
            }
//            $(".sh").on("click",function(){
//                $(".back").css("display","-webkit-box");
//                $(".shouhuo").css("display","-webkit-box");
//            })
            $(".close").on("click",function(){
                $(".shouhuo").hide();
                $(".back").hide();
            })
       </script>
</body>
</html>