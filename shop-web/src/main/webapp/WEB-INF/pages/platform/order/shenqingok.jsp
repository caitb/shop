<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shenqingok.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
<header class="xq_header">
    <a href="javascript :;" onClick="javascript :history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>等待审核</p>
</header>
</div>
<div class="drap">
    <h2><img src="<%=path%>/static/images/yes.png" alt=""></h2>
    <p>您的 抗引力 授权证书已经申请成功，审核结果会在1个工作日内完成</p>
</div>
<div class="he">
    <h1>您获得以下特权</h1>
</div>
<main>
    <section class="sec1">
        <h2><img src="<%=path%>/static/images/shenqing_3.png" alt=""></h2>
        <div>
            <h2>独立店铺</h2>
            <p>拥有自己的独立店铺进行推广装修等</p>
        </div>
        <a href="javascript:;">点击访问</a>
    </section>
    <section class="sec1">
        <h2><img src="<%=path%>/static/images/shenqing_4.png" alt=""></h2>
        <div>
            <h2>寻找合伙人</h2>
            <p>可以用采购价格购买商品，赚取利差</p>
        </div>
    </section>
    <section class="sec1">
        <h2><img src="<%=path%>/static/images/shenqing_5.png" alt=""></h2>
        <div>
            <h2>推广渠道</h2>
            <p>平台提供多样的推广渠道编辑推广自己商品</p>
        </div>
    </section>
    <section class="sec1">
        <h2><img src="<%=path%>/static/images/shenqing_6.png" alt=""></h2>
        <div>
            <h2>团队管理</h2>
            <p>提供完善的售后和团队管理工具</p>
        </div>
        <a href="javascript:;">点击访问</a>
    </section>
    <section class="sec1">
        <h2><img src="<%=path%>/static/images/shenqing_7.png" alt=""></h2>
        <div>
            <h2>平台补助</h2>
            <p>消费者分享商品可获得佣金，佣金来自于平台的补助</p>
        </div>
    </section>
</main>
</body>
</html>