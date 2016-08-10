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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/winning.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">
</head>
<body>
    <div class="wrap">
        <header class="xq_header">
              <a href="${path}/turnTableDetailShow/getTurnTableInfo.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                <p>中奖记录</p>           
        </header>
        <main>
            <c:forEach items="${records}" var="record" >
                <section>
                    <p><span><b>奖品名称：</b>${record.turnTableGiftName}</span></p>
                    <p>
                        <span><b>中奖时间：</b>${record.createTimeString}</span>
                        <c:if test="${record.status==1}">
                            <i>已领取</i>
                        </c:if>
                        <c:if test="${record.status==0}">
                            <i>未领取</i>
                            <button onclick="skipToReceiveGiftPage(${record.turnTableId},${record.giftId},${record.id})">立即领取</button>
                        </c:if>
                    </p>
                </section>
            </c:forEach>
        </main>
    </div>
</body>
<script>
    function skipToReceiveGiftPage(turnTableId,giftId,userTurnTableRecordId){
        window.location.href="<%=path%>/turnTableGorder/getTurnTableGiftInfo.html?turnTableId="+turnTableId+"&giftId="+giftId+"&userTurnTableRecordId="+userTurnTableRecordId;
    }
</script>
</html>