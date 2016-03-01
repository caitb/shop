<%--
  Created by IntelliJ IDEA.
  User: muchaofeng
  Date: 2016/3/1
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%-- 默认收货地址 --%>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/morenaddress.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>

</head>
<body>
<header class="xq_header">
    <a href="<%=path%>/pages/platform/index.jsp"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>默认收货地址</p>
</header>
<div>
    <p>收货人</p>
    <h2>陈消灾</h2>
</div>
<div>
    <p>手机号码</p>
    <h2>13121527850</h2>
</div>
<div>
    <p>邮政编码</p>
    <h2>100000</h2>
</div>
<div>
    <p>所在地址</p>
    <h2>北京市朝阳区</h2>
</div>
<div>
    <p>街道</p>
    <h2>朝阳门街道</h2>
</div>
<div>
    <p>详情地址</p>
    <h2>丰联广场8楼800A</h2>
</div>
<div>
    <h3>删除收货地址</h3>
</div>
<div>
    <h4></h4>
</div>

</body>
</html>