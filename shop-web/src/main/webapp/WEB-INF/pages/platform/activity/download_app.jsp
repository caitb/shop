<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <style>
        img{
            width: 100%;
            display: block;
        }
        button{
            background:#000000;
            border-radius:3px;
            border:1px solid #9D814F;
            color:#9D814F;
            display:block;
            margin:20px auto;
            width:80%;
            padding:8px 0;
        }
    </style>
</head>
<body>
    <img src="<%=path%>/static/images/activity/app_promotion/store.jpg" alt="" />
    <button>立 即 下 载</button>
</body>
</html>
