<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/common/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/fans.css">
</head>
<body>
<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
<input type="hidden" id="totalPage" name="totalPage" value="${pageNums}"/>
<input type="hidden" id="totalCount" name="totalCount" value="${threeSum}"/>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.location.href='${basepath}sfOrderManagerController/borderManagement.html'"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>粉丝</p>
    </header>
    <div class="floor">
        <div>
            <span>筛选条件：</span>
            <div>
                <label class="goods">
                    <b></b>
                    <select id="goods">
                        <option value="0">全部</option>
                        <c:forEach items="${shops}" var="shop">
                            <option value="${shop.id}">${shop.name}</option>
                        </c:forEach>
                    </select>
                </label>
                <label class="level">
                    <b></b>
                    <select id="level">
                        <option value="0">全部</option>
                        <option value="1">一级粉丝</option>
                        <option value="2">二级粉丝</option>
                        <option value="3">三级粉丝</option>
                    </select>
                </label>
            </div>
        </div>
    </div>
    <div class="floor2">
        <h1>所属店铺：<a id="shop">全部</a></h1>
        <nav>
            <p>
                <span id="total">${pageViewPo.totalCount}</span>
                <span>粉丝量</span>
            </p>
            <p>
                <span id="first">${pageViewPo.firstCount}</span>
                <span>一级粉丝</span>
            </p>
            <p>
                <span id="second">${pageViewPo.secondCount}</span>
                <span>二级粉丝</span>
            </p>
            <p>
                <span id="third">${pageViewPo.thirdCount}</span>
                <span>三级粉丝</span>
            </p>
        </nav>
    </div>
    <main id="distributions">
        <c:forEach items="${pageViewPo.sfSpokesAndFansInfos}" var="info">
        <div class="sec1" >
                <h1 style="background:url('${info.headImg}');background-size:100% 100%;"></h1>
                <div class="fans">
                    <h2>${info.wxName}<span>${info.userLevelView}</span> <b>${info.sopkenManView}</b></h2>
                    <p>
                        <span>ID:${info.ID}</span>
                        <span>${info.createTimeView}</span>
                    </p>
                </div>
        </div>
        </c:forEach>
    </main>
    <div id="showMore" style="text-align: center;">
        <c:if test="${pageViewPo.sfSpokesAndFansInfos != null && fn:length(pageViewPo.sfSpokesAndFansInfos) < threeSum}">
            <a href="#" onclick="viewMore()">查看更多></a>
        </c:if>
    </div>
</div>
<script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
<script type="application/javascript" src="${path}/static/js/pageJs/sf_fans.js"></script>
<script type="application/javascript" src="${path}/static/js/common/commonAjax.js"></script>
<script type="application/javascript" src="${path}/static/js/common/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/pageJs/hideWXShare.js"></script>
<script>
    var path = "${path}";
    var basepath = "${basePath}";
    $(document).ready(function(){
        $(".goods b").html($("#goods option:selected").text());
        $(".level b").html($("#level option:selected").text());
    })
    $("#goods").on("change",function(){
        var tabVal = $("#goods option:selected").text();
        $(".goods b").html(tabVal);
        $("#shop").html(tabVal);
        queryFans(1);
    })
    $("#level").on("change",function(){
        var tabVal=$("#level option:selected").text();
        $(".level b").html(tabVal);
        queryFans(2);
    })
</script>
</body>
</html>
