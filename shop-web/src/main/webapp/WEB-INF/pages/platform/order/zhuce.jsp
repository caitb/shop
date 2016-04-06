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
    <link rel="stylesheet" href="<%=path%>/static/css/zhuce.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css"/>
</head>
<body>

<div class="wrap">
    <div id="box">
        <header class="xq_header">
            <a href="javascript:;" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png"
                                                                              alt=""></a>
            <p>申请合伙人</p>
        </header>
                <div class="xinxi">
                    <p>注册信息</p>
                    <p>支付订单</p>
                    <p>选择拿货方式</p>
                </div>
        <div class="paidan" style="display: none">
            <h1><img src="<%=path%>/static/images/loading.png" alt=""><b>在您前面还有<span>1233</span>人排单</b></h1>
            <p style="color: #FF5200">
                *由于商品火爆导致库存不足，本次申请将进入排单系统，待产能提升，我们会按付款顺序发货
            </p>
        </div>
        <p class="xuanze">
            选择商品：<span>${skuName}</span>
        </p>
        <main>
            <section class="sec1">
                <div>
                    <p>微信号：
                        <input type="text" class="wei" id="weixin" name="weixin"/>
                        <span class="onc"></span><b class="gao"></b>
                    </p>
                        <h1>*此微信号将在授权证书上显示</h1>
                </div>
            </section>
            <section class="sec2">
                <c:if test="${pWxNkName==''}">
                    <h3 class="tui" style="display: block">
                        <span>是否有推荐人：</span>
                        <input type="radio" id="q" name="danx" class="shi" checked="checked">
                        <label for="q" style="margin-left:5px;">是</label>
                        <input type="radio" id="b" name="danx" class="fou"/>
                        <label for="b" style="margin-left:30px;">否</label>
                    </h3>
                    <%--<h3 class="Bhide">您的推荐人：<span>校长</span></h3>--%>
                    <div id="hehuo">
                        <h3>上级合伙人电话： <input type="text" class="stel" id="pMobile" name="pMoble">
                            <span class="onc"></span>
                            <b class="gao"></b>
                        </h3>
                    </div>
                </c:if>
                <c:if test="${pWxNkName!=''}">
                    <div id="hehuo">
                        <h3>您的推荐人： <input type="text" class="stel" value="${pWxNkName}" readonly="readonly"/></h3>
                    </div>
                </c:if>

                <h2 style="text-indent:10px;font-weight:500;margin-bottom: 5px;">选择合伙人等级：</h2>
                <div class="dengji">
                    <c:forEach items="${agentSkuViews}" var="view">
                        <c:if test="${view.agent.agentLevelId >= pUserLevelId}">
                            <p levelId="${view.agent.agentLevelId}" agentFee="${view.agentFee}"><label>${view.level.name}</label>
                                <b>商品数量：</b> <span>${view.agent.quantity}</span> <b>金额：</b> <span>${view.sinFee}</span> <b>保证金：</b> <span>${view.agent.bail}</span>
                            </p>
                        </c:if>
                    </c:forEach>
                </div>
            </section>
            <section class="sec3">
                <p>
                    <!--<input type="checkbox" id="fu" checked disabled>-->
                    <label>我已同意<a href="#">《代理商注册协议》</a></label>
                </p>
            </section>
            <section class="sec4">
                <a id="next" style="color:white" href="javascript:void(0);">下一步</a>
            </section>
        </main>
    </div>
    <div class="back_que">
        <p>数据确认</p>
        <h4><b>商品选择:</b><span id="q_skuName">${skuName}</span></h4>
        <h4><b>微信:</b><span id="q_weixinId"></span></h4>
        <c:choose>
            <c:when test="${pWxNkName==''}">
                <h4><b>推荐人电话:</b><span id="q_pMobile"></span></h4>
            </c:when>
            <c:otherwise>
                <h4><b>推荐人微信:</b><span id="q_pWx">${pWxNkName}</span></h4>
            </c:otherwise>
        </c:choose>
        <h4><b>合伙人等级:</b><span id="q_levelName"></span></h4>
        <h4><b>需要缴纳货款:</b><span id="q_amount"></span></h4>
        <h3>
            <span class="que_qu" id="getBack">返回修改</span>
            <span class="que_que" id="submit">我填的正确</span>
        </h3>
    </div>
    <div class="back">
    </div>
</div>
</body>
<script>
    var path = "<%=basePath%>";
    var skuId = "${skuId}";
    var skuName = "${skuName}";
    var mobile = "${mobile}";
    var pUserId = "${pUserId}";
    var pMobile = "${pMobile}";
    var pUserLevelId = "${pUserLevelId}";
</script>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script src="<%=path%>/static/js/iscroll.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script src="<%=path%>/static/js/zhuceUtil.js"></script>
</html>