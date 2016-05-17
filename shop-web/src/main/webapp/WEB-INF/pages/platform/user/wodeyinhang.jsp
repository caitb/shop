<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <%@ include file="/WEB-INF/pages/common/foot.jsp" %>
    <script src="${path}/static/js/bankCard.js"></script>
    <link rel="stylesheet" href="${path}/static/css/wodeyinhang.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
            <a href="${path}/personalInfo/personalHomePageInfo.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>我的银行卡</p>
            </header>
            <div class="xinz">
                    <p><a href="${path}/personalInfo/toAddBankCardPage.html" style="color:#333;">新增银行卡</a></p>
            </div>
       <div class="main">
                <img src="${path}/static/images/icon_56.png" alt="">
                <p>您还没有银行卡，马上新增一个银行卡吧！</p>
        </div>
        <main>
            <p id="chooseBankCardId">选择提现银行卡</p>
        </main>
     </div>
   <input id="confirmBankCardId" value="" type="hidden"/>
   <div class="back_box" style="display: block;">
       <div class="back" style="display: none;"></div>
       <div class="back_que" style="display: none;">
           <p>删除</p>
           <h4>确定删除银行卡?</h4>
           <h3>
               <span class="que_qu" id="getBack" onclick="bankCardJS.hideDeleteDialog()">取消</span>
               <span class="que_que" id="submit" onclick="bankCardJS.deleteBankCard()">删除</span>
           </h3>
       </div>
   </div>
</body>
</html>