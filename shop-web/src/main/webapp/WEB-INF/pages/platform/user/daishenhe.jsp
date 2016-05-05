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
<c:if test="${isUserForcus==false}">
<div class="na">
    <p>关注麦链公众号“<span class="add">麦链合伙人</span>”，管理店铺，发展下级。</p>
    <label class="close">×</label>
</div>
</c:if>
<header class="xq_header" style="margin:0;">
    <p>等待审核</p>
</header>
<div class="drap">
    <img src="${path}/static/images/yes.png" alt=""
         style="width: 20%;margin: 0 auto;display: block;margin-bottom:2em;">
    <h2>您的实名认证已经提交，审核结果会在1个工作日内下发到您的手机请注意查收。</h2>
</div>
<a href="${path}/marketGood/market" class="jixu">
    返回市场
</a>
<div class="back_box">
    <div class="back"></div>
    <div class="back_f">
        <span class="close">×</span>
        <img src="${path}/static/images/b.png" alt="">
    </div>
</div>
<script src="${path}/static/js/zepto.min.js"></script>
<script>
    $(".add").on("tap",function () {
        $(".back_box").show()
        $(".back_f").show()
    })
    $(".close").on("tap",function () {
        $(".back_box").hide()
        $(this).parent().hide()
    })


    $(document).ready(function(){
        setTimeout(function(){
            skipPage();
        },3000)
    })
    function skipPage(){
        window.location.href = ${path}/${returnPagePath};
    }
</script>
</body>
</html>