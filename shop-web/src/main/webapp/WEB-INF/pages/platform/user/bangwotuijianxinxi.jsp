<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/tuijianxinxi.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="javascript:window.history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>推荐信息</p>            
        </header>
        <main>
            <div class="floor">
                <p>
                    推荐人：　${userName}
                </p>
                <p>
                    产　品：　${skuName}
                </p>
            </div>
             <div class="floor2">
                <h1>被推荐人列表<span>${number}人</span></h1>
                 <c:forEach items="${certificates}" var="certificate">
                <div class="sec1">
                    <div>
                        <p>成员信息详情</p>
                        <h1><span>${certificate.userName}</span><img src="<%=path%>/static/images/lv.png" alt="">等级<b>${certificate.agentName}</b></h1>
                    </div>
                    <p class="s_1"><span>手机号：</span><span>${certificate.mobile}</span></p>
                    <p><span>微信号：</span><span>${certificate.wxId}</span></p>
                    <p><span>加入时间：</span><span><fmt:formatDate value="${certificate.beginTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate> </span></p>
                </div>
                 </c:forEach>
             </div>
        </main>
    </div>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
</body>
</html>