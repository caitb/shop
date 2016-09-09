<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>麦链合伙人</title>
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
        <main>
            <div class="head_img">
                <p><img src="${comUser.wxHeadImg}" alt=""></p>
                <div><h1>${comUser.wxNkName}</h1></div>
                <span onclick="personCenter()">个人信息</span>
            </div>
            <div class="ban">
                <a href="<%=path%>/account/home/shuoming.shtml"><img src="${path}/static/images/what.png" alt=""></a>
                <p>累计收入</p>
                <h1>${totalIncom}</h1>
            </div>
            <div class="floor" onclick="showJiesuan()">
                <p>结算中</p>
                <p><b>${account.viewCountingFee}</b></p>
                <p><img src="${path}/static/images/top.png" alt=""></p>
            </div>
            <div class="floordown">
                <p><span>结算中(合伙人)</span><span><b>${account.viewAgentBillAmount}</b></span></p>
                <p><span>结算中(店铺)</span><span><b>${account.viewDistributionBillAmount}</b></span></p>
                <p><span>结算中(推荐奖)</span><span><b>${account.viewRecommenBillAmount}</b></span></p>
            </div>
            <nav>
                <ul>
                    <li><b> ${account.viewExtractableFee} <br></b>可提现</li>
                    <li><b> ${account.viewAppliedFee} <br></b>申请中</li>
                    <li><b> ${withdrawd}<br></b>已提现</li>
                </ul>
            </nav>
            <button class="btn" onclick="clickShow()">
                申请提现
            </button>
            <div class="floor2">
                <p><a href="#" onclick="toIncomRecord()">收入记录</a></p>
                <p><a href="#" onclick="toExtract()">提现记录</a></p>
            </div>
        </main>
    </div>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="back_a">
        <img src="${path}/static/images/ku.png" alt=""/>
        <p>此功能仅支持在麦链合伙人APP使用</p>
        <h1>
            <span onclick="clickHide()">取消</span>
            <span><a href="<%=path%>/static/html/all.html" style="color:#fff;">去下载</a></span>
        </h1>
    </div>
</div>
<div class="back" id="detail">
    <div class="back_j">
        <h1>什么是结算中？</h1>
        <p>
            结算中=结算中（合伙人）+结算中（店铺）
        </p>
        <botton onClick="hideDetail()">我知道了</botton>
    </div>
</div>
<div class="back" id="Hedetail">
    <div class="back_j">
        <h1>什么是合伙人结算中？</h1>
        <p>
            合伙人在成为您的下级或补货后会产生结算中的资金，结算资金=合伙人订单+合伙人补货订单资金。1天后即可转入可提现。
        </p>
        <botton onClick="hideDetail()">我知道了</botton>
    </div>
</div>
<div class="back" id="Didetail">
    <div class="back_j">
        <h1>什么是店铺结算中？</h1>
        <p>
            店铺结算资金是指消费者通过您的店铺购买商品后产生的资金，由于消费者可以退货，所以结算中的资金需要7天后转入可提现。结算中资金=订单金额-运费金额-分销佣金
        </p>
        <botton onClick="hideDetail()">我知道了</botton>
    </div>
</div>
<div class="bottom">
    <footer>
        <div class="btm" >
            <a href="<%=path%>/index">
                <span><img src="<%=path%>/static/images/footer%20(2).png" alt=""></span>
                <span>我是合伙人</span>
            </a>
        </div>
        <div class="btm">
            <a href="<%=path%>/shop/manage/index">
                <span><img src="<%=path%>/static/images/footer%20(3).png" alt=""></span>
                <span>我的店铺</span>
            </a>
        </div>
        <div class="btm" style="background: #DA3600;">
            <a href="<%=path%>/account/home">
                <span><img src="<%=path%>/static/images/footer%20(1).png" alt=""></span>
                <span>我的账户</span>
            </a>
        </div>
    </footer>
</div>
<script type="text/javascript" src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/date.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/iscroll.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/dropload.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script src="<%=basePath%>static/js/zepto.min.js"></script>
<script>
    $("body").on("swipeRight", function () {
        location.href='<%=path%>/shop/manage/index';
    })
</script>
<script type="text/javascript">
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
                    $("#divall").append("<div><p><span class='sd'>"+month+"-"+arr[i].date+"</span><span>"+year+"</span></p><h1>+"+arr[i].incom+"</h1></div>")
                }
            },
            error: function(){
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    }
    function withdrawRequest(){
        fullShow();//跳转页面钱展示全屏遮罩loading...
        window.location.href="<%=basePath%>extract/toapply";
    }
    function backLastPage(){
        fullShow();//跳转页面钱展示全屏遮罩loading...
        window.location.href="<%=basePath%>personalInfo/personalHomePageInfo.html";
    }
    function showDetail(){
        $("#detail").show();
    }
    function showHedetail(){
        $("#Hedetail").show();
    }
    function showDidetail(){
        $("#Didetail").show();
    }
    function hideDetail(){
        $("#detail").hide();
        $("#Hedetail").hide();
        $("#Didetail").hide();
    }
    function toIncomRecord(){
        fullShow();//跳转页面钱展示全屏遮罩loading...
        <%--window.location.href="<%=basePath%>account/getIncomRecord.shtml";--%>
        window.location.href="<%=basePath%>account/getIncomRecord14.shtml";
    }
    function toExtract(){
        fullShow();//跳转页面钱展示全屏遮罩loading...
        window.location.href="<%=basePath%>extract/list";
    }
    function personCenter(){
        fullShow();
        window.location.href = "<%=basePath%>personalInfo/personalHomePageInfo.html";
    }
    function showJiesuan(){
        if($(".floordown").is(":hidden")){
            $(".floordown").show();    //如果元素为隐藏,则将它显现
            $(".floor p img").attr("src","${path}/static/images/down.png");
        }else{
            $(".floordown").hide();     //如果元素为显现,则将其隐藏
            $(".floor p img").attr("src","${path}/static/images/top.png")
        }
    }
    function clickShow(){
        $(".black").show();
    }
    function clickHide(){
        $(".black").hide();
    }
</script>
</body>
</html>