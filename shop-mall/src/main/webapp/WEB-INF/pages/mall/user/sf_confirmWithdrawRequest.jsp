<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/base.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/querenshenqing.css">
    <link rel="stylesheet" href="${path}/static/css/devCss/loading.css">
    <style>
        a{
            background: url("${path}/static/images/icon_37.png") no-repeat 0;
            background-size: 13px 13px;
            padding-left: 18px;
            margin-top: 2rem;
        }
        h1{
            margin: 0 10%;
            margin-bottom: 8rem;
            line-height: 15px;
            margin-top: 2rem;
        }
    </style>
</head>
<body>
   <div class="wrap">
       <img src="${path}/static/images/icon_66.png" alt="">
       <p>提现申请已经提交</p>
       <h1>您的提现申请提交成功，工作人员会在一个工作日内处理，提现结果会通过短信和微信的方式通知您，请注意查收。</h1>
       <a href="${basepath}index">返回首页</a>
    </div>
</body>
</html>