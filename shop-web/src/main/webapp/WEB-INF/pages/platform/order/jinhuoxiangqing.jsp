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
    <link rel="stylesheet" href="<%=path%>/static/css/jinhuoxiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
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
                <c:if test="${borderDetail.pfBorder.orderStatus==0}">
                    <img src="<%=path%>/static/images/icon_65.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;"><h1>未付款</h1>
                    <p>亲，未付款的订单可以保留7天~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus==7}">
                    <img src="<%=path%>/static/images/icon_40.png" alt=""  style="display: block;width: 35px;height: 25px;top: 18px;"><h1>待发货</h1>
                    <p>亲，卖家会很快发货~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.payStatus==8 && borderDetail.pfBorder.sendType ==2}">
                    <img src="<%=path%>/static/images/icon_68.png" alt="" style="display: block;width: 48px;height: 25px;top: 18px;"><h1>已发货</h1>
                    <p>亲，卖家已发货~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus==3 }">
                    <img src="<%=path%>/static/images/icon_64.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;"><h1>已完成</h1>
                    <p>亲，交易完成~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus==6 && borderDetail.pfBorder.sendType!=2}">
                    <img src="<%=path%>/static/images/icon_64.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;"><h1>排单中</h1>
                    <p>亲，您的订单排单中~~</p>
                </c:if>
                <c:if test="${borderDetail.pfBorder.orderStatus==9}">
                    <img src="<%=path%>/static/images/icon_65.png" alt="" style="display: block;width: 40px;height: 30px;top: 18px;"><h1>排单中</h1>
                    <p>亲，您选择的是线下支付，请及时付款~~</p>
                </c:if>
            </div>
            <div class="kuaidi">
                <p>拿货方式：<span><c:if test="${borderDetail.pfBorder.sendType==0}">未选择</c:if><c:if test="${borderDetail.pfBorder.sendType==1}">平台代发</c:if><c:if test="${borderDetail.pfBorder.sendType==2}">自己发货</c:if></span></p>
                <p>类    型：<span><c:if test="${borderDetail.pfBorder.orderType==0}">合伙人订单</c:if><c:if test="${borderDetail.pfBorder.orderType==1}">补货</c:if><c:if test="${borderDetail.pfBorder.sendType==1 && borderDetail.pfBorder.orderType==2}">申请拿货</c:if></span></p>
                <c:if test="${borderDetail.pfBorder.sendType!=1 || borderDetail.pfBorder.orderType==2}">
                ${stringBuffer}
                </c:if>
            </div>
           <%--<div class="paidan"><c:if test="${borderDetail.pfBorder.sendType==1 && borderDetail.pfBorder.orderType!=2 && borderDetail.pfBorder.orderStatus!=6}">--%>
               <%--<h1><img src="<%=path%>/static/images/kucun.png" alt=""><b>平台在线库存增加<span>${borderDetail.pfBorder.totalQuantity}</span>件</b></h1>--%>
               <%--<p>当前平台在线库存量为<span>${stockNum}</span>件</p></c:if>--%>
               <%--<c:if test="${borderDetail.pfBorder.sendType==1 && borderDetail.pfBorder.orderType!=2 && borderDetail.pfBorder.orderStatus==6}">--%>
           <%--</div>--%>
           <c:if test="${borderDetail.pfBorder.sendType==2 || borderDetail.pfBorder.orderType==2 && borderDetail.pfBorder.sendType==1}">
            <section class="sec1">
                       <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                       <div>
                            <a href="#"><h2>收货人：<b>${borderDetail.pfBorderConsignee.consignee}</b> <span>${borderDetail.pfBorderConsignee.mobile}</span></h2></a>
                            <a href="#"><p>收货地址： <span>${borderDetail.pfBorderConsignee.provinceName} ${borderDetail.pfBorderConsignee.cityName} ${borderDetail.pfBorderConsignee.regionName} ${borderDetail.pfBorderConsignee.address}</span><%--<img src="<%=path%>/static/images/next.png" alt="">--%></p></a>
                       </div>
            </section></c:if>
           <c:forEach items="${borderDetail.pfBorderItems}" var="bdpi">
            <section class="sec2">
                <p class="photo">
                   <a href="<%=path%>/static/html/xiangqing.html">
                        <img src="${bdpi.skuUrl}" alt="">
                    </a>
                </p>
                <div>
                    <h2>${bdpi.skuName}<b>x${bdpi.quantity}</b></h2>
                    <h3>规格：<span>默认</span></h3>
                    <c:if test="${borderDetail.pfBorder.orderType!=2}">
                    <p> 价格：<span>￥${bdpi.unitPrice}</span></p>
                    </c:if>
                    <%--<h1><b style="color:#333333">合计：</b><span>￥${bdpi.totalPrice}</span></h1>--%>
                </div>
            </section></c:forEach>
            <section class="sec3">
                <p>留言： <span>${borderDetail.pfBorder.userMessage}</span></p>
            </section>
           <section class="sec4">
               <c:if test="${borderDetail.pfBorder.orderType==2}">
                   <p>运费：<span>到付</span></p>
                   <p>实付：<span>￥0.00</span></p>
               </c:if>
               <c:if test="${borderDetail.pfBorder.orderType!=2}">
               <p>商品合计：<span>￥${borderDetail.pfBorder.productAmount}</span></p>
               <c:if test="${borderDetail.pfBorder.sendType==1 && borderDetail.pfBorder.orderType==2 || borderDetail.pfBorder.sendType==2}">
                   <p>运费：<span>到付</span></p>
               </c:if>
               <h1>共<b>${borderDetail.pfBorder.totalQuantity}</b>件商品　
                   <c:if test="${borderDetail.pfBorder.sendType==1 && borderDetail.pfBorder.orderType==2 || borderDetail.pfBorder.sendType==2}">
                       运费：<span>到付</span>
                   </c:if>　
                   <b style="color:#333333">合计：</b><span>￥${borderDetail.pfBorder.orderAmount}</span></h1>
               </c:if>
           </section>
            <div class="sec5">
                <p>订单编号：<span>${borderDetail.pfBorder.orderCode}</span></p>
                <p>创建时间：<span><fmt:formatDate value="${borderDetail.pfBorder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                <c:if test="${borderDetail.pfBorder.orderType!=2}">
                <p>付款时间：<span><fmt:formatDate value="${borderDetail.pfBorder.payTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
                </c:if>
                <p>发货时间：<span><fmt:formatDate value="${borderDetail.pfBorder.shipTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
            </div><c:if test="${borderDetail.pfBorder.orderStatus==1 && borderDetail.pfBorder.shipStatus==5}">
            <botton class="btn" onclick="querenshouhuo('${borderDetail.pfBorder.id}','${borderDetail.pfBorder.orderStatus}','${borderDetail.pfBorder.shipStatus}') ">
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
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/commonAjax.js"></script>
    <script src="<%=path%>/static/js/definedAlertWindow.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="<%=path%>/static/js/hideWXShare.js"></script>
    <script>
//        var myScroll = new IScroll("main",{
//            preventDefault: false
//        })


//        $(".btn").on("click",function(){
//            $(".back").css("display","-webkit-box");
//            $(".back_que").css("display","-webkit-box");
//        })
        function querenshouhuo(id,orderStatus,shipStatus){
            $(".back").css("display","-webkit-box");
            $(".back_que").css("display","-webkit-box");
            $(".que_que").on("click",function(){
                $(".back_que").hide();
                $(".back").hide();
                $.ajax({
                    type:"POST",
                    url : "<%=path%>/borderManage/closeDeal.do",
                    data:{orderStatus:3,orderId:id,shipStatus:9},
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