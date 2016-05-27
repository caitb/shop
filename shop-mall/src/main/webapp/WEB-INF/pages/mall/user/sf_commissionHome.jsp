<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/wodeyongjin.css">
</head>
<body>
    <header>
        <a href="javascript:window.location.href='${basepath}sfOrderManagerController/borderManagement.html'"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>我的佣金</p>
    </header>
    <input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
    <input type="hidden" id="totalCount" name="totalCount" value="${totalCount}"/>
    <div class="wrap">
        <nav>
            <ul>
                <li>
                    <%--<p>可提现<span onclick="withdraw(${userAccount.userId},${userAccount.extractableFee})" >申请提现</span></p>--%>
                    <p>可提现<span onclick="validateCodeJS.applyTrial('withdrawRequest')" >申请提现</span></p>
                    <h1><span>￥</span>${userAccount.extractableFee}</h1>
                    <h2><a href="${basepath}withdraw/withdrawRecord.shtml">提现记录</a></h2>
                </li>
                <li>
                    <p>结算中：</p>
                    <h1><span>￥</span>${userAccount.countingFee}</h1>
                    <h2><a onclick="showDetail()">查看说明</a></h2>
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
                        <b>￥${distribution.distributionAmount}&nbsp;&nbsp;</b>
                        <b>
                            <span>${distribution.nkName}</span>在您的分享<a href="#">${distribution.skuName}</a>中产生了购买
                        </b>
                    </p>
                    <h1>
                        <span><fmt:formatDate value="${distribution.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                    </h1>
                </div>
            </c:forEach>
        </div>
        <%--<p style="text-align: center;"><a href="#" onclick="viewMore(${userAccount.userId},${fn:length(orderItemDistributions)})">查看更多></a></p>--%>
        <p id="showMore" style="text-align: center;">
            <c:if test="${orderItemDistributions != null && fn:length(orderItemDistributions) < totalCount}">
                <a href="#" onclick="viewMore(${userAccount.userId})">查看更多></a>
            </c:if>
        </p>
    </div>
    <div class="back_j" style="display: none">
        <span class="close">×</span>
        <p class="biao">绑定手机号</p>
        <div>
            <p>手机号：<input type="tel" class="phone" id="phoneId"></p>
        </div>
        <div class="d">
            <p>验证码：<input type="tel" id="validateNumberDataId">
                <button id="validateNumberId">获取验证码</button>
            </p>
        </div>
        <p class="tishi" id="errorMessageId"></p>
        <h1 class="j_qu" id="nextPageId">下一步</h1>
    </div>
    <div class="back_s" id="detail" style="display: none">
        <h1>什么是结算中</h1>
        <p>
            您的资金在对方确认收货后7天内属于结算中，7天后将自动转到可提现。
        </p>
        <button onClick="hideDetail()">我知道了</button>
    </div>
    <div class="back">
    </div>
    <script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script type="application/javascript" src="${path}/static/js/common/commonAjax.js"></script>
    <script type="application/javascript" src="${path}/static/js/common/definedAlertWindow.js"></script>
    <script type="application/javascript" src="${path}/static/js/pageJs/sf_commission.js"></script>
    <script type="application/javascript" src="${path}/static/js/plugins/validateCode.js"></script>
    <script type="application/javascript" src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="${path}/static/js/pageJs/hideWXShare.js"></script>
    <script type="application/javascript">
        var path = "${path}";
        var basepath = "${basePath}";
        $(document).ready(function () {
            validateCodeJS.initPage();
        });
    </script>
</body>
</html>