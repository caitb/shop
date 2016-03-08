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
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zhuce.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>

<div class="wrap">

    <header class="xq_header">
        <a href="shenqing.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>申请合伙人</p>
    </header>
    <div class="xinxi">
        <p>1.注册信息</p>
        <p>2.支付订单</p>
        <p>3.支付订单</p>
    </div>
    <p class="xuanze">
        选择商品：<span>${skuName}</span>
    </p>
    <main>
        <section class="sec1">
            <div>
                <p>申请人信息　<b style="color:#999999">您可以凭手机号登录麦链商城</b></p>
            </div>
            <div>
                <p>姓名： <input id="name" name="name" type="text" placeholder=""></p>
            </div>

            <div>
                <p>手机号： <input id="mobile" name="mobile" type="text"></p>
            </div>
            <div>
                <p>验证码： <input type="text"><span>获取验证码</span></p>
            </div>

            <div>
                <p>微信号：<input id="weixinId" name="weixinId" type="text"></p>
            </div>
            <div>
                <p>上级合伙人电话： <input id="parentMobile" name="parentMobile" type="text"></p>
            </div>
        </section>
        <section class="sec2">
            <h2>选择合伙人等级</h2>
            ${agentInfo}
        </section>
        <section class="sec3">
            <p>
                <input type="checkbox" id="fu" checked disabled>
                <label for="fu">我已同意《代理商注册协议》</label>
            </p>
        </section>
        <section class="sec4">
            <a href="javascript:;" onclick="submit()"> 下一步</a>
        </section>
    </main>

</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script>
    $(".sec2 p").on("click", function () {
        $(".sec2 p").removeClass("on")
        $(this).addClass("on");
    })
    var myScroll = new IScroll("body", {
        preventDefault: false
    })

    var submit = function () {
        var paraData = {};
        paraData.name = $("#name").val();
        paraData.mobile = $("#mobile").val();
        paraData.weixinId = $("#weixinId").val();
        paraData.parentMobile = $("#parentMobile").val();
        paraData.levelName = $(".sec2 .on label").html();
        paraData.amount = $(".sec2 .on [name='amount']").html();
        if (paraData.levelName == null || paraData.amount == null) {
            alert("NNN");
            return;
        }
        window.location.href = "<%=basePath%>border/registerConfirm?name='" + paraData.name
        + "&mobile=" + paraData.mobile
        + "&weixinId=" + paraData.weixinId
        + "&parentMobile=" + paraData.parentMobile
        + "&skuName=${skuName}"
        + "&levelName=" + paraData.levelName
        + "&amount=" + paraData.amount;
    }
</script>

</body>
</html>