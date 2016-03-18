<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/zichan.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="#" onClick="javascript :history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>我的资产</p>
    </header>
    <main>
        <div class="ban">
            <h1>￥<b>23456.00</b></h1>
            <p>累计收入</p>
        </div>
        <nav>
            <ul>
                <li>
                    <p>可提现<span>申请提现</span></p>
                    <h1>￥12313.00</h1>
                </li>
                <li>
                    <p>结算中<b>?</b></p>
                    <h1>￥<span>123123.00</span></h1>
                </li>
            </ul>
        </nav>
        <div class="sec1">
            <p>本月：<span>查看月账单</span></p>
            <div>
                <p><span>昨天</span><span class="sd">03-16</span></p>
                <p><span>-865.00</span><span>抗应力-瘦脸精华</span></p>
                <h1>进货</h1>
            </div>

            <div>
                <p><span>昨天</span><span class="sd">03-16</span></p>
                <p><span>-865.00</span><span>抗应力-瘦脸精华</span></p>
                <h1>进货</h1>
            </div>

            <div>
                <p><span>昨天</span><span class="sd">03-16</span></p>
                <p><span>-865.00</span><span>抗应力-瘦脸精华</span></p>
                <h1>进货</h1>
            </div>

            <div>
                <p><span>昨天</span><span class="sd">03-16</span></p>
                <p><span>-865.00</span><span>抗应力-瘦脸精华</span></p>
                <h1>进货</h1>
            </div>
        </div>
    </main>
</div>
<div class="back">
    <div class="back_j">
        <h1>什么事结算中</h1>
        <p>
            为了响应国家爱号召，增强用户体验，平台支持7天退货，您的资金在对方确认收货后7天内属于结算中，7天后将自动转到可提现。
        </p>
        <botton>我知道了</botton>
    </div>
</div>
</body>
</html>
