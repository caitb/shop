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
                  <a href="javascript:window.history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>获取奖励订单</p>
        </header>
       <div class="sel">
           <div>
               <span>商品：</span>
               <label for="goods" class="goods">
                   <b></b>
                   <select id="goods" class="myValue">
                       <option value="">全部</option>
                       <c:forEach items="${agentSkus}" var="sku">
                           <option value="${sku.id}" <c:if test="${sku.id == skuId}">selected</c:if> >${sku.name}</option>
                       </c:forEach>
                   </select>
               </label>
           </div>
       </div>
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
                        <c:forEach items="${bOrderTypes}" var="orderType">
                            <c:if test="${orderType.code == pfBorders.orderType}"><span>${orderType.desc}</span></c:if>
                        </c:forEach>
                    </p>
                    <p>购买人：<b onclick="blackShow('${pfBorders.userName.realName}','${pfBorders.userName.wxId}','${pfBorders.userName.mobile}','1')">${pfBorders.userName.realName}</b></p>
                    <p>合计：￥${pfBorders.orderAmount}</p>
                </div>
                <div class="f_1">
                    <p>推荐人：<b onclick="blackShow('${pfBorders.userPname.realName}','${pfBorders.userPname.wxId}','${pfBorders.userPname.mobile}','2')">${pfBorders.userPname.realName}</b></p>
                    <p>获得奖励：￥${pfBorders.recommenAmount}</p>
                </div>
            </div>
            </c:forEach>
        </main>
    </div>
    <div class="black">
        <div class="backb"></div>
        <div class="back_x">
            <h1 id="xin"></h1>
            <p><span>姓　名：</span><span id="1"></span></p>
            <p><span>微信号：</span><span id="2"></span></p>
            <p><span>手机号：</span><span id="3"></span></p>
            <button onclick="blackHide()">知道了</button>
        </div>
    </div>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script>
        $(document).ready(function(){
            var goodsWidth=$(".goods").width();
            $(".goods b").html($("#goods option:selected").text());
            $("#goods").width(goodsWidth);
        })
        $("#goods").on("change",function(){
            var skuId=$("#goods option:selected").val();
            window.location.href = skuId ? '<%=basePath%>myRecommend/getRewardBorder?skuId='+skuId : '<%=basePath%>myRecommend/getRewardBorder';
        })

        function blackShow(a,b,c,d){
            $(".black").show();
            if(d==1){
                $("#xin").html("购买人信息")
            }
            if(d==2){
                $("#xin").html("推荐人")
            }
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