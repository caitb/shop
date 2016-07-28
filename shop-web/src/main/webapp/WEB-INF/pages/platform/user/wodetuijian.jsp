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
                  <a href="<%=path%>/index"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>我的推荐</p>            
        </header>
            <div class="floor">
                <%--<div>--%>
                    <%--<img src="<%=path%>/static/images/tuijian_1.jpg" alt="">--%>
                    <%--<p>￥<span>${pfUserStatistics.recommenGetFee}</span></p>--%>
                    <%--<h1>获得奖励</h1>--%>
                <%--</div>--%>
                <%--<div>--%>
                    <%--<img src="<%=path%>/static/images/tuijian_2.jpg" alt="">--%>
                    <%--<p>￥<span>${pfUserStatistics.recommenSendFee}</span></p>--%>
                    <%--<h1>发出奖励</h1>--%>
                <%--</div>--%>
                    <div>
                        <img src="<%=path%>/static/images/tuijian_s%20(2).png" alt="">
                        <p><span>${pfUserStatistics.recommenSendFee}</span></p>
                        <h1>我推荐的人</h1>
                    </div>
                    <div>
                        <img src="<%=path%>/static/images/tuijian_s%20(1).png" alt="">
                        <p><span>${pfUserStatistics.recommenSendFee}</span></p>
                        <h1>推荐的团队总数</h1>
                    </div>
                <div>
                    <img src="<%=path%>/static/images/tuijian_s%20(3).png" alt="">
                    <p>￥<span>${pfUserStatistics.recommenSendFee}</span></p>
                    <h1>总销售额</h1>
                </div>
            </div>
            <%--<div class="floor1">--%>
                <%--<p>推荐人</p>--%>
                <%--<div>--%>
                    <%--<p onclick="javascript:window.location.href = '<%=basePath%>myRecommend/myRecommendList';">--%>
                        <%--<span>${numByUserPid}</span>--%>
                        <%--<span>我推荐的人</span>--%>
                    <%--</p>--%>
                    <%--<p onclick="javascript:window.location.href = '<%=basePath%>myRecommend/recommendGiveList';">--%>
                        <%--<span>${numByUserId}</span>--%>
                        <%--<span>帮我推荐的人</span>--%>
                    <%--</p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="floor1 floor2">--%>
                <%--<p>推荐人订单</p>--%>
                <%--<div>--%>
                    <%--<p onclick="javascript:window.location.href = '<%=basePath%>myRecommend/getRewardBorder';">--%>
                        <%--<span>${borders}</span>--%>
                        <%--<span>获得奖励订单</span>--%>
                    <%--</p>--%>
                    <%--<p onclick="javascript:window.location.href = '<%=basePath%>myRecommend/sendRewardBorder';">--%>
                        <%--<span>${pBorders}</span>--%>
                        <%--<span>发出奖励订单</span>--%>
                    <%--</p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="tap">
                <p class="on">获得奖励：￥123.00</p>
                <p>获得奖励：￥123.00</p>
            </div>
       <main>
           <h1>获得奖励订单(80)</h1>
           <section class="sec1">
               <h2>
                   订单号：<span>12345678990(合伙订单)</span><b class="querenshouhuo_${pb.id}" >购买人：再走</b >
               </h2>
                   <div class="shangpin">
                       <div>
                           <h2><span>阿斯达岁的sad(￥321)</span><span style="color: #666">x123</span></h2>
                           <h3><b>合计：￥123.00</b></h3>
                       </div>
                   </div>
               <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" />
                   <span class="jixu">购买人：王平</span>
               </p>
           </section>
        </main>
    </div>
   <div class="load">暂无数据</div>
   <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
   <script>
       $(".tap p").on("click", function () {
           $(this).addClass("on").siblings().removeClass("on");
       })
   </script>
</body>
</html>
