<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
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
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shuoming.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="javascript:;" onClick="javascript:history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>说明</p>            
        </header>
        <main>
            <h1>结算中说明</h1>
            <p>
                <span>什么是结算中？</span>
                结算中=结算中（合伙人）+结算中（店铺）
            </p>
            <p>
                <span>什么是合伙人结算中？</span>
                合伙人在成为您的下级或补货后会产生结算中的资金，结算资金=合伙人订单+合伙人补货订单资金。1天后即可转入可提现。
            </p>
            <p>
                <span>什么是店铺结算中？</span>
                店铺结算资金是指消费者通过您的店铺购买商品后产生的资金，由于消费者可以退货，所以结算中的资金需要7天后转入可提现。结算中资金=订单金额-运费金额-分销佣金
            </p>
        </main>
    </div>
</body>
</html>