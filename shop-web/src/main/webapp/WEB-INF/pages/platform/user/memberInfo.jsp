<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/duiyuanxinxi.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%=basePath%>myteam/teamdetail?userSkuId=${pid}"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>队员信息</p>
    </header>
    <main>
        <div class="nav">
            <p>
                <span>${memberMap.stock}</span>
                <span>进货次数</span>
            </p>
            <p>
                <span>${memberMap.totalAmount}</span>
                <span>进货总额</span>
            </p>
            <p>
                <span>${memberMap.countChild}</span>
                <span>ta的下级合伙人</span>
            </p>
        </div>
        <div class="sec1">
            <div>
                <p>上级合伙人信息详情</p>
                <h1><span>${memberMap.comUserName}</span>${memberMap.skuName}</h1>
                <h2><img src="<%=basePath%>static/images/lv.png" alt="">合伙人等级<b>${memberMap.skuName}</b></h2>
            </div>
            <p><span>等级</span><span>${memberMap.agentLevelName}</span></p>
            <p><span>手机号</span><span>${memberMap.mobile}</span></p>
            <p><span>微信号</span><span>${memberMap.weixin}</span></p>
            <p><span>实名认证</span><span class="four">
                <c:if test="${memberMap.idCardFrontImg == 0}">
                    <img src="<%=basePath%>static/images/guanli.png" alt="">未审核
                </c:if>
                <c:if test="${memberMap.auditStatus == 1}">
                    <img src="<%=basePath%>static/images/guanli.png" alt="">审核中
                </c:if>
                <c:if test="${memberMap.auditStatus == 2}">
                    <img src="<%=basePath%>static/images/guanli.png" alt="">审核通过
                </c:if>
                <c:if test="${memberMap.auditStatus == 3}">
                    <img src="<%=basePath%>static/images/guanli.png" alt="">审核不通过
                </c:if>
            </span></p>
            <p><span>身份证号</span><span>${memberMap.idCard}</span></p>
            <p><span>身份证扫描件</span><span class="four">
                <c:if test="${memberMap.idCardImg != null}">
                    已上传
                </c:if>
                <c:if test="${memberMap.idCardImg == null}">
                    未上传
                </c:if>
            </span></p>
            <p><span>授权证书</span><span><b><img src="${memberMap.certificateImg}" alt=""></b></span></p>
            <p><span>加入时间</span><span><fmt:formatDate value="${memberMap.applyTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></span></p>
        </div>
    </main>
</div>

</body>
</html>