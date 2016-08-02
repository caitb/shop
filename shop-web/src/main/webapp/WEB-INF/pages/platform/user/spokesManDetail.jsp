<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/spokesman.css">
</head>
<body>

<div class="wrap">
    <header class="xq_header">
        <a href="javascript:history.go(-1);" ><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>代言人详情</p>
    </header>
    <main>
        <div class="top">
            <h1>代言人详情</h1>
            <p>
                <strong>${detail.info.wxName}</strong>
                <b>是否购买：${detail.info.isBuyView}</b>
            </p>
            <p>
                <span>ID：${detail.info.ID}</span>
                <span>加入时间：${detail.info.createTimeView}</span>
            </p>
        </div>
        <div class="m_bottom">
            <div>
                <p>代言人总数：</p>
                <p>${detail.info.spokesManNum}</p>
            </div>
            <div>
                <p>一级代言人数：</p>
                <p>${detail.firstSpokesManNum}</p>
            </div>
            <div>
                <p>二级代言人数：</p>
                <p>${detail.secondSpokesManNum}</p>
            </div>
            <div>
                <p>粉丝总数：</p>
                <p>${detail.info.fansNum}</p>
            </div>
            <div>
                <p>一级粉丝数：</p>
                <p>${detail.firstFansNum}</p>
            </div>
            <div>
                <p>二级粉丝数：</p>
                <p>${detail.secondFansNum}</p>
            </div>
            <div>
                <p>三级粉丝数：</p>
                <p>${detail.thirdFansNum}</p>
            </div>
        </div>
    </main>
</div>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/hideWXShare.js"></script>
</body>
</html>