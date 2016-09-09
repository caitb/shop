<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/base.css">
    <link rel="stylesheet" href="${path}/static/css/reset.css">
    <link rel="stylesheet" href="${path}/static/css/header.css">
    <link rel="stylesheet" href="${path}/static/css/wodetuijian.css">
</head>
<body>
<input type="hidden" id="totalCount" name="totalCount" value="${myRecommendPo.totalCount}">
<input type="hidden" id="currentPage" name="currentPage" value="${myRecommendPo.currentPage}">
<input type="hidden" id="tab" name="tab" value="0">
    <div class="wrap">
        <header class="xq_header">
        <a href="${path}/index"><img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>我的推荐</p>
        </header>
        <div class="floor">
            <div onclick="toTuijian()">
                <img src="${path}/static/images/tuijian_s%20(2).png" alt="">
                <p><span>${myRecommendPo.myRecommedPeople}</span></p>
                <h1>我推荐的人</h1>
            </div>
            <div onclick="toTuijian()">
                <img src="${path}/static/images/tuijian_s%20(1).png" alt="">
                <p><span>${myRecommendPo.recommenTeamCount}</span></p>
                <h1>推荐团队人数</h1>
            </div>
            <div onclick="toTuijian()">
                <img src="${path}/static/images/tuijian_s%20(3).png" alt="">
                <p><span>${myRecommendPo.totalSalesView}</span></p>
                <h1>总销售额</h1>
            </div>
        </div>
        <div class="tap">
            <p class="on" id="target" onclick="changeTab(0)">获得奖励：${myRecommendPo.incomeRewardsView}</p>
            <p onclick="changeTab(1)">发出奖励：${myRecommendPo.sendRewardsView}</p>
        </div>
        <main id="rewardOrder">
            <h1>获得奖励订单(${myRecommendPo.totalCount})</h1>
        </main>
        <main id="showDiv">
            <c:forEach items="${myRecommendPo.recommenOrders}" var="recommenOrder">
               <section class="sec1" onclick="toOrderDetail(${recommenOrder.orderId})">
                   <h2>
                       订单号：<span>${recommenOrder.orderCode}(${recommenOrder.orderTypeView})</span><b >购买人：${recommenOrder.buyUserName}</b >
                   </h2>
                   <div class="shangpin">
                       <div>
                           <h2><span>${recommenOrder.skuName}</span></h2>
                           <h3><span style="color: #666">x${recommenOrder.quantity}</span><b>&nbsp;&nbsp;&nbsp;奖励：${recommenOrder.totalPriceView}</b></h3>
                       </div>
                   </div>
                   <p>时间： <fmt:formatDate value="${recommenOrder.createTime}" pattern="yyyy-MM-dd HH:mm" />
                       <span class="jixu">发奖励的人：${recommenOrder.sendUserName}</span>
                   </p>
               </section>
           </c:forEach>
        </main>
    </div>
    <div class="load" id="show">
        <c:if test="${myRecommendPo.recommenOrders != null && fn:length(myRecommendPo.recommenOrders) < myRecommendPo.totalCount}">
            <a href="#" onclick="showMore()">查看更多></a>
        </c:if>
    </div>
    <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${path}/static/js/definedAlertWindow.js" ></script>
    <script type="text/javascript" src="${path}/static/js/commonAjax.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="${path}/static/js/hideWXShare.js"></script>
    <script>
        var path = "${path}";
        var basePath = "${basePath}";
        $(".tap p").on("click", function () {
            $(this).addClass("on").siblings().removeClass("on");
        })
        function toTuijian(){
            fullShow();
            window.location.href = basePath + "myRecommend/myRecommendList";
        }
        function showMore(){
            var tab = $("#tab").val();
            var currentPage = $("#currentPage").val();
            ajaxRequest(tab,currentPage);
        }
        function changeTab(tab){
            ajaxRequest(tab,0);
            $("#tab").val(tab);
        }
        function ajaxRequest(tab,currentPage){
            $.ajax({
                type:"POST",
                async:true,
                url : basePath+"myRecommend/myRecommen.do",
                data:{tab:tab,currentPage:currentPage},
                dataType:"Json",
                success:function(data){
                    var isTrue = data.isTrue;
                    if (isTrue == "true"){
                        $("#currentPage").val(data.pageNum);
                        if(parseInt(currentPage) == 0){
                            $("#showDiv").empty();
                            $("#showDiv").html(data.html);
                            if ($(".sec1").length >= data.totalCount){
                                $("#show").empty();
                            }else {
                                $("#show").html("<a href=\"#\" onclick=\"showMore()\">查看更多></a>");
                            }
                            $("#totalCount").val(data.totalCount);
                            if (parseInt(tab) == 0){
                                $("#rewardOrder").html("<h1>获得奖励订单("+data.totalCount+")</h1>")
                            }else {
                                $("#rewardOrder").html("<h1>发出奖励订单("+data.totalCount+")</h1>")
                            }

                        } else {
                            $("#showDiv").append(data.html);
                            var totalCount = $("#totalCount").val();
                            if ($(".sec1").length >= totalCount){
                                $("#show").empty();
                            }else {
                                $("#show").html("<a href=\"#\" onclick=\"showMore()\">查看更多></a>");
                            }
                        }
                    }else {
                        alert(data.message);
                    }
                },
                error: function(){
                    //请求出错处理
                    alert("请求出错，请稍后再试");
                }
            });
        }

        function toOrderDetail(orderId){
            fullShow();
            window.location.href = basePath + "borderManage/deliveryBorderDetils.html?id=" + orderId;
        }
        window.onload = function load() {
            document.getElementById("target").onclick();
        }
    </script>
</body>
</html>
