<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shenqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css"/>
</head>
<body>
<header class="xq_header">
    <a href="javascript:;" onClick="javascript :history.back(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>合伙人申请</p>
</header>
<%--<div class="kang">
    <a href="<%=path%>/product/${skuId}">
        <p class="photo">
            <img src="${skuImg}">
        </p>
        <div>
            <h2>${skuName}</h2>
            <p>${slogan}</p>
        </div>
    </a>
</div>--%>
<div class="paidan">
    <p>奥斯卡了的骄傲是离开的骄傲了啥空间的卢卡斯惊呆了卡三季度来看阿斯利康三</p>
    <h1>在您前面还有<span>1233</span>人排单</h1>
</div>
<div class="biao">
    <h1>申请条件：</h1>
    <table>
    <tr><td>申请条件</td><td>是否完成</td><td>任务入口</td></tr>
    <tr><td>申请阿萨德条件</td><td>是否完成</td><td>任务入口</td></tr>
    <tr><td>申请条件</td><td>是否完成</td><td>任务入口</td></tr>
    </table>
</div>
<div class="he">
    <h1>合伙人特权</h1>
</div>
<main>
    <section class="sec1">
        <img src="<%=path%>/static/images/shenqing_3.png" alt="">
        <div>
            <h2>独立店铺</h2>
            <p>拥有自己的独立店铺进行推广装修等</p>
        </div>
    </section>
    <section class="sec1">
        <img src="<%=path%>/static/images/shenqing_4.png" alt="">
        <div>
            <h2>寻找合伙人</h2>
            <p>可以用采购价格购买商品，赚取利差</p>
        </div>
    </section>
    <section class="sec1">
        <img src="<%=path%>/static/images/shenqing_5.png" alt="">
        <div>
            <h2>推广渠道</h2>
            <p>平台提供多样的推广渠道编辑推广自己商品</p>
        </div>
    </section>
    <section class="sec1">
        <img src="<%=path%>/static/images/shenqing_6.png" alt="">
        <div>
            <h2>团队管理</h2>
            <p>提供完善的售后和团队管理工具</p>
        </div>
    </section>
    <section class="sec1">
        <img src="<%=path%>/static/images/shenqing_7.png" alt="">
        <div>
            <h2>平台补助</h2>
            <p style="margin-right:10px;">消费者分享商品可获得佣金，佣金来自于平台的补助</p>
        </div>
    </section>
    <section class="sec2">
        <%--
                <p><a href="<%=path%>/userApply/register.shtml?skuId=${skuId}&pUserId=${pUserId}">继续</a></p>
        --%>
        <p><a id="applyTrial" onclick="validateCodeJS.applyTrial()">继续</a></p>
    </section>
    <input id="skuId" value="${skuId}" style="display: none"/>
    <input id="pUserId" value="${pUserId}" style="display: none"/>
    <input id="skipPageId" value="register" style="display: none"/>
    <input id="type" value="${type}" style="display: none"/>
</main>
<div class="back_j">
    <p class="biao">绑定账号</p>
    <div>
        <p>手机号：<input type="tel" class="phone" id="phoneId"></p>
    </div>
    <div class="d">
        <p>验证码：<input type="tel" id="validateNumberDataId">
            <button id="validateNumberId">获取验证码</button>
        </p>
    </div>
    <p class="tishi" id="errorMessageId"></p>
    <h1 class="j_qu" id="nextPageId">下一步</h1>
</div>
<div class="back_login" style="display:none;">
    <p>您还不是合伙人，先去好货市场看看把~</p>
    <h1><span id="quxiao">取消</span><span id="goMark">去认证</span></h1>
</div>
<div class="back" style="display: none">

</div>
</body>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"/>
<script src="<%=path%>/static/js/iscroll.js"></script>
<script src="<%=path%>/static/js/validateCode.js"></script>
<script>
    $(document).ready(function () {
        validateCodeJS.initPage();
    });
    function goVerified() {
        var para = "?";
        para += "goToURL=<%=basePath%>userApply/apply.shtml?skuId=${skuId}&type=${type}&pUserId=${pUserId}";
        window.location.href = "<%=basePath%>user/userVerified.shtml" + para;
    }
</script>
</html>