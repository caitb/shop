<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/lingquzhengshu.css">
</head>
<body>
<main>
    <div class="wrap">
        <header class="xq_header">
            <a href="javascript:;" onClick="javascript:history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png"
                                                                               alt=""></a>
            <p>申请合伙人</p>
        </header>
        <div class="xinxi">
            <p style="color:#9A9A9A">注册信息</p>
            <p style="color:#F74A11;">支付订单</p>
            <p>提交资料</p>
        </div>
        <div class="drap">
            <h2><img src="<%=path%>/static/images/yes.png" alt=""></h2>
            <p>恭喜您，完成订单支付</p>
        </div>
        <div class="main">
            <div>
                <p>姓名：</p>
                <p>${orderUserSku.userName}</p>
            </div>
            <div>
                <p>合作产品：</p>
                <p><c:forEach items="${orderUserSku.skuName}" var="name">${name}</c:forEach></p>
            </div>
            <div>
                <p>合伙人等级：</p>
                <p>${orderUserSku.agentLevel}</p>
            </div>
            <div>
                <p>上级合伙人：</p>
                <p>${orderUserSku.superiorName}</p>
            </div>
        </div>
        ${opStr}
        <p class="ll">领取证书后可以获得更多特权哦</p>
    </div>
</main>
</body>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"/>
<script>
    function submit() {
        var para = {};
        para.pfuId = "${userSkuId}";
        $.ajax({
            url: "<%=basePath%>userCertificate/add",
            type: "post",
            data: para,
            dataType: "json",
            success: function (data) {
                if (data.isError == false) {
                    window.location.href = "<%=basePath%>userApply/applyOK.shtml";
                }
                else {
                    alert(data.message);
                }
            }
        });
    }
</script>
</html>