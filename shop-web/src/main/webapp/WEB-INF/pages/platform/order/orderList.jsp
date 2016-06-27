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
            <c:if test="${isShipment == 0}"><p>我的订单</p></c:if>
            <c:if test="${isShipment == 1}"><p>下级合伙人订单</p></c:if>
        </header>
        <nav>
            <ul>
                <li class="order-list" isShipment="${isShipment}"                ><a href="javascript:void(0);" <c:if test="${orderStatus == null}">class="on"</c:if> >全部</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="0"><a href="javascript:void(0);" <c:if test="${orderStatus == 0}">class="on"</c:if> >待付款</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="7"><a href="javascript:void(0);" <c:if test="${orderStatus == 7}">class="on"</c:if> >待发货</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="8"><a href="javascript:void(0);" <c:if test="${orderStatus == 8}">class="on"</c:if> >待收货</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="3"><a href="javascript:void(0);" <c:if test="${orderStatus == 3}">class="on"</c:if> >已完成</a></li>
                <li class="order-list" isShipment="${isShipment}" orderStatus="6"><a href="javascript:void(0);" <c:if test="${orderStatus == 6}">class="on"</c:if> >排单中</a></li>
            </ul>
            <img src="${path}/static/images/youdao.png" alt="" class="you">
        </nav>
        <c:if test="${isShipment == 1}">
        <p><img src="/static/images/laba.png" alt="">您只可以查看直接下级的订单</p>
        </c:if>
        <main>
            <div class="all">

                <c:forEach items="${orderMaps}" var="orderMap">
                    <section class="sec1">

                        <p>时间： <span><fmt:formatDate value="${orderMap.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                        <h2>
                            订单号：<span>${orderMap.orderCode}</span>
                            <c:forEach items="${orderStatuses}" var="orderStatus">
                                <c:if test="${orderStatus.code == orderMap.orderStatus}"><b>${orderStatus.desc}</b></c:if>
                            </c:forEach>
                        </h2>

                        <c:forEach items="${orderMap.bItems}" var="bItem">
                        <div class="shangpin">
                            <p class="photo">
                                <a href="javascript:void(0);">
                                    <img src="${imgUrlPrefix}${bItem.imgUrls.imgUrl}" alt="">
                                </a>
                            </p>
                            <div>
                                <h2>${bItem.skuName}</h2>
                                <h3><span>￥${bItem.unitPrice}</span><b>x${bItem.quantity}</b></h3>
                            </div>
                        </div>
                        </c:forEach>

                        <h1>
                            <b style="color:#A5A5A5">合计：￥${orderMap.orderAmount}</b>
                                                            <c:if test="${orderMap.orderType==0 || orderMap.orderType==3}">(保证金：￥${orderMap.bailAmount})</c:if>
                                                            <c:if test="${orderMap.orderType==2}">(运费：到付)</c:if>
                        </h1>

                        <h1>
                            <b>发货方：</b>
                            <span>
                            <c:if test="${orderMap.sendType == 0}">未选择</c:if>
                            <c:if test="${orderMap.sendType == 1}">平台代发</c:if>
                            <c:if test="${orderMap.sendType == 2}">自己发货</c:if>
                            </span>
                            <b>类型：</b>
                            <c:forEach items="${orderTypes}" var="orderType">
                                <c:if test="${orderType.code == orderMap.orderType}"><span>${orderType.desc}</span></c:if>
                            </c:forEach>
                        </h1>

                        <div class="ding">
                            <c:if test="${isShipment == 0}">
                                <p><a href="<%=path%>/borderManage/borderDetils.html?id=${orderMap.id}">查看订单详情</a></p>
                            </c:if>
                            <c:if test="${isShipment == 1}">
                                <p><a href="<%=path%>/borderManage/deliveryBorderDetils.html?id=${orderMap.id}">查看订单详情</a></p>
                            </c:if>
                            <c:if test="${orderMap.sendType==0 && orderMap.orderStatus !=0 && isShipment == 0}">
                                <span class="jixu">选择拿货方式</span>
                            </c:if>
                            <c:if test="${orderMap.orderStatus ==0 && isShipment == 0 }">
                                <span class="jixu"><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${orderMap.id}">继续支付</a></span>
                            </c:if>
                            <c:if test="${orderMap.orderStatus ==9 && isShipment == 0 }">
                                <span class="jixu" onclick="xinxi('${orderMap.orderAmount}','${orderMap.payTime}','${orderMap.orderCode}')">支付信息</span>
                            </c:if>
                            <c:if test="${orderMap.orderStatus ==8 && isShipment == 0}">
                                <span class="fa" name="querenshouhuo_${orderMap.id}" onclick="querenshouhuo('${orderMap.orderStatus}','${orderMap.id}')">确认收货</span>
                            </c:if>
                            <c:if test="${orderMap.orderStatus ==9 && isShipment == 0}">
                                <span><a href="<%=basePath%>border/goToPayBOrder.shtml?bOrderId=${orderMap.id}">改变支付方式</a></span>
                            </c:if>
                            <c:if test="${orderMap.orderType   == 2 || orderMap.sendType==2 && isShipment == 1}"><p class="sh" onclick="shouhuorenxinxi(${orderMap.id})">收货人信息</p></c:if>
                            <c:if test="${orderMap.orderStatus == 7 && orderMap.sendType==2 && isShipment == 1}"><span class="fa" name="fahuo_${orderMap.id}" onclick="fahuo('${orderMap.id}')">发货</span></c:if>
                        </div>

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
            <h1>订单号：<span id="11">1,000,000</span></h1>
            <p>您需要在<span id="12">2016-4-30</span>前将￥<span id="13">1,000,000.00</span>转到麦链合伙人对公账户。</p>
        </div>
        <%--<div>--%>
        <%--<h1>￥1,000,000元</h1>--%>
        <%--<p>您需要在2016-4-30前将￥1,000,000.00转到麦链合伙人对公账户。</p>--%>
        <%--</div>--%>
        <p>*请在汇款单的附言处注明绑定的订单号”（<span>非常重要！</span>）</p>
        <h1><span></span>麦链对公账户信息</h1>
        <h2><span>开户行：</span><span>${defaultBank.bankName}</span></h2>
        <h2><span>开户名：</span><span>${defaultBank.accountName}</span></h2>
        <h2><span>卡号：</span><span>${defaultBank.cardNumber}</span></h2>
        <button class="xinxn">我知道了</button>
    </div>

    <div class="back_que" style="display: none">
        <p>确认发货?</p>
        <h4>快递公司:<select id="select"><c:forEach items="${comShipMans}" var="comShipMans"><option value="${comShipMans.id}">${comShipMans.name}</option></c:forEach></select></h4>
        <h4>快递单号:<input type="text" id="input"/></h4>
        <h3 id="faHuo">发货</h3>
    </div>

    <div class="shouhuo" style="display: none">
        <p>收货人信息</p>
        <h4><span>姓　名:</span><span id="1"></span></h4>
        <h4><span>地　址:</span><span id="2">阿斯科利的阿</span></h4>
        <h4><span>手机号:</span><span id="3"></span></h4>
        <h4><span>邮　编:</span><span id="4"></span></h4>
        <h3 class="close">关闭</h3>
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
    $(document).on('click', '.order-list', function(){
        var isShipment = $(this).attr('isShipment');
        var orderStatus = $(this).attr('orderStatus');
        var param  = isShipment  == undefined ? '' : '?isShipment=' + isShipment;
            param += orderStatus == undefined ? '' : '&orderStatus=' + orderStatus;
        window.location.replace('<%=basePath%>borderManage/orderList'+param);
    });

    function xinxi(orderAmount,payTimes,ids){
        $(".back").css("display","-webkit-box");
        $(".back_pay").show();
        $("#11").html(ids);
        $("#13").html(orderAmount);
        $("#12").html(payTimes);
    }

    $(".xinxn").on("click",function(){
        $(".back_pay").hide();
        $(".back").hide();
    });

    $(".fa").on("click",function(){
        $(".back").css("display","-webkit-box");
        $(".back_shouhuo").css("display","-webkit-box");
    });

    var oid = "";
    function querenshouhuo(orderStatus,id) {
        $(".back").css("display", "-webkit-box");
        $(".back_shouhuo").css("display", "-webkit-box");
        oid = id;
    }
    $(function(){
        $(".que_que").on("click",function(){
            $(".back_shouhuo").hide();
            $(".back").hide();
            var aa="querenshouhuo_"+oid;
            $.ajax({
                type:"POST",
                url : "<%=path%>/borderManage/closeDeal.do",
                data:{orderStatus:3,shipStatus:9,orderId:oid},
                dataType:"Json",
                success:function(date){
//                            if(date.msgs){
                    $("span[name="+aa+"]").attr("style","display:none");
                    $("b."+aa+"").html("已完成");
                    location.reload(true);
//                            }else{
//                                alert(date.message);
//                            }
                }
            })
        })
    });

    function shouhuorenxinxi(bOrderId){
        $.ajax({
            url: '<%=basePath%>borderManage/getConsignee',
            data: {bOrderId: bOrderId},
            success: function(data){
                data = window.eval('('+data+')');
                $(".back").css("display","-webkit-box");
                $(".shouhuo").css("display","-webkit-box");
                $("#1").html(data.pfBorderConsignee.consignee);
                $("#2").html(data.pfBorderConsignee.provinceName);
                $("#3").html(data.pfBorderConsignee.cityName);
                $("#4").html(data.pfBorderConsignee.regionName);
            },
            error: function(){
                alert('加载收货人信息失败!');
            }
        });
    }

    function fahuo(id){
        $(".back").css("display","-webkit-box");
        $(".back_que").css("display","-webkit-box");
        $("#faHuo").on("click",function(){
            $(".back_que").hide();
            $(".back").hide();
            var shipManId = $("#select option:selected").val();
            var shipManName = $("#select option:selected").text();
            var freight = $("#input").val();
            var aa="fahuo_"+id;
            $.ajax({
                type:"POST",
                url : "<%=path%>/borderManage/deliver.do",
                data:{shipManName:shipManName,freight:freight,orderId:id,shipManId:shipManId},
                dataType:"Json",
                success:function(date){
                    $("span[name=" + aa + "]").attr("style", "display:none");
                    $("b." + aa + "").html("待收货");
                    location.reload(true);
                }
            })
        })
    }

    $(".que_qu").on("click",function(){
        $(".back_shouhuo").hide();
        $(".back").hide();
    });
</script>
</body>
</html>