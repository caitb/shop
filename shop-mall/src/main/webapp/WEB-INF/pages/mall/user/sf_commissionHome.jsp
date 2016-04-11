<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/base.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/wodeyongjin.css">
    <script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script type="application/javascript" src="${path}/static/js/common/commonAjax.js"></script>
    <script type="application/javascript" src="${path}/static/js/pageJs/sf_commission.js"></script>
    <script type="application/javascript">
        var path = "${path}";
        var basepath = "${basePath}";
    </script>
</head>
<body>
    <header>
        <a href="javascript:history.back(-1)"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                <p>个人信息</p>
    </header>
    <input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
    <input type="hidden" id="totalCount" name="totalCount" value="${totalCount}"/>
    <div class="wrap">
        <nav>
            <ul>
                <li>
                    <p>可提现<span onclick="withdraw(${userAccount.userId},${userAccount.extractableFee})" >申请提现</span></p>
                    <h1><span>￥</span>${userAccount.extractableFee}</h1>
                    <h2><a href="${basepath}withdraw/withdrawRecord.shtml">提现记录</a></h2>
                </li>
                <li>
                    <p>结算中：</p>
                    <h1><span>￥</span>${userAccount.countingFee}</h1>
                    <h2>查看说明</h2>
                </li>
            </ul>
        </nav>
        <div class="sec1">
            <p>佣金记录</p>
            <p>共<span>${totalCount}</span>笔记录</p>
        </div>
        <div id="itemDistributions">
            <c:forEach var="distribution" items="${orderItemDistributions}">
                <div class="sec2">
                    <p>
                        <b>￥${distribution.distributionAmount}</b>
                        <b>
                            <span>${distribution.nkName}</span>
                            在您的分享
                            <a href="" onclick="showDetail(${distribution.skuId})">${distribution.skuName}</a>
                            中产生了购买
                        </b>
                    </p>
                    <h1>
                        <span><fmt:formatDate value="${distribution.orderTime}"  type="time" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                    </h1>
                </div>
            </c:forEach>
        </div>
        <%--<p style="text-align: center;"><a href="#" onclick="viewMore(${userAccount.userId},${fn:length(orderItemDistributions)})">查看更多></a></p>--%>
        <p style="text-align: center;"><a href="#" onclick="viewMore(${userAccount.userId})">查看更多></a></p>
    </div>
    <div class="back_j">
        <p class="biao">绑定账号</p>
        <div>
            <p>手机号：<input type="tel"></p>
            <em>asd</em>
        </div>
        <div class="d">
            <p>验证码：<input type="number"><button>获取验证码</button></p>
            <em>asd</em>
        </div>
        <p class="tishi"></p>
        <h1 class="j_qu">下一步</h1>
    </div>
    <div class="back">
    </div>
</body>
</html>