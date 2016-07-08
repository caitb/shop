<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/radio.css">
    <script type="application/javascript">
        var path = "${path}";
    </script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>新建群发</p>
    </header>
    <main>
        <div class="floor">
            <h1>推送给：</h1>
            <div>
                <p class="on">
                    <span>2000000</span>
                    <span>全部粉丝</span>
                    <img src="${path}/static/images/message/message_4.jpg" alt="">
                </p>
                <p>
                    <span>2000000</span>
                    <span>全部代言人</span>
                    <img src="${path}/static/images/message/message_4.jpg" alt="">
                </p>
            </div>
        </div>
        <div class="floor2">
            <h1>消息内容：</h1>
            <div>
                <textarea id="textarea" onkeydown="LimitTextArea(this)" onkeyup="LimitTextArea(this)" onkeypress="LimitTextArea(this)"></textarea>
                <button onclick="clickShow()">
                    <b>跳转网址(已设置，跳转到您的店铺首页)</b>
                </button>
            </div>
        </div>
        <h1>您还可以输入<span class="textlength">140</span>/140字</h1>
        <p>*禁止发送与政治、色情、暴力、等违法内容，违者必究！</p>
        <button class="btn">
            发送
        </button>
    </main>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="see">
        <h1>设置“点击查看”网址</h1>
        <p>点击查看网址设置后，粉丝在查看信息的时候可以点击链接访问。为了保证良好的体验，目前跳转地址只能设置为麦链商城站内地址。</p>
        <div class="s_b">
            <div class="b_l">
                设置跳转地址：
            </div>
            <div class="b_r">
                <p>
                    <span class="on">您的店铺首页</span>
                    <span>抗引力瘦脸精华详情页</span>
                    <span>抗引力瘦脸精华2详情页</span>
                </p>
            </div>
        </div>
        <h2>
            <span onclick="clickHide()">取消</span>
            <span >确定</span>
        </h2>
    </div>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script>
    $(function(){
        $(".b_r p").on("click","span",function(){
            $(this).addClass("on").siblings().removeClass("on");
        });

        $(".floor div").on("click","p",function(){
            $(this).addClass("on").siblings().removeClass("on");
        });
    });

    function LimitTextArea(field){
        var maxlimit = 140;
        var Length = field.value.length;
        if (Length > maxlimit) {
            field.value = field.value.substring(0, maxlimit);
            alert("请不要超过最大长度:" + maxlimit);
        }

        if(Length >= maxlimit){
            Length = maxlimit;
        }
        $(".textlength").html(maxlimit-Length);
    }

    function clickHide(){
        $(".black").hide();
    }
    function clickShow(){
        $(".black").show();
    }
</script>
</body>
</html>
