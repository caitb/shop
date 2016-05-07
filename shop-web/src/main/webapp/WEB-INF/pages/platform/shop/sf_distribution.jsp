<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/shop/css/fenxiaojilu.css">
    <link rel="stylesheet" href="<%=path%>/static/css/main.css">
    <link rel="stylesheet" href="<%=path%>/static/css/common.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css"/>
</head>
<body>
<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}"/>
<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
<input type="hidden" id="totalCount" name="totalCount" value="${totalCount}">
<input type="hidden" id="year" name="year" value="${year}"/>
<input type="hidden" id="month" name="month" value="${month}"/>
    <header>
            <a href="javascript:window.location.replace('<%=basePath%>shop/manage/index')"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>分销记录</p>
    </header>
    <div class="wrap">
       <div class="index_login">
            <p><b>${sumLevel}</b><span>参与人次</span></p>
            <ul>
                <li><p>总销售额</p><h1>￥<span>${sfShop.saleAmount}</span></h1></li>
                <li><p>发放佣金</p><h1>￥<span>${distributionAmount}</span></h1></li>
            </ul>
       </div>
       <div class="sec1" id="sec1">
            <p>分销记录：<label for="beginTime"><b>${year}</b>年<b>${month}</b>月</label><input  id="beginTime" class="kbtn" style="display:none;"/></p>
            <div id="divall">
                <c:forEach var="sfDistributionRecord" items="${sfDistributionRecords}">
                    <c:set var="count" value="0"></c:set>
                    <c:forEach items="${sfDistributionRecord.sfDistributionPersons }" var="item">
                        <c:set var="count" value="${count+item.amount }"></c:set>
                    </c:forEach>
                    <div class="record">
                        <p><span><b>${sfDistributionRecord.level}</b>人参加</span><span>${sfDistributionRecord.skuName}</span><span onclick="viewOrder(${sfDistributionRecord.orderId})">查看订单></span></p>
                        <h1><span><fmt:formatDate value="${sfDistributionRecord.createTime}"  type="time" pattern="dd"/>日</span><span>购买人：${sfDistributionRecord.wxNkName}</span><span>￥${sfDistributionRecord.orderAmount}</span></h1>
                        <h1><span><b>${ fn:length(sfDistributionRecord.sfDistributionPersons) }</b>人分佣</span><span>￥${count}</span><span onclick="showDetails(${sfDistributionRecord.sfDistributionPersons})">分佣明细></span></h1>
                    </div>
                </c:forEach>
            </div>
        </div>
        <p id="show" style="text-align: center;">
            <c:if test="${sfDistributionRecords != null && fn:length(sfDistributionRecords) < totalCount}">
                <a href="#" onclick="showMore()">查看更多></a>
            </c:if>
        </p>
    </div>
    <div id="datePlugin"></div>
    <div class="back"></div>
    <div class="back_f"></div>
<script type="application/javascript" src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script type="application/javascript" src="<%=path%>/static/js/date.js" ></script>
<script type="application/javascript" src="<%=path%>/static/js/iscroll.js" ></script>
<script type="application/javascript" src="<%=path%>/static/js/commonAjax.js"></script>
<script type="application/javascript" src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script type="application/javascript" src="<%=path%>/static/js/sf_distribution.js"></script>
<script>
    var path = "<%=path%>";
    var basePath = "<%=basePath%>";
    $(function(){
        $('#beginTime').date(undefined,undefined,undefined,function(year, month){
            turnMonth(year,month);
            $("#year").val(year);
            $("#month").val(month);
        });
        $('#endTime').date({theme:"datetime"});
    });
</script>
</body>
</html>