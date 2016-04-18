<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/lingquzhengshu.css">
</head>
<body>
<main>
    <div class="wrap">
        <header class="xq_header">
            <a href="zhifu.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>申请合伙人</p>
        </header>
        <div class="xinxi">
            <p>注册信息</p>
            <p style="color:#F74A11;">选择拿货方式</p>
            <p style="color:#F74A11;">支付订单</p>
        </div>
        <div class="main">
            <div class="drap">
                <h2><img src="${path}/static/images/ready.png" alt=""></h2>
                <p>恭喜您，申请成功！</p>
            </div>
            <c:choose>
                <c:when test="${isQueuing==true}">
                    <p>本次申请进入排单系统，支付订单时前面有<span>${count}</span>人预约。待产能提升，我们将按照付款先后顺序发货。
                        <a href="${basePath}borderManage/borderDetils.html?id=${pfBorder.id}"><b>查看订单</b></a></p>
                </c:when>
                <c:otherwise>
                    <c:if test="${pfBorder.sendType == 1}">
                        <p>您的在线库存已经变更 <span>+${quantity}</span></p>
                    </c:if>
                    <c:if test="${pfBorder.sendType == 2}">
                        <p>我们将尽快安排发货，请耐心等待。
                            <a href="${basePath}borderManage/borderDetils.html?id=${pfBorder.id}"><b>查看订单</b></a></p>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <div class="Name">
                <p>姓名：</p>
                <p>${realName}</p>
            </div>
            <div class="Name">
                <p>合作产品：</p>
                <p>${skuName}</p>
            </div>
            <div class="Name">
                <p>合伙人等级：</p>
                <p>${levelName}</p>
            </div>
            <div class="Name">
                <p>上级合伙人：</p>
                <p>${pRealName}</p>
            </div>
            <div class="Name">
                <p>拿货方式：</p>
                <p>${sendTypeName}</p>
            </div>
        </div>
        <p>您现在可以：</p>
        <div class="box">
            <a href="${basePath}">
                <div class="sec1">
                    <img src="${path}/static/images/zs%20(4).png" alt="">
                    <p>
                        <span>返回首页</span>
                        <span>使用您的合伙人特权吧</span>
                    </p>
                </div>
            </a>
            <a href="${path}/shop/manage/index">
                <div class="sec1">
                    <img src="${path}/static/images/zs%20(1).png" alt="">
                    <p>
                        <span>查看店铺</span>
                        <span>系统已经为您生成的店铺，请去管理吧</span>
                    </p>
                </div>
            </a>
            <a href="${path}/marketGood/market">
                <div class="sec1">
                    <img src="${path}/static/images/zs%20(3).png" alt="">
                    <p>
                        <span>返回市场</span>
                        <span>继续查看感兴趣的商品</span>
                    </p>
                </div>
            </a>
            <a href="${basePath}userCertificate/userList/${pfBorder.userId}">
                <div class="sec1">
                    <img src="${path}/static/images/zs%20(2).png" alt="">
                    <p>
                        <span>查看授权书</span>
                        <span>查看您的商品授权书</span>
                    </p>
                </div>
            </a>
        </div>
    </div>
</main>
</body>
</html>