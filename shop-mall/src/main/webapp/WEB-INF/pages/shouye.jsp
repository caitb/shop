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
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/shouye.css">
</head>
<body>
    <div class="wrap">
        <div class="na">
                   <p>${pUser.wxHeadImg}</p>
                   <h1>
                       <span>我是${pUser.wxNkName}，我为麦链商城呐喊！</span>
                       <span>跟我一起：呐喊得红包，分享赚佣金</span>
                   </h1>
        </div>
        <div class="header">
            <div>
                <p>${sfShop.name}</p>
                <p>${planation}</p>
                <img src="<%=path%>/static/images/fen.png" alt="">${sfShop.logo}
            </div>
            <div>
                <p>
                    <span>麦链商城官方认证</span>
                    <span>商家已缴纳${bail}保证金</span>
                </p><c:forEach begin="0" end="${size}" step="2" var="i">
                <p><c:forEach items="${SfShopDetails}" begin="${i}" end="${i+1}" var="sf">
                    <span style="background:url('<%=path%>/static/images/f.png')no-repeat 0;background-size: 14px 14px;">${sf.icon}${sf.skuName}${sf.agentLevelName}合伙人认证</span>
                    </c:forEach>
                </p></c:forEach>
            </div>
            <img src="<%=path%>/static/images/admin.png" alt="">
        </div>
        <div class="banner">
            <p>
                <span>已有</span>
                <span><em>${sfShop.shoutNum}</em>人</span>
                <span>为ta呐喊</span>
                <img src="<%=path%>/static/images/an.png" alt="">
            </p>
        </div>
        <div class="content">
            <h1>在售商品</h1><c:forEach items="${SfShopDetails}" var="sd">
            <section class="sec1">
                    <p class="photo">
                                   <img src="${sd.skuUrl}" alt="">
                    </p>
                    <div>
                        <h2>${sd.skuName}</h2>
                        <h3>30秒瘦脸立即见效</h3>
                        <h2>运费：<span>${sfShop.shipAmount}</span><b>￥${sd.priceRetail}</b></h2>
                        <p>
                            <button>立即购买</button>
                        </p>
                    </div>
            </section></c:forEach>
        </div>
        <footer>
           <div>
                <p class="active">
                    <span><img src="<%=path%>/static/images/footer_x%20(3).png" alt=""></span>
                    <span>首页</span>
                </p>
                <p>
                    <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
                    <span>分享计划</span>
                </p>
                <p>
                    <span><img src="<%=path%>/static/images/footer%20(2).png" alt=""></span>
                    <span>个人中心</span>
                </p>
            </div>
        </footer>
    </div>
    <div class="back_f">
        <h1>呐喊成功！</h1>
        <img src="<%=path%>/static/images/qwe%20(1).png" alt="">
        <p>分享到店铺到朋友圈，为您的朋友呐喊，通过您分享的链接产生购买后，您将获得佣金</p>
        <button>获取我的专属海报</button>
        <span class="close">×</span>
    </div>
    <div class="back"></div>
    <script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
    <script>
        $(".close").on("click",function(){
            $(this).parent().hide();
            $(".back").hide();
        })
    </script>
</body>
</html>