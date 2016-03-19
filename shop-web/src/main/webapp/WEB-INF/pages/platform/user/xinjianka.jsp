<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/xinjianka.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
            <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>添加银行卡</p>            
        </header>
    <main>
            <p>新增银行卡信息</p>
            <h1>银行卡号：<input type="text" placeholder="填写您的卡号"></h1>
            <h1>银行名称：<select><option value="">请选择银行</option></select></h1>
            <h1>开户行名称 ：<input type="text" placeholder="输入您的开户行名称"></h1>
            <h1>持卡人姓名：<input type="text" placeholder="输入持卡人姓名"></h1>
            <botton>
                提现
            </botton>
    </main>
</body>
</html>