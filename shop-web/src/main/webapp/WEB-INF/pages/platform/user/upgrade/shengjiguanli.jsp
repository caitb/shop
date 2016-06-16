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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/upGrade/shengjiguanli.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>升级管理</p>
    </header>
    <nav>
        <p><a>我的申请</a></p>
        <p class="on"><a>下级申请</a></p>
        <p><a>一次性返利</a></p>
    </nav>
    <div class="floor">
        <div>
            <span>商品：</span>
            <label class="goods">
                <b></b>
                <select id="goods">
                    <c:forEach items="${skuList}" var="sku">
                        <option value="${sku.id}">${sku.name}</option>
                    </c:forEach>
                </select>
            </label>
                <span>等级：</span>
                <label class="level">
                    <b></b>
                    <select id="level">
                        <c:forEach items="${statusPickList}" var="status" varStatus="statusIndex">
                            <option value="${statusIndex.index}">${status}</option>
                        </c:forEach>
                    </select>
                </label>
                <%--<span id="leixing">类型：</span>--%>
                <%--<label class="level" id="fanli">--%>
                    <%--<b></b>--%>
                    <%--<select id="level1">--%>
                        <%--<option value="0">获得返利</option>--%>
                        <%--<option value="1">支付返利</option>--%>
                    <%--</select>--%>
                <%--</label>--%>
        </div>
        <button onclick="search()" id="search">查询</button>
    </div>
    <div class="box">
        <main id="main">
            <c:forEach items="${pfUserUpGradeInfoList}" var="grade">
            <div class="sec1">
                    <div class="s_1">
                        <p>商品：${grade.skuName}</p>
                        <p>状态：<span class="active">申请中</span></p>
                    </div>
                    <div class="s_2">
                        <img src="${grade.comUser.wxHeadImg}" alt="">
                        <div>
                            <p>${grade.comUser.realName}</p>
                            <h1><span class="on">${grade.orgLevelName}</span>${grade.wishLevelName}</h1>
                        </div>
                    </div>
                    <div class="s_3">
                        <p>升级编号：${grade.pfUserUpgradeNotice.code}</p>
                        <p>申请时间：${grade.createDate}</p>
                    </div>
            </div>
            </c:forEach>
        </main>
    </div>
    <a href="#" class="fix">我要升级</a>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/repetitionForm.js"></script>
<script>
    var tabId;
    $(document).ready(function(){
        $(".goods b").html($("#goods option:selected").text());
        $(".level b").html($("#level option:selected").text());
        tabId = 1;
    })
    $("#goods").on("change",function(){
        var tabVal=$("#goods option:selected").text();
        $(".goods b").html(tabVal);
    })
    $("#level").on("change",function(){
        var tabVal=$("#level option:selected").text();
        $(".level b").html(tabVal);
    })
    $("nav").on("click","p",function(){
        var index=$(this).index();
        tabId = index;
        if (tabId == 0) {
        }
        if (tabId == 2) {
        }
            $(this).addClass("on").siblings().removeClass("on");
            $.ajax({
                url: '<%=basePath%>upgradeInfo/tab',
                type: 'post',
                async:true,
                data: {tabId:index},
                dataType: 'json',
                success: function (res) {
                    var trHtml = "";
                    $.each(res.pfUserUpGradeInfoList, function(i, grade){
                        trHtml+="<div class=\"sec1\">";
                        trHtml+="<div class=\"s_1\">";
                        trHtml+="<p>商品："+grade.skuName+"</p>>";
                        trHtml+="<p>状态：<span class=\"active\">申请中</span></p>";
                        trHtml+="</div>";
                        trHtml+="<div class=\"s_2\">";
                        trHtml+="<img src=\""+grade.comUser.wxHeadImg+"\" alt=\"\">";
                        trHtml+="<div>";
                        trHtml+="<p>"+grade.comUser.realName+"</p>";
                        trHtml+="<h1><span class=\"on\">"+grade.orgLevelName+"</span>"+grade.wishLevelName+"</h1>";
                        trHtml+="</div>";
                        trHtml+="</div>";
                        trHtml+=" <div class=\"s_3\">";
                        trHtml+="<p>升级编号："+grade.pfUserUpgradeNotice.code+"</p>";
                        trHtml+="<p>申请时间："+grade.createDate+"</p>";
                        trHtml+="</div>";
                        trHtml+="</div>";
                    });
                    $("#main").empty().html(trHtml);
                }
            });
    })

    function search(){
        var searchParam = {};
        searchParam.skuId = $("#goods option:selected").val();
        searchParam.upStatus = $("#level option:selected").val();
        searchParam.rebateType = "";
        $.ajax({
            url: '<%=basePath%>upgradeInfo/search',
            type: 'post',
            async:true,
            data: searchParam,
            dataType: 'json',
            beforeSend:function(){
                fullShow();
            },
            success: function (res) {
                var trHtml = "";
                $.each(res.pfUserUpGradeInfoList, function(i, grade){
                    trHtml+="<div class=\"sec1\">";
                    trHtml+="<div class=\"s_1\">";
                    trHtml+="<p>商品："+grade.skuName+"</p>>";
                    trHtml+="<p>状态：<span class=\"active\">申请中</span></p>";
                    trHtml+="</div>";
                    trHtml+="<div class=\"s_2\">";
                    trHtml+="<img src=\""+grade.comUser.wxHeadImg+"\" alt=\"\">";
                    trHtml+="<div>";
                    trHtml+="<p>"+grade.comUser.realName+"</p>";
                    trHtml+="<h1><span class=\"on\">"+grade.orgLevelName+"</span>"+grade.wishLevelName+"</h1>";
                    trHtml+="</div>";
                    trHtml+="</div>";
                    trHtml+=" <div class=\"s_3\">";
                    trHtml+="<p>升级编号："+grade.pfUserUpgradeNotice.code+"</p>";
                    trHtml+="<p>申请时间："+grade.createDate+"</p>";
                    trHtml+="</div>";
                    trHtml+="</div>";
                });
                $("#main").empty().html(trHtml);
            },
            complete:function(){
                fullHide();
            }
        });
    }
</script>
</body>
</html>