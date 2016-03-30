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
                            <p>已售：<span>0</span>　　在线库存：<span id="sku2">${sku.stock}</span></p>
                            <input type="hidden" id="pfuId" value="${sku.pfuId}">
                            <input type="hidden" id="skuId" value="${sku.id}">
                            <p>拿货方式：<span>平台代发货</span>
                        </div>
                    </section>
                    <section class="sec3">
                        <p class="jianku"><a href="#">申请拿货</a></p>
                        <p class="buhuo"><a href="#"></a>补货</p>
                    </section>
                </c:forEach>
            </div>
            </div>
        </div>
        <div class="back">
        </div>
    </main>
</div>
<script src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script>
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
    $(".b_que").on("click",function(){
        var skuId = $("#skuId").val();
        $.ajax({
            url: '<%=basePath%>product/user/addStock',
            type: 'post',
            data: {stock:i,skuId:skuId},
            dataType: 'json',
            success: function (data) {
                if(data['isError'] == false){
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