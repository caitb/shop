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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shangpin.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
</head>
<body>

<div class="wrap">
    <main>
        <header class="xq_header">
            <a href="<%= request.getHeader("REFERER") %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>商品管理</p>
        </header>
        <div id="box">
            <div class="main">
                <div class="d_box">
                    <c:forEach items="${userProducts}" var="sku">
                        <section class="sec2">
                            <p class="photo">
                                <img src="${sku.comSkuImage.fullImgUrl}" alt="">
                            </p>
                            <div>
                                <h2 id="sku1">${sku.name}</h2>
                                <h3>零售价：<span>${sku.priceRetail}</span></h3>
                                <p>已售：<span>0</span>　　库存：<span id="sku2">${sku.customStock}</span></p>
                                <input type="hidden" id="pfuId" value="${sku.pfuId}">
                                <input type="hidden" id="skuId" value="${sku.id}">
                                <p>拿货方式：<span>自己发货</span>
                            </div>
                        </section>
                        <section class="sec3">
                            <p class="jianku" onclick="jiankucun('${sku.name}','${sku.customStock}')">库存维护</p>
                            <p class="buhuo" onclick="buhuokucun('${sku.name}','${sku.upperStock}','${sku.stock}')">补货</p>
                        </section>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="back_j">
            <p>库存维护</p>
            <h4>商　　品:　　<span id="skuName"></span></h4>
            <h4>当前库存:　　<span id="skuStock"></span></h4>
            <h4>数　　量:　　<div>
                <input type="tel" id="stockNumber"/>
            </div>
            </h4>
            <div>
                <h1 class="j_qu">取消</h1>
                <h1 class="j_que">确定</h1>
            </div>
        </div>
        <div class="back_que">
            <p>库存维护</p>
            <h4><b>商品:</b><span id="sku3"></span></h4>
            <h4><b>当前库存:</b><span id="stock1"></span></h4>
            <h4><b>最新库存:</b><span id="stock3"></span></h4>
            <h4>您确定要修改库存?</h4>
            <h3>
                <span class="que_qu">取消</span>
                <span class="que_que">确认</span>
            </h3>
        </div>
        <div class="back">
        </div>
        <div class="back_b">
            <p>补货信息</p>
            <h4>商品:　　<span id="addsku"></span></h4>
            <h4>本次最多可补货数量:　　<span id="maxStock"></span></h4>
            <h4>补货数量:　　<div>
                <span class="jian">-</span>
                <input type="tel" class="number" value="1"/>
                <span class="jia">+</span>
            </div>
            </h4>
            <div>
                <h1 class="b_qu">取消</h1>
                <h1 class="b_que">确定</h1>
            </div>
        </div>
    </main>
</div>
<script src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script>
    function jiankucun(a,b){
        $("#skuName").html(a);
        $("#skuStock").html(b);
        $(".back").css("display","-webkit-box");
        $(".back_j").show();
    }
    $(".j_qu").on("click",function(){
        $(".back").css("display","none");
        $(".back_j").hide();
    })
    $(".j_que").on("click",function(){
        var skuName = $("#skuName").text();
        $("#sku3").html(skuName);
        var skuStock = $("#skuStock").text();
        var stockNumber = $("#stockNumber").val();
        $("#stock1").html(skuStock);
        $("#stock3").html(stockNumber);
        $(".back_j").hide();
        $(".back_que").css("display","-webkit-box");
    })
    $(".que_qu").on("click",function(){
        $(".back").css("display","none");
        $(".back_que").hide();
    })
    //ajax
    $('.que_que').on('click', function () {
        var pfuId = $("#pfuId").val();
        var stockNumber = $("#stockNumber").val();
        $.ajax({
            url: '<%=basePath%>product/selfUser/updateStock.do',
            type: 'post',
            data: {stock:stockNumber,id:pfuId},
            dataType: 'json',
            success: function (data) {
                $(".back").css("display","none");
                $(".back_que").css("display","none");
                if(data.isError == false){
                    location.reload(true);
                    alert(data.message);
                }else{
                    alert(data.message);
                }
            }
        });
    });
    var i=1;
    $(".jia").on("click",function(){
        i++;
        $(".number").val(i)
    })
    $(".number").on("change", function () {
        i=$(this).val();
    })
    $(".jian").on("click",function(){
        if(i==1){
            return false;
        }
        i--;
        $(".number").val(i)
    })
    function buhuokucun(a,b){
        $("#addsku").html(a);
        $("#maxStock").html(b);
        $(".back").css("display","-webkit-box");
        $(".back_b").show();
    }
    $(".b_qu").on("click",function(){
        $(".back").css("display","none");
        $(".back_b").hide();
    })
    $(".b_que").on("click",function(){
        var skuId = $("#skuId").val();
        $.ajax({
            url: '<%=basePath%>product/user/addStock.do',
            type: 'post',
            data: {stock:i,skuId:skuId},
            dataType: 'json',
            success: function (data) {
                if(data['isError'] == false){
                    if(data['isQueue'] == true){
                        alert(data['message']);
                    }
                    window.location.href = "<%=basePath%>border/payBOrder.shtml/?bOrderId="+data.orderCode+"";
                }else{
                    alert(data['message']);
                }
            }
        });
    })
</script>
</body>
</html>