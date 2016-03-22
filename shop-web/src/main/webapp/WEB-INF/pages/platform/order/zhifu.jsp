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
    <link rel="stylesheet" href="<%=path%>/static/css/zhifu.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/iscroll.js"></script>
    <script>
        $(document).ready(function () {
            var addressId = $("#addressId").val();
            if (addressId == "") {
                $(".xinz").show();
                $(".sec1").hide();
            } else {
                $(".xinz").hide();
                $(".sec1").attr("style", "display:-webkit-box;");
            }
        })
        var myScroll = new IScroll(".wrap", {
            preventDefault: false
        })

        function toChooseAddressPage() {
            var selectedAddressId = $("#addressId").val();
            window.location.href = "<%=path%>/userAddress/toChooseAddressPage.html?pageType=zhifu&selectedAddressId=" + selectedAddressId + "&orderId=${bOrderId}";
        }

        function submit() {
            var paraData = "?";
            paraData += "bOrderId=${bOrderId}";
            paraData += "&userMessage=" + $("#userMessage").val();
            paraData += "&userAddressId=" + $("#addressId").val();
            if($("#addressId").val()==null||$("#addressId").val()==""){
                alert("请填写收获地址");
                return;
            }
            window.location.href = "<%=basePath%>border/payBOrderSubmit.do" + paraData;
            <%--$.ajax({--%>
            <%--url: "<%=basePath%>border/payBOrderSubmit.do",--%>
            <%--type: "post",--%>
            <%--data: paraData,--%>
            <%--dataType: "json",--%>
            <%--success: function (data) {--%>
            <%--if (data.isError == false) {--%>
            <%--alert("OK");--%>
            <%--}--%>
            <%--else {--%>
            <%--alert(data.message);--%>
            <%--}--%>
            <%--}--%>
            <%--});--%>
        }
    </script>
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="javascript :;" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png"
                                                                               alt=""></a>
            <p>支付订单</p>
        </header>
        <main>
            <div class="drap">
                <h2><img src="<%=path%>/static/images/yes.png" alt=""></h2>
                <p>您已经成功注册麦链合伙人，需要您完成订单支付</p>
            </div>
            <div class="xinxi">
                <p>注册信息</p>
                <p>支付订单</p>
                <p>提交资料</p>
            </div>
            <div class="xinz" onclick="toChooseAddressPage()">
                <p><a>选择收货地址</a></p>
            </div>
            <section class="sec1">
                <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                <div onclick="toChooseAddressPage()">
                    <input style="display: none" type="text" id="addressId" value="${comUserAddress.id}"/>
                    <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.id}</span></h2></a>
                    <a href="#"><p>收货地址：
                        <span>${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName} ${comUserAddress.address}</span><img
                                 src="<%=path%>/static/images/next.png" alt=""></p></a>
                </div>
            </section>
            ${productInfo}
            <section class="sec3">
                <p>运费<span>到付</span></p>
                <h1>共<b>${quantity}</b>件商品　运费：<span>到付</span><b>　合计：</b><span
                        style="padding-right: 10px;">￥${orderAmount}</span></h1>
                <p>留言：<input type="text" id="userMessage" name="userMessage"></p>
            </section>

            <section class="sec4">
                <p><b>合计：</b><span>￥${orderAmount}</span></p>
                <p><b>运费：</b><b style="text-align:left;text-indent:2px;">到付</b></p>
                <p><b>需付：</b><span>￥${receivableAmount}</span></p>
            </section>
            <a href="javascript:;" onclick="submit()" class="weixin">微信支付</a>
            <%--<a href="javascript:;" class="xianxia">线下支付</a>--%>
        </main>
    </div>
</div>
</body>
</html>