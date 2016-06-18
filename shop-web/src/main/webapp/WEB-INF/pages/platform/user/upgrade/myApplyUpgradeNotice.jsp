<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/upGrade/chexiao.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="${basePath}upgradeInfo/lower"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                    <p>升级</p>            
        </header>
        <main>
            <div class="floor">
                <h1>升级信息详情</h1>
                <p>
                    <span>商品名称：</span>
                    <span>${upGradeInfoPo.skuName}</span>
                </p>
                <p>
                    <span>姓名：</span>
                    <span>${upGradeInfoPo.applyName}</span>
                </p>
                <p>
                    <span>原等级：</span>
                    <span>${upGradeInfoPo.orgAgentName}</span>
                </p>
                <p>
                    <span>升级后等级：</span>
                    <span>${upGradeInfoPo.wishAgentName}</span>
                </p>
                <p>
                    <span>状态：</span>
                    <span>${status}</span>
                </p>
                <p>
                    <span>申请时间：</span>
                    <span>${createTime}</span>
                </p>
                <p>
                    <span>升级编号：</span>
                    <span>${upGradeInfoPo.applyCode}</span>
                </p>
            </div>
            <c:if test="${upGradeInfoPo.applyStatus == 0}">
                <h1>请耐心等待上级处理</h1>
                <button class="chexiao" onclick="blackShow()">
                    撤销
                </button>
            </c:if>
            <c:if test="${upGradeInfoPo.applyStatus == 1}">
                <h1>您的上级正在处理，处理完成会返回支付订单，请耐心等待</h1>
            </c:if>
            <c:if test="${upGradeInfoPo.applyStatus == 2}">
                <h1>您的上级已处理完成，请您继续支付升级申请</h1>
                <button class="zhifu">
                    支付
                </button>
            </c:if>
        </main>
    </div>
   <div class="black">
       <div class="backb"></div>
       <div class="back_que">
           <p>您确定撤销?</p>
           <h4>您是否确定撤销您的升级单？</h4>
           <h3>
               <span class="que_qu" onclick="blackHide()">我再想想</span>
               <span class="que_que">确定</span>
           </h3>
       </div>
   </div>
   <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
   <script src="${path}/static/js/commonAjax.js"></script>
   <script src="${path}/static/js/definedAlertWindow.js"></script>
   <script src="${path}/static/js/myApplyUpgradeNotice.js"></script>
   <script>
       var path = "${path}";
       var basePath = "${basePath}";
   </script>
</body>
</html>