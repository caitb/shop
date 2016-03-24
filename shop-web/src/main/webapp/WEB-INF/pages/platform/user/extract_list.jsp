<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zichan.css">
    <link rel="stylesheet" href="<%=path%>/static/css/common.css">
    <link rel="stylesheet" href="<%=path%>/static/css/dropload.css">
</head>
<body>
    <div class="wrap">
        <header class="xq_header">
            <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>提现申请</p>
        </header>
        <main>
            <div class="sec1" id="sec1">
                <p>收入记录：<label for="beginTime" ><b>2016</b>年<b>1</b>月</label><input  id="beginTime" class="kbtn" style="display:none;"/></p>
                <div id="Ldivall">
                    <div>
                        <p><span class="sd">03-16</span><span>+980.00</span></p>
                        <h1><span>银行卡号</span><span>1233****1111</span><b>审核中</b></h1>
                    </div>
                    <div>
                        <p><span class="sd">03-16</span><span>+980.00</span></p>
                        <h1><span>银行卡号</span><span>1233****1111</span><b>审核中</b></h1>
                    </div>
                    <div>
                        <p><span class="sd">03-16</span><span>+980.00</span></p>
                        <h1><span>银行卡号</span><span>1233****1111</span><b>审核中</b></h1>
                    </div>
                    <div>
                        <p><span class="sd">03-16</span><span>+980.00</span></p>
                        <h1><span>银行卡号</span><span>1233****1111</span><b>审核中</b></h1>
                    </div>
                    <div>
                        <p><span class="sd">03-16</span><span>+980.00</span></p>
                        <h1><span>银行卡号</span><span>1233****1111</span><b>审核中</b></h1>
                    </div>
                    <div>
                        <p><span class="sd">03-16</span><span>+980.00</span></p>
                        <h1><span>银行卡号</span><span>1233****1111</span><b>审核中</b></h1>
                    </div>
                    <div>
                        <p><span class="sd">03-16</span><span>+980.00</span></p>
                        <h1><span>银行卡号</span><span>1233****1111</span><b>审核中</b></h1>
                    </div>
                    <div>
                        <p><span class="sd">03-16</span><span>+980.00</span></p>
                        <h1><span>银行卡号</span><span>1233****1111</span><b>审核中</b></h1>
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
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/date.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/iscroll.js" ></script>
<script src="<%=path%>/static/js/dropload.min.js"></script>
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
