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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/sharePlan.css">
</head>
<input type="hidden" id="shopId" name="shopId" value="${shopId}">
<body>
<div class="wrap">
    <img src="<%=basePath%>static/images/fenx.png" alt="">
    <button onclick=validateCodeJS.applyTrial("getPoster")>
        获取我的专属海报
    </button>
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
<footer>
    <div>
        <p onclick="javascript:window.location.replace('<%=basePath%>${shopId}/${userPid}/shop.shtml');">
            <span><img src="<%=path%>/static/images/dibu1.png" alt=""></span>
            <span>首页</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${sfShop.id}');">
            <span><img src="<%=path%>/static/images/dibu22.png" alt=""></span>
            <span class="active" >二维码</span>
        </p>
        <p onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/toBorderManagement?fm=1');">
            <span><img src="<%=path%>/static/images/dibu3.png" alt=""></span>
            <span>个人中心</span>
        </p>
    </div>
</footer>
</body>
<script src="<%=path%>/static/js/plugins/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/plugins/validateCode.js"></script>
<script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
<script>
    $(document).ready(function () {
        validateCodeJS.initPage();
    });
</script>
</html>