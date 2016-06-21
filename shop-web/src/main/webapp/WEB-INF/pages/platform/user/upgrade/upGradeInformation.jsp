<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/upGrade/shengjixinxi.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
            <a href="${basePath}upgradeInfo/lower?tabId=1"><img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>升级信息</p>
        </header>
        <main>
            <div class="floor3" id="floor3">
                <c:if test="${upGradeInfoPo.applyStatus == 4}">
                    <h1>您的下级已取消升级</h1>
                </c:if>
                <c:if test="${upGradeInfoPo.upStatus == 1 && upGradeInfoPo.applyStatus != 4}">
                    <h1>您已选择暂不升级，您的下级将会与您解除关系。您可以获得一次性奖励</h1>
                </c:if>
                <c:if test="${upGradeInfoPo.upStatus == 2}">
                    <h1>您已选择我要升级，请去<a href="">我的申请</a>里查看升级单</h1>
                </c:if>
            </div>
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
            <div class="floor2" id="floor2">
                <c:if test="${upGradeInfoPo.upStatus == 0 && upGradeInfoPo.applyStatus != 4}">
                    <h1>
                        <b>*</b>您需要在${overdueDate}之前选择是否升级。
                        <span>（逾期默认不升级）</span>
                    </h1>
                    <div>
                        <button id="notUpgrade" onclick="blackShow()">暂不升级</button>
                        <button id="upgrade" onclick="toUpgrade()">我要升级</button>
                    </div>
                    <p>如果您升级：${upGradeInfoPo.applyName}还是您的下线<br>如果不升级：${upGradeInfoPo.applyName}将不是您的下线，您将获得一次性奖励</p>
                </c:if>
            </div>
        </main>
    </div>
    <div class="black">
        <input type="hidden" id="upgradeId" name="upgradeId" value="${upGradeInfoPo.upgradeId}">
        <div class="backb"></div>
        <div class="back_que">
                    <p>您确定不升级?</p>
                    <h4>如果不升级，${upGradeInfoPo.applyName}将不是您的下线，您可以获得一次性奖励。</h4>
                    <h3>
                        <span class="que_qu" onclick="blackHide()">我再想想</span>
                        <span class="que_que" id="confirm">确定</span>
                    </h3>
                </div>
    </div>
   <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
   <script src="${path}/static/js/commonAjax.js"></script>
   <script src="${path}/static/js/definedAlertWindow.js"></script>
   <script src="${path}/static/js/upGradeInformation.js"></script>
   <script>
       var path = "${path}";
       var basePath = "${basePath}";
   </script>
</body>
</html>