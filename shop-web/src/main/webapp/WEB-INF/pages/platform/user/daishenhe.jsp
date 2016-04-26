<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/jixuguangguang.css">
</head>
<body>
<div class="na">
    <p>关注麦链公众微信号“<span class="add">麦链商城</span>”，差佣金，查订单。</p>
    <label class="close">×</label>
</div>
<header class="xq_header" style="margin:0;">
    <p>等待审核</p>
</header>
<div class="drap">
    <img src="${path}/static/images/yes.png" alt=""
         style="width: 20%;margin: 0 auto;display: block;margin-bottom:2em;">
    <p>实名认证已经提交</p>
    <h2>您的实名认证已经提交，审核结果会在1个工作日内下发到您的手机请注意查收。</h2>
</div>
<a href="${path}/marketGood/market" class="jixu">
    返回市场
</a>
<div class="back_box">
    <div class="back"></div>
    <div class="back_f">
        <p>关注公众账号查佣金、查订单</p>
        <span class="close">×</span>
        <div>
            <p><img src="${path}/static/images/zhiwen.png" alt=""><span>麦链商城</span></p>
            <p><img src="${path}/static/images/zhiwen.png" alt=""><span>长按左侧二维码关注</span></p>
        </div>
    </div>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script>
    $(".add").on("tap",function () {
        $(".back_box").show()
        $(".back_f").show()
    })
    $(".close").on("tap",function () {
        $(".back_box").hide()
        $(this).parent().hide()
    })
</script>
</body>
</html>