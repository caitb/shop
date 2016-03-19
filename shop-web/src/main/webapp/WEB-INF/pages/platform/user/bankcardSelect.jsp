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
<body>
<input type="hidden" id="selectId" name="selectId" value = ""/>
<input type="hidden" id="userId" name="userId" value = "${userId}"/>
   <div class="wrap">
        <header class="xq_header">
            <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>选择银行卡</p>            
            </header>
            <div class="xinz">
                    <p><a href="<%=basePath%>extractwayinfo/toCreateBankcard.do" style="color:#333;">新增银行卡</a></p>
            </div>
       <div class="main">
                <img src="<%=path%>/static/images/icon_56.png" alt="">
                <p>您还没有银行卡，马上新增一个银行卡吧！</p>
        </div>
        <main>
            <p>选择提现银行卡</p>
            <c:forEach var="extractway" items="${extractwayList}">
                <div class="sec1" id="${extractway.id}">
                    <span><img src="<%=path%>${extractway.cardImg}" alt=""></span>
                    <p>
                        <span><em>${extractway.bankName}</em></span>
                        <span>持卡人:<b>${extractway.cardOwnerName}</b>卡号:<b>${extractway.bankCard}</b></span>
                    </p>
                </div>
            </c:forEach>
        </main>
     </div>
     <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
     <script>
        $(".sec1").on("click",function(){
            $(this).addClass("on").siblings().removeClass("on")
            var id = $(this).attr("id");
            $("#selectId").val(id);
        })
    </script>
</body>
</html>