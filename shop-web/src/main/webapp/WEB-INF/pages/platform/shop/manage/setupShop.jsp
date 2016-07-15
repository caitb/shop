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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/setshop.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
</head>
<body>

<form action="<%=basePath%>shop/manage/updateShop" method="post" id="shopForm" enctype="multipart/form-data">
    <div class="wrap">
        <header class="xq_header">
            <a href="index.html"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
            <p>店铺设置</p>
        </header>
        <main>
            <input type="hidden" name="Referer" value="<%=request.getHeader("Referer")%>" >
            <div class="floor">
                <p>店铺名称：</p>
                <input type="hidden" name="id" value="${sfShop.id}">
                <input type="text" name="name" value="${sfShop.name}" id="name">
                <h1>不超过10个字</h1>
            </div>
            <div class="floor">
                <p>店铺介绍：</p>
                <textarea name="explanation" value="${sfShop.explanation}" id="explanation" cols="30" rows="10">${sfShop.explanation}</textarea>
                <h1>不超过25个字</h1>
            </div>
            <div class="floor2">
                <p>店主微信：</p>
                <label for="qrImg"><img src="<%=basePath%>static/images/images/set_1.png" alt=""><b class="wei">未选择文件</b></label>
                <input type="file" id="qrImg" name="qrImg">
                <h1 onclick="clickShow()">如何获取微信二维码？</h1>
            </div>
            <div class="floor">
                <p>二维码描述：</p>
                <textarea name="wxQrCodeDescription">${sfShop.wxQrCodeDescription}</textarea>
                <h1>不超过30个字</h1>
            </div>
        </main>
        <div class="zhu">
            <p>注：</p>
            <h1>请勿上传政治、色情、暴力、低俗等与违法信息相关的图片和介绍，违反规定扣除保证金、严重的将取消代理资格。</h1>
        </div>
        <button>保存</button>
    </div>
</form>
<div class="black">
    <div class="backb"></div>
    <div class="set">
        <h1>如何获取二维码？</h1>
        <p><img src="${path}/static/images/wechat.png" alt=""></p>
        <button onclick="clickHide()">我知道了</button>
    </div>
</div>
</body>
<script src="<%=basePath%>static/js/jquery/jquery-1.8.3.min.js"></script>
<script>
    $("input").on("change",function(){
        $(".wei").html("已选择")
    })
    $('input[name="name"]').keyup(function(){
        if($(this).val().length>10){
            $(this).val($(this).val().substring(0,10));
        }
    });
    $('textarea[name="explanation"]').keyup(function(){
        if($(this).val().length>25){
            $(this).val($(this).val().substring(0,25));
        }
    });
    $('textarea[name="wxQrCodeDescription"]').keyup(function(){
        if($(this).val().length>30){
            $(this).val($(this).val().substring(0,30));
        }
    });
    function clickShow(){
        $(".black").show();
    }
    function clickHide(){
        $(".black").hide();
    }
</script>
</html>