<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/main.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/wodedianpu.css">
</head>
<body>
<div class="wrap" <c:if test="${sfShop==null}">style="display: none;"</c:if> >
    <div class="na">
<%--        <p><img src="${comUser.wxHeadImg}" alt=""></p>
        <h1>
            <span>${comUser.realName}，欢迎登陆~</span>
        </h1>--%>
        <h1>店铺地址：<input value="${shopUrl}"/></h1>
        <p id="copyShopUrl"><b>关闭</b></p>
    </div>
    <div class="header">
        <%--<div>
            <p>${sfShop.name}</p>
            <span onclick="javascript:window.location.replace('<%=basePath%>shop/manage/getPoster?shopId=${sfShop.id}');">分享</span>
            <span style="right: 70px;">浏览</span>
        </div>
        <div>
            <p>
                <span>${sfShop.explanation}</span>
            </p>
        </div>
        <img src="${sfShop.logo}" alt="">--%>
            <div>
                <img src="${comUser.wxHeadImg}" alt="">
                <h1>${sfShop.name}</h1>
                <p>${sfShop.explanation}</p>
                <h3>
                    <span id="showUrl">店铺地址</span>
                    <span onclick="javascript:window.location.replace('${shopUrl}');">店铺预览</span>
                    <span  onclick="javascript:window.location.replace('<%=basePath%>shop/manage/getPoster?shopId=${sfShop.id}');">分享店铺</span>
                </h3>
            </div>
        <nav>
            <p onclick="javascript:window.location.replace('<%=basePath%>sfOrderController/stockShipOrder');"><span>${orderCount}</span><span>店铺总订单</span></p>
            <p><span>${sfShop.saleAmount}</span><span>店铺总销售额</span></p>
            <p><span>
                <c:if test="${shopView == null}">0</c:if>
                <c:if test="${shopView != null}">${shopView}</c:if>
               </span>
                <span>店铺总浏览人数</span>
            </p>
        </nav>
    </div>
    <nav>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/managePro.htmls?shopId=${sfShop.id}&&isSale=1');"><span><img src="<%=basePath%>static/images/foot_icon%20(5).png" alt=""></span><span>商品管理</span></p>
        <p onclick="javascript:window.location.replace('<%=basePath%>sfOrderController/stockShipOrder');"><span><img src="<%=basePath%>static/images/foot_icon%20(3).png" alt=""></span><span>店铺订单</span><%--<c:if test="${sfOrderSize!=0}"><b></b></c:if>--%></p>
        <p onclick="javascript:window.location.replace('<%=basePath%>shop/manage/setupShop');"><span><img src="<%=basePath%>static/images/foot_icon%20(2).png" alt=""></span><span>店铺设置</span></p>
    </nav>
    <nav style="margin:0;">
        <p onclick="javascript:window.location.replace('<%=basePath%>distribution/distribution.shtml');"><span><img src="<%=basePath%>static/images/foot_icon%20(4).png" alt=""></span><span>分销记录</span></p>
        <%--<p onclick="javascript:window.location.replace('<%=basePath%>shop/manage/setupFreight');"><span><img src="<%=basePath%>static/images/foot_icon%20(1).png" alt=""></span><span>运费模板</span></p>--%>
        <p></p>
        <p></p>
    </nav>
</div>
<c:if test="${sfShop==null}">
    <div class="wrap_no">
        <p>您还不是合伙人，去<a href="<%=basePath%>marketGood/market">好货市场</a>看看吧~</p>
    </div>
</c:if>
<c:import url="/WEB-INF/pages/common/nav-footer.jsp"></c:import>
</body>
<script src="<%=basePath%>static/js/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>static/js/jquery.zclip.js"></script>
<script>
    $(document).ready(function(){
        if ( window.clipboardData ) {
            $('#copyShopUrl').click(function() {
                window.clipboardData.setData("Text", $('#shopUrl').text());
                alert('复制成功！');
            });
        } else {
            $("#copyShopUrl").zclip({
                path:'http://img3.job1001.com/js/ZeroClipboard/ZeroClipboard.swf',
                copy:function(){return $('#shopUrl').text();},
                afterCopy:function(){alert('复制成功！');}
            });
        }
        $("#showUrl").on("click",function(){
            $(".na").css("display","-webkit-box");
        });x
        $("#copyShopUrl").on("click",function(){
            $(this).parent().hide();
        });
    });
</script>
</html>