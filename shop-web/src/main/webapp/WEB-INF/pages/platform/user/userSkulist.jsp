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
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>

<div class="wrap">
    <main>
    <header class="xq_header">
        <a href="javascript:;"onClick="javascript:history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
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
                            <p>已售：<span>0</span>　　库存：<span id="sku2">${sku.stock}</span></p>
                            <input type="hidden" id="pfuId" value="${sku.pfuId}">
                        </div>
                    </section>
                    <section class="sec3">
                        <p class="jianku" onclick="jiankucun('${sku.name}','${sku.stock}')">减库存</p>
                        <p class="buhuo" onclick="buhuokucun('${sku.name}')">补货</p>
                    </section>
                </c:forEach>
            </div>
            </div>
        </div>
        <div class="back_j">
            <p>减少库存</p>
            <h4>商　　品:　　<span id="skuName"></span></h4>
            <h4>当前库存:　　<span id="skuStock"></span></h4>
            <h4>数　　量:　　<div>
                <span class="jian">-</span>
                <span class="number">1</span>
                <span class="jia">+</span>
            </div>
            </h4>
            <div>
                <h1 class="j_qu">取消</h1>
                <h1 class="j_que">确定</h1>
            </div>
        </div>
        <div class="back_b">
            <p>增加库存</p>
            <h4>商品:　　<span id="addsku"></span></h4>
            <h4>数量:　　<div>
                <span class="jian">-</span>
                <span class="number">1</span>
                <span class="jia">+</span>
            </div>
            </h4>
            <div>
                <h1 class="b_qu">取消</h1>
                <h1 class="b_que"><a href="buhuodingdan.html">确定</a></h1>
            </div>
        </div>
        <div class="back_que">
            <p>确认减库存?</p>
            <h4>商品:<span id="sku3"></span></h4>
            <h4>当前库存:<span id="stock1"></span></h4>
            <h4>减库存:<span id="stock2"></span></h4>
            <h4>剩余库存:<span id="stock3"></span></h4>
            <h3>
                <span class="que_qu">取消</span>
                <span class="que_que">确认</span>
            </h3>
        </div>
        <div class="back">
        </div>
    </main>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script>
    var myScroll = new IScroll("main",{
        preventDefault: false
    })
    var i=1;
    $(".jia").on("click",function(){
        i++;
        $(".number").html(i)
    })
    $(".jian").on("click",function(){
        if(i==1){
            return false;
        }
        i--;
        $(".number").html(i)
    })
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
        $("#stock1").html(skuStock);
        $("#stock2").html(i);
        $("#stock3").html(skuStock-i);
        $(".back_j").hide();
        $(".back_que").css("display","-webkit-box");
    })
    $(".que_qu").on("click",function(){
        $(".back").css("display","none");
        $(".back_que").css("display","none");
    })
    //ajax
    $('.que_que').on('click', function () {
        var pfuId = $("#pfuId").val();
        $.ajax({
            url: '<%=basePath%>product/user/stock',
            type: 'post',
            data: {stock:i,id:pfuId},
            dataType: 'json',
            success: function (data) {
               $(".back").css("display","none");
               $(".back_que").css("display","none");
               if(data['isError'] == false){
                   location.reload(true);
                }else{
                   alert(data['message']);
               }
            }
        });
    });
    function buhuokucun(a){
        $("#addsku").html(a);
        $(".back").css("display","-webkit-box");
        $(".back_b").show();
    }
    $(".b_qu").on("click",function(){
        $(".back").css("display","none");
        $(".back_b").hide();
    })
</script>
</body>
</html>