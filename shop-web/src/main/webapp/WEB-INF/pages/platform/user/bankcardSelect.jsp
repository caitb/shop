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
    <link rel="stylesheet" href="<%=path%>/static/css/tixian.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
            <a href="#" onClick="javascript :history.go(-1);"><img src="../images/xq_rt.png" alt=""></a>
            <p>选择银行卡</p>            
            </header>
            <div class="xinz">
                    <p><a href="../html/xinjianka.html" style="color:#333;">新增银行卡</a></p>
            </div>
       <div class="main">
                <img src="../images/icon_56.png" alt="">
                <p>您还没有银行卡，马上新增一个银行卡吧！</p>
        </div>
        <main>
            <p>选择提现银行卡</p>
            <div class="sec1">
                <img src="../images/icon_57.png" alt="">
                <p>
                    <span><em>交通银行</em></span>
                    <span>持卡人:<b>张云</b>卡号:<b>1231231231231231233</b></span>
                </p>
            </div>
            <div class="sec1">
                <img src="../images/icon_57.png" alt="">
                <p>
                    <span><em>交通银行</em></span>
                    <span>持卡人:<b>张云</b>卡号:<b>1231231231231231233</b></span>
                </p>
            </div>
            <div class="sec1">
                <img src="../images/icon_57.png" alt="">
                <p>
                    <span><em>交通银行</em></span>
                    <span>持卡人:<b>张云</b>卡号:<b>1231231231231231233</b></span>
                </p>
            </div>
        </main>
     </div>
     <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
     <script>
        $(".sec1").on("click",function(){
            $(this).addClass("on").siblings().removeClass("on")
        })
    </script>
</body>
</html>