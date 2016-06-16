<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wotuijianderen.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="<%=path%>/myRecommend/feeList"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>我推荐的人</p>            
        </header>
        <main>
            <div class="floor">
                <div>
                    <span>商品：</span>
                    <label for="goods" class="goods"><b></b></label>
                    <select id="goods">
                        <option value="">asdasd</option>
                        <option value="">13242</option>
                        <option value="">hgfds</option>
                        <option value="">7654</option>
                        <option value="">lkjhgfdsa</option>
                        <option value="">oiuytrewASDFGHJKL</option>
                    </select>
                    <span>等级：</span>
                    <label for="level" class="level"><b></b></label>
                    <select id="level">
                        <option value="">asdasd</option>
                        <option value="">13242</option>
                        <option value="">hgfds</option>
                        <option value="">7654</option>
                        <option value="">lkjhgfdsa</option>
                        <option value="">oiuytrewASDFGHJKL</option>
                    </select>
                </div>
                <button>查询</button>
            </div>
            <div class="sec1">
                <c:forEach items="${sumByUserPid}" var="sumByUserPid">
                <img src="${sumByUserPid.wxHeadImg}" alt="">
                <div>
                    <p>${sumByUserPid.name} <b>${sumByUserPid.agentName}</b></p>
                    <p>${sumByUserPid.skuName}</p>
                </div>
                </c:forEach>
            </div>
        </main>
    </div>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script>
        $(document).ready(function(){
            $(".goods b").html($("#goods option:selected").text());
            $(".level b").html($("#level option:selected").text());
        })
        $("#goods").on("change",function(){
            var tabVal=$("#goods option:selected").text();
            $(".goods b").html(tabVal);
        })
        $("#level").on("change",function(){
            var tabVal=$("#level option:selected").text();
            $(".level b").html(tabVal);
        })
    </script>
</body>
</html>