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
        body{
            position: relative;
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
            width:96%;
            position: absolute;
            left:2%;
            top:30%;
            background: #fff;
            border-radius: 5px;
        }
        .back_f p{
            text-align: center;
            margin-top: 3em;
        }
        .back_f .close{
            position: absolute;
            right: 5px;
            top: 5px;
            width: 25px;
            height: 25px;
            border-radius: 15px;
            text-align: center;
            line-height: 24px;
            font-size: 18px;
            border: 1px solid #ccc;
        }
        .back_f img{
            width:90%;
            margin:15% 0 5% 5%;
        }
        .wrap .na {
            display: -webkit-box;
            margin-bottom: 5px;
            padding: 0px 10px;
            background: #FFFFFF;
            position: relative;
        }
        .wrap .na p {
            margin-top: 3px;
            width: 30px;
            height: 30px;
            border-radius: 25px;
            background: #ccc;
            overflow: hidden;
        }
        .wrap .na p img {
            width: 100%;
        }
        .wrap .na h1 {
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-box-pack: center;
            margin-left: 10px;
        }
        .wrap .na h1 span {
            display: block;
            font-size: 12px;
        }
        .wrap .na h1 span:last-child{
            font-size: 10px;
            color: #999;
        }
        .wrap .na label{
            position: absolute;
            line-height: 25px;
            right: 5px;
            top: 6px;
            color: #fff;
            padding: 0 15px;
            background: #ff0000;
            border-radius: 5px;
            font-size: 12px;
        }
    </style>
</head>
<body>
<header>
    <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>店铺分享专属海报</p>
</header>
<div class="wrap">
    <div class="na">
        <p></p>
        <h1>
            <span>麦链合伙人</span>
            <span>关注可查资金，管理店铺，发展下级</span>
        </h1>
        <label class="add">加关注</label>
    </div>
    <canvas id="canvasOne" style="display: none;">
        Your browser does not support HTML5 Canvas.
    </canvas>
    <img src="${shopPoster}" />
    <b id="downloadPoster2">长按图片保存海报</b>
</div>
<div class="back_box">
    <div class="back"></div>
    <div class="back_f">
        <p>关注公众账号查资金，管理店铺，发展下级</p>
        <span class="close">×</span>
        <img src="${path}/static/images/asd.JPG" alt="">
    </div>
</div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    /*
     * 注意：
     * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
     * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
     * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
     *
     * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
     * 邮箱地址：weixin-open@qq.com
     * 邮件主题：【微信JS-SDK反馈】具体问题
     * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
     */
    wx.config({
        debug: false,
        appId: '${shareMap.appId}',
        timestamp: ${shareMap.timestamp},
        nonceStr: '${shareMap.nonceStr}',
        signature: '${shareMap.signature}',
        jsApiList: [
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone',
            'hideMenuItems',
            'showMenuItems',
            'hideAllNonBaseMenuItem',
            'showAllNonBaseMenuItem',
            'translateVoice',
            'startRecord',
            'stopRecord',
            'onVoiceRecordEnd',
            'playVoice',
            'onVoicePlayEnd',
            'pauseVoice',
            'stopVoice',
            'uploadVoice',
            'downloadVoice',
            'chooseImage',
            'previewImage',
            'uploadImage',
            'downloadImage',
            'getNetworkType',
            'openLocation',
            'getLocation',
            'hideOptionMenu',
            'showOptionMenu',
            'closeWindow',
            'scanQRCode',
            'chooseWXPay',
            'openProductSpecificView',
            'addCard',
            'chooseCard',
            'openCard'
        ]
    });

    var shareData = {
        title: '${shareMap.shareTitle}',
        desc: '${shareMap.shareDesc}',
        link: '${shareMap.shareLink}',
        imgUrl: '${shareMap.shareImg}'
    };

</script>
<script src="<%=basePath%>static/js/pageJs/zepto.min.js"></script>
<script src="<%=basePath%>static/js/pageJs/share.js"> </script>
<script>
    $(".add").on("tap",function () {
        $(".back_box").show()
    })
    $(".close").on("tap",function () {
        $(".back_box").hide()
    })
</script>
</html>