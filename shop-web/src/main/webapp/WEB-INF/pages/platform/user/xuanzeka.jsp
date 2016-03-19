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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/tixian.css">
</head>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"/>
<script>
    function choiceBank(){
        alert("111");
    }
</script>
<body>
   <div class="wrap">
        <header class="xq_header">
            <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>申请提现 </p>            
        </header>
        <main>
             <p>提现到银行卡</p>
            <div class="sec2" onclick="choiceBank()">
                <img src="<%=path%>/static/images/icon_57.png" alt="">
                <p>
                    <span><em>交通银行</em></span>
                    <span>持卡人:<b>张云</b>卡号:<b>1231231231231231233</b></span>
                </p>
            </div> 
            <h1>
                转出金额<input type="text" placeholder="输入金额" style="font-size:14px;">
            </h1>
            <h2>您当前可体检的金额为<span>￥123123.00</span></h2>
            <botton>
                提现
            </botton>
        </main>
    </div>
    <div class="back">
        <div class="back_que">
                    <p>确认减库存?</p>
                    <h4><span>提现金额:</span><span>抗引力-手链</span></h4>
                    <h4><span>提现方式:</span><span>抗引力-手链</span></h4>
                    <h4><span>银行卡号:</span><span>抗引力-手链</span></h4>
                    <h4><span>银行名称:</span><span>抗引力-手链</span></h4>
                    <h4><span>开户行:</span><span>抗引力-手链</span></h4>
                    <h4><span>持卡人姓名:</span><span>抗引力-手链</span></h4>
                    
                    <h3>
                        <span class="que_qu">取消</span>
                        <span class="que_que">确认</span>
                    </h3>
                </div>
    </div>
</body>
</html>