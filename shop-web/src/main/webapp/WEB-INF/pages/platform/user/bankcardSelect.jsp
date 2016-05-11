<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/tixian.css">
</head>
<body>
<input type="hidden" id="selectId" name="selectId" value = ""/>
   <div class="wrap">
        <header class="xq_header">
            <a href="javascript:history.back(-1)" onClick=""><img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>选择银行卡</p>            
            </header>
            <div class="xinz">
                    <p><a href="${basePath}extractwayinfo/toCreateBankcard.shtml" style="color:#333;">新增银行卡</a></p>
            </div>
       <div class="main">
                <img src="${path}/static/images/icon_56.png" alt="">
                <p>您还没有银行卡，马上新增一个银行卡吧！</p>
        </div>
        <main>
            <p>选择提现银行卡</p>
            <c:forEach var="extractway" items="${extractwayList}">
                <div class="sec1" id="${extractway.id}">
                    <span><img src="${path}${extractway.cardImg}" alt=""></span>
                    <p>
                        <span><em>${extractway.bankName}</em></span>
                        <span>持卡人:<b>${extractway.cardOwnerName}</b>卡号:<b>${extractway.bankCard}</b></span>
                    </p>
                </div>
            </c:forEach>
        </main>
     </div>
    <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
    <script src="${path}/static/js/commonAjax.js"></script>
    <script src="${path}/static/js/definedAlertWindow.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="${path}/static/js/hideWXShare.js"></script>
     <script>
        var path = "${path}";
        var basePath = "${basePath}";
        $(".sec1").on("click",function(){
            $(this).addClass("on").siblings().removeClass("on")
            var id = $(this).attr("id");
            $("#selectId").val(id);
            $.ajax({
                type:"POST",
                async:true,
                url : basePath + "extractwayinfo/setbankdefault.do",
                data:{id:id},
                dataType:"Json",
                success:function(data){
                    if(data.isTrue == "false"){
                        alert(data.message);
                    }else {
                        fullShow();//跳转页面钱展示全屏遮罩loading...
                        window.location.href = basePath + "extract/toapply";
                    }
                },
                error: function(){
                    //请求出错处理
                    alert("请求出错，请稍后再试");
                }
            });
        })

         <%--function backLastPage(){--%>
             <%--fullShow();//跳转页面钱展示全屏遮罩loading...--%>
             <%--window.location.href="<%=basePath%>extract/toapply";--%>
         <%--}--%>
    </script>
</body>
</html>