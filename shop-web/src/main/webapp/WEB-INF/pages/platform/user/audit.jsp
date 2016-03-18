<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/kangyinli.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <script src="<%=basePath%>static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>队员信息</p>
    </header>
    <main>
        <div class="sec1"><p>提 交 时 间：<span><fmt:formatDate value="${memberMap.applyTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></span></p></div>
        <div class="sec1"><p>申 请 产　品：<span>${memberMap.skuName}</span></p></div>
        <div class="sec1"><p>申 请 等　级：<span>${memberMap.agentLevelName}</span></p></div>
        <div class="sec1"><p>申 请 人：<span>${memberMap.comUserName}</span></p></div>
        <div class="sec1"><p>付 款 状 态：<span>
                                         <c:if test="${memberMap.payStatus == 0}">未付款</c:if>
                                         <c:if test="${memberMap.payStatus == 1}">已付款</c:if>
                                        </span></p></div>
        <div class="sec1"><p>手机号：<span>${memberMap.mobile}</span></p></div>
        <div class="sec1"><p>微信号：<span>${memberMap.weixin}</span></p></div>
        <div class="sec1"><p>身份证号：<span>${memberMap.idCard}</span></p> </div>
        <div class="sec1" style="border:none;"><p>身份证扫描件：</p></div>
        <div>
            <h1><img src="${memberMap.idCardFrontImg}" alt="">正面</h1>
            <h1><img src="${memberMap.idCardBackImg}" alt="">反面</h1>
        </div>
        <footer>
            <botton class="btn">拒绝</botton>
            <botton class="btn2">通过</botton>
        </footer>
    </main>
    <div class="back">
        <div class="back_j">
            <p>请填写拒绝原因</p>
            <textarea></textarea>
            <div>
                <h1 class="j_qu">取消</h1>
                <h1 class="j_que">确定</h1>
            </div>
        </div>
    </div>
</div>
</body>
</html>