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
            <p style="height: 200px;overflow-y: auto;">
                在此特别提醒您（用户）在注册成为用户之前，请认真阅读本《用户协议》（以下简称“协议”），确保您充分理解本协议中各条款。请您审慎阅读并选择接受或不接受本协议。除非您接受本协议所有条款，否则您无权注册、登录或使用本协议所涉服务。您的注册、登录、使用等行为将视为对本协议的接受，并同意接受本协议各项条款的约束。本协议约定“麦士集团”与代理商之间关于“麦士产品”的权利义务。本协议可由麦士集团随时更新，更新后的协议条款一旦公布即代替原来的协议条款，恕不再另行通知，用户可在本公众号中查阅最新版协议条款。在修改协议条款后，如果用户不接受修改后的条款，请立即停止麦士产品代理，用户继续销售麦士产品将被视为接受修改后的协议。 
                一、账号注册
                1、用户在代理产品前需要注册一个“麦士合伙人”账号。“麦士合伙人”账号应当使用手机号码绑定注册，请用户使用尚未与“麦士合伙人”账号绑定的手机号码，以及未被“麦士集团：根据本协议封禁的手机号码注册“麦士合伙人”账号。注册后需进行身份证验证的实名认证，通过后即可进行产品代理。（还有要做什么步骤你们补充一下） 
                二、代理协议
                1、甲方为“麦士抗引力品牌“的品牌拥有者。
                2、代理方在  微信  渠道店铺分销甲方产品，认同甲方的品牌及其产品，合法经营并独立承担法律责任，甲方不负担任何由您导致第三方损害的连带责任，代理方需接受本合同中规定的分销经营模式及销售政策。
                3、代理方必须向甲方提供：法人（代理人）身份证、银行帐号等材料原件并复印留底；若代理方法人授权相关人员进行货物签收的,必须提供受托人的身份证，并提供货物签收委托证明。代理方对提供的上述材料的真实性和有效性负责，如果您提供的资料虚假、无效，由此给甲方造成损失时，您需承担赔偿责任。您提供的资料复印件应加盖公章或者由负责人签名有效。
                4、代理方所有的店铺都必须向甲方备案 ，未经备案不得销售甲方品牌产品。
                5、代理方通过供销平台向甲方采购商品并通过后台付款，甲方收到并确认代理方订单及全额货款后，负责发货。您户顾客收到货物当天组织并完成货物验收，并有权提出异议，逾期不提的视为验收合格。
                三、产品价格
                1、就产品种类与产品价格，见附件一《产品种类与价格明细》，代理方不应以任何形式（包括但不限于优惠券、满就减、返利、赠***品牌产品等）低于甲方规定的建议销售价格。
                2、如果建议销售价格不符合代理方实际情况，代理方欲调整销售价格时，应当提前书面通知甲方，甲方综合考虑整体市场情况和乙方所在地的市场情况，作出是否调整价格的决定，如代理方有异议，且协商不成的，甲方有权单方解除本合同，由此产生的一切损失，由代理方承担。
                3、甲方进行价格调整时，需提前15个工作日通知乙方，如代理方有异议，且协商不成的，甲方均有权单方解除本合同，由此产生的一切损失，由代理方承担，调整后的价格不溯及既往。
                四、违约责任
                1、代理方不得在未经甲方同意情况下，在经营活动中，擅自使用“***总分销、总代理、直营总店、唯一指定、独家代理”等排他性用语，或其它甲方认为不应当出现的宣传用语及图片进行自我宣传。因此造成的损失甲方有权3倍追加赔偿。
                2、代理方不得串货、销售假货，一经发现，扣除保证金并终止合作且对甲方造成的损失，甲方有权3倍追加赔偿。
                3、代理方如违反本协议约定第三条:产品价格。第一次出现时，提出警告，代理方需要彻底改正，并杜绝类似事件再次发生；第二次出现时，甲方有权单方解除本合同，由此产生的一切损失，由代理方承担。
                4、代理方未经甲方书面授权，以甲方名义给第三方造成任何损失的，由代理方承担一切责任，权利人向甲方主张赔偿的，甲方赔偿后，有权向代理方追偿。
                五、其他
                1、麦士合伙人郑重提醒用户注意本协议中各项的条款，请用户仔细阅读，自主考虑风险。未成年人应在法定监护人的陪同下阅读本协议。 
                2、本协议的效力、解释及纠纷的解决，适用于中华人民共和国法律。若代理方和麦士合伙人中心之间发生任何纠纷或争议，首先应友好协商解决，协商不成的，代理方同意将纠纷或争议提交麦士集团住所地有管辖权的人民法院管辖。 
                3、本协议的任何条款无论因何种原因无效或不具可执行性，其余条款仍有效，对双方具有约束力。
            </p>
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