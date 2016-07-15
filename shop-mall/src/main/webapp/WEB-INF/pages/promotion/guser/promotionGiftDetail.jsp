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
    <link rel="stylesheet" href="<%=path%>/static/css/prize.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>奖品详情</p>            
        </header>
        <banner>
            <img src="${promotionGiftInfo.giftImageUrl}" alt="">
            <h1>${promotionGiftInfo.giftName}<button onclick="skipPromotionGorderPage()">点击领取</button></h1>
        </banner>
        <main>
            <h1>
                <span></span>
                <b>奖品描述</b>
                <span></span>
            </h1>
            <p>阿克苏了解到拉萨空间打开拉萨觉得绿卡时间的卡拉司机打开拉萨觉得卡拉斯进口量跌价阿斯科利大家阿斯科利的艰苦拉萨机</p>
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