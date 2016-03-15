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
    <link rel="stylesheet" href="<%=path%>/static/css/bangdingchenggong.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
   
    <div class="wrap">

       <header class="xq_header">
                  <a href="bangding.jsp"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>申请合伙人</p>            
            </header>
            <div class="drap">
                <img src="<%=path%>/static/images/yes.png" alt="">
                <p>恭喜您，帐号！绑定成功！</p>
            </div>
            <div class="main">
                <div>
                    <p>姓名：</p>
                    <p>${comUser.realName}</p>
                    <p></p>
                </div>
                <div>
                    <p>手机号：</p>
                    <p>${comUser.mobile}</p>
                    <p>已验证</p>
                </div>
                <div>
                    <p>密码：</p>
                    <p>${comUser.password}</p>
                    <p>已设置</p>
                </div>
            </div>
            <a href="<%=path%>/index" class="lingqu">返回首页</a>
    </div>
</body>
</html>