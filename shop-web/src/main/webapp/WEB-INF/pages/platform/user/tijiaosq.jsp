<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/tijiaosq.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="lingquzhengshu.html"><img src="../images/xq_rt.png" alt=""></a>
            <p>授权书申请 </p>
        </header>
        <div class="xinxi">
            <p>注册信息</p>
            <p>支付订单</p>
            <p>提交资料</p>
        </div>
        <p class="cp">
            合伙产品：<span>抗引力—瘦脸精华</span>
        </p>

        <main>
            <p class="sf" style="color:#333333;text-indent:10px; font-size:14px;">姓名：<span
                    style="font-size:14px;">王平</span></p>
            <div class="sf">
                身份证号：
                <input type="tel">
            </div>
            <div class="sf" style="border-bottom:none;">
                身份证照片：
            </div>
            <div class="sfphoto">
                <label for="zheng" class="zheng">
                    <img src="../images/shenfen.png" alt="">
                    <input id="zheng" type="file"/>
                </label>
                <label for="fan" class="fan" style="margin-left:10px;">
                    <img src="../images/shenfenf.png" alt="">
                    <input id="fan" type="file"/>
                </label>
            </div>

        </main>
        <a href="shenqingok.html" class="tijiao">提交申请</a>
    </div>
</div>
</body>
</html>