<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <%@include file="/WEB-INF/pages/commonhead.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/pageCss/base.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="${path}/static/css/pageCss/shenqingtixian.css">
</head>
<body>
    <header>
              <a href="javascript:history.back(-1)"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                <p>申请提现</p>            
        </header>
        <div class="wrap">
                <div class="na">
                   <p></p>
                   <h1>
                       <span>${comWxUser.nkName}</span>
                       <span>您已绑定微信</span>
                   </h1>
               </div>
               <main>
                   <p>您当前可提现金额为<span>￥${userAccount.extractableFee}</span></p>
                   <div class="sec1">
                       提现金额：￥<input type="text" placeholder="请输入提现金额">
                   </div>
                   <h1>*请确认您已关注“麦链商城”微信公众账号，否则提现提现会失败</h1>
                   <h2>为保障奖励及时到账，麦链商城采用的是微信转账形式发放。</h2>
                   <button>确认提现</button>
               </main>
        </div>
</body>
</html>