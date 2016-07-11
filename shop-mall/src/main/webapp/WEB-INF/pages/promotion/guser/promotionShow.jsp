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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/receive.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="${path}/sfOrderManagerController/borderManagement.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>领取奖励</p>            
        </header>
        <banner>
            <img src="<%=path%>/static/images/receive.jpg" alt="">
            <p>
                达到指定粉丝数量即可获得奖品！
            </p>
        </banner>
        <nav>
            <p>${fansQuantity}</p>
            <h1>当前粉丝数</h1>
        </nav>
        <main>
            <c:forEach items="${promotionInfos}" var="promotionInfo">
                <h1>
                    <img src="<%=path%>/static/images/receive4.png" alt="">
                    活动事件： ${promotionInfo.beginTime}——${promotionInfo.endTime}
                </h1>
                <c:forEach items="${promotionInfo.ruleInfos}" var="promotionRule">
                    <c:forEach items="${promotionRule.giftInfos}" var="giftInfo"  varStatus="status">
                        <div class="floor">
                            <h1>${status.index+1}、粉丝达到<span class="people">${promotionRule.promotionFansQuantity}</span>人送 ${giftInfo.giftName}</h1>
                            <h2>(已领取<span>${giftInfo.sendedQuantity}</span>份，限领<span>${giftInfo.maxQuantity}</span>份)</h2>
                            <p onclick="clickShow()">
                                <img src="${giftInfo.giftImageUrl}" alt="">
                            </p>
                            <h3>${giftInfo.giftName}</h3>
                           <c:if test="${promotionRule.status==0}">
                                <button class="on" disabled>还差${promotionRule.needFansQuantity}人</button>
                            </c:if>
                            <c:if test="${promotionRule.status==1}">
                                <button onclick="skipPromotionGorderPage(${promotionInfo.promoId},${promotionRule.promoRuleId})">点击领取</button>
                            </c:if>
                            <c:if test="${promotionRule.status==2}">
                                <button>已领取</button>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:forEach>
            </c:forEach>
        </main>
        <p>
            注：以单个店铺的粉丝数量为计算方法
        </p>
    </div>
</div>
   <script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script>
        function skipPromotionGorderPage(promoId,promoRuleId){
            window.location.href="<%=path%>/promotionGorder/getPromotionGorderPageInfo.html?promoId="+promoId+"&promoRuleId="+promoRuleId;
        }
    </script>
</body>
</html>