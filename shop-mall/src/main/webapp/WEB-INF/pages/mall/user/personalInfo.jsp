<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/gerenxinxi.css">
    <script src="<%=path%>/static/js/plugins/jquery/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
    <script src="<%=path%>/static/js/plugins/validateCode.js"></script>
    <script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
</head>
<body>
<header>
    <a href="<%=basePath%>sfOrderManagerController/borderManagement.html"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>个人信息</p>
</header>
<div class="wrap">
    <div>
        <p><img src="${comUser.wxHeadImg}" /> </p>
        <h1>
            <span>${comUser.wxNkName}</span>
        </h1>
    </div>
    <div class="phone">
        <p>手机号</p>
        <c:if test="${comUser.isBinding!=1}">
            <p onclick=validateCodeJS.applyTrial("mallPersonalInfo")>绑定手机号</p>
        </c:if>
        <c:if test="${comUser.isBinding==1}">
            <p>${comUser.mobile}<b>(已绑定)</b></p>
        </c:if>
    </div>
    <p>Copyright2015-2016  麦链合伙人  iimai.com<br/>
        北京麦士网络科技有限公司</p>
</div>
<div class="back_j" style="display: none">
    <span class="close">×</span>
    <p class="biao">绑定手机号</p>
    <div>
        <p>手机号：<input type="tel" class="phone" id="phoneId"></p>
    </div>
    <div class="d">
        <p>验证码：<input type="tel" id="validateNumberDataId">
            <button id="validateNumberId">获取验证码</button>
        </p>
    </div>
    <p class="tishi" id="errorMessageId"></p>
    <h1 class="j_qu" id="nextPageId">下一步</h1>
</div>
<div class="back"></div>
</body>
<script>
    $(document).ready(function () {
        validateCodeJS.initPage();
    });
</script>
</html>