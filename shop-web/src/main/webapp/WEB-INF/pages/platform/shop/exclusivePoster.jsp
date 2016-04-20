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
    <link rel="stylesheet" href="<%=basePath%>static/css/main.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
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
    </style>
    <%--<script src="//cdn.bootcss.com/modernizr/2010.07.06dev/modernizr.min.js"></script>--%>
    <%--<script src="<%=basePath%>static/js/plugins/canvas2image.js"></script>--%>
    <%--<script src="<%=basePath%>static/js/plugins/base64.js"></script>--%>
    <script type="text/javascript">
        //window.addEventListener("load", eventWindowLoaded, false);
        function eventWindowLoaded() {
            canvasApp();
        }

        function canvasSupport() {
            return Modernizr.canvas;
        }

        function eventWindowLoaded() {
            canvasApp();
        }

        function canvasApp() {

            if(!canvasSupport()) {
                return;
            }
            var theCanvas = document.getElementById("canvasOne");
            theCanvas.width = 520;
            theCanvas.height =710;
            var context = theCanvas.getContext("2d");
            context.fillStyle = "#EEEEEE";
            context.fillRect(0, 0, theCanvas.width, theCanvas.height);

            var imgSrcs = ['<%=basePath%>${userImg}', '<%=basePath%>${bgShop}', '<%=basePath%>${shopQRCode}'];
            var oImgs = [];
            for(var i in imgSrcs){
                oImgs[i] = new Image();
                oImgs[i].src = imgSrcs[i];
                oImgs[i].isLoaded = false;

                oImgs[i].addEventListener('load', function(){
                    this.isLoaded = true;
                }, false);

            }

            var drawTimer = setInterval(function(){
                var isAllLoaded = true;
                for(var i in oImgs){
                    if(!oImgs[i].isLoaded) isAllLoaded = false;
                }

                if(isAllLoaded){
                    context.drawImage(oImgs[0], 195, 130, 130, 130);
                    context.drawImage(oImgs[1], 0, 0);
                    context.drawImage(oImgs[2], 160, 368);

                    context.font = 'normal 28px Microsoft YaHei';
                    context.textBaseline = 'top';
                    context.strokeStyle = '#F73C8C';
                    var text = '我是' + '${userName}';
                    context.strokeText(text,520/2-text.length/2*28, 284);

                    clearInterval(drawTimer);
                }
            },100);


            document.getElementById('downloadPoster').onclick = function(){
                Canvas2Image.saveAsPNG(theCanvas);
            }

        }
    </script>
</head>
<body>
<header>
    <a href="javascript:window.location.replace('<%=basePath%>shop/manage/index');"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>店铺分享</p>
</header>
<div class="wrap">
    <canvas id="canvasOne" style="display: none;">
        Your browser does not support HTML5 Canvas.
    </canvas>
    <img src="${shopPoster}" />
    <b id="downloadPoster2">长按图片保存海报</b>
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

    wx.hideOptionMenu();

</script>
<script src="<%=basePath%>static/js/zepto.min.js"></script>
<script src="<%=basePath%>static/js/share.js"> </script>
</html>