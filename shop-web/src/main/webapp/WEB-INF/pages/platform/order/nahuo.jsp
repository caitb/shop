<%--
  Created by IntelliJ IDEA.
  User: ZhaoLiang
  Date: 2016/4/2
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/nahuo.css">
</head>
<body>
<div class="wrap">

    <div class="main">
        <header class="xq_header">
            <a href="${basePath}userApply/register.shtml?skuId=${skuId}">
                <img src="${path}/static/images/xq_rt.png" alt=""></a>
            <p>拿货方式</p>
        </header>
        <div class="xinxi">
            <p>注册信息</p>
            <p style="color: #F74A11;">选择拿货方式</p>
            <p>支付订单</p>
        </div>
        <c:if test="${isQueuing==true}">
            <div class="paidan">
                <h1><img src="${path}/static/images/loading.png" alt=""><b>在您前面还有<span>${count}</span>人排单</b></h1>
                <p style="color: #FF5200">*由于商品火爆导致库存不足,本次申请将进入排单系统,待产能提升,我们会按付款顺序发货</p>
            </div>
        </c:if>
        <div class="content">
            <div class="sec1">
                <h1>拿货方式简介</h1>
                <p>麦链商城提供两种拿货方式：平台代发货和自己发货，以下为两种方式的对比介绍，请您阅读后酌情选择。</p>
                <table>
                    <tr>
                        <td></td>
                        <td>平台代发</td>
                        <td>自己发货</td>
                    </tr>
                    <tr>
                        <td>仓库</td>
                        <td>平台提供</td>
                        <td>自建（成本高）</td>
                    </tr>
                    <tr>
                        <td>发货</td>
                        <td>平台提供</td>
                        <td>自建（成本高）</td>
                    </tr>
                    <tr>
                        <td>下级合伙人平台代发</td>
                        <td>支持</td>
                        <td>不支持</td>
                    </tr>
                    <tr>
                        <td>店铺发货</td>
                        <td>平台代发</td>
                        <td>自己发货（人工成本高）</td>
                    </tr>
                    <tr>
                        <td>消费者发票</td>
                        <td>平台提供</td>
                        <td>自己提供</td>
                    </tr>
                </table>
                <div>
                    <h1>1. 平台代发<span>(推荐)</span></h1>
                    <p>您可以省去仓储和发货事物，平台将为您提供专业的仓储、发货服务（价值58800元的仓储发货套餐免费试用一年）；无论您的订单是发给下级合伙人还是发给普通消费者，平台统统可以为您搞定</p>
                    <p><span></span>此方式支持您在商品管理中提货</p>
                </div>
                <div>
                    <h1>2. 由我自己发货</h1>
                    <p>平台将一次性发给您所有的商品，商品由您自己保存、发货，此方式比较耗费成本</p>
                    <p><span></span>选择此方式，您的下级合伙人将不能选择平台代发货方式，请谨慎选择</p>
                </div>
            </div>
            <div class="sec2">
                <h1>请选择拿货方式</h1>
                <button id="platformSendGoodsId" onclick="platformSendGoods()" class="active">平台代发货（90%的用户选择）</button>
                <button id="ownSendGoodsId" onclick="ownSendGoods()">自己发货</button>
                <p>*选择后，您的其他合伙商品将使用同一种方式，不可更改</p>
            </div>
            <button onclick="submit(this)">确定</button>
        </div>
    </div>
</div>
<div class="back_box">
    <div class="back"></div>
    <div class="back_que">
        <p>数据确认</p>
        <input type="checkbox" id="a"><label for="a">我已了解自己发货需要-自建仓库<br/>
        （每200瓶仓库成本200元）</label>

        <input type="checkbox" id="b"><label for="b">我已了解自己发货需要-自己发货<br/>
        （每200瓶的物流费用2500元）</label>

        <input type="checkbox" id="c"><label for="c">我已了解自己发货-下级合伙人不支持平台代发</label>

        <input type="checkbox" id="f"><label for="f">我已了解自己发货-店铺发货需要自己发货<br/>
        （每200瓶需要3000元的人工和物流费用）</label>

        <input type="checkbox" id="g"><label for="g">我已了解自己发货-消费者产生的发票需要自己提供</label>

        <input type="checkbox" id="h"><label for="h">我已阅读并了解自己发货的风险</label>

        <h3>
            <span class="que_qu" id="getBack">我再想想</span>
            <span class="que_que" id="getOK">确定选择</span>
        </h3>
    </div>
</div>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script>
    var sendType = 1;
    function platformSendGoods() {
        $("#platformSendGoodsId").attr("class", "active");
        $("#ownSendGoodsId").removeClass("active");
        sendType = 1;
    }
    function ownSendGoods() {
        $(".back_box").show();
    }

    function submit(para) {
        if ($(para).html() == "正在提交...") {
            return;
        }
        if (sendType == 0) {
            alert("请选择拿货方式");
            return;
        }
        var paraData = "?";
        paraData += "skuId=${skuId}";
        paraData += "&agentLevelId=${agentLevelId}";
        paraData += "&weiXinId=${weiXinId}";
        paraData += "&sendType=" + sendType;
        window.location.href = "${basePath}BOrderAdd/agentBOrder.shtml" + paraData;
    }
    /*
     * 返回修改
     * */
    $("#getBack").on("click", function (event) {
        var event = event || event.window;
        event.stopPropagation();
        $(".back_box").hide();
    })
    $("#getOK").click(function (event) {
        var n = 0;
        $(".back_que :checkbox").each(function () {
            if ($(this).is(':checked') == false) {
                alert("请确定所有选项");
                n++;
                return false;
            }
        });
        if (n == 0) {
            $("#ownSendGoodsId").attr("class", "active");
            $("#platformSendGoodsId").removeClass("active");
            sendType = 2
            $(".back_box").hide();
        }
    });
</script>
</body>
</html>
