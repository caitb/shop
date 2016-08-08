<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <style>
        img{
            width: 100%;
            display: block;
        }
        button{
            display: block;
            background: #f3bd5d;
            color: #333;
            width: 70%;
            padding: 2.5% 0;
            margin: 10px auto;
            border-radius: 5px;
            margin-top: 15px;
        }
        p{
            color: #666;
            font-size: 12px;
            text-align: center;
            margin: 15px 0 25px 0;
        }
    </style>
</head>
<body>
<img src="${path}/static/images/activity/qixi/banner.jpg" alt="" />
<img src="${path}/static/images/activity/qixi/main.jpg" alt="" />
<img src="${path}/static/images/activity/qixi/foter.jpg" alt="" />
<button id="btn">
    我要成为命中注定之人
</button>
<p>(本活动最终解释权归北京麦链网络科技有限公司所有)</p>
</body>
<script>
    document.getElementById("btn").onclick = function(){
        window.location.href = "${path}/shop/manage/index";
    }
</script>
</html>
