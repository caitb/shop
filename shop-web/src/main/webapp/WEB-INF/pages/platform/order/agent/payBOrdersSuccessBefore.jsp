<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/jixuguangguang.css">
</head>
<body>
<%--<header class="xq_header" style="margin:0;">--%>
    <%--<p>正在为您处理，请稍后。</p>--%>
<%--</header>--%>
<div class="drap">
    <img src="${path}/static/images/yes.png" alt=""
         style="width: 20%;margin: 0 auto;display: block;margin-bottom:2em;">
    <%--<h2>正在为您处理，请稍后</h2>--%>
    <h1>正在为您处理，请稍后...</h1>
</div>
<script src="${path}/static/js/zepto.min.js"></script>
<script>
    $(document).ready(function () {
        function timedMsg() {
            var t = setTimeout(function () {
                skipPage();
            }, 3000)
        }

        timedMsg();
    })
    function skipPage() {
        window.location.href = "${successURL}";
    }
</script>
</body>
</html>
