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

    <header class="xq_header">
        <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>商品管理</p>
    </header>
    <main>
        <div id="box">
            <div class="d_box">
                <section class="sec2">
                    <c:forEach items="${userProducts}" var="sku">
                        <p class="photo">
                            <img src="${sku.comSkuImage.fullImgUrl}" alt="">
                        </p>
                        <div>
                            <h2>${sku.name}</h2>
                            <h3>零售价：<span>${sku.priceRetail}</span></h3>
                            <p>已售：<span>0</span>　　库存：<span>${sku.stock}</span></p>
                        </div>
                    </c:forEach>
                </section>
                <section class="sec3">
                    <p class="jianku">减库存</p>
                    <p class="buhuo">补货</p>
                </section>
            </div>
        </div>
    </main>
    <div class="back">
        <div class="back_j">
            <p>减少库存</p>
            <h4>商　　品:　　<span>抗引力-手链</span></h4>
            <h4>当前库存:　　<span>抗引力-手链</span></h4>
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
        <div class="back_que">
            <p>确认减库存?</p>
            <h4>商品:<span>抗引力-手链</span></h4>
            <h4>商品:<span>抗引力-手链</span></h4>
            <h4>商品:<span>抗引力-手链</span></h4>
            <h4>商品:<span>抗引力-手链</span></h4>
            <h3>
                <span class="que_qu">取消</span>
                <span class="que_que">确认</span>
            </h3>
        </div>
        <div class="back_b">
            <p>减少库存</p>
            <h4>商品:　　<span>抗引力-手链</span></h4>
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
    </div>
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
    $(".jianku").on("click",function(){
        $(".back").css("display","-webkit-box");
        $(".back_j").show();
    })
    $(".j_qu").on("click",function(){
        $(".back").css("display","none");
        $(".back_j").hide();
    })
    $(".j_que").on("click",function(){
        $(".back_j").hide();
        $(".back_que").css("display","-webkit-box");
    })
    $(".que_qu").on("click",function(){
        $(".back").css("display","none");
        $(".back_que").css("display","none");
    })
    $(".que_que").on("click",function(){
        $(".back").css("display","none");
        $(".back_que").css("display","none");
    })
    $(".buhuo").on("click",function(){
        $(".back").css("display","-webkit-box");
        $(".back_b").show();
    })
    $(".b_qu").on("click",function(){
        $(".back").css("display","none");
        $(".back_b").hide();
    })

</script>
</body>
</html>