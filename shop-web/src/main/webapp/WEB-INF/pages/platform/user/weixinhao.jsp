<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/weixinhao.css">
    
</head>
<body>
    <div class="wrap">
        <header class="xq_header">
                   <a href="javascript:window.history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                   <p>微信号</p>  
        </header>
        <main>
            <p>您的证书对应的微信号</p>
        <div class="sec1">
            <c:forEach items="${pfUserCertificates}" var="pcf">
                <p><span>  <c:out value="${pcf.comSku.name}" /> </span><span>  <c:out value="${pcf.wxId}"/>  </span></p>
            </c:forEach>
        </div>
        </main>
    </div>
    
</body>
</html>