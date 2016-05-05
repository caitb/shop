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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/wodedingdan.css">
    <%--<link rel="stylesheet" href="<%=path%>/static/css/pageCss/dingdan.css">--%>
    <%--<link rel="stylesheet" href="<%=path%>/static/css/pageCss/loading.css">--%>
    <%--<link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">--%>

</head>
<body>
       <div class="wrap">
           <div class="box">
                <header class="xq_header">
                   <a href="<%=path%>/sfOrderManagerController/borderManagement.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
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
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                           <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                   <a href="javascript:void(0);">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}</h2>
                                    <p class="defult"><span style="float:none;color:#f73c8c;">￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}件商品 合计：￥${pb.orderAmount} （含运费￥${pb.shipAmount}）</h1>
                            <div class="ding">
                                <p><a href="<%=path%>/sfOrderManagerController/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                <p>
                                    <c:if test="${pb.orderStatus ==8}"><button id="querenshouhuo_${pb.id}" onclick="querenshouhuo('${pb.id}')">确认收货</button></c:if>
                                    <c:if test="${pb.orderStatus ==0}"><button onclick="javascript:window.location.replace('<%=path%>/orderPay/getOrderInfo.html?orderId=${pb.id}');">继续支付</button></c:if>
                                </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="javascript:void(0);">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}</h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}件商品 合计：￥${pb.orderAmount} （含运费￥${pb.shipAmount}）</h1>
                            <div class="ding">
                                <p><a href="<%=path%>/sfOrderManagerController/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                    <p>
                                        <c:if test="${pb.orderStatus ==8}"><button id="querenshouhuo_${pb.id}" onclick="querenshouhuo('${pb.id}')">确认收货</button></c:if>
                                        <c:if test="${pb.orderStatus ==0}"><button onclick="javascript:window.location.replace('<%=path%>/orderPay/getOrderInfo.html?orderId=${pb.id}');">继续支付</button></c:if>
                                    </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="javascript:void(0);">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}</h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}件商品 合计：￥${pb.orderAmount} （含运费￥${pb.shipAmount}）</h1>
                            <div class="ding">
                                <p><a href="<%=path%>/sfOrderManagerController/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                    <p>
                                        <c:if test="${pb.orderStatus ==8}"><button id="querenshouhuo_${pb.id}" onclick="querenshouhuo('${pb.id}')">确认收货</button></c:if>
                                        <c:if test="${pb.orderStatus ==0}"><button onclick="javascript:window.location.replace('<%=path%>/orderPay/getOrderInfo.html?orderId=${pb.id}');">继续支付</button></c:if>
                                    </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="javascript:void(0);">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}</h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}件商品 合计：￥${pb.orderAmount} （含运费￥${pb.shipAmount}）</h1>
                            <div class="ding">
                                <p><a href="<%=path%>/sfOrderManagerController/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                    <p>
                                        <c:if test="${pb.orderStatus ==8}"><button id="querenshouhuo_${pb.id}" onclick="querenshouhuo('${pb.id}')">确认收货</button></c:if>
                                        <c:if test="${pb.orderStatus ==0}"><button onclick="javascript:window.location.replace('<%=path%>/orderPay/getOrderInfo.html?orderId=${pb.id}');">继续支付</button></c:if>
                                    </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">待收货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="javascript:void(0);">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}</h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span><b>x${pbi.quantity}</b></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}件商品 合计：￥${pb.orderAmount} （含运费￥${pb.shipAmount}）</h1>
                            <div class="ding">
                                <p><a href="<%=path%>/sfOrderManagerController/borderDetils.html?id=${pb.id}">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                    <p>
                                        <c:if test="${pb.orderStatus ==8}"><button id="querenshouhuo_${pb.id}" onclick="querenshouhuo('${pb.id}')">确认收货</button></c:if>
                                        <c:if test="${pb.orderStatus ==0}"><button onclick="javascript:window.location.replace('<%=path%>/orderPay/getOrderInfo.html?orderId=${pb.id}');">继续支付</button></c:if>
                                    </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                </main>
           </div>
       </div>
        <div class="back_que" style="display: none">
                    <p>确认减库存?</p>
                    <h4>快递公司:<span><select><option>顺风</option></select></span></h4>
                    <h4>快递单号:<span><input type="text"/></span></h4>
                    <h3>发货</h3>
        </div>
        <div class="shouhuo" style="display: none">
            <p>收货人信息</p>
            <h4><span>姓　名:</span><span></span></h4>
            <h4><span>地　址:</span><span>阿斯科利的asdasdasdasdas将阿</span></h4>
            <h4><span>手机号:</span><span></span></h4>
            <h4><span>邮　编:</span><span></span></h4>
            <h3 class="close">关闭</h3>
        </div>
        <div class="back_shouhuo" style="display: none">
           <p>确认收到货品?</p>
           <h4>亲，请您核对商品后再操作确认收货</h4>

           <h3>
               <span class="que_qu">取消</span>
               <span class="que_que">确认</span>
           </h3>
        </div>
           <div class="back" style="display: none">
               
           </div>
       <script src="<%=path%>/static/js/plugins/jquery/jquery-1.8.3.min.js"></script>
       <%--<script src="<%=path%>/static/js/common/commonAjax.js"></script>--%>
       <script src="<%=path%>/static/js/pageJs/jinhuoshijian.js"></script>
       <script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
       <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
       <script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
       <script>
           $(function(){
               $("li").on("click",function(){
                   var index=$(this).index();
                   $("li").children("a").removeClass("on")
                   $(this).children("a").addClass("on");
                   $(".all").eq(index).show().siblings().hide();
                   $.ajax({
                       type:"POST",
                       url : "<%=path%>/sfOrderManagerController/clickSfOrderType.do",
                       data:{index:index},
                       dataType:"Json",
                       success:function(data){
                           var trHtml = "";
                           $.each(data, function(i, sfOrder) {
//                               alert(data);
                               var time2 = new Date(sfOrder.createTime).Format("yyyy-MM-dd hh:mm");
                               trHtml+="<section class='sec1'>";
                               trHtml+="<p>时间: <span>"+time2 +"</span></p>";
                               if(sfOrder.orderStatus==0){
                                   trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class='querenshouhuo_"+sfOrder.id+"' >待付款</b ></h2>";
                               }else if(sfOrder.orderStatus ==7){
                                   trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class='querenshouhuo_"+sfOrder.id+"' >待发货</b ></h2>";
                               }else if(sfOrder.orderStatus ==8){
                                   trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class='querenshouhuo_"+sfOrder.id+"' >待收货</b ></h2>";
                               }else if(sfOrder.orderStatus ==8){
                                   trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class='querenshouhuo_"+sfOrder.id+"' >交易成功</b ></h2>";
                               }
                               $.each(sfOrder.sfOrderItems, function(i, sfOrderItem) {
                                   trHtml+="<div class=\"shangpin\">";
                                   trHtml+=" <p class=\"photo\">";
                                   trHtml+="<a href=\"javascript:void(0);\">";
                                   trHtml+="<img src=\""+sfOrderItem.skuUrl+"\" alt=\"\"></a></p>";
                                   trHtml+="<div><h2>"+sfOrderItem.skuName+"</h2><p class=\"defult\"><span style=\"float:none;color:#f73c8c;\">￥"+sfOrderItem.unitPrice+"</span><b>x"+sfOrderItem.quantity+"</b></p></div></div>";
                               })
                               trHtml+=" <h1>共"+sfOrder.totalQuantity+"件商品 合计：￥"+sfOrder.orderAmount+" （含运费￥"+sfOrder.shipAmount+"）</h1>";
                               trHtml+="<div class=\"ding\"><p><a href=\"<%=path%>/sfOrderManagerController/borderDetils.html?id="+sfOrder.id+"\">查看订单详情</a></p>";
                               if(sfOrder.orderStatus ==8 ||sfOrder.orderStatus ==0){
                                   trHtml+="<p>";
                                   if(sfOrder.orderStatus ==8 ){
                                       trHtml+="<button id=\"querenshouhuo_"+sfOrder.id+"\" onclick=\"querenshouhuo('"+sfOrder.id+"')\">确认收货</button></p>";
                                   }
                                   if(sfOrder.orderStatus ==0 ){
                                       trHtml+="<button onclick=\"javascript:window.location.replace('<%=path%>/orderPay/getOrderInfo.html?orderId="+sfOrder.id+"');\">继续支付</button>";
                                   }
                               }
                               trHtml+="</div></section>";
                           });
                           $(".all").eq(index).html(trHtml);
                       }
                   })
               });
           })

            $(document).ready(function(){
                var index=${index};
                $("li").children("a").removeClass("on")
                $("li").eq(index).children("a").addClass("on");
                $(".all").eq(index).show().siblings().hide();
            });
            var oid = "";
            function querenshouhuo(id){
                $(".back").css("display","-webkit-box");
                $(".back_shouhuo").css("display","-webkit-box");
                oid = id;
            }

           $(function(){
               $(".que_que").on("click",function(){
                   $(".back_shouhuo").hide();
                   $(".back").hide();
                   var aa="#querenshouhuo_"+oid;
                   var bb=".querenshouhuo_"+oid;
                   $.ajax({
                       type:"POST",
                       url : "<%=path%>/sfOrderManagerController/deliverSfOrder.do",
                       data:{orderId:oid},
                       dataType:"Json",
                       success:function(date){
                           $(""+aa+"").css("display","none");
                           $(""+bb+"").html("交易成功");
                           location.reload(true);
                       }
                   });
               });
           });


            $(".que_qu").on("click",function(){
                $(".back_shouhuo").hide();
                $(".back").hide();
            })
            $(".sh").on("click",function(){
                $(".back").css("display","-webkit-box");
                $(".shouhuo").css("display","-webkit-box");
            })
            $(".close").on("click",function(){
                $(".shouhuo").hide();
                $(".back").hide();
            });
       </script>
</body>
</html>