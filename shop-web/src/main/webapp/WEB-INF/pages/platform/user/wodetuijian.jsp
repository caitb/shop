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
    <link rel="stylesheet" href="<%=path%>/static/css/wodetuijian.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>我的推荐</p>            
        </header>
        <main>
            <div class="floor">
                <div>
                    <img src="<%=path%>/static/images/tuijian_1.jpg" alt="">
                    <p>￥<span>${pfUserStatistics.recommenGetFee}</span></p>
                    <h1>获得奖励</h1>
                </div>
                <div>
                    <img src="<%=path%>/static/images/tuijian_2.jpg" alt="">
                    <p>￥<span>${pfUserStatistics.recommenSendFee}</span></p>
                    <h1>发出奖励</h1>
                </div>
            </div>
            <div class="floor1">
                <p>推荐人</p>
                <div>
                    <p onclick="javascript:window.location.replace('<%=basePath%>myRecommend/RecommendGiveList')">
                        <span>${numByUserPid}</span>
                        <span>我推荐的人</span>
                    </p>
                    <p>
                        <span>${numByUserId}</span>
                        <span>帮我推荐的</span>
                    </p>
                </div>
            </div>
            <div class="floor1 floor2">
                <p>推荐人订单</p>
                <div>
                    <p onclick="javascript:window.location.replace('<%=basePath%>myRecommend/getRewardBorder')">
                        <span>${borders}</span>
                        <span>获得奖励订单</span>
                    </p>
                    <p onclick="javascript:window.location.replace('<%=basePath%>myRecommend/sendRewardBorder')">
                        <span>${pBorders}</span>
                        <span>发出奖励订单</span>
                    </p>
                </div>
            </div>
        </main>
    </div>
</body>
</html>