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
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/chakanxiangqing.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
       <div class="wrap">
          <header class="xq_header">
                   <a href="<%=basePath%>border/deliveryBorder"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>订单详情</p>  
                </header>
           <main>
               <div id="box">
                   <div class="sec1"><c:if test="${borderDetail.pfBorder.payStatus==1 &&borderDetail.pfBorder.shipStatus==0}">
                       <h1>未发货</h1>
                       <p>亲，请及时发货~~</p></c:if><c:if test="${borderDetail.pfBorder.payStatus==0 }"> <h1>未付款</h1>
                       <p>亲，订单还未处理~~</p></c:if><c:if test="${borderDetail.pfBorder.payStatus==1 &&borderDetail.pfBorder.shipStatus==5}"> <h1>未收货</h1>
                       <p>亲，订单还未完结~~</p></c:if><c:if test="${borderDetail.pfBorder.orderStatus==3 &&borderDetail.pfBorder.shipStatus==9}"> <h1>已完成</h1>
                       <p>亲，交易成功~~</p></c:if>
                   </div>
                   <div class="sec2">
                       <p><span>订单编号：</span><span>${borderDetail.pfBorder.orderCode}</span></p>
                       <p><span>订单状态：</span><span>${borderDetail.pfBorder.orderSkuStatus}</span></p>
                       <p><span>下单日期：</span><span><fmt:formatDate value="${borderDetail.pfBorder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                       <p><span>支付日期：</span><span><fmt:formatDate value="${borderDetail.pfBorder.payTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                       <p><span>支付类型：</span><span>微信支付</span></p>
                       <p><span>物流状态：</span><c:if test="${borderDetail.pfBorder.orderStatus==3 &&borderDetail.pfBorder.shipStatus==9}"><span>已完成</span></c:if><c:if test="${borderDetail.pfBorder.payStatus==1 &&borderDetail.pfBorder.shipStatus==5}"><span>已发货</span></c:if><c:if test="${borderDetail.pfBorder.payStatus==1 &&borderDetail.pfBorder.shipStatus==0}"><span>未发货</span><a class="fah">发货</a></c:if></p>
                       <p><span>配送方式：</span><span>物流配送</span></p>
                       <p><span>发货时间：</span><span><fmt:formatDate value="${borderDetail.pfBorder.shipTime}" pattern="yyyy-MM-dd HH:mm"/></span></p><c:forEach items="${borderDetail.pfBorderFreights}" var="bpf"><c:if test="${bpf.freight!=null}">
                       <p><span>发货单号：</span><span>${bpf.freight}</span></p></c:if></c:forEach>
                   </div>
                   <div class="sec3">
                       <section class="dizhi">
                           <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                           <div>
                                <a href="#"><h2>收货人：<b>${borderDetail.pfBorderConsignee.consignee}</b> <span>${borderDetail.pfBorderConsignee.mobile}</span></h2></a>
                                <a href="#"><p>收货地址： <span>${borderDetail.pfBorderConsignee.provinceName} ${borderDetail.pfBorderConsignee.cityName} ${borderDetail.pfBorderConsignee.regionName} ${borderDetail.pfBorderConsignee.address}</span></p></a>
                            </div>
                        </section>
                        <p>购物人：<span>${borderDetail.buyerName}</span></p>
                        <p>备注：<span>${borderDetail.pfBorder.userMessage}</span></p>
                   </div>
                   <div class="sec4"><c:forEach items="${borderDetail.pfBorderItems}" var="bdpd">
                       <p><span>订单商品：</span><span>${bdpd.skuName}</span></p>
                       <p><span>购买数量：</span><span>${bdpd.quantity}瓶</span></p>
                       <p><span>商品单价：</span><span>${bdpd.unitPrice}</span></p></c:forEach>
                       <p><span>运费：</span><span>${borderDetail.pfBorder.shipAmount}</span></p>
                       <p><span>商品总金额：</span><span>${borderDetail.pfBorder.productAmount}</span></p>
                       <p><span>实付金额：</span><span>${borderDetail.pfBorder.payAmount}</span></p>
                       <p><span>订单状态：</span><c:if test="${borderDetail.pfBorder.orderStatus==0}"><span>未处理</span></c:if><c:if test="${borderDetail.pfBorder.orderStatus==1}"><span>已付款</span></c:if><c:if test="${borderDetail.pfBorder.orderStatus==3}"><span>成功交易</span></c:if></p>
                   </div>
               </div>
           </main>
           <div class="back_que">
               <p>确认发货?</p>
               <h4>快递公司:<select id="select"><option>顺风</option><option >EMS</option></select></h4>
               <h4>快递单号:<input type="text" id="input"/></h4>
               <h3 id="faHuo">发货</h3>
           </div>
           <div class="back">

           </div>
        </div>
        <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
        <script>
        var myScroll = new IScroll("main",{
                 preventDefault: false
            })

            $(".fah").on("click",function(){
                $(".back").show();
                $(".back_que").css("display","-webkit-box");
            })

            $("#faHuo").on("click",function(){
                $(".back_que").hide();
                $(".back").hide();
                var shipManName = $("#select").val();
                var freight = $("#input").val();
                var borderId = ${borderDetail.pfBorder.id};
                $.ajax({
                    type:"POST",
                    url : "<%=path%>/border/deliver.do",
                    data:{shipManName:shipManName,freight:freight,orderId:borderId},
                    dataType:"Json",
                    success:function(date){
                        if(!date.msgs){
                            alert(date.msg);
                        }else{
                            $(".fah").html("");
                            location.reload(true);
                        }
                    }
                })
            })
            $(".close").on("click",function(){
                $(".back_que").hide();
                $(".back").hide();
            })
            $(".back").on("click",function(){
                $(".back_que").hide();
                $(".back").hide();
            })
    </script>
</body>
</html>