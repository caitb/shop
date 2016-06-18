<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/shengjishenhe.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                    <p>升级</p>            
        </header>
        <main>
            <h1>您的升级申请已经审核通过，请继续支付。</h1>
            <p>升级信息：</p>
            <div class="floor">
                <p>
                    产品名称：
                    <span>${upgradeDetail.skuName}</span>
                </p>
                <p>
                    当前级别：
                    <span>${upgradeDetail.currentAgentLevelName}</span>
                </p>
                <p>
                    申请级别：
                    <span>${upgradeDetail.applyAgentLevelName}</span>
                </p>
                <p>
                    上级代理：
                    <span>${upgradeDetail.pAgentName}</span>
                </p>
                <p>
                    商品数量：
                    <span>${upgradeDetail.quantity}</span>
                </p>
            </div>
            <p>
                需付金额：
            </p>
            <div class="floor2">
                <p>合计：<span>￥${upgradeDetail.totalPrice} </span></p>
                <h1><b>*</b>在${payDate}前（3天内）支付升级申请，逾期将取消升级申请</h1>
            </div>
            <button>
                下一步
            </button>
        </main>
    </div>
    <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
</body>
</html>