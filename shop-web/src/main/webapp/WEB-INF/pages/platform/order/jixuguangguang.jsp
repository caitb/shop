<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
   <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/jixuguangguang.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
</head>
<body>
    <header class="xq_header" style="margin:0;">
           <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>申请试用 </p>            
    </header>
    <div class="drap">
        <p>提交成功</p>
        <h2>您的试用申请已经提交，审核结果会在1个工作日内下发到您的手机请注意查收。</h2>
    </div>
    <a href="<%=path%>/index" class="jixu">
                继续逛逛
            </a>
</body>
</html>