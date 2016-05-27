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
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/plugins/swipwr/swiper.3.1.7.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <%--<script src="<%=path%>/static/js/hideWXShare.js"></script>--%>
</head>
<body>
<%--<c:if test="${forcusPF!=true}">--%>
<%--<div class="na">--%>
    <%--<p>关注麦链公众号“<span class="add">麦链合伙人</span>”，管理店铺，发展下级。</p>--%>
    <%--<label class="close">×</label>--%>
<%--</div>--%>
<%--</c:if>--%>
<div class="wrap">
    <div class="box">
        <div class="banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach items="${pbBanner}" var="url">
                        <div class="swiper-slide" onclick="javascript:window.location.replace('<%=path%>${url.hyperlinkUrl}');"><img src="${url.imgUrl}" alt=""></div>
                    </c:forEach>
                    <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner.png" alt=""></div>--%>
                    <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner2.png" alt=""></div>--%>
                    <%--<div class="swiper-slide"><img src="<%=path%>/static/images/shouye_banner3.png" alt=""></div>--%>
                </div>
                <!-- 如果需要分页器 -->
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <div class="index_login" style="display: block">
            <div class="admin" >
                <img src="${user.wxHeadImg}" alt="">
                <h3>${user.wxNkName}—欢迎您登入</h3>
            </div> <c:if test="${user.isAgent==1}">
            <p><b>${num}</b><span>下级合伙人</span></p>
            <ul>
                <li><p>总销售额</p><h1>￥<span>${comUserAccount.totalIncomeFee}</span></h1></li>
                <li><p>总利润</p><h1>￥<span>${comUserAccount.profitFee}</span></h1></li>
            </ul></c:if>
        </div><c:if test="${user.isAgent==0}">
        <div class="index_Nlogin" >
            <span style="background: url('<%=path%>/static/images/index_login1.png') no-repeat;background-size: contain;"></span>
            <img src="<%=path%>/static/images/index_login.png" alt=""><p>您还不是合伙人，先去好货市场看看吧~</p>
        </div></c:if>
        <nav>
            <ul>
                <li>
                    <a href="<%=path%>/marketGood/market">
                        <h1><img src="<%=path%>/static/images/1%20(1).png" alt=""></h1>
                        <span>好货市场</span></a>
                </li>
                <li class="market">
                    <a class="herf" href="<%=basePath%>product/user/${user.id}">
                        <h1><img src="<%=path%>/static/images/1%20(2).png" ></h1>
                        <span>库存管理</span></a>
                </li>
                <li>
                    <a  href="<%=path%>/borderManage/borderManagement.html">
                        <h1><img src="<%=path%>/static/images/1%20(5).png" alt=""><%--<c:if test="${borderNum!=0}"><span></span></c:if>--%></h1>
                        <span>合伙人订单</span></a>
                </li>
            </ul>
        </nav>
        <nav>
            <ul>
                <li class="market"><a class="herf" href="<%=basePath%>myteam/teamlist">
                    <h1><img src="<%=path%>/static/images/1%20(4).png" alt="">
                        <%--<span>1</span>--%>
                    </h1>
                    <span>我的团队</span></a>
                </li>
                <li class="market"><a class="herf" href="<%=basePath%>developing/ui">
                    <h1><img src="<%=path%>/static/images/1%20(6).png" alt=""></h1>
                    <span>发展合伙人</span></a>
                </li>
                <li class="market"><a class="herf" href="<%=basePath%>userCertificate/userList/${user.id}">
                    <h1><img src="<%=path%>/static/images/1%20(3).png" alt=""></h1>
                    <span>我的授权书 </span></a>
                </li>
            </ul>
        </nav>
        <%--                <nav>
                         <ul>
                                <li class="market"><a class="herf" href="javascript:;">
                                        <h1><img src="<%=path%>/static/images/index_nav%20(5).png" alt=""></h1>
                                        <span>我的店铺</span></a>
                                </li>
                                <li class="market"><a class="herf" href="javascript:;">
                                        <h1><img src="<%=path%>/static/images/index_nav%20(8).png" alt=""></h1>
                                        <span>我的资产</span></a>
                                </li>
                                <li ><a href="<%=path%>/personalInfo/personalHomePageInfo.html">
                                        <h1><img src="<%=path%>/static/images/index_nav%20(2).png" alt=""></h1>
                                        <span>个人信息</span></a>
                                </li>
                        </ul>
                    </nav>--%>
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
                <a href="<%=path%>/personalInfo/personalHomePageInfo.html">
                    <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
                    <span>个人中心</span>
                </a>
            </div>
        </footer>
    </div>
    <script>
        var mySwiper = new Swiper ('.swiper-container', {
            direction: 'horizontal',
            loop: true,
            autoplay: 3000,
            autoplayDisableOnInteraction : false,
            // 如果需要分页器
            pagination: '.swiper-pagination'
        })
        //             var myScroll = new IScroll(".wrap",{
        //                 preventDefault: false
        //            })
        var length=10;
        var obj={
            length:5,
            method:function(fn){
                fn();
                arguments[0]();
            }
        };
        var fn1=function(){
            console.log(this.length);
        };
        obj.method(fn1);
        $(".market").on("click",function(){
            var agent= ${user.isAgent};
            if(agent==0) {
                $(".back_login").attr("style", "display:block");
                $(".back").attr("style", "display:block");
                $(".herf").attr("href","javascript:;");
            }
        })
        <%--$(function(){--%>
            <%--var border= ${pfBorders10};--%>
            <%--if(border==null || border==""){--%>
                <%--$(".back_j").attr("style", "display:block");--%>
                <%--$(".back").attr("style", "display:block");--%>
                <%--$(".j_qu").attr("href","javascript:;");--%>
            <%--}--%>
        <%--})--%>
        $(".back").on("click",function(){
            $(".back_login").hide();
            $(".back").hide();
        })
        $("#quxiao").on("click",function(){
            $(".back_login").hide();
            $(".back").hide();
        })
        $("#goMark").on("click",function(){
            $(".back_login").hide();
            $(".back").hide();
            <%--window.setTimeout("window.location='<%=path%>/marketGood/market'",1000);--%>
            window.location.href="<%=path%>/marketGood/market";
        })
        $(".add").on("click",function () {
            $(".back").show()
            $(".back_f").show()
        })
        $(".close").on("click",function () {
            $(".back").hide()
            $(".back_f").hide()
        })

        //var config = window.eval('('+shareMap+')');
        wx.config({
            debug: false,
            appId: '${shareMap.appId}',
            timestamp: '${shareMap.timestamp}',
            nonceStr: '${shareMap.nonceStr}',
            signature: '${shareMap.signature}',
            jsApiList: [
                'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'onMenuShareQZone',
                'hideOptionMenu',
            ]
        });
    </script>
</body>
</html>