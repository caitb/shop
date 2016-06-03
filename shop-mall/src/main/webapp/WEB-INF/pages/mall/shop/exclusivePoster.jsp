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
            width: 80%;
            margin: 1em auto 0 auto;
            border-radius: 5px;
            box-shadow:1px 2px 2px 1px #ccc;
        }
        b{
            display: block;
            text-align: center;
            color: #F73C8C;
            width: 100%;
            margin-top: 5%;
        }
        .back_box{
            width:100%;
            height:100%;
            position: fixed;
            top: 0;
            background: rgba(255,255,255,0);
            display: none;
        }
        .back{
            width:100%;
            height:100%;
            background: rgba(0,0,0,0.5);
            position: absolute;
            top:0;
        }
        .back_f{
            width:90%;
            position: fixed;
            left:5%;
            top: 18%;
            background: #fff;
            z-index:11;
            display: none;
        }
        .back_f>p{
            text-align: center;
            margin-top: 3em;
            font-size: 16px;
        }
        .back_f img{
            width: 90%;
            margin-left: 5%;
        }
        .back_f .close{
            position: absolute;
            right: 5px;
            top: 5px;
            width: 25px;
            height: 25px;
            border-radius: 15px;
            text-align: center;
            line-height: 21px;
            font-size: 26px;
            border: 1px solid #ccc;
            color: #999;
        }
        .na {
            display: -webkit-box;
            padding: 5px 10px;
            background: #F7E911;
            position: relative;
            box-shadow: 0px 4px 6px -4px rgba(0,0,0,0.5);
        }
        .na p {
            color: #F75140;
            font-size:12px;
        }
        .na p span{
            font-size: 12px;
            color: #421405;
            text-decoration: underline;
        }
        .na label{
            display: block;
            width: 20px;
            height: 20px;
            line-height: 19px;
            text-align: center;
            background: #FDFBCF;
            font-size: 16px;
            border-radius: 10px;
            color: #7C7A65;
            position: absolute;
            right: 5px;
            top: 4px;
        }
        .floor{
                background: url("/static/images/floorbg.png") no-repeat;
                background-size: 100% 100%;
            width:90%;
            margin:1em auto 0 auto;
            padding: 18% 0;
        }
        .floor2 {
            width: 90%;
            margin:1em auto 0 auto;
            padding: 25% 0;
        }
        .floor2 p{
            text-align: center;;
        }
    </style>
</head>
<body>
<header>
    <c:if test="${sessionScope.userPid == null}"><a href="javascript:window.history.go(-1);"></c:if>
    <c:if test="${sessionScope.userPid != null}"><a href="<%=basePath%>${sessionScope.shopId}/${sessionScope.userPid}/shop.shtml"></c:if>
        <img src="<%=basePath%>static/images/xq_rt.png" alt="">
    </a>
    <p>我的专属海报</p>
</header>
<div class="wrap">
        <div class="floor">

        </div>
        <c:if test="${shopPoster != null}">
        <img src="${shopPoster}">
        <b id="downloadPoster2">长按二维码保存图片到手机</b>
        </c:if>

        <c:if test="${shopPoster == null}">
        <div class="floor2">
            <p>由于网络原因,海报加载失败,请刷新。</p>
        </div>
        </c:if>
</div>
<div class="back_box">
    <div class="back"></div>
    <div class="back_f">
        <span class="close">×</span>
        <img src="${path}/static/images/duan.png" alt="">
    </div>
</div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=basePath%>static/js/plugins/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>static/js/pageJs/hideWXShare.js"></script>
<%--<script>--%>
    <%--/*--%>
     <%--* 注意：--%>
     <%--* 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。--%>
     <%--* 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。--%>
     <%--* 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html--%>
     <%--*--%>
     <%--* 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：--%>
     <%--* 邮箱地址：weixin-open@qq.com--%>
     <%--* 邮件主题：【微信JS-SDK反馈】具体问题--%>
     <%--* 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。--%>
     <%--*/--%>
    <%--wx.config({--%>
        <%--debug: false,--%>
        <%--appId: '${shareMap.appId}',--%>
        <%--timestamp: ${shareMap.timestamp},--%>
        <%--nonceStr: '${shareMap.nonceStr}',--%>
        <%--signature: '${shareMap.signature}',--%>
        <%--jsApiList: [--%>
            <%--'checkJsApi',--%>
            <%--'onMenuShareTimeline',--%>
            <%--'onMenuShareAppMessage',--%>
            <%--'onMenuShareQQ',--%>
            <%--'onMenuShareWeibo',--%>
            <%--'onMenuShareQZone',--%>
            <%--'hideMenuItems',--%>
            <%--'showMenuItems',--%>
            <%--'hideAllNonBaseMenuItem',--%>
            <%--'showAllNonBaseMenuItem',--%>
            <%--'translateVoice',--%>
            <%--'startRecord',--%>
            <%--'stopRecord',--%>
            <%--'onVoiceRecordEnd',--%>
            <%--'playVoice',--%>
            <%--'onVoicePlayEnd',--%>
            <%--'pauseVoice',--%>
            <%--'stopVoice',--%>
            <%--'uploadVoice',--%>
            <%--'downloadVoice',--%>
            <%--'chooseImage',--%>
            <%--'previewImage',--%>
            <%--'uploadImage',--%>
            <%--'downloadImage',--%>
            <%--'getNetworkType',--%>
            <%--'openLocation',--%>
            <%--'getLocation',--%>
            <%--'hideOptionMenu',--%>
            <%--'showOptionMenu',--%>
            <%--'closeWindow',--%>
            <%--'scanQRCode',--%>
            <%--'chooseWXPay',--%>
            <%--'openProductSpecificView',--%>
            <%--'addCard',--%>
            <%--'chooseCard',--%>
            <%--'openCard'--%>
        <%--]--%>
    <%--});--%>

    <%--var shareData = {--%>
        <%--title: '${shareMap.shareTitle}',--%>
        <%--desc: '${shareMap.shareDesc}',--%>
        <%--link: '${shareMap.shareLink}',--%>
        <%--imgUrl: '${shareMap.shareImg}'--%>
    <%--};--%>

<%--</script>--%>
<%--<script src="<%=basePath%>static/js/pageJs/zepto.min.js"></script>--%>
<%--<script src="<%=basePath%>static/js/pageJs/share.js"> </script>--%>
<script>
    $(".add").on("tap",function () {
        $(".back_box").show()
        $(".back_f").show()
    })
    $(".close").on("tap",function () {
        $(".back_box").hide()
        $(this).parent().hide()
    })
</script>
</html>