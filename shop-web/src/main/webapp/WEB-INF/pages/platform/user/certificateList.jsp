<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wodezhengshu.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%= request.getHeader("REFERER") %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>我的证书</p>
    </header>
    <main>
        <div id="box">
            <c:forEach items="${pfUserCertificates}" var="cet">
                <c:choose>
                    <c:when test="${cet.agentLevelId==1}">
                        <div class="big">
                            <div class="sec1">
                                    <%--                            <p><span>合伙产品</span><b>${cet.skuName}</b></p>

                                                                <p><span>合伙人等级：
                                                                    <em name="agentLevel">高级合伙人</em>
                                                                    <em name="upperName"><a href="<%=path%>/userCertificate/userInfo.list/?uskId=${cet.pid}">${cet.upperName}</a></em>
                                                            </span></p>
                                                                <a href="<%=path%>/userCertificate/detail/?pfuId=${cet.id}"><img
                                                                        src="<%=path%>/static/images/rightgo.png" alt=""></a>--%>
                                <p>合伙产品</p>
                                <h1>抗引力—瘦脸精华</h1>
                            </div>
                            <div class="small">
                                <div class="left">
                                    <p>上级合伙人：<span>王平</span></p>
                                    <h1>合伙人等级：<span>中介合伙人</span>保证金：<span>1000.00</span>元</h1>
                                </div>
                                <div class="right">
                                    <p><img src="<%=path%>/static/images/see.png" alt="">查看证书</p>

                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${cet.agentLevelId==2}">
                        <div class="sec2">
                            <p><span>合伙产品</span><b>${cet.skuName}</b></p>

                            <p><span>合伙人等级：
                                <em name="agentLevel">中级合伙人</em>
                        </span></p>
                            <a href="<%=path%>/userCertificate/detail/?pfuId=${cet.id}"><img
                                    src="<%=path%>/static/images/rightgo.png" alt=""></a>
                        </div>
                    </c:when>
                    <c:when test="${cet.agentLevelId==3}">
                        <div class="sec2">
                            <p><span>合伙产品</span><b>${cet.skuName}</b></p>

                            <p><span>合伙人等级：
                                <em name="agentLevel">初级合伙人</em>
                        </span></p>
                        </div>
                    </c:when>
                </c:choose>
            </c:forEach>
        </div>
    </main>
</div>
</body>
</html>