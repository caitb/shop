<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>订单详情</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/warning.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/base.css">
    <script type="application/javascript">
        window.onload = function(){
            setInterval("auto_redirect()", 2000);
            WeixinJSBridge.call('hideOptionMenu');
        }

        function auto_redirect(){
            window.location.href = "${redirectUrl}";
        }
    </script>
</head>
<body>
<div class="wrap">
    <img src="${pageContext.request.contextPath}/static/images/warning.png" alt="">
    <p>您还不是合伙人，暂时不能使用此功能~</p>
    <h1>页面将自动跳转到 首页....</h1>
</div>
</body>
</html>
