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
    <link rel="stylesheet" href="<%=path%>/static/css/zhuce2.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="zhuce.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
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
                <p>姓名： <b>${name}</b></p>
            </div>
            <div>
                <p>手机号： <b>${mobile}</b><span>(已验证)</span></p>
            </div>
            <div>
                <p>微信： <b>${weixinId}</b></p>
            </div>
        </section>
        <section class="sec2">
            <h2>合伙人信息</h2>
            <p>合伙人等级： <b>
                <lable levelId="${levelId}">${levelName}</lable>
            </b></p>
            <p>需要缴纳货款： <b>￥${amount}</b></p>
            <p>上级合伙人手机号： <b>${parentMobile}</b><span>${pName}</span></p>
        </section>

    </main>
    <div class="footer">
        <section class="sec3">
            <p><a href="zhuce.html">返回修改</a></p>
            <p><a href="javascript:;" onclick="submit()">确定</a></p>
        </section>
    </div>
</div>
</body>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script>
    var myScroll = new IScroll("body", {
        preventDefault: false
    })
    var submit = function () {
            var paraData = {};
            paraData.realName = "${name}";
            paraData.mobile = "${mobile}";
            paraData.weixinId = "${weixinId}";
            paraData.skuId = "${skuId}";
            paraData.levelId = "${levelId}";
            paraData.parentUserId = "${parentUserId}";
            paraData.userMassage = "";
        $.ajax({
            url: "<%=basePath%>border/registerConfirm/save.do",
            type: "post",
            data: paraData,
            dataType: "json",
            success: function (data) {
                if (data.isError == false) {
                    window.location.href = "<%=basePath%>" + data.url;
                }
                else {
                    alert(data.message);
                }
            }
        });
    }
</script>
</html>