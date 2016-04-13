<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/bangding.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/zhengze.js"></script>
    <script src="<%=path%>/static/js/checkUtil.js"></script>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
   
    <div class="wrap">

        <header class="xq_header">
              <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                <p>绑定帐号</p>            
        </header>
        <div class="banner">
            <p><img src="<%=path%>/static/images/shouye_nav.png" alt=""></p>
            <h1>王平</h1>
            <h2>绑定账号后，您可以使用您的手机号码登录麦链商城</h2>
        </div>
        <h4 class="tishi">姓名有误</h4>
        <section class="input_t phone">
            <p>手机号：</p>
            <input type="text" class="tel">
        </section>
        <section class="input_t">
            <p>验证码：</p>
            <input type="text" class="yan">
            <h4>获取验证码</h4>
        </section>
        <section class="input_t mima">
            <p>密码：</p>
            <input type="password" class="password">
        </section>
        <p class="rodia">
            <input type="checkbox" id="fu" checked>
                <label for="fu">同意<a href="javascript:;">《代理商注册协议》</a></label>
        </p>
        <a href="bangdingchenggong.html" class="bd">绑定帐号</a>
    </div>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script>
        $("label").toggle(function(){
            $(this).addClass("on")
        },function(){
            $(this).removeClass("on")
        })
    </script>
</body>
</html>