<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/lingquzhengshu.css">
</head>
<body>
<div id="fakeLoader"></div>
<main>
    <%--<c:if test="${isUserForcus==false}">--%>
        <%--<div class="na">--%>
            <%--<p>关注麦链公众号“<span class="add">麦链合伙人</span>”，管理店铺，发展下级。</p>--%>
            <%--<label class="close">×</label>--%>
        <%--</div>--%>
    <%--</c:if>--%>
    <div class="wrap">
        <header class="xq_header">
            <%--<a href="zhifu.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>--%>
            <p>升级申请</p>
        </header>
        <div class="main">
            <div class="drap">
                <h2><img src="${path}/static/images/ready.png" alt=""></h2>
                <p>恭喜您，申请成功！</p>
            </div>
            <c:choose>
                <c:when test="${isQueuing==true}">
                    <p class="row">本次订单将进入排单期，在您前面有<span>${count}</span>人排单。</p>
                </c:when>
                <c:otherwise>
                    <c:if test="${pfBorder.sendType == 1}">
                        <p>您的在线库存已经变更 <span>+${quantity}</span></p>
                    </c:if>
                    <c:if test="${pfBorder.sendType == 2}">
                        <p>我们将尽快安排发货，请耐心等待。
                            <a href="${basePath}borderManage/borderDetils.html?id=${pfBorder.id}"><b>查看订单</b></a></p>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <div class="Name">
                <p>姓名：</p>
                <p>${orderUpgradeDetail.name}</p>
            </div>
            <div class="Name">
                <p>合作产品：</p>
                <p>${orderUpgradeDetail.skuName}</p>
            </div>
            <div class="Name">
                <p>原等级：</p>
                <p>${orderUpgradeDetail.currentAgentLevelName}</p>
            </div>
            <div class="Name">
                <p>原上级：</p>
                <p>${orderUpgradeDetail.oldPUserName}</p>
            </div>
            <div class="Name">
                <p>现等级：</p>
                <p>${orderUpgradeDetail.applyAgentLevelName}</p>
            </div>
            <div class="Name">
                <p>现上级：</p>
                <p>${orderUpgradeDetail.newPUserName}</p>
            </div>
        </div>
        <p>您现在可以：</p>
        <div class="box">
            <a href="${basePath}">
                <div class="sec1">
                    <img src="${path}/static/images/zs%20(4).png" alt="">
                    <p>
                        <span>返回首页</span>
                    </p>
                </div>
            </a>
            <a href="${path}/shop/manage/index">
                <div class="sec1">
                    <img src="${path}/static/images/zs%20(1).png" alt="">
                    <p>
                        <span>查看店铺</span>
                    </p>
                </div>
            </a>
            <a href="${path}/marketGood/market">
                <div class="sec1">
                    <img src="${path}/static/images/zs%20(3).png" alt="">
                    <p>
                        <span>返回市场</span>
                        <span>继续查看感兴趣的商品</span>
                    </p>
                </div>
            </a>
            <a href="${basePath}userCertificate/userList/${pfBorder.userId}">
                <div class="sec1">
                    <img src="${path}/static/images/zs%20(2).png" alt="">
                    <p>
                        <span>查看授权书</span>
                        <span>查看您的商品授权书</span>
                    </p>
                </div>
            </a>
        </div>
    </div>
</main>
<div class="back_box">
    <div class="back"></div>
    <div class="back_f">
        <span class="close">×</span>
        <img src="${path}/static/images/b.png" alt="">
    </div>
</div>
<div class="paidanqi">
    <div class="back_q">
        <h1>什么是排单期？</h1>
        <p>
            由于商品过于火爆，导致库存量不足。申请合伙人、补货或升级我们将记录付款的先后顺序，待产能提升，麦链商城将按照付款顺序发货
        </p>
        <button class="kNow">我知道了</button>
    </div>
    <div class="Modal"></div>
</div>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/hideWXShare.js"></script>
<script src="${path}/static/js/zepto.min.js"></script>
<script>
    $(".add").on("tap", function () {
        $(".back_box").show()
        $(".back_f").show()
    })
    $(".close").on("tap", function () {
        $(".back_box").hide()
        $(this).parent().hide()
    })
    $(".row").on("click", function () {
        $(".paidanqi").show();
    });
    $(".kNow").on("click", function () {
        $(".paidanqi").hide();
    });
</script>
</body>
</html>