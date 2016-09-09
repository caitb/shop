<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>/static/css/xiajihehuo.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="<%=basePath%>index"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>我的家族</p>
    </header>
    <main>
        <div class="nav">
            <p>
                <span>${agentSkuMaps.size()}</span>
                <span>合伙产品数</span>
            </p>
            <p>
                <span>${totalChild}</span>
                <span>总人数</span>
            </p>
            <p>
                <span>￥${totalSales}</span>
                <span>总销售额</span>
            </p>
        </div>
        <ul>
            <li class="active"><span>我创建的</span></li>
            <li onclick="clickShow()"><span>我加入的</span></li>
        </ul>
        <div class="listBox">
            <c:forEach items="${agentSkuMaps}" var="agentSkuMap">
                <div class="floor">
                    <div class="sec1">
                    <p><img src="${agentSkuMap.brandLogo}" alt=""></p>
                    <div>
                        <p><b>${agentSkuMap.skuName}</b></p>
                        <p>
                            <span>团队人数：<b>${agentSkuMap.countChild}</b></span>
                            <span>销售额：<b>￥${agentSkuMap.countSales}</b></span>
                        </p>
                    </div>
                </div>
                        <%--<c:if test="${agentSkuMap.isLastLevel == 'no'}"><h1 class="set" onclick="javascript:window.location.replace('<%=basePath%>myteam/teamdetail?userSkuId=${agentSkuMap.userSkuId}');">管理团队</h1></c:if>--%>
                        <%--<c:if test="${agentSkuMap.isLastLevel == 'yes'}"><h1 class="team">您没有团队管理功能<img class="once" src="${path}/static/images/icon_70.png"/></h1></c:if>--%>
                    <h1>
                        <span onclick="clickShow()">设置<i></i></span>
                        <span><a href="<%=basePath%>myteam/teamdetail?userSkuId=${agentSkuMap.userSkuId}">管理团队</a></span>
                    </h1>
                </div>
            </c:forEach>
        </div>
    </main>
    <div class="paidanqi">
        <div class="back_q">
            <p style="padding: 20px">您代理的这款产品等级是最后一级,无团队管理功能!您可以升级您的合伙人等级获取此功能。
            </p>
            <button class="kNow" style="font-size: 12px;">我知道了</button>
        </div>
        <div class="Modal"></div>
    </div>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="back_a">
        <img src="${path}/static/images/ku.png" alt=""/>
        <p>此功能仅支持在麦链合伙人APP使用</p>
        <h1>
            <span onclick="clickHide()">取消</span>
            <span><a href="${path}/download/loadapp" style="color:#fff;">去下载</a></span>
        </h1>
    </div>
</div>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>static/js/hideWXShare.js"></script>
<script src="<%=path%>/static/js/download.js"></script>
<script>
    $(".once").on("click", function () {
        $(".paidanqi").show();
    });
    $(".kNow").on("click", function () {
        $(".paidanqi").hide();
    });
//    var index=0;
//    $("ul li").on("click", function () {
//        index=$(this).index();
//        $("ul li").eq(index).addClass("active").siblings().removeClass("active");
//    })
    function clickShow(){
        $(".black").show();
    }
    function clickHide(){
        $(".black").hide();
    }
</script>
</body>
</html>