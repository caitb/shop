<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/base.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/tixianjilu.css">
    <link rel="stylesheet" href="${path}/static/css/common/common.css">
    <link rel="stylesheet" href="${path}/static/css/common/dropload.css">
    <link rel="stylesheet" href="${path}/static/css/devCss/loading.css">
    <script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script type="application/javascript" src="${path}/static/js/common/commonAjax.js"></script>
    <script type="text/javascript" src="${path}/static/js/plugins/date.js" ></script>
    <script type="text/javascript" src="${path}/static/js/plugins/iscroll.js" ></script>
    <script type="text/javascript" src="${path}/static/js/plugins/dropload.min.js"></script>
    <script type="text/javascript" src="${path}/static/js/pageJs/sf_withdrawRecord.js"></script>
    <script type="text/javascript">
        var path = "${path}";
        var basepath = "${basepath}";
    </script>
</head>
<body>
<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}"/>
<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
<input type="hidden" id="year" name="year" value="${year}"/>
<input type="hidden" id="month" name="month" value="${month}"/>
    <header>
        <a href="javascript:history.back(-1)" onClick=""><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>提现记录</p>
    </header>
    <div class="wrap">
        <div class="sec1" id="sec1">
            <p>提现记录：<label for="beginTime"><b>${year}</b>年<b>${month}</b>月</label><input  id="beginTime" class="kbtn" style="display:none;"/></p>
            <div id="divall">
                <c:forEach items="${list}" var="sfUserExtractApply">
                    <div>
                        <p><span class="sd"><fmt:formatDate value="${sfUserExtractApply.applyTime}"  type="time" pattern="MM-yyyy"/></span><span>+${sfUserExtractApply.extractFee}</span></p>
                        <h1>
                            <span>微信提现</span>
                            <c:if test="${sfUserExtractApply.auditType == 0}"><span>审核中</span></c:if>
                            <c:if test="${sfUserExtractApply.auditType == 1}"><span>已拒绝</span></c:if>
                            <c:if test="${sfUserExtractApply.auditType == 2}"><span>待打款</span></c:if>
                            <c:if test="${sfUserExtractApply.auditType == 3}"><span>已付款</span></c:if>
                        </h1>
                    </div>
                </c:forEach>
                <%--<div class="dropload-load"><span class="loading"></span>加载中...</div>--%>
                <p style="text-align: center;"><a href="#" onclick="getMore()">查看更多></a></p>
            </div>
            <div id="datePlugin"></div>
        </div>
     </div>
</body>
</html>