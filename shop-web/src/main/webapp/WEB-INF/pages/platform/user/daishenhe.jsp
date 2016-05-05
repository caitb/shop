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
<header class="xq_header" style="margin:0;">
    <p>等待审核</p>
</header>
<div class="drap">
    <img src="${path}/static/images/yes.png" alt=""
         style="width: 20%;margin: 0 auto;display: block;margin-bottom:2em;">
    <h2>您的实名认证已经提交，审核结果会在1个工作日内下发到您的手机请注意查收。</h2>
    <h1>${message}<span class="time">3</span>s</h1>
</div>
<script src="${path}/static/js/zepto.min.js"></script>
<script>
    $(document).ready(function() {
        function timedMsg() {
            var t = setTimeout(function () {
                for (i = 3; i > 0; i--) {
                    $(".time").val(i);
                }
                skipPage();
            }, 1000)
        }

        timedMsg();
    }
    function skipPage(){
        window.location.href = "${path}/${returnPagePath}";
    }
</script>
</body>
</html>