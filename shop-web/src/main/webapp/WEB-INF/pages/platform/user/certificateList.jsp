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
    <title>我的证书</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wodezhengshu.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script>
        $(function () {
            var levelColor = $("em[name='agentLevel']").text();
            if (levelColor == "初级合伙人") {
                $("#levelColor").addClass("sec1");
            } else if (levelColor == "中级合伙人") {
                $("#levelColor").addClass("sec2");
            } else {
                $("#levelColor").addClass("sec2");
            }
        });
    </script>
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
                <div id="levelColor">
                    <p><span>合伙产品</span><b id="skuname">${cet.skuName}</b></p>
                    <p><span>合伙人等级：
                        <c:choose>
                            <c:when test="${cet.agentLevelId==1}">
                                <em  name="agentLevel">初级合伙人</em>
                            </c:when>
                            <c:when test="${cet.agentLevelId==2}">
                                <em  name="agentLevel">中级合伙人</em>
                            </c:when>
                            <c:when test="${cet.agentLevelId==3}">
                                <em  name="agentLevel">高级合伙人</em>
                            </c:when>
                        </c:choose>
                        </span><span>授权书状态：
                    <c:choose>
                        <c:when test="${cet.isCertificate==0}">
                            <em>未生成证书</em>
                        </c:when>
                        <c:when test="${cet.isCertificate==1 && cet.receivect==0}">
                            <em>领取证书</em>
                        </c:when>
                        <c:when test="${cet.isCertificate==1 && cet.pfUserCertificateInfo.status==0}">
                            <em>未审核</em>
                        </c:when>
                        <c:when test="${cet.isCertificate==1 && cet.pfUserCertificateInfo.status==1}">
                            <em>审核成功</em>
                        </c:when>
                        <c:when test="${cet.isCertificate==1 && cet.pfUserCertificateInfo.status==2}">
                            <em>审核失败</em>
                        </c:when>
                    </c:choose>
                    </span></p>
                    <c:choose>
                        <c:when test="${cet.isCertificate==0}">
                            <a href="<%=path%>/userCertificate/setUserCertificate.shtml/?userSkuId=${cet.id}"><img src="<%=path%>/static/images/rightgo.png" alt=""></a>
                        </c:when>
                        <c:when test="${cet.isCertificate==1 && cet.receivect==0}">
                            <a href="<%=path%>/userCertificate/add/${cet.id}"><img src="<%=path%>/static/images/rightgo.png" alt=""></a>
                        </c:when>
                        <c:when test="${cet.isCertificate==1 && cet.pfUserCertificateInfo.status==0}">
                            <a href="<%=path%>/userCertificate/ready/${cet.skuId}"><img src="<%=path%>/static/images/rightgo.png" alt=""></a>
                        </c:when>
                        <c:when test="${cet.isCertificate==1 && cet.pfUserCertificateInfo.status==1}">
                            <a href="<%=path%>/userCertificate/userct/${cet.id}"><img src="<%=path%>/static/images/rightgo.png" alt=""></a>
                        </c:when>
                        <c:when test="${cet.isCertificate==1 && cet.pfUserCertificateInfo.status==2}">
                            <a href="<%=path%>/userCertificate/fail/${cet.id}"><img src="<%=path%>/static/images/rightgo.png" alt=""></a>
                        </c:when>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
    </main>
</div>
</body>
</html>