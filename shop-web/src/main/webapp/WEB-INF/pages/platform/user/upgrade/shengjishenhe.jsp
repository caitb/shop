<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/shengjishenhe.css">
</head>
<body>
   <div class="wrap">
        <header class="xq_header">
                  <a href="${path}/upgrade/init.shtml"><img src="${path}/static/images/xq_rt.png" alt=""></a>
                    <p>升级</p>
        </header>
        <main>
            <c:if test="${upgradeDetail.upStatus == 3}">
                <h1>您的升级申请已经审核通过，请继续支付。</h1>
            </c:if>
            <p>升级信息：</p>
            <div class="floor">
                <p>
                    <span>升级编号：</span>
                    <span>${upgradeDetail.upgradeOrderCode}</span>
                </p>
                <p>
                    <span>产品名：</span>
                    <span>${upgradeDetail.skuName}</span>
                </p>
                <p>
                    <span>当前等级：</span>
                    <span>${upgradeDetail.currentAgentLevelName}</span>
                </p>
                <p>
                    <span>商品数量：</span>
                    <span>${upgradeDetail.quantity}</span>
                </p>
                <p>
                    <span>申请等级：</span>
                    <span>${upgradeDetail.applyAgentLevelName}</span>
                </p>
                <p>
                    <span>当前上级：</span>
                    <span>${upgradeDetail.oldPUserName}</span>
                </p>
                <p>
                    <span>升级后的上级：</span>
                    <span>${upgradeDetail.newPUserName}</span>
                </p>
            </div>
            <p>
                需付金额：
            </p>
            <div class="floor2">
                <p>合计：<span>￥${upgradeDetail.totalPrice} </span></p>
                <h1><b>*</b>在${payDate}前（2天内）支付升级申请，逾期将取消升级申请</h1>
            </div>
            <button onclick="skipGenerateOrder(${upgradeDetail.upgradeNoticeId})">
                下一步
            </button>
        </main>
    </div>
    <script src="${path}/static/js/jquery-1.8.3.min.js"></script>
</body>
<script>
    var promise =  $.Deferred().promise();
    function skipGenerateOrder(upgradeNoticeId){
        if (promise.state()=="pending"){
            $.ajax({
                type:"POST",
                url : "${path}/BOrderAdd/upgradeInsertOrder.do",
                data:"upgradeNoticeId="+upgradeNoticeId,
                dataType:"Json",
                success:function(data){
                    if (data.isError == false) {
                        if (data.isRedirect == true){
                            window.location.href = "${basePath}"+data.redirectUrl;
                        }else{
                            window.location.href = "${basePath}border/goToPayBOrder.shtml?bOrderId=" + data.bOrderId;
                        }
                    }
                }
            });
        }
        promise.then()
    }
</script>
</html>