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
    <link rel="stylesheet" href="<%=basePath%>static/css/pageCss/shouye.css">
</head>
<body>
<div class="wrap">
    <div class="na">
        <p></p>
        <h1>
            <span>俺是快乐的金卡了手机打开了速度</span>
            <span>娶我uepqowiepowqi</span>
        </h1>
    </div>
    <div class="header">
        <div>
            <p>王平的小店</p>
            <p>专营各类化妆品，欢迎大家选购！</p>
            <img src="<%=basePath%>static/images/fen.png" alt="">
        </div>
        <div>
            <p>
                <span>麦链商城光放认证</span>
                <span>麦链商城光放认证</span>
            </p>
            <p>
                <span style="background:url('<%=basePath%>static/images/f.png')no-repeat 0;background-size: 14px 14px;">麦链商城光放认证</span>
            </p>
        </div>
        <img src="<%=basePath%>static/images/admin.png" alt="">
    </div>
    <div class="banner">
        <p id="shout">
            <span>已有</span>
            <span><em>1054321</em>人</span>
            <span>为ta呐喊</span>
            <img src="<%=basePath%>static/images/an.png" alt="">
        </p>
    </div>
    <div class="content">
        <h1>在售商品</h1>
        <section class="sec1">
            <p class="photo">
                <img src="<%=basePath%>static/images/haohuo.png" alt="">
            </p>
            <div>
                <h2>王平的小店</h2>
                <h3>30秒瘦脸立即见效</h3>
                <h2>运费：<span>15.00</span><b>￥298.00</b></h2>
                <p>
                    <button>立即购买</button>
                </p>
            </div>
        </section>
        <section class="sec1">
            <p class="photo">
                <img src="<%=basePath%>static/images/haohuo.png" alt="">
            </p>
            <div>
                <h2>王平的小店</h2>
                <h3>30秒瘦脸立即见效</h3>
                <h2>运费：<span>15.00</span><b>￥298.00</b></h2>
                <p>
                    <button>立即购买</button>
                </p>
            </div>
        </section>
        <section class="sec1">
            <p class="photo">
                <img src="<%=basePath%>static/images/haohuo.png" alt="">
            </p>
            <div>
                <h2>王平的小店</h2>
                <h3>30秒瘦脸立即见效</h3>
                <h2>运费：<span>15.00</span><b>￥298.00</b></h2>
                <p>
                    <button>立即购买</button>
                </p>
            </div>
        </section>
    </div>
    <footer>
        <div>
            <p class="active">
                <span><img src="<%=basePath%>static/images/footer_x(3).png" alt=""></span>
                <span>首页</span>
            </p>
            <p>
                <span><img src="<%=basePath%>static/images/footer(1).png" alt=""></span>
                <span>分享计划</span>
            </p>
            <p>
                <span><img src="<%=basePath%>static/images/footer(2).png" alt=""></span>
                <span>个人中心</span>
            </p>
        </div>
    </footer>
</div>
<div class="back_f" id="shoutAlert">
    <h1 id="result"></h1>
    <img src="<%=basePath%>static/images/qwe(1).png" alt="">
    <p>分享到店铺到朋友圈，为您的朋友呐喊，通过您分享的链接产生购买后，您将获得佣金</p>
    <button onclick="javascript:window.location.replace('<%=basePath%>shop/getPoster?shopId=1');">获取我的专属海报</button>
    <span class="close">×</span>
</div>
<div class="back"></div>
<script src="<%=basePath%>static/js/plugins/jquery/jquery-1.8.3.min.js"></script>
<script>
    $(".close").on("click",function(){
        $(this).parent().hide();
        $(".back").hide();
    });
    $('#shout').on('click', function(){
        $.ajax({
            url: '<%=basePath%>shop/shout',
            data: {shopId: 1},
            success: function(msg){
                if(msg == 'true'){
                    $('#result').html('呐喊成功!');
                }else{
                    $('#result').html('您已呐喊过，请明天再来');
                }
                $('#shoutAlert').show();
            }
        });
    });
</script>
</body>
</html>