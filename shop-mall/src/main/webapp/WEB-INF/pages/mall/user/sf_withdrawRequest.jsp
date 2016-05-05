<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/shenqingtixian.css">
</head>
<body>
<input type="hidden" id="extractableFee" name="extractableFee" value="${userAccount.extractableFee}"/>
<input type="hidden" id="appliedFee" name="appliedFee" value="${userAccount.appliedFee}"/>
<header>
    <a href="javascript:history.back(-1)"><img src="${path}/static/images/xq_rt.png" alt=""></a>
    <p>申请提现</p>
</header>
<div class="wrap">
    <div class="na">
        <p><img src="${comUser.wxHeadImg}" alt=""></p>
        <h1>
            <span>${comUser.wxNkName}</span>
            <span>您已绑定微信</span>
        </h1>
    </div>
    <main>
        <p>您当前可提现金额为<span>￥<fmt:formatNumber value="${userAccount.extractableFee}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></span><span>&nbsp;&nbsp;&nbsp;已经申请提现金额为￥<fmt:formatNumber value="${userAccount.appliedFee}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></span></p>
        <div class="sec1">
            提现金额：￥<input id="inputAccount" name="inputAccount" type="text" placeholder="请输入提现金额">
        </div>
        <h1>*请确认您已关注“麦链商城”微信公众账号，否则提现会失败</h1>
        <h2>为保障奖励及时到账，麦链商城采用的是微信转账形式发放。</h2>
        <button onclick="withdraw(${userAccount.userId})">确认提现</button>
    </main>
</div>
<script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
<script type="application/javascript" src="${path}/static/js/common/commonAjax.js"></script>
<script type="application/javascript" src="${path}/static/js/pageJs/sf_withdrawRequest.js"></script>
<script type="application/javascript" src="${path}/static/js/common/definedAlertWindow.js"></script>
<script type="application/javascript">
    var path = "${path}";
    var basepath = "${basePath}";
</script>
</body>
</html>