<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/main.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
    <link rel="stylesheet" href="<%=path%>/static/shop/css/dingdanguan.css">
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="<%=path%>/shop/manage/index"><img src="<%=path%>/static/shop/images/xq_rt.png" alt=""></a>
            <p>店铺订单</p>
        </header>
        <nav>
            <ul>
                <li><a href="javascript:;" class="on">全部</a></li>
                <li><a href="javascript:;">待付款</a></li>
                <li><a href="javascript:;">待发货</a></li>
                <li><a href="javascript:;">待收货</a></li>
                <li><a href="javascript:;">已完成</a></li>
                <li><a href="javascript:;">已取消</a></li>
            </ul>
        </nav>
        <div class="tapfix">
            <h1>发货方式：</h1>
            <p class="on">平台代发</p>
            <p>店主发货</p>
        </div>
        <main>
            <div class="all">
                <c:forEach items="${sfOrders}" var="pb">
                    <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(${pb.sendTypeDes})</span>
                            <b class="fahuo">${pb.orderStatusDes}</b >
                        </h2>
                        <c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <a href="<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}">
                            <div class="shangpin">
                                <div>
                                    <h2>
                                        ${pbi.skuName}(￥${pbi.unitPrice})
                                            <span> x${pbi.quantity}</span>
                                    </h2>
                                    <p class="defult">合计：<span>￥${pb.orderAmount}</span></p>
                                </div>
                            </div>
                                </a></c:forEach>
                        <div class="ding">
                            <p>时间：<fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></p>
                                <p>购买人：${pb.createUserName}</p>
                            <c:if test="${pb.orderStatus ==7 && pb.sendType==2}">
                                <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}',event)">发货</button>
                            </c:if>
                        </div>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <c:forEach items="${sfOrders}" var="pb">
                    <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(${pb.sendTypeDes})</span>
                            <b class="fahuo">${pb.orderStatusDes}</b >
                        </h2>
                        <c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <a href="<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}">
                                <div class="shangpin">
                                    <div>
                                        <h2>
                                                ${pbi.skuName}(￥${pbi.unitPrice})
                                            <span> x${pbi.quantity}</span>
                                        </h2>
                                        <p class="defult">合计：<span>￥${pb.orderAmount}</span></p>
                                    </div>
                                </div>
                            </a></c:forEach>
                        <div class="ding">
                            <p>时间：<fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></p>
                            <p>购买人：${pb.createUserName}</p>
                            <c:if test="${pb.orderStatus ==7 && pb.sendType==2}">
                                <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}',event)">发货</button>
                            </c:if>
                        </div>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <c:forEach items="${sfOrders}" var="pb">
                    <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(${pb.sendTypeDes})</span>
                            <b class="fahuo">${pb.orderStatusDes}</b >
                        </h2>
                        <c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <a href="<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}">
                                <div class="shangpin">
                                    <div>
                                        <h2>
                                                ${pbi.skuName}(￥${pbi.unitPrice})
                                            <span> x${pbi.quantity}</span>
                                        </h2>
                                        <p class="defult">合计：<span>￥${pb.orderAmount}</span></p>
                                    </div>
                                </div>
                            </a></c:forEach>
                        <div class="ding">
                            <p>时间：<fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></p>
                            <p>购买人：${pb.createUserName}</p>
                            <c:if test="${pb.orderStatus ==7 && pb.sendType==2}">
                                <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}',event)">发货</button>
                            </c:if>
                        </div>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <c:forEach items="${sfOrders}" var="pb">
                    <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(${pb.sendTypeDes})</span>
                            <b class="fahuo">${pb.orderStatusDes}</b >
                        </h2>
                        <c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <a href="<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}">
                                <div class="shangpin">
                                    <div>
                                        <h2>
                                                ${pbi.skuName}(￥${pbi.unitPrice})
                                            <span> x${pbi.quantity}</span>
                                        </h2>
                                        <p class="defult">合计：<span>￥${pb.orderAmount}</span></p>
                                    </div>
                                </div>
                            </a></c:forEach>
                        <div class="ding">
                            <p>时间：<fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></p>
                            <p>购买人：${pb.createUserName}</p>
                            <c:if test="${pb.orderStatus ==7 && pb.sendType==2}">
                                <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}',event)">发货</button>
                            </c:if>
                        </div>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <c:forEach items="${sfOrders}" var="pb">
                    <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(${pb.sendTypeDes})</span>
                            <b class="fahuo">${pb.orderStatusDes}</b >
                        </h2>
                        <c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <a href="<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}">
                                <div class="shangpin">
                                    <div>
                                        <h2>
                                                ${pbi.skuName}(￥${pbi.unitPrice})
                                            <span> x${pbi.quantity}</span>
                                        </h2>
                                        <p class="defult">合计：<span>￥${pb.orderAmount}</span></p>
                                    </div>
                                </div>
                            </a></c:forEach>
                        <div class="ding">
                            <p>时间：<fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></p>
                            <p>购买人：${pb.createUserName}</p>
                            <c:if test="${pb.orderStatus ==7 && pb.sendType==2}">
                                <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}',event)">发货</button>
                            </c:if>
                        </div>
                    </section>
                </c:forEach>
            </div>
            <div class="all">
                <c:forEach items="${sfOrders}" var="pb">
                    <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}');">
                        <h2>
                            订单号：<span>${pb.orderCode}(${pb.sendTypeDes})</span>
                            <b class="fahuo">${pb.orderStatusDes}</b >
                        </h2>
                        <c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <a href="<%=path%>/sfOrderController/sfOrderDetal.html?id=${pb.id}">
                                <div class="shangpin">
                                    <div>
                                        <h2>
                                                ${pbi.skuName}(￥${pbi.unitPrice})
                                            <span> x${pbi.quantity}</span>
                                        </h2>
                                        <p class="defult">合计：<span>￥${pb.orderAmount}</span></p>
                                    </div>
                                </div>
                            </a></c:forEach>
                        <div class="ding">
                            <p>时间：<fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></p>
                            <p>购买人：${pb.createUserName}</p>
                            <c:if test="${pb.orderStatus ==7 && pb.sendType==2}">
                                <button class="fa" name="fahuo_${pb.id}" onclick="fahuo('${pb.id}',event)">发货</button>
                            </c:if>
                        </div>
                    </section>
                </c:forEach>
            </div>
        </main>
    </div>
</div>
<div class="black">
    <div class="backb"></div>
    <div class="back_que">
        <div class="backt">
            <h1>发货信息</h1>
            <p>
                <span>快递公司：</span>
                <label for="" class="bWidth"><b></b><select class="se">
                <c:forEach items="${comShipMans}" var="comShipMans">
                <option value="${comShipMans.id}">${comShipMans.name}</option>
                </c:forEach>
                    </select>
                    </label>
                    </p>
            <p><span>快递单号：</span><input type="text" id="input"/></p>
            <button id="tofaHuo">发货</button>
        </div>
        <div class="backd">
            <p>
                <span>收货人：</span>
                <span id="shouhuo"></span>
            </p>

            <p>
                <span>收货地址：</span>
                <span style="width: 100px;" id="adress"></span>
            </p>
            <p>
                <span>联系电话：</span>
                <span id="mobile"></span>
            </p>
            <p>
                <span>邮编：</span>
                <span id="youbian"></span>
            </p>
            <p>
                <span>购买人：</span>
                <span id="buyuser"></span>
            </p>
            <p>
                <span>留言：</span>
                <span id="msg"></span>
            </p>
        </div>
    </div>
</div>
<script src="<%=path%>/static/shop/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/shop/js/commonAjax.js"></script>
<script src="<%=path%>/static/shop/js/jinhuoshijian.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<%=path%>/static/js/hideWXShare.js"></script>
<script>

    $(document).ready(function(){
        var index=${index};
        $("li").children("a").removeClass("on")
        $("li").eq(index).children("a").addClass("on");
        $(".all").eq(index).show().siblings().hide();
        $(".tapfix").hide();
    });
    function shouhuorenxinxi(a,b,c,d){
        $("#1").html(a);
        $("#2").html(b);
        $("#3").html(c);
        $("#4").html(d);
    }
    $(".se").on("change",function(){
        var tabVal=$(".se option:selected").text();
        $(".bWidth b").html(tabVal);
    })
    $(function(){
        $(".tapfix p").on("click",function(){
            var sendType = $(this).index();
            $(this).addClass("on").siblings().removeClass("on");
            ajaxAction(2,sendType);
        })

        $("li").on("click",function(){
            var index=$(this).index();
            var sendType = "";
            if(index ==2){
                $(".tapfix").show();
                sendType=1;
            }else{
                $(".tapfix").hide();
            }
            $(".all").html("");
            $(".all").eq(index).show().siblings().hide();
            $("li").children("a").removeClass("on");
            $(this).children("a").addClass("on");
            ajaxAction(index,sendType);
        })
    })

    function ajaxAction(index,sendType){
        $.ajax({
            type:"POST",
            url : "<%=path%>/sfOrderController/clickSfOrder.do",
            data:{index:index,sendType:sendType},
            dataType:"Json",
            success:function(data){
                var trHtml = "";
                $.each(data, function(i, sfOrder) {
                    var ordertime = new Date(sfOrder.createTime).Format("yyyy-MM-dd hh:mm");
                    trHtml+="<section class=\"sec1\" onclick=\"javascript:window.location.replace('<%=path%>/sfOrderController/sfOrderDetal.html?id="+sfOrder.id+"\');\">";
                    trHtml+="<h2>订单号: <span>"+sfOrder.orderCode +"("+sfOrder.sendTypeDes+")</span>";
                    trHtml+="<b>"+sfOrder.orderStatusDes+"</b>";
                    trHtml+="</h2>";
                    $.each(sfOrder.sfOrderItems, function(i, sfOrderItem) {
                        trHtml+="<div class=\"shangpin\"><div>";
                        trHtml+="<h2>"+sfOrderItem.skuName+"("+"￥"+sfOrderItem.unitPrice+")";
                        trHtml+="<span> x"+sfOrderItem.quantity+"</span></h2>";
                        trHtml+="<p class=\"defult\">合计：<span>￥"+sfOrder.orderAmount+"</span></p>";
                        trHtml+="</div></div>";
                    });
                    trHtml+="<div class=\'ding\'><p>时间："+ordertime+"</p>";
                    trHtml+="<p class=\"jixu\">购买人："+sfOrder.createUserName+"</p>";
                    if(sfOrder.orderStatus ==7 && sfOrder.sendType==2){
                        trHtml+="<button class=\"fa\" name=\"fahuo_"+sfOrder.id+"\" onclick=\"fahuo("+sfOrder.id+",event)\">发货</button>";
                    }
                    trHtml+="</div>";
                    trHtml+="</section>";
                });
                $(".all").eq(index).html(trHtml);
            }
        })
    }
    $(".backb").on("click", function () {
        $(".black").hide();
    })
    function fahuo(id,event){
        var event=event||event.window;
        event.stopPropagation();
        //异步获取发货信息
        $.ajax({
            type:"POST",
            url : "<%=path%>/sfOrderController/getSfOrderConsignee.do",
            data:{id:id},
            dataType:"Json",
            async:false,
            success:function(data){
                $("#shouhuo").html(data.sfOrderConsignee.consignee);
                $("#adress").html(data.sfOrderConsignee.provinceName+" "+data.sfOrderConsignee.cityName+" "+data.sfOrderConsignee.regionName+" "+data.sfOrderConsignee.address);
                $("#mobile").html(data.sfOrderConsignee.mobile);
                $("#youbian").html(data.sfOrderConsignee.zip);
                $("#buyuser").html(data.buyUser);
                $("#msg").html(data.userMsg);
            }
        })
        $(".black").show();

        $("#tofaHuo").on("click",function(){
            $(".black").hide();
            var shipManId = $(".se option:selected").val();
            var shipManName = $(".se option:selected").text();
            var freight = $("#input").val();
            var aa="fahuo_"+id;
            $.ajax({
                type:"POST",
                url : "<%=path%>/sfOrderController/deliverOrder.do",
                data:{shipManName:shipManName,freight:freight,orderId:id,shipManId:shipManId},
                dataType:"Json",
                success:function(data){
                    $("button[name=" + aa + "]").attr("style", "display:none");
                    $("b." + aa + "").html("待收货");
                    location.reload(true);
                }
            })
        })
    }
</script>
</body>
</html>
