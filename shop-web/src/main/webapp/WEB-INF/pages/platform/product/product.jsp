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
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <%--<link rel="stylesheet" href="<%=path%>/static/css/reset.css">--%>
    <link rel="stylesheet" href="<%=path%>/static/css/xiangqing.css">
    <%--<link rel="stylesheet" href="<%=path%>/static/css/header.css">--%>
    <%--<link rel="stylesheet" href="<%=path%>/static/css/base.css">--%>
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
</head>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="javascript:window.location.replace('<%=basePath%>marketGood/market')"><img
                    src="<%=path%>/static/images/xq_rt.png"
                    alt=""></a>

            <p>我要成为合伙人</p>
        </header>
        <div class="banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach items="${productDetails.comSkuImages}" var="urlImg">
                        <div class="swiper-slide"><img src="${urlImg.fullImgUrl}" alt=""></div>
                    </c:forEach>
                </div>
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <div class="price">
            <p style="padding-right:30px">${productDetails.name}</p>

            <p><span style="padding:0;">${productDetails.slogan}</span></p>

            <p>￥<b>${productDetails.priceRetail}</b><span>最高利润${productDetails.maxDiscount}%
            </span><span style="color: #999999;float:right;margin:4px 0 0 0;">代理人数：
                <c:if test="${productDetails.agentNum >9999}">
                   超过9999</span>
                </c:if>
                ${productDetails.agentNum}</span>
            </p>
            <%--<p style="padding-bottom: 5px;"><b style="color:#999999;font-weight: normal;font-size: 12px">利润率超过${productDetails.maxDiscount}%</b>超过<span style="color: #FF7D54">${productDetails.agentNum}</span>人代理</p>--%>
        </div>
        <c:choose>
            <c:when test="${productDetails.stock<=0 || productDetails.isQueue==1}">
                <div class="dlpople">
                    <p class="laba" style="margin-right: 10px;"><img src="<%=path%>/static/images/laba.png" alt="">此商品已经进入排单期<b
                            class="paidan">?</b>
                    </p>
                        <%--<c:otherwise>--%>
                        <%--<p><span>${productDetails.stock}件</span></p>--%>
                        <%--</c:otherwise>--%>
                </div>
            </c:when>
        </c:choose>
        <div class="dlpople">
            <p>运费</p>

            <p><span>到付</span></p>
        </div>
        <nav>
            <ul>
                <li>
                    <h1><img src="<%=path%>/static/images/xq_nav.png" alt=""></h1>
                    <span>正品保障</span>
                </li>
                <li>
                    <h1><img src="<%=path%>/static/images/xq_nav2.png" alt=""></h1>
                    <span>合伙人优惠</span>
                </li>
                <li>
                    <h1><img src="<%=path%>/static/images/xq_nav4.png" alt=""></h1>
                    <span>创业补贴</span>
                </li>
            </ul>
        </nav>
        <main>
            <h1 style="background:white url('<%=path%>/static/images/xiangqing_1.png') no-repeat 10px;background-size: 15px;">
                <a name="1f">品牌介绍</a></h1>
            <%--<img src="${productDetails.logoUrl}" alt="">--%>
            <p>${productDetails.brand}</p>

            <h1 style="background:white url('<%=path%>/static/images/xiangqing_2.png') no-repeat 10px;background-size: 15px;">
                <a name="2f">商业政策</a></h1>

            <p>${productDetails.policy}</p>

            <h1 style="background:white url('<%=path%>/static/images/xiangqing_3.png') no-repeat 10px;background-size: 15px;">
                <a name="3f">商品详情</a></h1>

            <p>${productDetails.content}</p>
        </main>
        <div class="fixe">
            <div class="left">
                <ul>
                    <li><a href="#1f"
                           style="background:url('<%=path%>/static/images/xiangqing_1.png') no-repeat 0px;background-size: 16px;">品牌介绍</a>
                    </li>
                    <li><a href="#2f"
                           style="background:url('<%=path%>/static/images/xiangqing_2.png') no-repeat 0px;background-size: 16px;">商业政策</a>
                    </li>
                    <li><a href="#3f"
                           style="background:url('<%=path%>/static/images/xiangqing_3.png') no-repeat 0px;background-size: 16px;">商品详情</a>
                    </li>
                </ul>
            </div>
            <span class="btn">+</span>
        </div>
    </div>
</div>
<footer>
    <section class="sec3">
        <input id="skuId" value="${productDetails.id}" style="display: none"/>
        <c:if test="${productDetails.isTrial==1 && (empty pfUserSku || pfUserSku.isPay==0)}">
            <p>
                <a class="first_p" onclick="validateCodeJS.applyTrial('trial')"><span>申请试用</span></a>
                <a class="first_p2"><span>已试用</span></a>
            </p>
        </c:if>
        <c:if test="${empty pfUserSku && productDetails.isUserByLink==0 && empty pfBorder}"><!--未代理,链接进入><-->
        <p><a class="last_p" onclick="validateCodeJS.applyTrial('applyPartner')">申请合伙人</a>
        </p>
        </c:if>
        <c:if test="${empty pfUserSku && productDetails.isUserByLink==1 && empty pfBorder}"><!--未代理，小白进入><-->
        <p><a class="last_p" onclick="showDialogOfCommonUser()">申请合伙人</a>
        </p>
        </c:if>
        <c:if test="${empty pfUserSku && productDetails.isUserByLink==0 && not empty pfBorder && (pfBorder.orderStatus==0 || pfBorder.orderStatus==9)}"><!--未支付><-->
        <p><a class="last_p" href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pfBorder.id}">申请合伙人</a>
        </p>
        </c:if>
        <c:if test="${not empty pfUserSku && pfUserSku.isPay==1}">
            <p onclick="gotoBuhuo()"><a class="last_p2">您已合伙</a></p>
        </c:if>
    </section>
</footer>
<div class="back_box">
    <div class="back_j" style="display: none">
        <span class="close">×</span>

        <p class="biao">绑定手机号</p>

        <div>
            <p>手机号：<input type="tel" class="phone" id="phoneId"></p>
        </div>
        <div class="d">
            <p>验证码：<input type="tel" id="validateNumberDataId">
                <button id="validateNumberId">获取验证码</button>
            </p>
        </div>
        <p class="tishi" id="errorMessageId"></p>

        <h1 class="j_qu" id="nextPageId">下一步</h1>
    </div>
    <div class="back"></div>
    <div class="back_q">
        <h1>什么是排单期？</h1>

        <p>
            由于商品过于火爆，导致库存量不足。申请合伙人或补货我们将记录付款的先后顺序，待产能提升，麦链商城将按照付款顺序发货
        </p>
        <button class="zhidao">我知道了</button>
    </div>
    <div class="back_login" style="display:none;">
        <h2>您已合伙</h2>

        <p>您已合伙，如需要补货，请在库存管理中补货</p>

        <h1><span class="zhidao">我知道了</span><span
                onclick="javascript:window.location.replace('<%=basePath%>product/user/${pfUserSku.userId}');">去补货</span>
        </h1>
    </div>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="message">
        <h1>您需要通过您的上级发送给您的二维码才可以申请合伙人。如无上级，填写表单。</h1>

        <p><em>姓　名：</em><input type="text" id="applyName"></p>

        <p><em>微信号：</em><input type="text" id="wxCode"></p>

        <p><em>手机号：</em><input type="text" id="mobile"></p>

        <p class="tishi"><span id="applyErrorMessageId"></span></p>

        <h2>
            <button onclick="clicHide()">取消</button>
            <button onclick="submitApply()">确定</button>
        </h2>
    </div>
</div>
<script src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/product.js"></script>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script src="<%=path%>/static/js/validateCode.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script src="${path}/static/js/repetitionForm.js"></script>
<script>
    $(document).ready(function () {
        productJS.initPage();
        validateCodeJS.initPage();
    });

    var mySwiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay: 3000,
        // 如果需要分页器
        pagination: '.swiper-pagination'
    })
    //表单隐藏
    function clickShow() {
        $(".black").show();
    }
    function clicHide() {
        $(".black").hide();
    }
    $(".b_qu").on("click", function () {
        $(".back").css("display", "none");
        $(".back_b").hide();
    })

    $(".b_que").on("click", function () {
        var skuId = $("#skuId").val();
        $.ajax({
            url: '<%=basePath%>product/user/addStock',
            type: 'post',
            data: {stock: i, skuId: skuId},
            dataType: 'json',
            success: function (data) {
                if (data['isError'] == false) {
                    window.location.href = "<%=basePath%>border/payBOrder.shtml/?bOrderId=" + data.orderCode + "";
                } else {
                    alert(data['message']);
                }
            }
        });
    })
    $(".btn").toggle(function () {
        $(this).parent().animate({
            width: "90%"
        })

        $(".left").show("slow");
        $(this).addClass("on").delay(3000).removeClass("on");
        $(this).html("—")
    }, function () {
        $(this).parent().animate({
            width: "50px"
        })
        $(this).prev().hide()
        $(".left").hide();
        $(".fixe").removeClass("active");
        $(this).addClass("on")
        $(this).html("+")
    })
    $(".paidan").on("click", function () {
        $(".back").show();
        $(".back_q").show();
        $(".back_box").show();
    })
    $(".zhidao").on("click", function () {
        $(".back").hide();
        $(".back_q").hide();
        $(".back_login").hide();
        $(".back_box").hide();
        $(".back_user").hide();
    })
    function gotoBuhuo() {
        $(".back").show();
        $(".back_login").show();
        $(".back_box").show()
    }

    //小白用户提醒
    function showDialogOfCommonUser() {
        $("#applyErrorMessageId").empty();
        $("#applyName").val("");
        $("#wxCode").val("");
        $("#mobile").val("");
        $(".black").show();
    }
    //小白用户提交意向申请
    function submitApply() {
        var ApplyData = {};
        ApplyData.applyName = $("#applyName").val();
        ApplyData.wxCode = $("#wxCode").val();
        ApplyData.mobile = $("#mobile").val();
        ApplyData.skuId = '${productDetails.id}';
        if (ApplyData.applyName == null || ApplyData.applyName == "") {
            $("#applyErrorMessageId").empty();
            $("#applyErrorMessageId").html("姓名不能为空");
            return;
        }
        if (ApplyData.wxCode == null || ApplyData.wxCode == "") {
            $("#applyErrorMessageId").empty();
            $("#applyErrorMessageId").html("微信号不能为空");
            return;
        }
        if (!isNumber(ApplyData.wxCode)) {
            $("#applyErrorMessageId").empty();
            $("#applyErrorMessageId").html("请输入6~20个字符，字母、数字、下划线或减号。");
            return false;
        }
        if (ApplyData.mobile == null || ApplyData.mobile == "") {
            $("#applyErrorMessageId").empty();
            $("#applyErrorMessageId").html("手机号不能为空");
            return;
        }
        if (!isMobile(ApplyData.mobile)) {
            $("#applyErrorMessageId").empty();
            $("#applyErrorMessageId").html("手机号格式不正确");
            return;
        }
        //check 手机号是否被绑定
        var checkPhone =function(){
            var isbinded = false;
            $.ajax({
                url: '<%=basePath%>product/checkBindedPhone.do',
                type: 'post',
                data: {phone:ApplyData.mobile},
                dataType: 'json',
                async:false,
                success: function (data) {
                    if (data.isError == true && data.isBinded == true) {
                        isbinded = true;
                    }
                }
            });
            return isbinded;
        }
        if(checkPhone()){
            $("#applyErrorMessageId").empty();
            $("#applyErrorMessageId").html("该手机号已经申请过！");
            return;
        }
        $.ajax({
            url: '<%=basePath%>product/addAgent.do',
            type: 'post',
            data: ApplyData,
            dataType: 'json',
            async:false,
            beforeSend:function(){
                fullShow();
            },
            success: function (data) {
                if (data.isError == false) {
                    alert("您的申请已经提交，请耐心等待");
                    $(".black").hide();
                }
            },
            complete:function(){
                fullHide();
            }
        });
    }

    function isMobile(code) {
        var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
        if (!patrn.exec(code)) {
            return false;
        }
        $("#errorMessageId").attr("style", "display:none");
        return true;
    }
    function isNumber(s) {
        var patrn = /^[a-zA-Z0-9_-]{6,20}$/;
        if (!patrn.exec(s)) {
            return false;
        }
        return true;
    }
</script>
</body>
</html>