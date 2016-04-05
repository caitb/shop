<%--
  Created by IntelliJ IDEA.
  User: ZhaoLiang
  Date: 2016/4/2
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/nahuo.css">
</head>
<body>
<div class="wrap">

    <div class="main">
        <header class="xq_header">
            <a href="javascript:;" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>拿货方式</p>
        </header>
        <div class="xinxi">
            <p>注册信息</p>
            <p>支付订单</p>
            <p>提交资料</p>
        </div>
        <div class="nahuo">
            <p>支付成功，请选择拿货方式</p>
        </div>
        <div class="content">
            <div class="sec1">
                <h1>拿货方式简介</h1>
                <p>麦链商城提供两种拿货方式：平台待发货和自己发货，以下为两种方式的对比介绍，请您阅读后酌情选择。</p>
                <table>
                    <tr>
                        <td></td>
                        <td>平台代发</td>
                        <td>自己发货</td>
                    </tr>
                    <tr>
                        <td>仓库</td>
                        <td>平台提供</td>
                        <td>自建（成本高）</td>
                    </tr>
                    <tr>
                        <td>仓库</td>
                        <td>平台提供</td>
                        <td>自建（成本高）</td>
                    </tr>
                    <tr>
                        <td>夏季合伙人平台</td>
                        <td>支持</td>
                        <td>不支持</td>
                    </tr>
                    <tr>
                        <td>店铺代发</td>
                        <td>平台代发</td>
                        <td>自己发货（人工成本高）</td>
                    </tr>
                    <tr>
                        <td>消费者发票</td>
                        <td>平台提供</td>
                        <td>自己提供</td>
                    </tr>
                </table>
                <div>
                    <h1>1. 平台代发<span>(推荐)</span></h1>
                    <p>您可以省去仓储和发货事物，平台将为您提专业的供仓储、发货服务（价值58800元的仓储发货套餐免费试用一年）；无论您的订单是发给下级合伙人还是发给普通消费者，平台统统可以为您搞定</p>
                    <p><span>注：</span>此方式支持您在商品管理中提货</p>
                </div>
                <div>
                    <h1>2. 由我自己发货</h1>
                    <p>平台将一次性发给您所有的商品，商品由您自己保存、发货，此方式比较耗费成本</p>
                    <p><span></span>选择此方式，您的下级合伙人将不能选择平台代发货方式，请谨慎选择</p>
                </div>
            </div>
            <div class="sec2">
                <h1>请选择拿货方式</h1>
                <button id="platformSendGoodsId"   class="active" onclick="platformSendGoods()">平台待发货（90%的用户选择）</button>
                <button  id="ownSendGoodsId" onclick="ownSendGoods()">自己发货</button>

                <section id=""class="shouhuo">
                    <div onclick="toChooseAddressPage()">
                        <input style="display: none" type="text" id="bOrderId" value="${bOrderId}"/>
                        <input style="display: none" type="text" id="addressId" value="${comUserAddress.id}"/>
                        <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.mobile}</span></h2></a>
                        <a href="#"><p>收货地址： <span>${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}</span></p></a>
                        <img src="<%=path%>/static/images/next.png" alt="">
                    </div>
                </section>
                <div class="xuan" onclick="toChooseAddressPage()"><h1>选择收获地址<span></span></h1></div>
                <p>*选择后，您的其他合伙商品将使用同一种方式，不可更改</p>
            </div>
            <button>继续</button>
        </div>
    </div>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script>
    $(document).ready(function () {
            isShowAddress();
    })
    function isShowAddress(){
        var addressId = $("#addressId").val();
        if (addressId == "") {
            $(".xuan").show();
            $(".shouhuo").hide();
        }else{
            $(".xuan").hide();
            $(".shouhuo").show();
        }
    }
    function platformSendGoods(){
        isShowAddress();
        $("#platformSendGoodsId").attr("class","active");
        $("#ownSendGoodsId").removeClass("active");
    }
    function ownSendGoods(){
        $(".xuan").hide();
        $(".shouhuo").hide();
        $("#ownSendGoodsId").attr("class","active");
        $("#platformSendGoodsId").removeClass("active");
    }
   /* $(".sec2 button").on("click", function () {
        $(this).addClass("active").siblings().removeClass("active")
    })*/
    function toChooseAddressPage() {
        var selectedAddressId = $("#addressId").val();
        var bOrderId = $("#bOrderId").val();
        window.location.href = "<%=path%>/userAddress/toChooseAddressPage.html?pageType=takeGoods&selectedAddressId=" + selectedAddressId + "&orderId=" + bOrderId;
    }
</script>
</body>
</html>
