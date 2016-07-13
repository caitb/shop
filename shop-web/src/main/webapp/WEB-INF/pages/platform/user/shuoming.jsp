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
                <span>累计收入：</span>
                是您成为合伙人以来的收入累计,累计收入=结算中+可提现+申请中+已提现的资金。
            </p>
            <p>
                <span>结算中：</span>
                分为合伙人结算、店铺结算和推荐人奖励结算。
            </p>
            <p>
                <span>【合伙人结算】</span>
                是指您的下级合伙人向您补货或申请合伙时产生的金额结算(需要减去发出的推荐奖励)，当合伙订单完成时，资金转入结算中，1天后即可转入可提现。
            </p>
            <p>
                <span>【店铺结算】</span>
                是指您的店铺产生的订单金额结算，当消费者确认收货后，资金转入结算中，结算中资金=订单金额-运费金额-分销佣金；消费者确认收货7天后资金由结算中转入可提现
            </p>
            <p>
                <span>【推荐人奖励结算】</span>
                是指您通过发展平级推荐产生的奖励。您发展平级推荐后，他的每笔订单您都可以获得相应的奖励。
            </p>
            <p>
                <span>可提现：</span>
                您可以申请提现将资金转入银行卡
            </p>
            <p>
                <span>申请中：</span>
                您申请可提现需要审核，此时资金处于申请中状态
            </p>
            <p>
                <span>已提现：</span>
                已成功提现金额的累计
            </p>
        </main>
    </div>
</body>
</html>