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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/main.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/dianpushezhi.css">
</head>
<body>
<header>
    <a href="javascript:window.location.replace('<%=basePath%>shop/manage/index');"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>店铺设置</p>
</header>

<div class="wrap">
    <form action="<%=basePath%>shop/manage/updateShop" method="post">
    <p>
        <label for="">店铺名称</label>
        <input type="hidden" name="id" value="${sfShop.id}">
        <input type="text" name="name" value="${sfShop.name}">
    </p>
        <p>
            不超过10个字
        </p>
    <p>
        <label for="">店铺介绍</label>
        <textarea name="explanation" value="${sfShop.explanation}" id="" cols="30" rows="10">${sfShop.explanation}</textarea>
    </p>
        <p>
            不超过25个字
        </p>
        <h1><span>*</span>请勿上传政治、色情、暴力、低俗等与违法信息相关的图片和介绍，违反规定将扣除保证金，严重的将取消代理资格</h1>
    <button type="submit">保存</button>
    </form>
</div>

</body>
</html>