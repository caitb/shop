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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/main.css">
    <link rel="stylesheet" href="<%=path%>/static/css/commodity.css">
    <link rel="stylesheet" href="<%=path%>/static/css/alert.css"/>
</head>
<body>
<div class="wrap">
    <header>
        <a href="javascript:window.location.replace('<%=basePath%>shop/manage/index')"><img src="<%=path%>/static/shop/images/xq_rt.png" alt=""></a>
        <p>商品管理</p>
    </header>
    <div class="nav">
        <p>
            <span class="on">出售中</span>
            <span>仓库中</span>
        </p>
        <div>
            <span>筛选条件：</span>
            <label class="goods">
                <b></b>
                <select id="goods">
                    <option value="">全部</option>
                    <option value="0">平台发货</option>
                    <option value="1">自己发货</option>
                </select>
            </label>
        </div>
    </div>
    <main>
            <div class="floor">
                <c:forEach items="${skuInfoList}" var="sku">
                <div class="sec1">
                    <c:if test="${sku.isOwnShip==0}">
                        <h1><img src="<%=path%>/static/images/commodity.png" alt=""><b>平台发货</b></h1>
                    </c:if>
                    <c:if test="${sku.isOwnShip==1}">
                        <h1><img src="<%=path%>/static/images/commodity.png" alt=""><b>自己发货</b></h1>
                    </c:if>
                    <div>
                        <p><img src="${sku.comSkuImage.fullImgUrl}" alt=""></p>
                        <div>
                            <h1>${sku.comSku.name}</h1>
                            <h2>零售价： <span>￥${sku.comSku.priceRetail}</span></h2>
                            <p>已售：<span style="margin-right: 5px;">${sku.saleNum}</span>  库存: <span>${sku.stock}</span></p>
                        </div>
                    </div>
                    <c:if test="${sku.isOwnShip==0}">
                        <ul>
                            <li onclick="showDown()"><b><img src="<%=path%>/static/images/commodity3.png" alt="">下架</b></li>
                            <li class="right myself"><b><img src="<%=path%>/static/images/commodity4.png" alt="">我要自己发货</b></li>
                        </ul>
                    </c:if>
                    <c:if test="${sku.isOwnShip==1}">
                        <ul>
                            <li onclick="showDown()"><b><img src="<%=path%>/static/images/commodity3.png" alt="">下架</b></li>
                            <li onclick="showStock()"><b><img src="<%=path%>/static/images/commodity5.png" alt="" style="width:18px;">库存设置</b></li>
                        </ul>
                    </c:if>
                </div>
                </c:forEach>

                <%--<div class="sec1 sec2 on">--%>
                    <%--<h1><img src="../images/images/commodity2.png" alt=""><b>平台发货</b></h1>--%>
                    <%--<div>--%>
                        <%--<p><img src="../images/admin.png" alt=""></p>--%>
                        <%--<div>--%>
                            <%--<h1>抗引力——快速瘦脸精华</h1>--%>
                            <%--<h2>零售价： <span>￥123</span></h2>--%>
                            <%--<p>--%>
                                <%--<span>已售：123</span>--%>
                                <%--<span>已售：123</span>--%>
                            <%--</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<ul>--%>
                        <%--<li onclick="showDown()"><b><img src="../images/images/commodity3.png" alt="">下架</b></li>--%>
                        <%--<li onclick="showStock()"><b><img src="../images/images/commodity5.png" alt="" style="width:18px;">库存设置</b></li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            </div>
    </main>
</div>
<div class="black down">
    <div class="backb"></div>
    <div class="set">
        <h1>确认下架？</h1>
        <p>下架后的商品将不在店铺展示，消费者也将无法购买。</p>
        <h3>
            <button onclick="clickHide()">取消</button>
            <button onclick="">确认下架</button></h3>
    </div>
</div>
<div class="black generate">
    <div class="backb"></div>
    <div class="set">
        <h1>生成店主发货类型商品？</h1>
        <p>确认“我要自己发货”后，系统将生成一个店主发货类型的商品。此商品您可以编辑库存。当您想销售自己手中的商品时，可以使用此功能。</p>
        <h3>
            <button onclick="clickHide()">取消</button>
            <button class="queren">确认</button></h3>
    </div>
</div>
<div class="black stock">
    <div class="backb"></div>
    <div class="set">
        <h1>库存设置</h1>
        <div>
            <span>当前库存：</span>
            12312
        </div>
        <div>
            <span>编辑库存：</span>
            <h4>
                <b>-</b>
                <input type="tel">
                <b>+</b>
            </h4>
        </div>
        <h3>
            <button onclick="clickHide()">取消</button>
            <button onclick="">确认</button></h3>
    </div>
</div>
<script src="<%=path%>/static/shop/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"> </script>
<script>
    var index;
    $(document).ready(function(){
        $(".goods b").html($("#goods option:selected").text());
    })
    $("#goods").on("change",function(){
        var tabVal=$("#goods option:selected").text();
        $(".goods b").html(tabVal);
    })
    function showDown(){
        $(".down").show();
    }
    $(".myself").on("click",function(){
        $(".generate").show();
        index=$(this).parents(".floor").index();
    })
    function showStock(){
        $(".stock").show();
    }
    function clickHide(){
        $(".black").hide();
    }
    $(".queren").on("click",function(){
        $(".floor").eq(index).find(".myself").remove();
        $(".generate").hide();
        console.log($(".floor").eq(index).children().removeClass("on"))
    })
</script>
</body>
</html>