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
    </style>
    <script src="//cdn.bootcss.com/modernizr/2010.07.06dev/modernizr.min.js"></script>
    <script src="<%=basePath%>static/js/plugins/canvas2image.js"></script>
    <script src="<%=basePath%>static/js/plugins/base64.js"></script>
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
    <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
    <p>店铺分享专属海报</p>
</header>
<div class="wrap">
    <canvas id="canvasOne" style="display: none;">
        Your browser does not support HTML5 Canvas.
    </canvas>
    <img src="${shopPoster}" />
    <b id="downloadPoster2">长按图片保存海报</b>
</div>
</body>
<script>
    function onBridgeReady(){
        WeixinJSBridge.call('hideOptionMenu');
    }

    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }
</script>
</html>