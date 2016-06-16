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
<!--            <h1>您已选择我要升级，请去<a href="">我的申请</a>里查看升级单</h1>-->
<!--            <h1>您已选择暂不升级，您的下级将会与您解除关系。您可以获得一次性奖励</h1>-->
<!--            <h1>您的下级已取消升级</h1>-->
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
                    <span>${upGradeInfoPo.createTime}</span>
                </p>
                <p>
                    <span>升级编号：</span>
                    <span>${upGradeInfoPo.applyCode}</span>
                </p>
            </div>
            <div class="floor2">
                <h1>
                    <b>*</b>您需要在2016-5-26 11：00之前选择是否升级。
                    <span>（逾期默认不升级）</span>
                </h1>
                <div>
                    <button onclick="blackShow()">暂不升级</button>
                    <button>我要升级</button>
                </div>
                <p>如果您升级：赵亮还是您的下线<br>如果不升级：赵亮不是您的下线，您将获得一次性补偿</p>
            </div>
        </main>
    </div>
    <div class="black">
        <div class="backb"></div>
        <div class="back_que">
                    <p>您确定不升级?</p>
                    <h4>如果不升级，赵亮不是您的下线，您可以获得一次性奖励。</h4>
                    <h3>
                        <span class="que_qu" onclick="blackHide()">我再想想</span>
                        <span class="que_que">确定</span>
                    </h3>
                </div>
    </div>
   <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
    <script>
        function blackShow(){
            $(".black").show();
        }
        function blackHide(){
            $(".black").hide();
        }
    </script>
</body>
</html>