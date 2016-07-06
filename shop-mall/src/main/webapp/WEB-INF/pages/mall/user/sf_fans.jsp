<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/common/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/fans.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
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
        <h1>所属店铺：全部</h1>
        <nav>
            <p>
                <span>${pageViewPo.totalCount}</span>
                <span>粉丝量</span>
            </p>
            <p>
                <span>${pageViewPo.firstCount}</span>
                <span>一级粉丝</span>
            </p>
            <p>
                <span>${pageViewPo.secondCount}</span>
                <span>二级粉丝</span>
            </p>
            <p>
                <span>${pageViewPo.thirdCount}</span>
                <span>三级粉丝</span>
            </p>
        </nav>
    </div>
    <main>
        <div class="sec1">
            <c:forEach items=""
            <h1 style="background:url('${path}/static/images/admin.png');background-size:100% 100%;"></h1>
            <div>
                <h2>王平<span>一级粉丝</span> <b>已代言</b></h2>
                <p>
                    <span>ID:131231313213</span>
                    <span>2021-2-22 23:22</span>
                </p>
            </div>
        </div>
    </main>
</div>
<script type="application/javascript" src="${path}/static/js/plugins/jquery-1.8.3.min.js"></script>
<script>
    $(document).ready(function(){
        $(".goods b").html($("#goods option:selected").text());
        $(".level b").html($("#level option:selected").text());
    })
    $("#goods").on("change",function(){
        var tabVal=$("#goods option:selected").text();
        $(".goods b").html(tabVal);
    })
    $("#level").on("change",function(){
        var tabVal=$("#level option:selected").text();
        $(".level b").html(tabVal);
    })
</script>
</body>
</html>
