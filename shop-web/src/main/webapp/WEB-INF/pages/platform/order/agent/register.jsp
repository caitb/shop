<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/zhuce.css">
</head>
<body>
<div class="wrap">
    <div id="box">
        <header class="xq_header">
            <a href="${basePath}userApply/apply.shtml?skuId=${skuId}">
                <img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>申请合伙人</p>
        </header>
        <div class="xinxi">
            <p>注册信息</p>
            <p>确认订单</p>
            <p>完成合伙</p>
        </div>
        <c:if test="${isQueuing==true}">
            <p class="row">本次订单将进入排单期，在您前面有<span>${count}</span>人排单。</p>
        </c:if>
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
                        <span style="font-size: 12px;">是否有推荐人：</span>
                        <input type="radio" id="q" name="danx" class="shi" checked="checked">
                        <label for="q" style="margin-left:5px;">是</label>
                        <input type="radio" id="b" name="danx" class="fou"/>
                        <label for="b" style="margin-left:30px;">否</label>
                    </h3>
                    <div id="hehuo">
                        <h3>上级合伙人电话： <input type="text" class="stel" id="pMobile" name="pMoble">
                            <span class="onc"></span>
                            <b class="gao"></b>
                        </h3>
                    </div>
                </c:if>
                <c:if test="${pWxNkName!=''}">
                    <div id="hehuo">
                        <h3 class="Bhide">您的推荐人：${pWxNkName}</h3>
                    </div>
                </c:if>

                <h2>选择合伙人套餐：</h2>
                <div class="dengji">
                    <c:forEach items="${agentSkuViews}" var="view">
                        <c:if test="${view.agent.agentLevelId > pUserLevelId && view.isShow==1}">
                            <p levelId="${view.agent.agentLevelId}"
                               agentFee="${view.agentFee}"
                               agentBailFee="${view.agent.bail}">
                                <span>${view.agentFee}元套餐</span>
                                <span>保证金：${view.agent.bail}元</span>
                                <span>包含商品：${view.agent.quantity}件</span>
                            </p>
                        </c:if>
                    </c:forEach>
                </div>
            </section>
            <section class="sec3">
                <p>
                    <!--<input type="checkbox" id="fu" checked disabled>-->
                    <label>我已同意<a href="#" class="daili">《代理商注册协议》</a></label>
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
        <h4><b>合伙人套餐:</b><span id="q_levelName"></span></h4>
        <h4><b>需要缴纳货款:</b><span id="q_amount"></span></h4>
        <h3>
            <span class="que_qu" id="getBack">返回修改</span>
            <span class="que_que" id="submit">我填的正确</span>
        </h3>
    </div>
    <div class="back"></div>
    <div class="paidanqi">
        <div class="back_q">
            <h1>什么是排单期？</h1>
            <p>
                由于商品过于火爆，导致库存量不足。申请合伙人或补货我们将记录付款的先后顺序，待产能提升，麦链商城将按照付款顺序发货
            </p>
            <button class="kNow">我知道了</button>
        </div>
        <div class="Modal"></div>
    </div>
    <div class="xieyi">
        <div class="back_q" style="margin-top:-166px">
            <h2 style="text-align: center;padding: 5px">麦链合伙人服务条款</h2>
            <p style="height: 200px;overflow-y: auto;">在此特别提醒您（用户）在注册成为用户之前，请认真阅读本《用户协议》（以下简称“协议”），确保您充分理解本协议中各条款。请您审慎阅读并选择接受或不接受本协议。除非您接受本协议所有条款，否则您无权注册、登录或使用本协议所涉服务。您的注册、登录、使用等行为将视为对本协议的接受，并同意接受本协议各项条款的约束。本协议约定“麦士集团”与代理商之间关于“麦士产品”的权利义务。本协议可由麦士集团随时更新，更新后的协议条款一旦公布即代替原来的协议条款，恕不再另行通知，用户可在本公众号中查阅最新版协议条款。在修改协议条款后，如果用户不接受修改后的条款，请立即停止麦士产品代理，用户继续销售麦士产品将被视为接受修改后的协议。</p>
            <button class="kNow">我知道了</button>
        </div>
        <div class="Modal"></div>
    </div>
</div>

</body>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${path}/static/js/hideWXShare.js"></script>
<script>
    var path = "${basePath}";
    var skuId = "${skuId}";
    var pUserId = "${pUserId}";
    var sendType = "${sendType}";
</script>
<script src="${path}/static/js/zhuceUtil.js"></script>
</html>