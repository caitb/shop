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
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/common.jsp" %>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/xiangqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
</head>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="javascript:;"onClick="javascript:history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>我要成为合伙人</p>
        </header>
        <div class="banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach items="${productDetails.comSkuImages}" var="urlImg">
                        <div class="swiper-slide"><img src="${urlImg.fullImgUrl}" alt=""></div>
                    </c:forEach>
                </div>
                <!-- 如果需要分页器 -->
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <div class="price">
            <p>${productDetails.name}</p>
            <p><span style="padding:0;">${productDetails.slogan}</span></p>
            <p><b>￥${productDetails.priceRetail}</b><%--<span>最高利润${productDetails.maxDiscount}%
            </span>--%></p>
            <p style="padding-bottom: 5px;">超过<span style="color: #FF7D54">${productDetails.agentNum}</span>人代理<b style="color:#999999;font-weight: normal;font-size: 12px">利润率超过${productDetails.maxDiscount}%</b></p>
        </div>
<%--        <div class="dlpople">
            <p>快递：<span>到付</span></p>
            <p>代理人数：<b><span>${productDetails.agentNum}</span>人</b></p>
        </div>--%>
        <div class="dlpople">
            <p>库存</p>
            <p>此商品已经进入排单期<b>?</b></p>
            <p><span>${productDetails.stock}件</span></p>
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
                    <h1><img src="<%=path%>/static/images/xq_nav3.png" alt=""></h1>
                    <span>七天退换</span>
                </li>
                <li>
                    <h1><img src="<%=path%>/static/images/xq_nav4.png" alt=""></h1>
                    <span>创业补贴</span>
                </li>
            </ul>
        </nav>
        <main>
            <%--${productDetails.content}--%>
            <h1 style="background:white url('<%=path%>/static/images/xiangqing_1.png') no-repeat 10px;background-size: 15px;"><a name="1f">品牌介绍</a></h1>
                <img src="<%=path%>/static/images/chanpin%20(1).png" alt="">
            <p>阿拉斯加的拉伸空间大莱卡时间到了卡上就的拉伸空间的拉伸空间的拉伸空间打开就打算离开的骄傲是老大说离开家德拉科阿斯兰的拉伸空间的拉伸空间的考拉三季度来看</p>
                <h1 style="background:white url('<%=path%>/static/images/xiangqing_2.png') no-repeat 10px;background-size: 15px;"><a name="2f">品牌介绍</a></h1>
                <img src="<%=path%>/static/images/chanpin%20(1).png" alt="">
                <p>阿拉斯加的拉伸空间大莱卡时间到了卡上就的拉伸空间的拉伸空间的拉伸空间打开就打算离开的骄傲是老大说离开家德拉科阿斯兰的拉伸空间的拉伸空间的考拉三季度来看</p>
                <h1 style="background:white url('<%=path%>/static/images/xiangqing_3.png') no-repeat 10px;background-size: 15px;"><a name="3f">品牌介绍</a></h1>
                <img src="<%=path%>/static/images/chanpin%20(1).png" alt="">
                <p>阿拉斯加的拉伸空间大莱卡时间到了卡上就的拉伸空间的拉伸空间的拉伸空间打开就打算离开的骄傲是老大说离开家德拉科阿斯兰的拉伸空间的拉伸空间的考拉三季度来看</p>
        </main>
        <div class="fixe">
        <div class="left">
            <ul>
                <li><a href="#1f" style="background:url('<%=path%>/static/images/xiangqing_1.png') no-repeat 0px;background-size: 15px;">品牌介绍</a></li>
                <li><a href="#2f"  style="background:url('<%=path%>/static/images/xiangqing_2.png') no-repeat 0px;background-size: 15px;">品牌介绍</a></li>
                <li><a href="#3f"  style="background:url('<%=path%>/static/images/xiangqing_3.png') no-repeat 0px;background-size: 15px;">品牌介绍</a></li>
            </ul>
        </div>
            <span class="btn">—</span>
        </div>
        <div class="back_j" style="display: none">
            <p class="biao">绑定账号</p>
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
        <div class="back_b">
            <p>增加库存</p>
            <h4>商品:　　<span id="addsku"></span></h4>
            <h4>数量:　　<div>
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
        <div class="back"></div>
        <div class="back_q">
            <h1>什么事结算中</h1>
            <p>
                为了响应国家爱号召，增强用户体验，平台支持7天退货，您的资金在对方确认收货后7天内属于结算中，7天后将自动转到可提现。
            </p>
            <button>我知道了</button>
        </div>
    </div>

</div>
<footer>
    <section class="sec3">
        <input id="skipPageId" value="trial" style="display: none" />
        <input id="skuId" value="${productDetails.id}" style="display: none"/>
        <c:if test="${empty pfUserSku || pfUserSku.isPay==0}">
            <p>
                <a id="applyTrial" onclick="validateCodeJS.applyTrial()">申请试用</a>
                <a id="trialed" style="display: none">已试用</a>
            </p>
            <p style="background: #DA3600;"><a href="<%=basePath%>userApply/apply.shtml?skuId=${productDetails.id}">申请合伙人</a>
            </p>
        </c:if>
        <c:if test="${not empty pfUserSku && pfUserSku.isPay==1}">
            <p style="background: #DA3600;"><a onclick="buhuokucun('${productDetails.name}')"
                    href="javascript:;">补货</a></p>
        </c:if>
    </section>
</footer>
<script src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/product.js"></script>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script src="<%=path%>/static/js/validateCode.js"></script>
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
    var i=1;
    $(".jia").on("click",function(){
        i++;
        $(".number").val(i)
    })
    $(".jian").on("click",function(){
        if(i==1){
            return false;
        }
        i--;
        $(".number").val(i)
    })
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
    $(".btn").toggle(function(){
        $(this).parent().animate({
            width:"90%"
        })
        $(".fixe").addClass("active");
        $(this).addClass("on").delay(3000).removeClass("on")
        $(".left").show()
    },function () {
        $(this).parent().animate({
            width:"50px"
        })
        $(this).prev().hide()
        $(".fixe").removeClass("active");
        $(this).addClass("on")
        $(".left").hide()
    })
</script>
</body>
</html>