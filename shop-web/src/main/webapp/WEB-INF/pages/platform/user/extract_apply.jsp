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
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
    <script type="application/javascript">
        var path = "<%=path%>";
        var basepath = "<%=basePath%>";
        var maxMoney = "${extractMoney}";
    </script>
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
            <a href="#" onclick="backLastPage()"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>申请提现 </p>            
        </header>
        <main>
            <p>提现到银行卡</p>
                <c:choose>
                    <c:when test="${hasCard == true}">
                        <div class="sec2" onclick="choiceBank()">
                            <span><img src="<%=path%>${extractwayInfo.cardImg}" alt=""></span>
                            <p>
                                <span><em>${extractwayInfo.bankName}</em></span>
                                <span>
                                    持卡人:
                                    <b>${extractwayInfo.cardOwnerName}</b>
                                    卡号:
                                    <b>${extractwayInfo.bankCard}</b>
                                </span>
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="xinz" style="margin-bottom:5px;" onclick="choiceBank()">
                            <p><a href="<%=basePath%>extractwayinfo/toCreateBankcard.shtml" style="color:#333;">新增银行卡</a></p>
                        </div>
                    </c:otherwise>
                </c:choose>
            <h1>
                转出金额<input id="extractMoneyId" type="tel"  placeholder="输入金额" style="font-size:14px;">
            </h1>
            <h2>您当前可提现的金额为<span>￥${extractMoney}</span></h2>
            <botton id="extractBtnId">
                提现
            </botton>
        </main>
    </div>
       <div class="back_que">
           <p>再次确认提现请求?</p>
           <h4><span>提现金额:</span><span id="ex_money"></span></h4>
           <h4><span>提现方式:</span><span>${extractWay}</span></h4>
           <h4><span>银行卡号:</span><span>${extractwayInfo.bankCard}</span></h4>
           <h4><span>银行名称:</span><span>${extractwayInfo.bankName}</span></h4>
           <h4><span>开户行:</span><span>${extractwayInfo.depositBankName}</span></h4>
           <h4><span>持卡人:</span><span>${extractwayInfo.cardOwnerName}</span></h4>

           <h3>
               <span class="que_qu" id="exBtnCancel">取消</span>
               <span class="que_que" id="exBtnOk">确认</span>
           </h3>
       </div>
    <div class="back">

    </div>
</body>
<script type="application/javascript" src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script type="application/javascript" src="<%=path%>/static/js/extract_apply.js"></script>
<script type="application/javascript" src="<%=path%>/static/js/commonAjax.js"></script>
</html>