<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/base.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/liulanguodedianpu.css">
    <link rel="stylesheet" href="${path}/static/css/devCss/loading.css">
    <script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
    <script type="application/javascript" src="${path}/static/js/common/commonAjax.js"></script>
    <script type="application/javascript" src="${path}/static/js/common/definedAlertWindow.js"></script>
    <script type="application/javascript">
        var path = "${path}";
        var basepath = "${basePath}";
    </script>
</head>
<body>
<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}"/>
<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
    <header>
        <a href="javascript:history.back(-1)"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>浏览过的店铺</p>
    </header>
    <div class="wrap">
        <section class="sec1">
            <div id="viewedShop">
                <c:forEach var="userShopView" items="${sfUserShopViews}">
                    <p class="photo">
                        <img src="${path}${userShopView.logo}" alt="">
                    </p>
                    <div class="shop">
                        <h2>${userShopView.shopName}</h2>
                        <h1>
                            <c:forEach items="${userShopView.shopSkus}" var="shopSku">
                                <img src="${path}${shopSku.icon}" alt="">
                            </c:forEach>
                                ${userShopView.bailFee}保证金
                        </h1>
                        <h3>${userShopView.explanation}</h3>
                        <c:if test="${userShopView.days == 0}">
                            <h2><span>今天浏览过</span><b>点击查看></b></h2>
                        </c:if>
                        <c:if test="${userShopView.days > 0}">
                            <h2><span>${userShopView.days}天前浏览过</span><b onclick="showShop(${userShopView.shopId})">点击查看></b></h2>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </section>
        <p style="text-align: center;"><a href="#" onclick="showMore()">查看更多></a></p>
    </div>
</body>
</html>