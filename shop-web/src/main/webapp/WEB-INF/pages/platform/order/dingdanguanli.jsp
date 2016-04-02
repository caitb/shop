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
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/dingdanguanli.css">
</head>
<body>
    <div class="wrap">
        <header class="xq_header">
                   <a href="<%=path%>/index"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>我的订单</p>  
        </header>
        <main>
            <div class="sec1">
                <h1>进货订单</h1>
                <ul>
                    <li>
                        <h1><img src="<%=path%>/static/images/fahuo.png" alt=""><c:if test="${pfBorders10 !=0}"><span>${pfBorders10}</span></c:if></h1>
                        <h1>等待发货</h1>
                    </li>
                    <li>
                        <h1><img src="<%=path%>/static/images/paidan.png" alt=""><c:if test="${pfBorders6 !=0}"><span>${pfBorders6}</span></c:if></h1>
                        <h1>我在拍单</h1>
                    </li>
                    <li>
                        <h1><img src="<%=path%>/static/images/fukuan.png" alt=""><c:if test="${pfBorders0 !=0}"><span>${pfBorders0}</span></c:if></h1>
                        <h1>需要我付款</h1>
                    </li>
                </ul>
                <ul>
                <li>
                    <h1><img src="<%=path%>/static/images/shouhuo.png" alt=""><c:if test="${pfBorders8 !=0}"><span>${pfBorders8}</span></c:if></h1>
                    <h1>需要我收货</h1>
                </li>
                <li><a href="<%=path%>/border/stockBorder">
                    <h1>查看全部》</h1></a>
                </li>
                <li style="background:#f6f6f6"></li>
                </ul>
            </div>
            <div class="sec1">
                <h1>出货订单</h1>
                <ul>
                    <li>
                        <h1><img src="<%=path%>/static/images/wofahuo.png" alt=""><c:if test="${pfBorderps10 !=0}"><span>${pfBorderps10}</span></c:if></h1>
                        <h1>我要发货</h1>
                    </li>
                    <li>
                        <h1><img src="<%=path%>/static/images/paidan.png" alt=""><c:if test="${pfBorderps6 !=0}"><span>${pfBorderps6}</span></c:if></h1>
                        <h1>我收到排单</h1>
                    </li>
                    <li>
                        <h1><img src="<%=path%>/static/images/fukuan.png" alt=""><c:if test="${pfBorderps0 !=0}"><span>${pfBorderps0}</span></c:if></h1>
                        <h1>需要对方付款</h1>
                    </li>
                </ul>
                <ul>
                <li>
                    <h1><img src="<%=path%>/static/images/shouhuo.png" alt=""><c:if test="${pfBorderps8 !=0}"><span>${pfBorderps8}</span></c:if></h1>
                    <h1>需要对方收货</h1>
                </li>
                <li><a href="<%=path%>/border/deliveryBorder">
                    <h1>查看全部》</h1></a>
                </li>
                <li style="background:#f6f6f6"></li>
                </ul>
            </div>
        </main>
    </div>
</body>
</html>