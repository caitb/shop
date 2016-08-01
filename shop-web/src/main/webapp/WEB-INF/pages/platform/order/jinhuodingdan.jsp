<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/jinhuodingdan.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="<%=path%>/borderManage/borderManagement.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>我的订单</p>
        </header>
        <nav>
            <ul>
                <li><a href="javascript:;" class="on">全部</a></li>
                <li><a href="javascript:;">待付款</a></li>
                <li><a href="javascript:;">待发货</a></li>
                <li><a href="javascript:;">待收货</a></li>
                <li><a href="javascript:;">已完成</a></li>
                <li><a href="javascript:;">排单中</a></li>
                <li><a href="javascript:;">已取消</a></li>
            </ul>
            <img src="${path}/static/images/youdao.png" alt="" class="you">
        </nav>
        <main>
            <div class="all">
                <%--0：显示全部--%>
                <c:forEach items="${pfBorders}" var="pb">
                    <section class="sec1"
                             onclick="javascript:window.location.replace('<%=path%>/borderManage/borderDetils.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                            <b>${pb.orderStatusDes}</b>
                        </h2>
                        <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <div>
                                    <h2><span>${pbi.skuName}</span>￥${pbi.unitPrice}</h2>
                                    <h3><span>x${pbi.quantity}</span><b>合计：￥${pb.orderAmount}</b></h3>
                                </div>
                            </div>
                        </c:forEach>
                        <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            <c:if test="${pb.orderStatus ==0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9 }">
                                <span class="jixu"
                                      onclick="xinxi('${pb.orderMoney}','${pb.payTimes}','${pb.orderCode}',event)">支付信息</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa" name="querenshouhuo_${pb.id}"
                                              onclick="querenshouhuo('${pb.orderStatus}','${pb.id}',event)">
                                            确认收货</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9}">
                                <span class="jixu" onclick="goToPayOrder('${pb.id}',event)">支付变更</span>
                                <%--<a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a>--%>
                            </c:if>
                        </p>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <%--1：未付款--%>
                <c:forEach items="${pfBorders}" var="pb">
                    <section class="sec1"
                             onclick="javascript:window.location.replace('<%=path%>/borderManage/borderDetils.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                            <b>${pb.orderStatusDes}</b>
                        </h2>
                        <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <div>
                                    <h2><span>${pbi.skuName}</span>￥${pbi.unitPrice}</h2>
                                    <h3><span>x${pbi.quantity}</span><b>合计：￥${pb.orderAmount}</b></h3>
                                </div>
                            </div>
                        </c:forEach>
                        <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            <c:if test="${pb.orderStatus ==0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9 }">
                              <span class="jixu"
                                    onclick="xinxi('${pb.orderMoney}','${pb.payTimes}','${pb.orderCode}',event)">支付信息</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa" name="querenshouhuo_${pb.id}"
                                              onclick="querenshouhuo('${pb.orderStatus}','${pb.id}',event)">
                                            确认收货</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9}">
                                <span class="jixu" onclick="goToPayOrder('${pb.id}',event)">支付变更</span>
                                <%--<a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a>--%>
                            </c:if>
                        </p>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <%--2：代发货--%>
                <c:forEach items="${pfBorders}" var="pb">
                    <section class="sec1"
                             onclick="javascript:window.location.replace('<%=path%>/borderManage/borderDetils.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                            <b>${pb.orderStatusDes}</b>
                        </h2>
                        <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <div>
                                    <h2><span>${pbi.skuName}</span>￥${pbi.unitPrice}</h2>
                                    <h3><span>x${pbi.quantity}</span><b>合计：￥${pb.orderAmount}</b></h3>
                                </div>
                            </div>
                        </c:forEach>
                        <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            <c:if test="${pb.orderStatus ==0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9 }">
                               <span class="jixu"
                                     onclick="xinxi('${pb.orderMoney}','${pb.payTimes}','${pb.orderCode}',event)">支付信息</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa" name="querenshouhuo_${pb.id}"
                                              onclick="querenshouhuo('${pb.orderStatus}','${pb.id}',event)">
                                            确认收货</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9}">
                                <span class="jixu" onclick="goToPayOrder('${pb.id}',event)">支付变更</span>
                                <%--<a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a>--%>
                            </c:if>
                        </p>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <%--3：待收货--%>
                <c:forEach items="${pfBorders}" var="pb">
                    <section class="sec1"
                             onclick="javascript:window.location.replace('<%=path%>/borderManage/borderDetils.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                            <b>${pb.orderStatusDes}</b>
                        </h2>
                        <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <div>
                                    <h2><span>${pbi.skuName}</span>￥${pbi.unitPrice}</h2>
                                    <h3><span>x${pbi.quantity}</span><b>合计：￥${pb.orderAmount}</b></h3>
                                </div>
                            </div>
                        </c:forEach>
                        <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            <c:if test="${pb.orderStatus ==0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9 }">
                               <span class="jixu"
                                     onclick="xinxi('${pb.orderMoney}','${pb.payTimes}','${pb.orderCode}',event)">支付信息</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa" name="querenshouhuo_${pb.id}"
                                              onclick="querenshouhuo('${pb.orderStatus}','${pb.id}',event)">
                                            确认收货</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9}">
                                <span class="jixu" onclick="goToPayOrder('${pb.id}',event)">支付变更</span>
                                <%--<a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a>--%>
                            </c:if>
                        </p>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <%--4：已完成--%>
                <c:forEach items="${pfBorders}" var="pb">
                    <section class="sec1"
                             onclick="javascript:window.location.replace('<%=path%>/borderManage/borderDetils.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                            <b>${pb.orderStatusDes}</b>
                        </h2>
                        <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <div>
                                    <h2><span>${pbi.skuName}</span>￥${pbi.unitPrice}</h2>
                                    <h3><span>x${pbi.quantity}</span><b>合计：￥${pb.orderAmount}</b></h3>
                                </div>
                            </div>
                        </c:forEach>
                        <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            <c:if test="${pb.orderStatus ==0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9 }">
                               <span class="jixu"
                                     onclick="xinxi('${pb.orderMoney}','${pb.payTimes}','${pb.orderCode}',event)">支付信息</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa" name="querenshouhuo_${pb.id}"
                                              onclick="querenshouhuo('${pb.orderStatus}','${pb.id}',event)">
                                            确认收货</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9}">
                                <span class="jixu" onclick="goToPayOrder('${pb.id}',event)">支付变更</span>
                                <%--<a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a>--%>
                            </c:if>
                        </p>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <%--5：排单中--%>
                <c:forEach items="${pfBorders}" var="pb">
                    <section class="sec1"
                             onclick="javascript:window.location.replace('<%=path%>/borderManage/borderDetils.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                            <b>${pb.orderStatusDes}</b>
                        </h2>
                        <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <div>
                                    <h2><span>${pbi.skuName}</span>￥${pbi.unitPrice}</h2>
                                    <h3><span>x${pbi.quantity}</span><b>合计：￥${pb.orderAmount}</b></h3>
                                </div>
                            </div>
                        </c:forEach>
                        <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            <c:if test="${pb.orderStatus ==0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9 }">
                               <span class="jixu"
                                     onclick="xinxi('${pb.orderMoney}','${pb.payTimes}','${pb.orderCode}',event)">支付信息</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa" name="querenshouhuo_${pb.id}"
                                              onclick="querenshouhuo('${pb.orderStatus}','${pb.id}',event)">
                                            确认收货</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9}">
                                <span class="jixu" onclick="goToPayOrder('${pb.id}',event)">支付变更</span>
                                <%--<a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a>--%>
                            </c:if>
                        </p>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <c:forEach items="${pfBorders}" var="pb">
                    <section class="sec1"
                             onclick="javascript:window.location.replace('<%=path%>/borderManage/borderDetils.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                            <b>${pb.orderStatusDes}</b>
                        </h2>
                        <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <div>
                                    <h2><span>${pbi.skuName}</span>￥${pbi.unitPrice}</h2>
                                    <h3><span>x${pbi.quantity}</span><b>合计：￥${pb.orderAmount}</b></h3>
                                </div>
                            </div>
                        </c:forEach>
                        <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            <c:if test="${pb.orderStatus ==0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9 }">
                               <span class="jixu"
                                     onclick="xinxi('${pb.orderMoney}','${pb.payTimes}','${pb.orderCode}',event)">支付信息</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==8}">
                                        <span class="fa" name="querenshouhuo_${pb.id}"
                                              onclick="querenshouhuo('${pb.orderStatus}','${pb.id}',event)">
                                            确认收货</span>
                            </c:if>
                            <c:if test="${pb.orderStatus ==9}">
                                <span class="jixu" onclick="goToPayOrder('${pb.id}',event)">支付变更</span>
                                <%--<a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${pb.id}">改变支付方式</a>--%>
                            </c:if>
                        </p>
                    </section>
                </c:forEach>
            </div>
        </main>
    </div>
    <div class="back_shouhuo" style="display: none">
        <p>确认收到货品?</p>
        <h4>亲，请您核对商品后再操作确认收货</h4>

        <h3>
            <span class="que_qu">取消</span>
            <span class="que_que">确认</span>
        </h3>
    </div>

    <div class="back_que" style="display: none">
        <p>确认取消订单？</p>
        <h4>亲，是否确认删除商品抗引力-收敛精华乳液订单？</h4>

        <h3>
            <span class="que_qu">取消</span>
            <span class="que_que">确认</span>
        </h3>
    </div>
    <div class="back_pay">
        <div>
            <h1>订单号：<span id="1">1,000,000</span></h1>
            <p>您需要在<span id="2">2016-4-30</span>前将￥<span id="3">1,000,000.00</span>转到麦链合伙人对公账户。</p>
        </div>
        <p>*请在汇款单的附言处注明绑定的订单号”（<span>非常重要！</span>）</p>
        <h1><span></span>麦链对公账户信息</h1>
        <h2><span>开户行：</span><span>${defaultBank.bankName}</span></h2>
        <h2><span>开户名：</span><span>${defaultBank.accountName}</span></h2>
        <h2><span>卡号：</span><span>${defaultBank.cardNumber}</span></h2>
        <button class="xinxn">我知道了</button>
    </div>
    <div class="back" style="display: none">

    </div>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"></script>
<script src="<%=path%>/static/js/jinhuoshijian.js"></script>
<script src="<%=path%>/static/js/definedAlertWindow.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script>
    function xinxi(orderAmount, payTimes, ids,event) {
        var event = event || event.window;
        event.stopPropagation();
        $(".back").css("display", "-webkit-box");
        $(".back_pay").show();
        $("#1").html(ids);
        $("#3").html(orderAmount);
        $("#2").html(payTimes);
    }

    $(".xinxn").on("click", function () {
        $(".back_pay").hide();
        $(".back").hide();
    })
    $(function () {
        $("li").on("click", function () {
            var index = $(this).index();
            $(".all").html("");
            $(".all").eq(index).show().siblings().hide();
            $("li").children("a").removeClass("on");
            $(this).children("a").addClass("on");
            $.ajax({
                type: "POST",
                url: "<%=path%>/borderManage/clickType.do",
                data: {index: index},
                dataType: "Json",
                success: function (data) {
                    var trHtml = "";
                    var orderStatusName = "";
                    $.each(data, function (i, pfBorder) {
                        var ordertime = new Date(pfBorder.createTime).Format("yyyy-MM-dd hh:mm");
                        trHtml += "<section class=\"sec1\" onclick=\"javascript:window.location.replace('<%=path%>/borderManage/borderDetils.html?id=" + pfBorder.id + "');\">";
                        trHtml += "<h2>订单号: <span>" + pfBorder.orderCode + "(" + pfBorder.orderTypeDes + ")</span>";
                        trHtml += "<b>" + pfBorder.orderStatusDes + "</b>";
                        trHtml += "</h2>";
                        $.each(pfBorder.pfBorderItems, function (i, pfBorderItem) {
                            trHtml += "<div class=\"shangpin\"><div>";
                            trHtml += "<h2><span>" + pfBorderItem.skuName +"</span>￥"+pfBorderItem.unitPrice+"";
                            trHtml += "<h3><span>x" + pfBorderItem.quantity + "</span><b>合计：￥" + pfBorder.orderAmount + "</b></h3>";
                            trHtml += "</h2>";
                            trHtml += "</div></div>";
                        });
                        trHtml += "<p>时间：" + ordertime;
                        if (pfBorder.sendType == 0 && pfBorder.orderStatus != 0) {
                            trHtml += "<span class=\"jixu\">选择拿货方式</span></a>";
                        } else if (pfBorder.orderStatus == 0) {
                            trHtml += "<span class=\"jixu\"><a href=\"<%=basePath%>border/goToPayBOrder.shtml?bOrderId=" + pfBorder.id + "\">继续支付</a></span></a>";
                        } else if (pfBorder.orderStatus == 9) {
                            trHtml += "<span class=\"jixu\" onclick=\"xinxi('" + pfBorder.orderMoney + "','" + pfBorder.payTimes + "','" + pfBorder.orderCode + "',event)\">支付信息</span></a>";
                        } else {
                            trHtml += "</a>";
                        }
                        if (pfBorder.orderStatus == 8) {
                            orderStatusName = "确认收货";
                            trHtml += "<span class=\"fa\"  name=\"querenshouhuo_" + pfBorder.id + "\"  onclick=\"querenshouhuo('" + pfBorder.orderStatus + "','" + pfBorder.id + "',event)\">" + orderStatusName + "</span>";
                        }
                        if (pfBorder.orderStatus == 9) {
                            trHtml += "<span class=\"jixu\" onclick=\"goToPayOrder("+pfBorder.id+",event)\">支付变更</span>";
                        } else {
                            trHtml += "";
                        }
                        trHtml += "</p>";
                        trHtml += "</section>";
                    });
                    $(".all").eq(index).html(trHtml);
                }
            })
        })
    })
    $(document).ready(function () {
        var index =${index};
        $("li").children("a").removeClass("on")
        $("li").eq(index).children("a").addClass("on");
        $(".all").eq(index).show().siblings().hide();
    });
    $(".fa").on("click", function () {
        $(".back").css("display", "-webkit-box");
        $(".back_shouhuo").css("display", "-webkit-box");
    })

    var oid = "";
    function querenshouhuo(orderStatus, id, event) {
        var event = event || event.window;
        event.stopPropagation();
        $(".back").css("display", "-webkit-box");
        $(".back_shouhuo").css("display", "-webkit-box");
        oid = id;
    }
    $(function () {
        $(".que_que").on("click", function () {
            $(".back_shouhuo").hide();
            $(".back").hide();
            var aa = "querenshouhuo_" + oid;
            $.ajax({
                type: "POST",
                url: "<%=path%>/borderManage/closeDeal.do",
                data: {orderStatus: 3, shipStatus: 9, orderId: oid},
                dataType: "Json",
                success: function (date) {
                    $("span[name=" + aa + "]").attr("style", "display:none");
                    $("b." + aa + "").html("已完成");
                    location.reload(true);
                }
            })
        })
    })
    $(".que_qu").on("click", function () {
        $(".back_shouhuo").hide();
        $(".back").hide();
    })

    //update zhaoliang
    function goToPayOrder(bOrderId,event) {
        var event = event || event.window;
        event.stopPropagation();
        window.location.href = "<%=basePath%>border/goToPayBOrder.shtml?bOrderId=" + bOrderId;
    }

</script>
</body>
</html>