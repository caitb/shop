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
            <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>升级信息</p>
        </header>
        <main>
            <div class="floor3" id="floor3">
                <h1>您需要给 ${upGradeInfoPo.applyName}  的原上级${former} 进行一次性返利，具体数额请线下沟通 </h1>
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
                    <span>抗引力</span>
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
        </main>
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