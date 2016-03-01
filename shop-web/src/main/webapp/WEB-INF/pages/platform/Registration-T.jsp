<%--
  Created by IntelliJ IDEA.
  User: muchaofeng
  Date: 2016/3/1
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%-- 注册 第二步 --%>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zhuce2.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
</head>
<body>
<header class="xq_header">
    <a href="<%=path%>/pages/platform/Registration.jsp"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>申请合伙人</p>
</header>
<div class="xinxi">
    <p>1.注册信息</p>
    <p>2.支付订单</p>
    <p>3.支付订单</p>
</div>
<p class="xuanze">
    选择商品：<span>抗引力—快速瘦脸精华</span>
</p>
<main>
    <section class="sec1">
        <div>
            <p>申请人信息： 这是您在麦链商城的登陆账户</p>
        </div>
        <div>
            <p>姓名： <b>王平</b></p>
        </div>
        <div>
            <p>手机号： <b>18611536163</b><span>(已验证)</span></p>
        </div>
        <div>
            <p>微信： <b>abcsdjksd</b></p>
        </div>
    </section>
    <section class="sec2">
        <h2>合伙人信息</h2>
        <p>上级合伙人手机号： <b>18611536163</b><span>王平</span></p>
        <p>合伙人等级： <b>高级合伙人</b></p>
        <p>需要缴纳贷款： <b>￥10200</b></p>
    </section>
    <section class="sec3">
        <a href="<%=path%>/pages/platform/Registration.jsp">返回修改</a>
        <a href="<%=path%>/pages/platform/payment.jsp">确认</a>
    </section>
</body>
</html>