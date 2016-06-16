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
    <link rel="stylesheet" href="<%=path%>/static/css/fachujianglidingdan.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>发出奖励订单</p>            
        </header>
        <main>
            <c:forEach items="${pfBorders}" var="pfBorders">
            <div class="floor">
                <div class="time">
                    <p>订单号：${pfBorders.orderCode}</p>
                    <p>时间：<fmt:formatDate value="${pfBorders.createTime}" pattern="yyyy-MM-dd HH:mm" /></p>
                </div>
                <c:forEach items="${pfBorders.pfBorderItems}" var="pbi">
                <div class="sec1">
                    <img src="${pbi.skuUrl}" alt="">
                    <div class="s_1">
                        <h1>
                            <span>${pbi.skuName}</span>
                            <span>x<b>${pbi.quantity}</b></span>
                        </h1>
                        <p>￥${pbi.unitPrice}</p>
                    </div>
                </div>
                </c:forEach>
                <div class="f_1">
                    <p>类型：
                        <c:if test="${pfBorders.orderType==2 && pfBorders.sendType==1}">申请拿货</c:if>
                        <c:if test="${pfBorders.orderType==0}">下级合伙订单</c:if>
                        <c:if test="${pfBorders.orderType==1}">下级补货</c:if></p>
                    <p>购买人：<b onclick="blackShow('${pfBorders.userName.realName}','${pfBorders.userName.wxId}','${pfBorders.userName.mobile}')">${pfBorders.userName.realName}</b></p>
                    <p>合计：￥${pfBorders.orderAmount}</p>
                </div>
                <div class="f_1">
                    <p>上级：<b onclick="blackShow('${pfBorders.userPname.realName}','${pfBorders.userPname.wxId}','${pfBorders.userPname.mobile}')">${pfBorders.userPname.realName}</b></p>
                    <p>获得奖励：￥${pfBorders.recommenAmount}</p>
                </div>
            </div>
            </c:forEach>
        </main>
    </div>
    <div class="black">
        <div class="backb"></div>
        <div class="back_x">
            <h1>购买人信息</h1>
            <p><span>姓　名：</span><span id="1"></span></p>
            <p><span>微信号：</span><span id="2"></span></p>
            <p><span>手机号：</span><span id="3"></span></p>
            <button onclick="blackHide()">知道了</button>
        </div>
    </div>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script>
        function blackShow(a,b,c){
            $(".black").show();
            $("#1").html(a);
            $("#2").html(b);
            $("#3").html(c);
        }
        function blackHide(){
            $(".black").hide();
        }
    </script>
</body>
</html>