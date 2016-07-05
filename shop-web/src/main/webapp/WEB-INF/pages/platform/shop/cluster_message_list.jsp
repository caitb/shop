<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/message.css">
</head>
<body>

<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>群发消息</p>
        <a href=""><img src="${path}/static/images/message/xiaoxi_1.jpg" alt=""></a>
    </header>
    <main id="mlist">
        <div class="sec1">
            <p style="background:url('../images/admin.png');background-size:100% 100%;"></p>
            <div class="s_b">
                <h1>王平的小店</h1>
                <div class="b_b">
                    <img src="${path}/static/images/message/massage_r1_c1.png" alt="">
                    <h1>阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的阿斯达斯大苏打打扫打扫打扫的</h1>
                    <p>
                        <a href="">点击查看</a>
                        <a>2016-4-44 14:23</a>
                    </p>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
<script type="application/javascript" src="${path}/static/js/jquery-1.8.3.min.js" ></script>
<script type="application/javascript" src="${path}/static/js/commonAjax.js" ></script>
<script type="application/javascript" src="${path}/static/js/shop_message_list.js" ></script>
</html>
