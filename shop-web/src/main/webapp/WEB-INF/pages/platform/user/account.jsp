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
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/zichan.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/dropload.css">
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="#" onClick="javascript :history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
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
                    <h2>查看说明</h2>
                </li>
            </ul>
        </nav>
        <div class="sec1" id="sec1">
            <p>收入记录：<label for="beginTime" ><b>2016</b>年<b>1</b>月</label><input  id="beginTime" class="kbtn" style="display:none;"/></p>
            <div id="divall">
                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>

                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>

                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>

                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>
                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>
                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>
                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>
                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>
                <div>
                    <p><span class="sd">03-16</span><span>2016</span></p>
                    <h1>+980.00</h1>
                </div>
            </div>
        </div>
    </main>
</div>
<div class="back">
    <div class="back_j">
        <h1>什么事结算中</h1>
        <p>
            为了响应国家爱号召，增强用户体验，平台支持7天退货，您的资金在对方确认收货后7天内属于结算中，7天后将自动转到可提现。
        </p>
        <botton>我知道了</botton>
    </div>
</div>
<div id="datePlugin"></div>
<script type="text/javascript" src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/date.js" ></script>
<script type="text/javascript" src="<%=basePath%>static/js/iscroll.js" ></script>
<script type="text/javascript" src="<%=basePath%>static/js/dropload.min.js"></script>
<script type="text/javascript">
    $(function(){
        $('#beginTime').date();
        $('#endTime').date({theme:"datetime"});
    });
    $('#divall').dropload({
        scrollArea : window,
        loadDownFn : function(me){
            $.ajax({
                type: 'GET',
                url: 'json/more.json',
                dataType: 'json',
                success: function(data){
                    alert(data);
                    // 代码执行后必须重置
                    me.resetload();
                },
                error: function(xhr, type){
                    alert('Ajax error!');
                    me.resetload();
                }
            });
        }
    });
</script>
</body>
</html>