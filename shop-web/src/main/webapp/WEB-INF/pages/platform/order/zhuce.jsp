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
    <link rel="stylesheet" href="<%=path%>/static/css/zhuce.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script>
        var path = "<%=basePath%>";
        var skuId = "${skuId}";
        var skuName = "${skuName}";
    </script>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/iscroll.js"></script>
    <%--<script src="<%=path%>/static/js/checkUtil.js"></script>--%>
    <script src="<%=path%>/static/js/zhuceUtil.js"></script>
</head>
<body>

<div class="wrap">

    <header class="xq_header">
        <a href="shenqing.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>申请合伙人</p>
    </header>
    <div class="xinxi">
        <p>注册信息</p>
        <p>支付订单</p>
        <p>申请成功</p>
    </div>
    <p class="xuanze">
        选择商品：<span>抗引力—快速瘦脸精华</span>
    </p>
    <main>
        <section class="sec1">
            <div>
                <p>申请人信息　<b style="color:#999999">您可以凭手机号登录麦链商城</b></p>
            </div>
            <div>
                <p>姓名： <input type="text" class="name" id="name" name="name"></p>
            </div>

            <div>
                <p>手机号： <input type="text" class="tel" id="tel" name="tel"></p>
            </div>
            <div>
                <p>验证码： <input type="text" class="yan" style="width:60px">
                    <botton class="btn" id="yanzhengma" name="yanzhengma">获取验证码</botton>
                </p>
            </div>

            <div>
                <p>微信号：<input type="text" id="weixin" name="weixin"></p>
            </div>
            <div>
                <p>上级合伙人电话： <input type="text" class="tel" id="parentMobile" name="parentMobile"></p>
            </div>
        </section>
        <section class="sec2">
            <h2>合伙人信息</h2>
            ${agentInfo}
        </section>
        <section class="sec3">
            <p>
                <input type="checkbox" id="fu" checked disabled>
                <label for="fu">我已同意<a href="#">《代理商注册协议》</a></label>
            </p>
        </section>
        <section class="sec4">
            <a href="javascript:;" id="submit">下一步</a>
        </section>
    </main>
</div>
</body>
</html>