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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/jinhuoxiangqing.css">
    <%--<link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">--%>
    <%--<link rel="stylesheet" href="<%=path%>/static/css/pageCss/loading.css">--%>
    <%--<script src="<%=path%>/static/js/iscroll.js"></script>--%>
</head>
<body>
   
    <div class="wrap">
       <main>
            <header class="xq_header">
                <a href="<%= request.getHeader("REFERER") %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>订单详情</p>
                <%--javascript:window.history.go(-1);--%>
            </header>
            <div class="tai">
                <c:if test="${orderMallDetail.sfOrder.orderStatus==0}">
                    <img src="<%=path%>/static/images/icon_65.png" alt="" style="display: block;width: 40px;height: 36px;"><h1>未付款</h1>
                    <p>亲，未付款的订单可以保留7天~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==7}">
                    <img src="<%=path%>/static/images/icon_40.png" alt=""  style="display: block;width: 50px;height: 36px;top: 14px;"><h1>待发货</h1>
                    <p>亲，卖家会很快发货~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==8}">
                    <img src="<%=path%>/static/images/icon_68.png" alt="" style="display: block;width: 47px;height: 30px;top: 14px;left: 7px;"><h1>已发货</h1>
                    <p>亲，卖家已发货~~</p>
                </c:if>
                <c:if test="${orderMallDetail.sfOrder.orderStatus==3 }">
                    <img src="<%=path%>/static/images/icon_64.png" alt="" style="display: block;width: 47px;height: 49px;top: 9px;"><h1>已完成</h1>
                    <p>亲，交易完成~~</p>
                </c:if>
            </div>
            <div class="kuaidi">
                <p>拿货方式：<span><c:if test="${orderMallDetail.sfOrder.sendType==0}">未选择</c:if><c:if test="${orderMallDetail.sfOrder.sendType==1}">平台代发</c:if><c:if test="${orderMallDetail.sfOrder.sendType==2}">自己发货</c:if></span></p>
                <p>类    型：<span><c:if test="${orderMallDetail.sfOrder.orderType==0}">合伙人订单</c:if><c:if test="${orderMallDetail.sfOrder.orderType==1}">补货</c:if><c:if test="${orderMallDetail.sfOrder.sendType==1 && orderMallDetail.sfOrder.orderType==2}">申请拿货</c:if></span></p>
                ${stringBuffer}
            </div>
            <section class="sec1">
               <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
               <div>
                    <a href="#"><h2>收货人：<b>${orderMallDetail.sfOrderConsignee.consignee}</b> <span>${orderMallDetail.sfOrderConsignee.mobile}</span></h2></a>
                    <a href="#"><p>收货地址： <span>${orderMallDetail.sfOrderConsignee.provinceName} ${orderMallDetail.sfOrderConsignee.cityName} ${orderMallDetail.sfOrderConsignee.regionName} ${orderMallDetail.sfOrderConsignee.address}</span></p></a>
                </div>
            </section>
           <c:forEach items="${orderMallDetail.sfOrderItems}" var="bdpi">
            <section class="sec2">
                <p class="photo">
                   <a href="<%=path%>/static/html/xiangqing.html">
                        <img src="${bdpi.skuUrl}" alt="">
                    </a>
                </p>
                <div>
                    <h2>${bdpi.skuName}</h2>
                    <h3>规格：<span>默认</span></h3>
                    <p> 价格： <span>${bdpi.unitPrice}</span><b>x${bdpi.quantity}</b></p>
                    <%--<h1><b style="color:#333333">合计：</b><span>￥${bdpi.totalPrice}</span></h1>--%>
                </div>
            </section></c:forEach>
            <section class="sec3">
                <p>留言： <input readonly="readonly" type="text" value="${orderMallDetail.sfOrder.userMessage}"></p>
            </section>
            <section class="sec4">
                <p>商品合计：<span>￥${orderMallDetail.sfOrder.productAmount}</span></p>
                <p>运费：<span>￥${orderMallDetail.sfOrder.shipAmount}</span></p>
                <h1>共<b>${orderMallDetail.sfOrder.totalQuantity}</b>件商品　<b style="color:#333333">合计：</b><span>￥${orderMallDetail.sfOrder.orderAmount}</span></h1>
            </section>
            <div class="sec5">
                <p>订单编号：<span>${orderMallDetail.sfOrder.orderCode}</span></p>
                <p>创建时间：<span><fmt:formatDate value="${orderMallDetail.sfOrder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <p>付款时间：<span><fmt:formatDate value="${orderMallDetail.sfOrder.payTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <p>发货时间：<span><fmt:formatDate value="${orderMallDetail.sfOrder.shipTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
            </div><c:if test="${orderMallDetail.sfOrder.orderStatus==8}">
            <botton class="btn" onclick="querenshouhuo('${orderMallDetail.sfOrder.id}')">
                确认收货
            </botton></c:if>
            <h3></h3>
        </main>
        <div class="back">
                <div class="back_que">
                    <p>确认收货?</p>
                    <h4>亲，请您核对商品后在操作确认收货</h4>

                    <h3>
                        <span class="que_qu">取消</span>
                        <span class="que_que">确认</span>
                    </h3>
                </div>
    </div>
    </div>
    <script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
    <%--<script src="<%=path%>/static/js/common/commonAjax.js"></script>--%>
    <script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
    <script>
//        var myScroll = new IScroll("main",{
//            preventDefault: false
//        })


//        $(".btn").on("click",function(){
//            $(".back").css("display","-webkit-box");
//            $(".back_que").css("display","-webkit-box");
//        })
        function querenshouhuo(id){
            $(".back").css("display","-webkit-box");
            $(".back_que").css("display","-webkit-box");
            $(".que_que").on("click",function(){
                $(".back_que").hide();
                $(".back").hide();
                $.ajax({
                    type:"POST",
                    url : "<%=path%>/sfOrderManagerController/deliverSfOrder.do",
                    data:{orderId:id},
                    dataType:"Json",
                    success:function(date){
                        $(".btn").html("已完成");
                    }
                })
            })
        }
        $(".que_qu").on("click",function(){
            $(".back_shouhuo").hide();
            $(".back").hide();
        })
    </script>
</body>
</html>