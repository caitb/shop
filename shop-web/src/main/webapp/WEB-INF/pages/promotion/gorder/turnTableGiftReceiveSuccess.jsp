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
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/success.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="${path}/turnTableDetailShow/getTurnTableInfo.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>领取成功</p>            
        </header>
        <main>
            <img src="<%=path%>/static/images/icon_66.png" alt="">
            <h1>领取成功</h1>
            <p>您的奖品已经领取成功，我们将尽快安排发货，请注意查收！</p>
            <h2><a href="${path}/turnTableDetailShow/getTurnTableInfo.html">返回转盘中心</a></h2>
        </main>
    </div>
   <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
</body>
</html>