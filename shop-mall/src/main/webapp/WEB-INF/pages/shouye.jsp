<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <%--<link rel="stylesheet" href="<%=path%>/static/css/pageCss/loading.css">--%>
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/shouye.css">
</head>
<body>
<%--<c:if test="${forcusSF!=true}">--%>
<%--<div class="addb">--%>
<%--<p>关注麦链公众微信号“<span class="add">麦链商城</span>”，查佣金，查订单。</p>--%>
<%--<label class="close">×</label>--%>
<%--</div>--%>
<%--</c:if>--%>
<div class="wrap">
    <c:if test="${userPid!=user.id && userPid != sfShop.userId && userPid !=null}">
        <div class="na">
            <p><img src="${pUser.wxHeadImg}" alt=""></p>
            <h1>
                <span>我是${pUser.wxNkName}<br>我为好友代言，跟我一起分享赚佣金！</span>
                    <%--<span>跟我一起：呐喊得红包，分享赚佣金</span>--%>
            </h1>
        </div>
    </c:if>
    <div class="header">
        <div>
            <img src="${path}/static/images/qwe%20(2).png" alt="">
            <p>${sfShop.name}<c:if test="${not empty sfShop.logo}"><img src="${sfShop.logo}" alt=""></c:if></p>
            <p>${sfShop.explanation}　</p>
            <%--<img id="fenxiang" src="<%=path%>/static/images/fen.png" alt="">--%>
        </div>
        <div>
            <p>
                <span>麦链商城官方认证</span>
                <span>商家已缴纳${bail}保证金</span>
            </p>
            <%--<c:forEach begin="0" end="${size}" step="2" var="i">--%>
            <p><c:forEach items="${SfShopDetails}" begin="0" end="0" var="sf">
                <span style="background:url('${sf.icon}')no-repeat 0;background-size: 14px 14px;">${sf.skuName}${sf.agentLevelName}级认证</span>
            </c:forEach>
            </p>
            <%--</c:forEach>--%>
        </div>

        <c:if test="${empty sfShop.logo}"><img src="<%=path%>/static/images/touxiang.png" alt=""></c:if>
    </div>
    <div class="banner">
        <div class="shout">
            <img src="${path}/static/images/yuan.png" alt="">
            <span>已有</span>
            <%--<span><em id="nahhan">${sfShop.shoutNum}</em>人</span>--%>
            <span id="shout">
                <p><c:forEach  items="${num}" var="i" >
                    <b>${i}</b>
                    <%--<b>1</b><b>2</b><b>3</b><b>4</b><b>5</b>--%>
                </c:forEach>
                    <em>人</em></p>
            </span>
            <span>为ta呐喊</span>
        </div>
    </div>
    <div class="content">
        <h1>在售商品</h1><c:forEach items="${SfShopDetails}" var="sd">
        <section class="sec1" onclick="javascript:window.location.replace('<%=basePath%>shop/detail.shtml/?skuId=${sd.skuId}&shopId=${sfShop.id}');">
            <p class="photo">
                <img src="${sd.skuUrl}" alt="">
            </p>

            <div>
                <h2 style="padding-right: 20px;">${sd.skuAssia}</h2>

                <h3>${sd.slogan}</h3>

                    <%--<h2>运费：<span><c:if test="${ok==false}">包邮</c:if><c:if test="${ok==true}">${sfShop.shipAmount}</c:if></span><b><i>￥</i>${sd.priceRetail}</b></h2>--%>
                <h2>运费：<span>包邮</span><b><i>￥</i>${sd.priceRetail}</b></h2>
                <p>
                    <button>立即购买</button>
                </p>
            </div>
        </section></c:forEach>
    </div>
    <footer>
        <div>
            <p class="active" onclick="javascript:window.location.replace('<%=basePath%>${shopId}/${userPid}/shop.shtml');">
                <span><img src="<%=path%>/static/images/footer_x%20(3).png" alt=""></span>
                <span>首页</span>
            </p>
            <p onclick="javascript:window.location.replace('<%=basePath%>shop/sharePlan?shopId=${sfShop.id}');">
                <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
                <span>分享赚钱</span>
            </p>
            <p onclick="javascript:window.location.replace('<%=path%>/sfOrderManagerController/toBorderManagement?fm=1');">
                <span><img src="<%=path%>/static/images/footer%20(2).png" alt=""></span>
                <span>个人中心</span>
            </p>
        </div>
    </footer>
</div>
<div id="ok" class="back_f" style="display: none">
    <h1>呐喊成功！</h1>
    <img src="<%=path%>/static/images/qwe%20(1).png" alt="">
    <p>获取您的专属海报，分享到朋友圈或微信好友。通过您分享的海报产生购买后，您将获得佣金</p>
    <button onclick="javascript:window.location.replace('<%=basePath%>shop/getPoster?shopId=${sfShop.id}');">获取我的专属海报</button>
    <span  id="okCloss">×</span>
</div>
<div id="no" class="back_f" style="display: none">
    <h1>您已呐喊过，请明天再来 </h1>
    <img src="<%=path%>/static/images/qwe%20(1).png" alt="">
    <p>获取您的专属海报，分享到朋友圈或微信好友。通过您分享的海报产生购买后，您将获得佣金</p>
    <button onclick="javascript:window.location.replace('<%=basePath%>shop/getPoster?shopId=${sfShop.id}');">获取我的专属海报</button>
    <span class="close" >×</span>
</div>
<div id="fen" class="back_f" style="display: none">
    <img src="<%=path%>/static/images/qwe%20(1).png" alt="">
    <p>获取您的专属海报，分享到朋友圈或微信好友。通过您分享的海报产生购买后，您将获得佣金</p>
    <button onclick="javascript:window.location.replace('<%=basePath%>shop/getPoster?shopId=${sfShop.id}');">获取我的专属海报</button>
    <span class="close">×</span>
</div>
<div class="back_g">
    <span class="close">×</span>
    <img src="${path}/static/images/duan.png" alt="">
</div>
<div class="back"></div>
<script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script src="<%=path%>/static/js/pageJs/hideWXShare.js"></script>
<script>
    $(".close").on("click",function(){
        $(this).parent().hide();
        $(".back").hide();
    })
    $(".add").on("click",function () {
        $(".back_g").show()
        $(".back").show()
    })
    $("#okCloss").on("click",function(){
        $("#ok").hide();
        $(".back").hide();
//        location.reload(true);
    })
    //    $("#fenxiang").on("click",function(){
    //        $("#fen").show();
    //        $(".back").show();
    //    })

    <%--var naNum =${sfShop.shoutNum+1};--%>
    $(".banner").on("click",function(){
        var shopId =${sfShop.id};
        $.ajax({
            type:"POST",
            url : "<%=path%>/shout.do",
            data:{shopId:shopId},
            dataType:"Json",
            success:function(data){
                if(data.mallShout){
                    $("#ok").show();
                    $(".back").show();
                    $("#shout").html("");
                    var trHtml = "";
                    trHtml+="<span class=\"shout\"> <p>";
                    $.each(data.num, function(i, String) {
                        trHtml+="<b>"+String+"</b>";
                    })
                    trHtml+="<em>人</em></p> </span>";
                    $("#shout").html(trHtml);
//                    location.reload(true);
                } else{
                    $("#no").show();
                    $(".back").show();
                }
            }
        })
    })
</script>
</body>
</html>