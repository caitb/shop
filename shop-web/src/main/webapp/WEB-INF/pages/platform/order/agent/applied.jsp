<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/shenqingguo.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <p>合伙人申请</p>
    </header>
    <div class="wrapno">
        <img src="${path}/static/images/yes.png" alt="">
        <h1>申请过啦！</h1>
        <p>您已申请过此商品的合伙人， 现在将自动返回管理首页</p>
    </div>
</div>
</body>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/hideWXShare.js"></script>
<script>
    $(document).ready(function () {
        setTimeout(function () {
            skipPage();
        }, 2000)
    })
    function skipPage() {
        window.location.href = "${basePath}";
    }
</script>
</html>