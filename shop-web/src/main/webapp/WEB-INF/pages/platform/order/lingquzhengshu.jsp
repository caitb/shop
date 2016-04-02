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
                <p>${realName}</p>
            </div>
            <div>
                <p>合作产品：</p>
                <p>${skuName}</p>
            </div>
            <div>
                <p>合伙人等级：</p>
                <p>${levelName}</p>
            </div>
            <div>
                <p>上级合伙人：</p>
                <p>${pRealName}</p>
            </div>
            <div>
                <p>拿货方式：</p>
                <p>${sendType}</p>
            </div>
        </div>
        <a href="javascript:;" class="lingqu" onclick="submit();">返回首页</a>
        <a href="javascript:;" class="lingqu" onclick="submit();">查看证书</a>
    </div>
</main>
</body>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script>
    function submit() {
        window.location.href = "<%=basePath%>userCertificate/setUserCertificate.shtml?userSkuId=${userSkuId}";
        <%--var para = {};--%>
        <%--para.pfuId = "${userSkuId}";--%>
        <%--$.ajax({--%>
        <%--url: "<%=basePath%>userCertificate/updatect.do",--%>
        <%--type: "post",--%>
        <%--data: para,--%>
        <%--dataType: "json",--%>
        <%--success: function (data) {--%>
        <%--if (data.isError == false) {--%>
        <%--window.location.href = "<%=basePath%>userApply/applyOK.shtml";--%>
        <%--}--%>
        <%--else {--%>
        <%--alert(data.message);--%>
        <%--}--%>
        <%--}--%>
        <%--});--%>
    }
</script>
</html>