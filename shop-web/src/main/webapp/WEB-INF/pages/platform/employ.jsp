<%--
  Created by IntelliJ IDEA.
  User: muchaofeng
  Date: 2016/3/1
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>

<%-- 使用界面 --%>

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
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shiyong.css">
</head>
<body>
<header class="xq_header">
    <a href="<%=path%>/lo/detail"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>申请试用</p>
</header>
<main>
    <section class="sec2">
        <p class="photo">
            <a href="<%=path%>/lo/detail">
                <img src="<%=path%>/static/images/shenqing_1.png" alt="">
            </a>
        </p>
        <div>
            <h2>抗引力——快速瘦脸精华<span>x1000</span></h2>
            <h3>规格：<span>默认</span></h3>
            <p>零售价：<span>￥298</span>　合伙人价：<span>￥238</span></p>
        </div>
    </section>
    <section class="sec3">
        <p>运费<span>300.0</span></p>
        <h1>共<span>800</span>件商品　运费：<span>￥300</span><b>合计：</b><span>￥15500.00</span></h1>
        <p>留言：<input type="text"></p>
    </section>
    <h1 class="pople">
        申请人信息
    </h1>
    <section class="sec4">
        <p>姓名：<input type="text"></p>
        <p>手机号：<input type="tel"></p>
        <p>验证码：<input type="text"><span>获取验证码</span></p>
        <p>微信：<input type="text"></p>
    </section>
    <a href="javascript:;" class="sq">试用申请</a>
</main>
</body>
</html>
