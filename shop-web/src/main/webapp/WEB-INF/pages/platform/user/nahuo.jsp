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
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shenqinghehu.css">
</head>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<%--<script src="<%=path%>/static/js/commonAjax.js"></script>--%>
<%--<script src="<%=path%>/static/js/checkUtil.js"></script>--%>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script src="<%=path%>/static/plugins/zepto.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"> </script>
<script>
    $(document).ready(function () {
        var addressId = $("#addressId").val();
        if (addressId == "") {
            $("#xz").show();
            $("#sec1").hide();
        }
    })
    function toChooseAddressPage() {
        var selectedAddressId = $("#addressId").val();
        var pfUserSkuStockId = $("#pfUserSkuStockId").val();
        window.location.href = "<%=path%>/userAddress/toChooseAddressPage.html?pageType=manageGoodsTakeGoods&selectedAddressId=" + selectedAddressId + "&pfUserSkuStockId=" + pfUserSkuStockId;
    }

</script>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%= request.getHeader("REFERER") %>"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>申请拿货</p>
    </header>
    <main>
        <div id="xz" style="display: none" onclick="toChooseAddressPage()">
            <div class="xinz">
                <p>选择收货地址</p>
            </div>
        </div>
        <div id="sec1">
            <section class="sec1">
                <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                <div onclick="toChooseAddressPage()">
                    <input style="display: none" type="text" id="pfUserSkuStockId" value="${pfUserSkuStockId}"/>
                    <input style="display: none" type="text" id="addressId" value="${comUserAddress.id}"/>
                    <a href="#"><h2>收货人：<b>${comUserAddress.name}</b> <span>${comUserAddress.mobile}</span></h2></a>
                    <a href="#"><p>收货地址：
                        <span>${comUserAddress.provinceName}  ${comUserAddress.cityName}  ${comUserAddress.regionName}  ${comUserAddress.address}
                        </span><img src="<%=path%>/static/images/next.png" alt=""></p></a>
                </div>
            </section>
        </div>
        <section class="sec2">
            <p class="photo">
                <img src="${comSkuImage}" alt="">
            </p>
            <div>
                <h2>${comSku.name}</h2>
                <h3>规格：<span>默认</span></h3>
                <p>零售价：<span>${comSku.priceRetail}</span><b
                        style="float:right; margin-right:10px;font-size:12px;">合伙价：<b
                        style="font-size:12px; color:#FF5200">${priceDiscount}</b></b></p>
            </div>
        </section>
        <section class="sec3">
            <p>留言：<input type="text" id="msg"></p>
            <input type="hidden" id="levelStock" value="${levelStock}"/>
        </section>
        <section class="sec4">
            <p><em>在线库存：</em><b style="margin-top:5px">${productInfo.stock}</b></p>
            <p>
                <em>拿货数量：</em><b><label class="jian">-</label><input type="tel" value="1" class="number">
                <label class="jia">+</label></b>
            </p>
            <p><span>注</span>您的剩余库存可发展下级合伙人的数量为1~${lowerCount}</p>
        </section>
        <section class="sec5">
            <div>
                <h1>说明</h1>
                <p>您申请的货物将由您自行保管；<br/>您只能使用在线库存发展下级合伙人，您得到的货物不再支持在线人合伙的发展</p>
            </div>
            <input type="checkbox" id="active">
            <label for="active"><b>拿货风险：</b>请确认以了解申请拿货的部分不能继续发展下线，货物又我自行销售</label>
            <button onclick="submit();">确认拿货</button>
        </section>
    </main>
</div>
<div class="back_que">
    <p>确认拿货?</p>
    <h4><b>在线库存:</b><span id="currentStock">${productInfo.stock}</span></h4>
    <h4><b>拿货数量:</b><span id="applyStock"></span></h4>
    <h4><b>拿货后可发展下级人数:</b><span id="afterLowerCount"></span></h4>
    <h4 style="color:#666666;"><em>注</em>您的拿货数量将不再能发展下级合伙人</h4>
    <h3>
        <span class="que_qu">返回修改</span>
        <span class="que_que">确定</span>
    </h3>
</div>

<div class="back">

</div>
<script type="text/javascript">
    var i = 1;
    $(".number").on("change", function () {
        i = $(this).val();
    })
    $(".jian").on("tap", function () {
        if (i <= 1) {
            return false;
        }
        i--;
        $(".number").val(i)
    })
    $(".jia").on("tap", function () {
        i++;
        $(".number").val(i)
    })
    $(".que_qu").on("tap", function () {
        $(".back").css("display", "none");
        $(".back_que").hide();
    })
    function submit() {
        var checked = document.getElementById("active").checked;
        var currentStock = $("#currentStock").text();
        var levelStock = $("#levelStock").val();
        var afterLowerCount = (currentStock - i) / levelStock;
        //地址
        var addressId = $("#addressId").val();
        if (addressId === undefined || addressId == "") {
            alert("请输入拿货地址！");
            return;
        }
        if (checked == true) {
            if (afterLowerCount >= 0) {
                $(".back").css("display", "-webkit-box");
                $(".back_que").show();
                $("#applyStock").html(i);
                $("#afterLowerCount").html(afterLowerCount);
            } else {
                alert("在线库存不足!");
            }
        } else {
            alert("请确认拿货风险!");
        }
    }
    $('.que_que').on('tap', function () {
        var paraData = {};
        paraData.userAddressId = "${comUserAddress.id}";
        paraData.message = $("#msg").val();
        paraData.stock = $("#applyStock").text();
        paraData.id = ${productInfo.id};
        $.ajax({
            url: "<%=basePath%>/product/user/applyStock.do",
            type: "post",
            data: paraData,
            dataType: "json",
            success: function (data) {
                if (data.isError == false) {
                    window.location.href = "<%=basePath%>borderManage/borderDetils.html?id=" + data.borderId;
                }
                else {
                    $(".back").css("display", "none");
                    $(".back_que").hide();
                    alert(data.message);
                }
            }
        });
    });
</script>
</body>
</html>