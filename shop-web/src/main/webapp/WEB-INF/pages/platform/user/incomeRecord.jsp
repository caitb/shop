<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ page import="com.masiis.shop.common.util.DateUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/common.css">
    <link rel="stylesheet" href="<%=path%>/static/css/dropload.css">
    <link rel="stylesheet" href="<%=path%>/static/css/tixianjilu.css">
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="#" onClick="toBack()"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>收入记录</p>
        </header>
        <main>
            <div class="sec1" id="sec1">
                <p>收入记录：<label id="timeShow" for="beginTime"><b><%=DateUtil.Date2String(new Date(), "yyyy")%></b>年<b><%=DateUtil.Date2String(new Date(), "MM")%></b>月</label><input id="beginTime" onInput="OnInput(event)" onPropertychange="OnPropChanged(event)" value="<%=DateUtil.Date2String(new Date(), "yyyy-MM")%>" class="kbtn" style="display:none;"/></p>
                <div id="divall">
                    <c:forEach items="${record}" var="pfUserBill">
                        <div>
                            <p><span class="sd"><fmt:formatDate value="${pfUserBill.balanceDate}"  type="time" pattern="dd"/>日</span><span>${pfUserBill.billAmount}</span></p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>
    </div>
</div>
<div id="datePlugin"></div>
<script type="text/javascript" src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/date.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/iscroll.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/definedAlertWindow.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/dropload.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/commonAjax.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>

<script>
    $(function(){
        $('#beginTime').date(undefined,undefined,undefined,function(year, month){
            getUserBill(year, month);
        });
        $('#endTime').date({theme:"datetime"});
    });


    function getUserBill(year,month){
        var yearLast = $("#year").val();
        var monthLast = $("#month").val();
        if(yearLast==year && monthLast==month){
            return;
        }
        $("#year").val(year);
        $("#month").val(month);
        $.ajax({
            type:"POST",
            async:true,
            url : "<%=basePath%>account/getMoreUserBill",
            data:{year:year,month:month,paging:'N',pageTotalCount:'0'},
            dataType:"Json",
            success:function(data){
                $("#divall").empty();
                var arr=eval(data);
                for(var i=0;i<arr.length;i++)
                {
                    $("#divall").append("<div><p><span class='sd'>"+arr[i].date+"日</span><span>"+arr[i].incom+"</span></p></div>")
                }
            },
            error: function(){
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    }
    function toBack(){
        fullShow();//跳转页面钱展示全屏遮罩loading...
        window.location.href="<%=basePath%>account/home";
    }
</script>
</body>
</html>
