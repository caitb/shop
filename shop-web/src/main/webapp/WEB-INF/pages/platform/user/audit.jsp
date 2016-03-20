<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>订单详情</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/base.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/reset.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/kangyinli.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/header.css">
    <script src="<%=basePath%>static/js/iscroll.js"></script>
    <script src="<%=basePath%>static/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<div class="wrap">
    <header class="xq_header">
        <a href="javascript:window.history.go(-1);"><img src="<%=basePath%>static/images/xq_rt.png" alt=""></a>
        <p>队员信息</p>
    </header>
    <main>
        <div class="sec1"><p>提 交 时 间：<span><fmt:formatDate value="${memberMap.applyTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></span></p></div>
        <div class="sec1"><p>申 请 产　品：<span>${memberMap.skuName}</span></p></div>
        <div class="sec1"><p>申 请 等　级：<span>${memberMap.agentLevelName}</span></p></div>
        <div class="sec1"><p>申 请 人：<span>${memberMap.comUserName}</span></p></div>
        <div class="sec1"><p>付 款 状 态：<span>
                                         <c:if test="${memberMap.payStatus == 0}">未付款</c:if>
                                         <c:if test="${memberMap.payStatus == 1}">已付款</c:if>
                                        </span>
                         </p>
        </div>
        <div class="sec1"><p>手机号：<span>${memberMap.mobile}</span></p></div>
        <div class="sec1"><p>微信号：<span>${memberMap.weixin}</span></p></div>
        <div class="sec1"><p>身份证号：<span>${memberMap.idCard}</span></p> </div>
        <div class="sec1" style="border:none;"><p>身份证扫描件：</p></div>
        <div>
            <h1><img src="${memberMap.idCardFrontImg}" alt="">正面</h1>
            <h1><img src="${memberMap.idCardBackImg}" alt="">反面</h1>
        </div>
        <footer>
            <button class="btn audit" status="0">拒绝</button>
            <button class="btn2 audit" status="1">通过</button>
        </footer>
    </main>
    <form id="auditForm" action="<%=basePath%>myteam/audit">
    <div class="back">
        <div class="back_j">
            <p>请填写拒绝原因</p>
            <input type="hidden" id="userSkuId" name="userSkuId" value="${memberMap.userSkuId}">
            <input type="hidden" id="pfUserCertificateId" name="id" value="${memberMap.pfUserCertificateId}" >
            <input type="hidden" id="status" name="status" value="1" >
            <textarea name="reason"></textarea>
            <div>
                <h1 class="j_qu">取消</h1>
                <h1 class="j_que audit" status="1">确定</h1>
            </div>
        </div>
    </div>
    </form>
</div>
<script>
    $('.audit').on('click', function(){
        //如果拒绝,完善表单信息
       if($(this).attr('status') == 0){
           $('#status').val(0);
           $('.back').css('display', '-webkit-box');
           return;
       }

        //提交表单
        $.ajax({
            url: '<%=basePath%>myteam/audit',
            type: 'post',
            data: $('#auditForm').serialize(),
            success: function(data){
                if(data == 'success'){
                    window.history.go(-1);
                }else{
                    $('.back').css('display', 'none');
                    alert('审核失败');
                }
            }
        });
    });

    $('.j_qu').on('click', function(){
        $('.back').css('display', 'none');
    });
</script>
</body>
</html>