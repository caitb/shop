<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>

    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/gerenxinxi.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="${path}/index"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>个人信息</p>
    </header>
    <main>
        <div class="head_img" style="background: url('${path}/static/images/icon_27.png')">
            <p><img src="${comUser.wxHeadImg}" alt=""></p>
            <div><h1>${comUser.realName}</h1>
                <h1>
                    <span><img src="${path}/static/images/admin.png" alt="">等级A1</span>
                    <span><img src="${path}/static/images/admin.png" alt="">等级A1</span>
                </h1>
            </div>
        </div>
        <div class="sec1" style="background:white url('${path}/static/images/people.png') no-repeat 10px;  background-size: 13px;margin-top: 10px;">
            <p>手机号</p>
            <p>${comUser.mobile}</p>
        </div>
        <div class="sec1" style="background:white url('${path}/static/images/weixin.png') no-repeat 10px;  background-size: 20px;">
            <p>微信号</p>
            <p>已绑定 ${comUser.wxNkName}</p>
        </div>
        <div class="sec1" id = "identityAuthId" style="padding-left: 10px">
            <p>实名认证</p>
            <p><b>${auditStatusName}</b><img src="${path}/static/images/next.png" alt="" style="margin-top:4px"></p>
            <input id="auditStatusId" style="display: none" value="${comUser.auditStatus}" />
        </div>
        <div class="sec1" id="capitalId" style="background:white url('${path}/static/images/qianban.png') no-repeat 10px;  background-size: 20px;margin-bottom: 0;">
            <p>我的资金</p>
            <p><b>可提现 ￥${comUserAccount.extractableFee}</b><img src="${path}/static/images/next.png" alt="" style="    margin-top:4px"></p>
        </div>
        <div class="sec1" id="bankCardId" style="background:white url('${path}/static/images/yinh.png') no-repeat 10px;  background-size: 20px;">
            <p>我的银行卡</p>
            <p><img src="${path}/static/images/next.png" alt=""></p>
        </div>
        <div  id="addressManageId" class="sec1" style="padding-left: 10px;margin-bottom: 20px;">
            <p>地址管理</p>
            <p><img  src="${path}/static/images/next.png" alt=""></p>
        </div>
    </main>
    <div class="bottom">
        <footer>
            <div class="btm">
                <a href="index.html">
                    <span><img src="${path}/static/images/footer%20(2).png" alt=""></span>
                    <span>我是合伙人</span>
                </a>
            </div>
            <div class="btm">
                <a href="javascript:;">
                    <span><img src="${path}/static/images/footer%20(3).png" alt=""></span>
                    <span>我的店铺</span>
                </a>
            </div>
            <div class="btm" style="background: #DA3600;">
                <a href="${path}/personalInfo/personalHomePageInfo.html">
                    <span><img src="${path}/static/images/footer%20(1).png" alt=""></span>
                    <span>个人中心</span>
                </a>
            </div>
        </footer>
    </div>
</div>
</body>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script type="text/javascript" src="${path}/static/js/personalInfo.js"></script>
</html>