<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wotuijianderen.css">
    <link rel="stylesheet" href="<%=path%>/static/css/common.css">

</head>
<body>
<input type="hidden" id="uid" name="uid" value="${comUser.id}"/>
<input type="hidden" id="year" name="year" value="${year}"/>
<input type="hidden" id="month" name="month" value="${month}"/>
<input type="hidden" id="currentPage" name="currentPage" value="${pfIncomRecordPo.pageNum}"/>
<input type="hidden" id="totalCount" name="totalCount" value="${pfIncomRecordPo.totalCount}"/>
<input type="hidden" id="time" name="time" value="1">
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:;" onClick="goBack()"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>收入记录-${comUser.realName}</p>
    </header>

    <div class="floor">
        <div>
            <span>筛选条件：</span>
            <label class="level kbtn" for="beginTime">
                <input type="text" style="display: none;"  id="beginTime" class="kbtn" />
                <span id="showYear">${year}</span>年
                <span id="showMonth">${month}</span>月
            </label>
        </div>
    </div>
    <div class="nav">
        <p>总收入：</p>
        <p id="totalIncom">${pfIncomRecordPo.totalIncomView}</p>
    </div>
    <main id="showDiv">
        <c:forEach items="${pfIncomRecordPo.pfIncomRecords}" var="pfIncomRecord">
            <div class="sec1">
                <p>
                    <span>${pfIncomRecord.yearView}</span>
                    <span>${pfIncomRecord.minView}</span>
                </p>
                <img src="${pfIncomRecord.headImg}" alt="">
                <div>
                    <p><span><a>${pfIncomRecord.realName}</a></span> <b>+${pfIncomRecord.inComView}</b></p>
                    <p><span>${pfIncomRecord.skuName}</span> <b style="color: #666;">${pfIncomRecord.orderTypeView}</b></p>
                </div>
            </div>
        </c:forEach>
    </main>
    <p id="show" style="text-align: center;">
        <c:if test="${pfIncomRecordPo.pfIncomRecords != null && fn:length(pfIncomRecordPo.pfIncomRecords) < pfIncomRecordPo.totalCount}">
            <a href="#" onclick="showMore()">查看更多></a>
        </c:if>
    </p>
</div>
<div id="datePlugin"></div>

<script type="text/javascript" src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/date.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/iscroll.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/definedAlertWindow.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/commonAjax.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script>
    var path = "<%=path%>";
    var basePath = "<%=basePath%>";
    $(function(){
        $('#beginTime').date(undefined,undefined,undefined,function(year, month){
            $("#showYear").text(year);
            $("#showMonth").text(month);
            findByChangeTime(year,month);
        });
        $('#endTime').date({theme:"datetime"});
    });

    function findByChangeTime(year,month){
        var yearLast = $("#year").val();
        var monthLast = $("#month").val();
        var flagLast = $("#flag").val();
        if(yearLast==year && monthLast==month && flag==flagLast){
            return;
        }
        $("#year").val(year);
        $("#month").val(month);
        var date = year + "-" + month + "-01";
        ajaxRequest(date,0);
    }

    function showMore(){
        var year = $("#year").val();
        var month = $("#month").val();
        var date = year + "-" + month + "-01";
        var currentPage = $("#currentPage").val();
        ajaxRequest(date,currentPage);
    }

    function ajaxRequest(date,currentPage){
        $("#time").val(2);
        $.ajax({
            type:"POST",
            async:true,
            url : basePath+"account/getIncomRecord14Person.do",
            data:{date:date,currentPage:currentPage},
            dataType:"Json",
            success:function(data){
                var isTrue = data.isTrue;
                if (isTrue == "true"){
                    if(parseInt(currentPage) == 0){
                        $("#showDiv").empty();
                        $("#showDiv").html(data.html);
                        if ($(".sec1").length >= data.totalCount){
                            $("#show").empty();
                        }else {
                            $("#show").html("<a href=\"#\" onclick=\"showMore()\">查看更多></a>");
                        }
                        $("#currentPage").val(data.currentPage);
                        $("#totalCount").val(data.totalCount);
                        $("#totalIncom").text(data.totalIncom);
                    }
                    else {
                        $("#showDiv").append(data.html);
                        var totalCount = $("#totalCount").val();
                        if ($(".sec1").length >= totalCount){
                            $("#show").empty();
                        }else {
                            $("#show").html("<a href=\"#\" onclick=\"showMore()\">查看更多></a>");
                        }
                        $("#currentPage").val(data.currentPage);
                    }
                }else {
                    alert(data.message);
                }
            },
            error: function(){
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    }
    function goBack(){
        var times = parseInt($("#time").val());
        javascript:history.go(0-times);
    }
</script>
</body>
</html>