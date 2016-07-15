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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/prize.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="${path}/showPromotion/getAllPromoDetail.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>奖品详情</p>            
        </header>
        <banner>
            <img src="${promotionGiftInfo.giftImageUrl}" alt="">
            <h1>${promotionGiftInfo.giftName}
                <c:if test="${isMayReceive==1}">
                    <button onclick="skipPromotionGorderPage(${promoId},${promoRuleId})">点击领取</button>
                </c:if>
            </h1>
        </banner>
        <main>
            <h1>
                <span></span>
                <b>奖品描述</b>
                <span>${promotionGiftInfo.content}</span>
            </h1>
            <p></p>
       </main>
    </div>
   <script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script>
        function skipPromotionGorderPage(promoId,promoRuleId){
            window.location.href="<%=path%>/promotionGorder/getPromotionGorderPageInfo.html?promoId="+promoId+"&promoRuleId="+promoRuleId;
        }
    </script>
</body>
</html>