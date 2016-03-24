<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zichan.css">
    <link rel="stylesheet" href="<%=path%>/static/css/common.css">
    <link rel="stylesheet" href="<%=path%>/static/css/dropload.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
</head>
<body>
<input type="hidden" id="account" name="account" value = "1"/>
<input type="hidden" id="year" name="year" value = "${year}"/>
<input type="hidden" id="month" name="month" value = "${month}"/>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="#" onClick="backLastPage()"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
            <p>我的资产</p>
        </header>
        <main>
            <div class="ban">
                <img src="<%=basePath%>static/images/icon_55.png" alt="">
                <h1>￥<b>${account.totalIncomeFee}</b></h1>
                <p>累计收入</p>
                <h2>(截止到<span>${year}-${month}-${day}}</span>)</h2>
            </div>
            <nav>
                <ul>
                    <li>
                        <p>可提现<span><a href="<%=basePath%>extract/toapply">申请提现</a></span></p>
                        <h1><span>￥</span>${account.extractableFee}</h1>
                        <h2>提现记录</h2>
                    </li>
                    <li>
                        <p>结算中：</p>
                        <h1><span>￥</span>${account.countingFee}</h1>
                        <h2><a onclick="showDetail()">查看说明</a></h2>
                    </li>
                </ul>
            </nav>
            <div class="sec1" id="sec1">
                <p>收入记录：<label id="lable" for="beginTime" ><b>${year}</b>年<b>  ${month}</b>月</label><input  id="beginTime" class="kbtn" style="display:none;"/></p>
                <div id="divall">
                    <c:forEach var="userBill" items="${userBills}">
                        <div>
                            <p><span class="sd">${month}-${day}</span><span>${year}</span></p>
                            <h1>+${userBill.pfIncome}</h1>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>
    </div>
</div>
<div class="back" id="detail">
    <div class="back_j">
        <h1>什么是结算中</h1>
        <p>
            为了响应国家爱号召，增强用户体验，平台支持7天退货，您的资金在对方确认收货后7天内属于结算中，7天后将自动转到可提现。
        </p>
        <botton onClick="hideDetail()">我知道了</botton>
    </div>
</div>
<div id="datePlugin"></div>
<script type="text/javascript" src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/date.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/iscroll.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/dropload.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script type="text/javascript">
    $(function(){
        $('#beginTime').date();
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
            url : "<%=path%>/account/getMoreUserBill",
            data:{year:year,month:month,paging:'N',pageTotalCount:'0'},
            dataType:"Json",
            success:function(data){
                $("#divall").empty();
                $("#lable").html("<b>"+year+"</b>年<b>  "+month+"</b>月");
                var arr=eval(data);
                for(var i=0;i<arr.length;i++)
                {
                    $("#divall").append("<div><p><span class='sd'>"+month+"-"+arr[i].date+"</span><span>"+year+"</span></p><h1>+"+arr[i].incom+"</h1></div>")
                }
            },
            error: function(){
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    }
    function backLastPage(){
        fullShow();//跳转页面钱展示全屏遮罩loading...
        window.location.href="<%=basePath%>profile/profile";
    }
    function showDetail(){
        $("#detail").show();
    }
    function hideDetail(){
        $("#detail").hide();
    }
</script>
</body>
</html>