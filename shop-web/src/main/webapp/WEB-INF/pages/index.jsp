<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/index.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.css">
</head>
<body>
<div class="wrap">
    <c:if test="${status == 1}">
        <div class="header" style="display:block">
            <h4>请3天内完成实名认证，逾期店铺不能访问<a href="<%=basePath%>identityAuth/toInentityAuthPage.html">点击认证</a></h4>
        </div>
    </c:if>
    <div class="box">
        <div class="banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach items="${pbBanner}" var="url">
                        <div class="swiper-slide"><a href="<%=path%>${url.hyperlinkUrl}"><img src="${url.imgUrl}"></a></div>
                    </c:forEach>
                </div>
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <div class="index_login" style="display: block">
            <div class="admin">
                <p style="background: url('${user.wxHeadImg}') no-repeat;background-size: 100% 100%;"></p>

                <h3>${user.wxNkName}—欢迎您登入</h3>
            </div>
            <div class="floor">
            </div>
        </div>
        <%--<c:if test="${user.isAgent==0}">--%>
            <%--<div class="index_Nlogin">--%>
                <%--<span style="background: url('<%=path%>/static/images/index_login1.png') no-repeat;background-size: contain;"></span>--%>
                <%--<img src="<%=path%>/static/images/index_login.png" alt="">--%>
                <%--<p>您还不是合伙人，先去好货市场看看吧~</p>--%>
            <%--</div>--%>
        <%--</c:if>--%>
        <nav>
            <ul>
                <li>
                    <%--<a href="<%=path%>/marketGood/market">--%>
                    <a onclick="clickShow()">
                    <h1><img src="<%=path%>/static/images/1%20(1).png" alt=""></h1>
                        <span>好货市场</span></a>
                </li>
                <li>
                    <a href="<%=path%>/borderManage/borderManagement.html">
                        <h1><img src="<%=path%>/static/images/1%20(5).png" alt="">
                        </h1>
                        <span>订单管理</span></a>
                </li>
                <li >
                    <a class="herf" href="<%=basePath%>product/user/${user.id}">
                        <%--<a onclick="clickShow()">--%>
                        <h1><img src="<%=path%>/static/images/1%20(2).png"></h1>
                        <span>库存</span></a>
                </li>
            </ul>
        </nav>
        <nav>
            <ul>
                <li >
                    <a class="herf" href="<%=basePath%>myteam/teamlist">
                        <%--<a onclick="clickShow()">--%>
                        <h1><img src="<%=path%>/static/images/1%20(4).png" alt="">
                        <span></span>
                    </h1>
                    <span>我的家族</span></a>
                </li>
                <li >
                    <%--<a class="herf" href="<%=basePath%>developing/ui">--%>
                        <a onclick="clickShow()">
                        <h1><img src="<%=path%>/static/images/1%20(6).png" alt=""></h1>
                    <span>发展合伙人</span></a>
                </li>
                <li class="market"><a class="herf" href="<%=basePath%>userCertificate/userList/${user.id}">
                    <h1><img src="<%=path%>/static/images/1%20(3).png" alt=""></h1>
                    <span>我的授权书 </span></a>
                </li>
            </ul>
        </nav>
        <nav>
            <ul>
                <li class="market"><a class="herf" href="<%=basePath%>upgradeInfo/lower?tabId=0">
                    <h1><img src="<%=path%>/static/images/s3.png" alt="" style="width: 30%">
                    </h1>
                    <span>升级管理</span></a>
                </li>
                <li class="market"><a class="herf" href="<%=basePath%>myRecommend/myRecommen.shtml">
                    <h1><img src="<%=path%>/static/images/s2.png" alt=""></h1>
                    <span>我的推荐</span></a>
                </li>
                <li class="market"><a class="herf" href="<%=basePath%>message/center.shtml">
                    <h1><img src="<%=path%>/static/images/s4.png" alt="">
                        <c:if test="${countMsg>0}">
                            <span style="right:28%"></span>
                        </c:if>
                    </h1>
                    <span>消息中心</span></a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<div class="back_j" style="display:none;">
    <p>您还没有选择拿货方式，这个功能很重要，请您务必设置。</p>
    <h1 class="j_qu">点击前往</h1>
</div>
<div class="back_login" style="display:none;">
    <p>您还不是合伙人，先去好货市场看看吧~</p>
    <h1><span id="quxiao">取消</span><span id="goMark">去好货市场</span></h1>
</div>
<div class="back" style="display: none;">
</div>
<div class="back_f">
    <span class="close">×</span>
    <img src="${path}/static/images/b.png" alt="">
</div>
<div class="black">
    <div class="backb"></div>
    <div class="back_a">
        <img src="${path}/static/images/ku.png" alt=""/>
        <p>此功能仅支持在麦链合伙人APP使用</p>
        <h1>
            <span onclick="clickHide()">取消</span>
            <span><a href="${path}/download/loadapp">去下载</a></span>
            <%--<span><a href="<%=path%>/static/html/all.html">去下载</a></span>--%>
        </h1>
    </div>
</div>
<div class="bottom">
    <footer>
        <div class="btm" style="background: #DA3600;">
            <a href="index.html">
                <span><img src="<%=path%>/static/images/footer%20(2).png" alt=""></span>
                <span>我是合伙人</span>
            </a>
        </div>
        <div class="btm">
            <a href="<%=path%>/shop/manage/index">
                <span><img src="<%=path%>/static/images/footer%20(3).png" alt=""></span>
                <span>我的店铺</span>
            </a>
        </div>
        <div class="btm">
            <a href="<%=path%>/account/home">
                <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
                <span>我的账户</span>
            </a>
        </div>
    </footer>
</div>
<script src="<%=basePath%>static/js/zepto.min.js"></script>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/download.js"></script>
<script>
    /**
     * 隐藏微信分享功能
     * @config json字符串或json对象
     */
    function hideWXShare(config){
        if((typeof config)=='string'){
            config = window.eval('('+config+')');
        }
        if((typeof config)!='object'){
            console.log('参数格式不对!');
            return;
        }
        wx.config({
            debug: false,
            appId: config.appId,
            timestamp: config.timestamp,
            nonceStr: config.nonceStr,
            signature: config.signature,
            jsApiList: [
                'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'onMenuShareQZone',
                'hideOptionMenu',
                'hideAllNonBaseMenuItem',
            ]
        });

        wx.ready(function() {
            wx.hideAllNonBaseMenuItem();
        });
    }

    $(function(){
        $.ajax({
            url: '/hideWXShare',
            data: {hideUrl: '<%=path%>/shop/manage/index'},
            ansync: false,
            success: function(config){
                hideWXShare(config);
            }
        })
    });
    $("body").on("swipeLeft", function () {
        location.href = '<%=path%>/shop/manage/index';
    })
</script>

<script>
    var mySwiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay: 3000,
        autoplayDisableOnInteraction: false,
        pagination: '.swiper-pagination'
    })
    var length = 10;
    var obj = {
        length: 5,
        method: function (fn) {
            fn();
            arguments[0]();
        }
    };
    var fn1 = function () {
        console.log(this.length);
    };
    obj.method(fn1);
    $(".market").on("click", function () {
        var agent = ${user.isAgent};
        if (agent == 0) {
            $(".back_login").attr("style", "display:block");
            $(".back").attr("style", "display:block");
            $(".herf").attr("href", "javascript:;");
        }
    })
    $(".back").on("click", function () {
        $(".back_login").hide();
        $(".back").hide();
    })
    $("#quxiao").on("click", function () {
        $(".back_login").hide();
        $(".back").hide();
    })
    $("#goMark").on("click", function () {
        $(".back_login").hide();
        $(".back").hide();
        window.location.href = "<%=path%>/marketGood/market";
    })
    $(".add").on("click", function () {
        $(".back").show()
        $(".back_f").show()
    })
    $(".close").on("click", function () {
        $(".back").hide()
        $(".back_f").hide()
    })
    function clickShow(){
        $(".black").show();
    }
    function clickHide(){
        $(".black").hide();
    }
</script>
<script>
    $(function () {
        var isAgent = ${user.isAgent==1};
        if (isAgent) {
            $.ajax({
                type: "POST",
                url: "${path}/ajaxIndexData.do",
                dataType: "Json",
                success: function (data) {
                    var trHtml1 = "";
                    trHtml1 += "<ul>";
                    trHtml1 += "<li><h1>" + data.count + "</h1><p>团队人数</p></li>";
                    trHtml1 += "<li><h1>" + data.groupSum + "</h1><p>团队总销售额</p></li>";
                    trHtml1 += "<li><h1>" + data.orderNum + "</h1><p>团队总订单</p></li>";
                    trHtml1 += "</ul>";
                    $(".floor").empty().html(trHtml1);
                }
            })
        }
    })
</script>
</body>
</html>