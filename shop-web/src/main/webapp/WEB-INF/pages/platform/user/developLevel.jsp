<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/hehuorenhaibao.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>合伙人海报</p>
    </header>
    <form id="levelIdForm" action="<%=basePath%>developing/sharelink" method="post">
    <input type="hidden" name="skuId" value="${skuId}" >
    <main>
        <h1>
            您的级别：<c:forEach items="${comAgentLevels}" var="agentLevel">
                     <c:if test="${agentLevelId == agentLevel.id}">${agentLevel.name}</c:if>
                    </c:forEach>
        </h1>
        <div class="floor">
            <p>请选择分享后可注册的等级：</p>
            <c:forEach items="${comAgentLevels}" var="agentLevel">
            <input type="checkbox" id="level_${agentLevel.id}" name="levelIds" value="${agentLevel.id}"/>
            <label for="level_${agentLevel.id}">
                    <div><b>${agentLevel.name}</b><c:if test="${agentLevelId == agentLevel.id}"><span>(平级推荐)</span><img src="${path}/static/images/i.png" alt="" onclick="blackShow()"></c:if></div>
                    <c:forEach items="${skuAgents}" var="skuAgent">
                       <c:if test="${skuAgent.agentLevelId == agentLevel.id}"><div><span>${skuAgent.totalPrice}元套餐</span> <span>(包含商品${skuAgent.totalPrice/skuAgent.unitPrice}件，保证金：${skuAgent.bail}元)</span></div></c:if>
                    </c:forEach>
            </label>
            </c:forEach>
        </div>
        <p><b>*</b>您可以多次获取不同等级的分享海报</p>
    </main>
    </form>
    <button id="getPoster">获取分享海报</button>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="back_que">
        <p>什么是平级推荐？</p>
        <h4>平级推荐不同于发展下级合伙人。如果您的好友通过二维码注册的合伙人等级与您相同，那么您就是他的推荐人，他每次拿货您都可以获得相应的奖励。推荐人关系永久绑定，即便是他以后升级了，您仍然是他的推荐人。</h4>
        <h3>
            <span class="que_qu" onclick="blackHide()">我知道了</span>
        </h3>
    </div>
</div>
</body>
<script src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>static/js/definedAlertWindow.js"></script>
<script>
    $('#getPoster').on('click', function(){
        var checkedCount = $('input[name="levelIds"]:checked').size();
        if(checkedCount <= 0){
            alert('请选择要发展的代理等级!');
            return;
        }
        $('#levelIdForm').submit();
    });
    $(".floor label").on("click", function () {
        if($(this).hasClass("active")){
            $(this).removeClass("active");
        }else{
            $(this).addClass("active")
        }
    })
    $("label img").on("click", function (event) {
        var event=event||event.widows;
        event.stopPropagation();
    })
    function blackShow(){
        $(".black").show();
    }
    function blackHide(){
        $(".black").hide();
    }
</script>
</html>