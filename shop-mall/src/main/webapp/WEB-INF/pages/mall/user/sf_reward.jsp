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
    <link rel="stylesheet" href="${path}/static/css/pageCss/reward.css">
</head>
<body>
    <header>
        <a href="javascript:window.location.href='${basepath}sfOrderManagerController/borderManagement.html'"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                <p>我的奖励</p>
    </header>
    <div class="wrap">
        <p>我的分享</p>
        <nav>
            <ul>
                <li>
                    <p>${oneShare}人(${oneBuy}人购买)</p>
                    <p>一级分享</p>
                </li>
                <li>
                    <p>${twoShare}人(${twoBuy}人购买)</p>
                    <p>二级分享</p>
                </li>
                <li>
                    <p>${threeShare}人(${threeBuy}人购买)</p>
                    <p>三级分享</p>
                </li>
            </ul>
        </nav>
        <p>我的财富</p>
        <h1>
            <span>￥<b><fmt:formatNumber value="${userAccount.extractableFee}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></b></span>
            <span>可提现的财富</span><span onclick="validateCodeJS.applyTrial('withdrawRequest')" >申请提现</span>
            <c:if test="${userAccount.appliedFee != null && userAccount.appliedFee > 0}">
                <span>（已申请提现财富：￥${userAccount.appliedFee}）</span>
            </c:if>
        </h1>
        <nav>
            <ul>
                <li>
                    <p class="ul_left">已提现的财富</p>
                    <p>￥<b><fmt:formatNumber value="${withdraw}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></b></p>
                    <span></span>
                </li>
                <li>
                    <p class="ul_left">已付款订单财富</p>
                    <p>￥<b><fmt:formatNumber value="${isPayDistribution}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></b></p>
                    <span></span>
                </li>
                <li>
                    <p class="ul_left">未付款订单财富</p>
                    <p>￥<b><fmt:formatNumber value="${isNotPayDistribution}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></b></p>
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
                    <h1>
                        <b>￥${distribution.distributionAmount}</b>
                        <span><fmt:formatDate value="${distribution.orderTime}" pattern="yyyy-MM-dd HH:mm" /></span>
                    </h1>
                    <p>
                        <b>
                            <span>${distribution.nkName}</span>在您的分享<a href="#">${distribution.skuName}</a>中产生了购买
                        </b>
                    </p>
                </div>
            </c:forEach>
        </div>
        <p id="showMore" style="text-align: center;">
            <c:if test="${orderItemDistributions != null && fn:length(orderItemDistributions) < totalCount}">
                <a href="#" onclick="viewMore()">查看更多></a>
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
    <div class="back">
    </div>

    <input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
    <input type="hidden" id="totalCount" name="totalCount" value="${totalCount}"/>

    <script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script type="application/javascript" src="${path}/static/js/common/commonAjax.js"></script>
    <script type="application/javascript" src="${path}/static/js/common/definedAlertWindow.js"></script>
    <script type="application/javascript" src="${path}/static/js/pageJs/sf_reward.js"></script>
    <script type="application/javascript" src="${path}/static/js/plugins/validateCode.js"></script>
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