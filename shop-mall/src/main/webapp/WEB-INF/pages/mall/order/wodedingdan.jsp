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
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/wodedingdan.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/loading.css">
    <link rel="stylesheet" href="<%=path%>/static/css/pageCss/header.css">

</head>
<body>
       <div class="wrap">
           <div class="box">
                <header class="xq_header">
                   <a href="zhifu.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>我的订单</p>  
                </header>
                <nav>
                    <ul>
                        <li><a href="javascript:;" class="on">全部</a></li>
                        <li><a href="javascript:;">待付款</a></li>
                        <li><a href="javascript:;">待发货</a></li>
                        <li><a href="javascript:;">待收货</a></li>
                        <li><a href="javascript:;">已完成</a></li>
                    </ul>
                </nav>
                <main>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                           <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">已发货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                   <a href="<%=path%>/static/html/xiangqing.html">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}家商品 合计：￥${pb.orderAmount} （含运费￥50.00）</h1>
                            <div class="ding">
                                <p><a href="chakanxiangqing.html">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                <p>
                                    <c:if test="${pb.orderStatus ==8}"><button>确认收货</button></c:if>
                                    <c:if test="${pb.orderStatus ==0}"><button>继续支付</button></c:if>
                                </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">已发货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="<%=path%>/static/html/xiangqing.html">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}家商品 合计：￥${pb.orderAmount} （含运费￥50.00）</h1>
                            <div class="ding">
                                <p><a href="chakanxiangqing.html">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                    <p>
                                        <c:if test="${pb.orderStatus ==8}"><button>确认收货</button></c:if>
                                        <c:if test="${pb.orderStatus ==0}"><button>继续支付</button></c:if>
                                    </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">已发货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="<%=path%>/static/html/xiangqing.html">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}家商品 合计：￥${pb.orderAmount} （含运费￥50.00）</h1>
                            <div class="ding">
                                <p><a href="chakanxiangqing.html">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                    <p>
                                        <c:if test="${pb.orderStatus ==8}"><button>确认收货</button></c:if>
                                        <c:if test="${pb.orderStatus ==0}"><button>继续支付</button></c:if>
                                    </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">已发货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="<%=path%>/static/html/xiangqing.html">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}家商品 合计：￥${pb.orderAmount} （含运费￥50.00）</h1>
                            <div class="ding">
                                <p><a href="chakanxiangqing.html">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                    <p>
                                        <c:if test="${pb.orderStatus ==8}"><button>确认收货</button></c:if>
                                        <c:if test="${pb.orderStatus ==0}"><button>继续支付</button></c:if>
                                    </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                    <div class="all"><c:forEach items="${sfOrders}" var="pb">
                        <section class="sec1">
                            <p>时间：<span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if>
                                <c:if test="${pb.orderStatus ==7}"> <b class="querenshouhuo_${pb.id}">待发货</b></c:if>
                                <c:if test="${pb.orderStatus ==8}"><b class="querenshouhuo_${pb.id}">已发货</b></c:if>
                                <c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2><c:forEach items="${pb.sfOrderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                    <a href="<%=path%>/static/html/xiangqing.html">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}<b>x${pbi.quantity}</b></h2>
                                    <p class="defult"><span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                </div>
                            </div></c:forEach>
                            <h1>共${pb.totalQuantity}家商品 合计：￥${pb.orderAmount} （含运费￥50.00）</h1>
                            <div class="ding">
                                <p><a href="chakanxiangqing.html">查看订单详情</a></p>
                                <c:if test="${pb.orderStatus ==8 ||pb.orderStatus ==0}">
                                    <p>
                                        <c:if test="${pb.orderStatus ==8}"><button>确认收货</button></c:if>
                                        <c:if test="${pb.orderStatus ==0}"><button>继续支付</button></c:if>
                                    </p>
                                </c:if>
                            </div>
                        </section></c:forEach>
                    </div>
                </main>
           </div>
       </div>
       <div class="back_que">
                    <p>确认减库存?</p>
                    <h4>快递公司:<span><select><option>顺风</option></select></span></h4>
                    <h4>快递单号:<span><input type="text"/></span></h4>
                    <h3>发货</h3>
                </div>
                <div class="shouhuo">
                    <p>收货人信息</p>
                    <h4><span>姓　名:</span><span></span></h4>
                    <h4><span>地　址:</span><span>阿斯科利的asdasdasdasdas将阿</span></h4>
                    <h4><span>手机号:</span><span></span></h4>
                    <h4><span>邮　编:</span><span></span></h4>
                    <h3 class="close">关闭</h3>
                </div>
           <div class="back">
               
           </div>
       <script src="<%=path%>/static/js/plugins/jquery-1.8.3.min.js"></script>
       <script src="<%=path%>/static/js/common/commonAjax.js"></script>
       <script src="<%=path%>/static/js/common/jinhuoshijian.js"></script>
       <script src="<%=path%>/static/js/common/definedAlertWindow.js"></script>
       <script>
            $("li").on("click",function(){
                var index=$(this).index();
                $("li").children("a").removeClass("on")
                $(this).children("a").addClass("on");
                $(".all").eq(index).show().siblings().hide();
                $.ajax({
                    type:"POST",
                    url : "<%=path%>/sfOrderManagerController/clickSfOrderType.do",
                    data:{index:index},
                    dataType:"Json",
                    success:function(data){
                        var trHtml = "";
                        $.each(data, function(i, sfOrder) {
                            var time2 = new Date(sfOrder.createTime).Format("yyyy-MM-dd hh:mm");
                            trHtml+="<section class='sec1'>";
                            trHtml+="<p>时间: <span>"+time2 +"</span></p>";
                            if(sfOrder.orderStatus==0){
                                trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class='querenshouhuo_"+sfOrder.id+"' >待付款</b ></h2>";
                            }else if(sfOrder.orderStatus ==7){
                                trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class='querenshouhuo_"+sfOrder.id+"' >待发货</b ></h2>";
                            }else if(sfOrder.orderStatus ==8){
                                trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class='querenshouhuo_"+sfOrder.id+"' >已发货</b ></h2>";
                            }else if(sfOrder.orderStatus ==8){
                                trHtml+="<h2>订单号：<span>"+sfOrder.orderCode+"</span><b class='querenshouhuo_"+sfOrder.id+"' >交易成功</b ></h2>";
                            }
                            $.each(sfOrder.sfOrderItems, function(i, sfOrderItem) {
                                trHtml+="<div class='shangpin'>";
                                trHtml+=" <p class=\"photo\">";
                                trHtml+="<a href=\"<%=path%>/static/html/xiangqing.html\">";
                                trHtml+="<img src=\""+sfOrderItem.skuUrl+"\" alt=\"\"></a></p>";
                                trHtml+="<div><h2>"+sfOrderItem.skuName+"<b>x"+sfOrderItem.quantity+"</b></h2><p class=\"defult\"><span style=\"float:none;color:#FF6A2A;\">￥"+sfOrderItem.unitPrice+"</span></p></div></div>";
                            });
                            trHtml+="<h1> 共<span>"+sfOrder.totalQuantity+"</span>件商品 <b style=\"color:#A5A5A5\">合计：￥"+sfOrder.orderAmount+"</b>( 运费：到付)</h1>";
                            trHtml+="<div class=\"ding\"><p><a href=\"<%=path%>/borderManage/borderDetils.html?id="+sfOrder.id+"\">查看订单详情</a></p>";
                            if(sfOrder.orderStatus ==8 ||sfOrder.orderStatus ==0){
                                trHtml+="<p>";
                                if(sfOrder.orderStatus ==8 ){
                                    trHtml+="<button>确认收货</button></p>";
                                }
                                if(sfOrder.orderStatus ==8 ){
                                    trHtml+="<button>继续支付</button></p>";
                                }
                            }
                            trHtml+="</div></section>";
                        });
                        $(".all").eq(index).html(trHtml);
                    }
                })
            })
            $(document).ready(function(){
                var index=${index};
                $("li").children("a").removeClass("on")
                $("li").eq(index).children("a").addClass("on");
                $(".all").eq(index).show().siblings().hide();
            });
            $(".sh").on("click",function(){
                $(".back").css("display","-webkit-box");
                $(".shouhuo").css("display","-webkit-box");
            })
            $(".close").on("click",function(){
                $(".shouhuo").hide();
                $(".back").hide();
            })
       </script>
</body>
</html>