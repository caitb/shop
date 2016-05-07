<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/main.css">
    <link rel="stylesheet" href="<%=path%>/static/shop/css/shangpinguanli.css">
</head>
<body>
<header>
    <a href="javascript:window.location.replace('<%=basePath%>shop/manage/index')"><img src="<%=path%>/static/shop/images/xq_rt.png" alt=""></a>
    <p>商品管理</p>
</header>
<div class="wrap">
    <nav>
        <p></p>
        <p><label class="on active" id="onsale">出售中</label></p>
        <p><label class="on" id="outsale">仓库中</label></p>
        <p></p>
    </nav>
    <main>

            <section class="all" style="display: block;">
                <c:forEach items="${skuInfoList}" var="sku">
                <div class="sec1">
                    <img src="${sku.comSkuImage.fullImgUrl}" alt="">

                    <div>
                        <h1>${sku.comSku.name}</h1>

                        <p style="color: #ff5200;">${sku.comSku.priceMarket}</p>

                        <p>已售：<span style="margin-right: 5px;">${sku.saleNum}</span>  库存: <span>${sku.stock}</span></p>
                    </div>
                </div>
                <div class="sec2">
                    <p onclick="xiajia('${sku.shopSkuId}')"><img src="${path}/static/images/set1.png" alt="">下架</p>

                    <p onclick="share('${sku.comSku.id}')"><img src="${path}/static/images/set2.png" alt="">分享</p>
                </div>
                </c:forEach>
            </section>

    </main>
</div>
<div class="back_que"style="display: none">
    <p>确认下架?</p>
    <h4>下架后的商品将不在店铺展示，消费者也将无法购买</h4>
    <input type="text" id="shopSkuId" style="display: none">
    <h3>
        <span class="que_qu">取消</span>
        <span class="que_que" onclick="applyxiajia()">我确认下架</span>
    </h3>
</div>
<div class="back_f">
    <p>保存图片到手机，复制文案，发送图文到朋友圈，产生购买后可获得佣金</p>
    <img id="skuPoster" src="<%=path%>/static/images/asd.JPG" alt="">
    <canvas id="canvasOne" style="display: none;">
        Your browser does not support HTML5 Canvas.
    </canvas>
    <b id="downloadPoster">长按图片保存海报</b>
    <span class="close">×</span>
</div>
<div class="back">

</div>
<script src="<%=path%>/static/shop/js/jquery-1.8.3.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/plugins/zepto.min.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"> </script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>

<script>
    var shopData = {};
    shopData.shopId = "${shopId}";
    $("#onsale").on("tap", function () {
        $(".on").removeClass("active");
        $(this).addClass("active");
        $(".sec1").show().siblings().hide();
        $.ajax({//上架中
            url: '<%=basePath%>shop/deliverSale.do',
            type: 'post',
            async:true,
            data: {shopId:shopData.shopId,isSale:1},
            dataType: 'json',
            success: function (data) {
                var trHtml = "";
                $.each(data.skuInfoList, function(i, sku){
                    trHtml+="<div class=\"sec1\">";
                    trHtml+="<img src=\""+sku.comSkuImage.fullImgUrl+"\" alt=\"\">";
                    trHtml+="<div>";
                    trHtml+="<h1>"+sku.comSku.name+"</h1>";
                    trHtml+="<p style=\"color: #ff5200;\">"+sku.comSku.priceMarket+"</p>";
                    trHtml+="<p>已售：<span>"+sku.saleNum+"</span>   库存: <span>"+sku.stock+"</span></p>";
                    trHtml+="</div>";
                    trHtml+="</div>";
                    trHtml+="<div class=\"sec2\">";
                    trHtml+="<p onclick=\"xiajia('"+sku.shopSkuId+"')\">下架</p>";
                    trHtml+="<p onclick=\"share('"+sku.comSku.id+"')\">分享</p>";
                    trHtml+="</div>";
                });
                $(".all").empty().html(trHtml);
            }
        });
    })
    $("#outsale").on("tap", function () {
        $(".on").removeClass("active");
        $(this).addClass("active");
        $.ajax({//仓库中
            url: '<%=basePath%>shop/deliverSale.do',
            type: 'post',
            async:true,
            data: {shopId:shopData.shopId,isSale:0},
            dataType: 'json',
            success: function (data) {
                var trHtml = "";
                $.each(data.skuInfoList, function(i, sku){
                    trHtml+="<div class=\"sec1\">";
                    trHtml+="<img src=\""+sku.comSkuImage.fullImgUrl+"\" alt=\"\">";
                    trHtml+="<div>";
                    trHtml+="<h1>"+sku.comSku.name+"</h1>";
                    trHtml+="<p style=\"color: #ff5200;\">"+sku.comSku.priceMarket+"</p>";
                    trHtml+="<p>已售：<span>"+sku.saleNum+"</span>   库存: <span>"+sku.stock+"</span></p>";
                    trHtml+="</div>";
                    trHtml+="</div>";
                    trHtml+="<div class=\"sec2\">";
                    trHtml+="<p onclick=\"shangjia('"+sku.shopSkuId+"')\">上架</p>";
                    <%--trHtml+="<p><a href=\"<%=basePath%>product/skuDetails.shtml?skuId="+sku.comSku.id+"\">预览</a></p>";--%>
                    trHtml+="</div>";
                });
                $(".all").empty().html(trHtml);
            }
        });

    })
    function xiajia(a){
        $(".back_que").css("display","-webkit-box");
        $(".back").show();
        $("#shopSkuId").val(a);
    }

    $(".que_qu").on("tap",function(){
        $(".back_que").hide();
        $(".back").hide();
    })
       //ajax 下架
    function applyxiajia() {
        var shopSkuId = $("#shopSkuId").val();
        $.ajax({
            url: '<%=basePath%>shop/updateSale.do',
            type: 'post',
            data: {shopSkuId: shopSkuId, isSale: 0},
            dataType: 'json',
            success: function (data) {
                if (data.isError == false) {
                      alert("下架成功！");
                      location.reload(true);
                } else {
                    alert(data.message);
                }
            }
        });
    }

    function shangjia(a){
        //ajax 上架
        $.ajax({
            url: '<%=basePath%>shop/updateSale.do',
            type: 'post',
            data: {shopSkuId: a,isSale:1},
            dataType: 'json',
            success: function (data) {
                if (data.isError == false) {
                    alert("上架成功！");
                    location.reload(true);
                } else {
                    alert(data.message);
                }
            }
        });
    }
    $(".close").on("tap",function(){
        $(this).parent().hide();
        $(".back").hide();
    })
</script>
<script src="//cdn.bootcss.com/modernizr/2010.07.06dev/modernizr.min.js"></script>
<script src="<%=path%>/static/shop/js/plugins/canvas2image.js"></script>
<script src="<%=path%>/static/shop/js/plugins/base64.js"></script>
<script type="text/javascript">

    function canvasSupport() {
        return Modernizr.canvas;
    }

    function canvasApp(userName,skuName,imgSrcs) {

        if(!canvasSupport()) {
            return;
        }
        var theCanvas = document.getElementById("canvasOne");
        theCanvas.width  = 904;
        theCanvas.height = 1200;
        var context = theCanvas.getContext("2d");
        context.fillStyle = "#EEEEEE";
        context.fillRect(0, 0, theCanvas.width, theCanvas.height);


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
                context.drawImage(oImgs[0], 46, 44, 90, 90);
                context.drawImage(oImgs[1], 0, 0);
                context.drawImage(oImgs[2], 304, 314);

                context.font = 'normal 28px Microsoft YaHei';
                context.textBaseline = 'top';
                context.strokeStyle = '#333333';
                context.strokeText('我是'+userName,170, 56);
                context.strokeText('我为'+skuName+'代言!',170, 90);

                clearInterval(drawTimer);
            }
        },100);

    }
     function share(skuId){
         $.ajax({
             url: '<%=basePath%>shop/manage/getSkuPoster',
             data: {shopId: ${shopId}, skuId: skuId},
             success: function(data){
                 data = window.eval('(' + data + ')');
                 $('#skuPoster').attr('src', data['skuPoster']);
                 $('.back_f').show();
                 $('.back').show();
             }
         });
     }
    document.getElementById('downloadPoster').onclick = function(){
        Canvas2Image.saveAsPNG(document.getElementById("canvasOne"));
    }
</script>
</body>
</html>