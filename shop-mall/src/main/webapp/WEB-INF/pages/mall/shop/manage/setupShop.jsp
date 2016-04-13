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
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/dianpushezhi.css">
</head>
<body>
<header>
    <a href="zhifu.html"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>店铺设置</p>
</header>
<form action="<%=basePath%>shop/manage/updateShop" method="post">
<div class="wrap">
    <p>
        <label for="">店铺名称</label>
        <input type="hidden" name="id" value="${sfShop.id}">
        <input type="text" name="name" value="${sfShop.name}">
    </p>
    <p>
        <label for="">店铺介绍</label>
        <textarea name="explanation" value="${sfShop.explanation}" id="" cols="30" rows="10"></textarea>
    </p>
    <button type="submit">保存</button>
</div>
</form>
</body>
</html>