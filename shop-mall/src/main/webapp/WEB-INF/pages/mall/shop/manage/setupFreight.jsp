<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/reset.css">
    <style>
        .wrap{
            background: #fff;
        }
        .wrap>img{
            display: block;
            width: 90%;
            margin: 1em auto 1em auto;
            border-radius: 5px;
            box-shadow:1px 2px 2px 1px #ccc;
        }
        b{
            display: block;
            text-align: center;
            color: #F73C8C;
            width: 100%;
        }
        .wrap div{
            display: -webkit-box;
            padding: 10px;
        }
        .wrap div>p{
            padding-top: 10px;
        }
        .wrap div h1{
            text-indent: 25px;
            margin-left: 10px;
        }
        .wrap div h1 p{
            background: url("<%=basePath%>static/images/icon_14.png") no-repeat 0;
            background-size: 15px;
            padding: 10px 0;
        }
        .active{
            background: url("<%=basePath%>static/images/guanli.png") no-repeat 0;
            background-size: 15px;
        }
        .wrap div input{
            border-radius: 5px;
            border: 1px solid #ddd;
            padding: 5px 0;
        }
        button{
            background: #ED6734;
            width: 60%;
            line-height: 30px;
            margin-left: 20%;
            margin-top: 4em;
            color: white;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<header>
    <a href="index.html"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>运费设置</p>
</header>
<div class="wrap">
    <div>
        <p><span>运费设置</span></p>
        <h1>
            <p><label for="">包邮</label></p>
            <p><input type="text" placeholder="　　请输入金额"></p>
        </h1>
    </div>
    <button>返回</button>
</div>
</body>
</html>