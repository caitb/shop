<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/messageDetail.css">
    <script>
        var path = "${path}";
        var uid = "${userId}";
    </script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="${path}/message/center.shtml"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p id="fromUserName"></p>
    </header>
    <main id="mlist">
    </main>
    <div id="more" style="display: none">
        <br/>
        <h3 align="center">加载更多</h3>
    </div>
    <div id="nomore" style="display: none">
        <br/>
        <h3 align="center">暂无更多数据</h3>
    </div>
    <div id="remore" style="display: none">
        <br/>
        <h3 align="center">点击重新加载</h3>
    </div>
    <br/>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/commonAjax.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script src="${path}/static/js/message_detail.js"></script>
</body>
</html>
