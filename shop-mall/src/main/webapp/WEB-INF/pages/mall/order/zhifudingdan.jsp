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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/zhifudingdan.css">
</head>
<body>
    <header>
              <a onclick="returnPage()">
                  <img src="<%=path%>/static/images/xq_rt.png" alt="">
              </a>
                <p>付款详情</p>            
        </header>
        <div class="wrap">
                   <main>
                       <input id="orderCodeId" value="${order.orderCode}" style="display: none">
                       <input id="orderId" value="${order.id}" style="display: none">
                       <c:forEach items="${orderItems}" var="orderItem">
                        <p>
                            商品信息：<span>${orderItem.skuName}</span>
                        </p>
                       </c:forEach>
                       <p>需付款　：<span>￥${order.receivableAmount}</span></p>
                    </main>
                   <button id="wxBtn">微信支付</button>
        </div>
</body>
<script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/common/wxpay.js"></script>
<script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
<script>
    /*function callWeChatPay(){
        var orderCode = $("#orderCodeId").val();
        var orderId = $("#orderId").val();
        window.location.href = "<%=path%>/orderPay/callWechatPay.do?orderCode="+orderCode+"&orderId="+orderId;
    }*/
    $(function(){
        $("#wxBtn").initWxPay("${paramReq}", "<%=basePath%>");
    });
    function returnPage(){
        window.location.href = "<%=path%>/sfOrderManagerController/stockOrder?orderStatus=0";
    }
</script>
</html>