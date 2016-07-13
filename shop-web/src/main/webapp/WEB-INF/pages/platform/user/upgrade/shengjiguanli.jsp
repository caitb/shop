<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/upGrade/shengjiguanli.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.location.replace('${basePath}index')"><img src="${path}/static/images/xq_rt.png" alt=""></a>
        <p>升级管理</p>
    </header>
    <nav>
        <p><a>我的申请</a></p>
        <p><a>下级申请</a></p>
        <p><a>一次性返利</a></p>
    </nav>
    <div class="floor">
        <div>
            <span>商品：</span>
            <label class="goods">
                <b></b>
                <select id="goods" class="search">
                    <option value="">全部</option>
                    <c:forEach items="${skuList}" var="sku">
                        <option value="${sku.id}">${sku.name}</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <div id="dengji">
            <span>状态：</span>
            <label class="level">
                <b></b>
                <select id="level" class="search">
                    <option value="">全部</option>
                    <c:forEach items="${statusPickList}" var="status" varStatus="statusIndex">
                        <option value="${statusIndex.index}">${status}</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <div id="fanli" style="display: none;">
            <span>状态：</span>
            <label class="level1">
                <b></b>
                <select id="level1" class="search">
                    <option value="">全部</option>
                    <option value="0">获得返利</option>
                    <option value="1">支付返利</option>
                </select>
            </label>
        </div>
        <%--<button onclick="search()" id="search">查询</button>--%>
    </div>

    <div class="box">
        <main id="main">
            <c:forEach items="${pfUserUpGradeInfoList}" var="grade">
            <div class="sec1" onclick="upgradeDetail(0,'${grade.pfUserUpgradeNotice.id}')">
                    <div class="s_1">
                        <p>商品：${grade.skuName}</p>
                        <p>状态：<span class="active">${grade.statusValue}</span></p>
                    </div>
                    <div class="s_2">
                        <img src="${grade.wxHeadImg}" alt="">
                        <div>
                            <p>${grade.realName}</p>
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
    <a href="${path}/upgrade/init.shtml" class="fix">我要升级</a>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/repetitionForm.js"></script>
<script>
    var tabId;
    $(document).ready(function(){
        var goodsWidth=$(".goods").width();
        var levelsWidth=$(".level").width();
        $(".goods b").html($("#goods option:selected").text());
        $(".level b").html($("#level option:selected").text());
        $(".level1 b").html($("#level1 option:selected").text());
        $("#goods").width(goodsWidth);
        $("#level").width(levelsWidth);
        $("#level1").width(levelsWidth);
        var myTabId = ${tabId};
        tabId = myTabId;
        if (myTabId == 0) {
            $("nav p").eq(myTabId).addClass("on").siblings().removeClass("on");
            $(".floor").hide();
            return;
        }
        if(myTabId ==1){
            $("nav p").eq(myTabId).addClass("on").siblings().removeClass("on");
            $(".goods b").html($("#goods option:eq(0)").attr("selected","true").text());
            $(".level b").html($("#level option:eq(0)").attr("selected","true").text());
            $(".floor").show();
            $("#dengji").show();
            $("#fanli").hide();
        }
        if (myTabId == 2) {
            $("nav p").eq(myTabId).addClass("on").siblings().removeClass("on");
            $(".goods b").html($("#goods option:eq(0)").attr("selected","true").text());
            $(".level1 b").html($("#level1 option:eq(0)").attr("selected","true").text());
            $(".floor").show();
            $("#dengji").hide();
            $("#fanli").show();
        }
        $.ajax({
            url: '${basePath}upgradeInfo/tab',
            type: 'post',
            async:true,
            data: {tabId:myTabId},
            dataType: 'json',
            success: function (res) {
                var trHtml = "";
                $.each(res.pfUserUpGradeInfoList, function(i, grade){
                    trHtml+="<div class=\"sec1\" onclick=\"upgradeDetail('"+myTabId+"','"+grade.pfUserUpgradeNotice.id+"')\">";
                    trHtml+="<div class=\"s_1\">";
                    trHtml+="<p>商品："+grade.skuName+"</p>";
                    trHtml+="<p>状态：<span class=\"active\">"+grade.statusValue+"</span></p>";
                    trHtml+="</div>";
                    trHtml+="<div class=\"s_2\">";
                    trHtml+="<img src=\""+grade.wxHeadImg+"\" alt=\"\">";
                    trHtml+="<div>";
                    trHtml+="<p>"+grade.realName+"</p>";
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

    $("nav p").on("click",function(){
        var index=$(this).index();
        tabId = index;
        if (tabId == 0) {
            $(".floor").hide();
        }
        if(tabId ==1){
            $(".goods b").html($("#goods option:eq(0)").attr("selected","true").text());
            $(".level b").html($("#level option:eq(0)").attr("selected","true").text());
            $(".floor").show();
            $("#dengji").show();
            $("#fanli").hide();
        }
        if (tabId == 2) {
            $(".goods b").html($("#goods option:eq(0)").attr("selected","true").text());
            $(".level1 b").html($("#level1 option:eq(0)").attr("selected","true").text());
            $(".floor").show();
            $("#dengji").hide();
            $("#fanli").show();
        }
            $(this).addClass("on").siblings().removeClass("on");
            $.ajax({
                url: '${basePath}upgradeInfo/tab',
                type: 'post',
                async:true,
                data: {tabId:index},
                dataType: 'json',
                success: function (res) {
                    var trHtml = "";
                    $.each(res.pfUserUpGradeInfoList, function(i, grade){
                        trHtml+="<div class=\"sec1\" onclick=\"upgradeDetail('"+index+"','"+grade.pfUserUpgradeNotice.id+"')\">";
                        trHtml+="<div class=\"s_1\">";
                        trHtml+="<p>商品："+grade.skuName+"</p>";
                        trHtml+="<p>状态：<span class=\"active\">"+grade.statusValue+"</span></p>";
                        trHtml+="</div>";
                        trHtml+="<div class=\"s_2\">";
                        trHtml+="<img src=\""+grade.wxHeadImg+"\" alt=\"\">";
                        trHtml+="<div>";
                        trHtml+="<p>"+grade.realName+"</p>";
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
    $("#goods").on("change",function(){
        var tabVal=$("#goods option:selected").text();
        $(".goods b").html(tabVal);
    })
    $("#level").on("change",function(){
        var tabVal=$("#level option:selected").text();
        $(".level b").html(tabVal);
    })
    $("#level1").on("change",function(){
        var tabVal=$("#level1 option:selected").text();
        $(".level1 b").html(tabVal);
    })
    $(".search").on("change",function(){
        search();
    })
    function search(){
        var searchParam = {};
        searchParam.skuId = $("#goods option:selected").val();
        searchParam.upStatus = $("#level option:selected").val();
        searchParam.rebateType = $("#level1 option:selected").val();
        searchParam.tabId = tabId;
        $.ajax({
            url: '${basePath}upgradeInfo/search',
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
                    trHtml+="<div class=\"sec1\" onclick=\"upgradeDetail('"+searchParam.tabId+"','"+grade.pfUserUpgradeNotice.id+"')\">";
                    trHtml+="<div class=\"s_1\">";
                    trHtml+="<p>商品："+grade.skuName+"</p>";
                    trHtml+="<p>状态：<span class=\"active\">"+grade.statusValue+"</span></p>";
                    trHtml+="</div>";
                    trHtml+="<div class=\"s_2\">";
                    trHtml+="<img src=\""+grade.wxHeadImg+"\" alt=\"\">";
                    trHtml+="<div>";
                    trHtml+="<p>"+grade.realName+"</p>";
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
    /**
     *  0：我的申请
     *  1：下级申请
     *  2：一次性返利
     *  @param tabId
     *  @param upgradeId
     */
    function upgradeDetail(tabId, upgradeId) {
        switch (Number(tabId)) {
            case 0:
                window.location.href = "${basePath}upgrade/myApplyUpgrade.shtml?upgradeId=" + upgradeId;
                break;
            case 1:
                window.location.href = "${basePath}upgrade/upgradeInfo.shtml?upgradeId=" + upgradeId;
                break;
            case 2:
                window.location.href = "${basePath}upgrade/upgradeInfoNewUp.shtml?upgradeId=" + upgradeId;
                break;
            default :
                break;
        }
    }
</script>
</body>
</html>