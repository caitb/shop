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
    <link rel="stylesheet" href="${path}/static/css/successfully.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="${path}/message/center.shtml"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>发送成功</p>
    </header>
    <main>
        <h1>
            <img src="${path}/static/images/ready.png" alt="">
            发送成功
        </h1>
        <p>您的直接下级将在2分钟内收到消息。</p>
    </main>
    <p><a href="${path}/message/center.shtml">返回消息中心</a></p>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script>
</script>
</body>
</html>