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
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/jinhuodingdan.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
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
                    <c:forEach items="${pfBorders}" begin="0" end="${pfBorders.size()}" var="pbs">
                    <div class="all">
                        <c:forEach items="${pbs}" var="pb">
                        <section class="sec1">
                           <p>时间： <span><fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" /></span></p>
                            <h2>
                                订单号：<span>${pb.orderCode}</span>
                                <c:if test="${pb.orderStatus ==0}"><b class="querenshouhuo_${pb.id}" >待付款</b ></c:if><c:if test="${pb.orderStatus ==1}"><b class="querenshouhuo_${pb.id}">等待收货</b></c:if><c:if test="${pb.orderStatus ==3}"><b class="querenshouhuo_${pb.id}">交易成功</b></c:if>
                            </h2>
                            <c:forEach items="${pb.pfBorderItems}" var="pbi">
                            <div class="shangpin">
                                <p class="photo">
                                   <a href="<%=path%>/static/html/xiangqing.html">
                                        <img src="${pbi.skuUrl}" alt="">
                                    </a>
                                </p>
                                <div>
                                    <h2>${pbi.skuName}</h2>
                                    <h3>规格：<span>默认</span><b>x${pbi.quantity}</b></h3>
                                    <p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥${pbi.unitPrice}</span></p>
                                </div>
                            </div></c:forEach>

                            <h1><b>合计：￥${pb.orderAmount}</b>(共<span>${pb.totalQuantity}</span>件商品 运费<span>￥${pb.shipAmount}</span>)</h1>
                            <div class="ding">
                                <p><a href="<%=path%>/userCenterController/borderDetils.html?id=${pb.id}">查看订单详情</a></p><c:if test="${pb.orderStatus ==0}">
                                <span class="jixu">
                                    <a href="buhuodingdan.html">继续支付</a>
                                </span></c:if><c:if test="${pb.orderStatus ==1}">
                                <span class="fa"  name="querenshouhuo_${pb.id}"  onclick="querenshouhuo('${pb.orderStatus}','${pb.id}')">
                                    确认收货
                                </span></c:if>
                            </div>
                        </section>
                        </c:forEach>
                    </div>
                    </c:forEach>

                    <%--<div class="all">           --%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="chakanxiangqing.html">查看订单详情</a></p>--%>
                                <%--<span class="fa">--%>
                                    <%--确认收货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="chakanxiangqing.html">查看订单详情</a></p>--%>
                                <%--<span class="jixu">--%>
                                    <%--继续支付--%>
                                <%--</span>--%>
                                <%--<span class="quxiao">--%>
                                    <%--取消订单--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                    <%--</div>--%>
                    <%--<div class="all">           --%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="chakanxiangqing.html">查看订单详情</a></p>--%>
                                <%--<span class="fa">--%>
                                    <%--确认收货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="chakanxiangqing.html">查看订单详情</a></p>--%>
                                <%--<span class="jixu">--%>
                                    <%--继续支付--%>
                                <%--</span>--%>
                                <%--<span class="quxiao">--%>
                                    <%--取消订单--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                    <%--</div>--%>
                    <%--<div class="all">           --%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="chakanxiangqing.html">查看订单详情</a></p>--%>
                                <%--<span class="fa">--%>
                                    <%--确认收货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="chakanxiangqing.html">查看订单详情</a></p>--%>
                                <%--<span class="jixu">--%>
                                    <%--继续支付--%>
                                <%--</span>--%>
                                <%--<span class="quxiao">--%>
                                    <%--取消订单--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                    <%--</div>--%>
                    <%--<div class="all">           --%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="chakanxiangqing.html">查看订单详情</a></p>--%>
                                <%--<span class="fa">--%>
                                    <%--确认收货--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                        <%--<section class="sec1">--%>
                           <%--<p>时间：<span>2016-2-24</span><span>16:24</span></p>--%>
                            <%--<h2>--%>
                                <%--订单号：<span>e12093891283091283</span>--%>
                                <%--<b>待发货</b>--%>
                            <%--</h2>--%>
                            <%--<div class="shangpin">--%>
                                <%--<p class="photo">--%>
                                   <%--<a href="<%=path%>/static/html/xiangqing.html">--%>
                                        <%--<img src="<%=path%>/static/images/shenqing_1.png" alt="">--%>
                                    <%--</a>--%>
                                <%--</p>--%>
                                <%--<div>--%>
                                    <%--<h2>抗引力——快速瘦脸精华</h2>--%>
                                    <%--<h3>规格：<span>默认</span><b>x1000</b></h3>--%>
                                    <%--<p class="defult">零售价： <span style="float:none;color:#FF6A2A;">￥123</span></p>--%>
                                    <%--<p><b>合计：￥2500.00</b>(共<span>100</span>件商品 运费<span>￥300</span>)</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="ding">--%>
                                <%--<p><a href="chakanxiangqing.html">查看订单详情</a></p>--%>
                                <%--<span class="jixu">--%>
                                    <%--继续支付--%>
                                <%--</span>--%>
                                <%--<span class="quxiao">--%>
                                    <%--取消订单--%>
                                <%--</span>--%>
                            <%--</div>--%>
                        <%--</section>--%>
                    <%--</div>--%>
                </main>
           </div>
            <div class="back">
                <div class="back_shouhuo">
                    <p>确认收到货品?</p>
                    <h4>亲，请您核对商品后在操作确认收货</h4>

                    <h3>
                        <span class="que_qu">取消</span>
                        <span class="que_que">确认</span>
                    </h3>
                </div>
                
                <div class="back_que">
                    <p>确认取消订单？</p>
                    <h4>亲，是否确认删除商品抗引力-收敛精华乳液订单？</h4>

                    <h3>
                        <span class="que_qu">取消</span>
                        <span class="que_que">确认</span>
                    </h3>
                </div>
            </div>
       </div>
       <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
       <script>
            var myScroll = new IScroll("main",{
                 preventDefault: false
            })
            $("li").on("click",function(){
                var index=$(this).index();
                $("li").children("a").removeClass("on")
                $(this).children("a").addClass("on");
                $(".all").eq(index).show().siblings().hide();
            })

            $(".fa").on("click",function(){
                $(".back").css("display","-webkit-box");
                $(".back_shouhuo").css("display","-webkit-box");
            })
            function querenshouhuo(orderStatus,id){
                $(".back").css("display","-webkit-box");
                $(".back_shouhuo").css("display","-webkit-box");
                $(".que_que").on("click",function(){
                    orderStatus=3;
                    $(".back_shouhuo").hide();
                    $(".back").hide();

                    var aa="querenshouhuo_"+id;
                    alert(aa);
                    $.ajax({
                        type:"POST",
                        async:false,
                        url : "<%=path%>/userCenterController/closeDeal.do",
                        data:{orderStatus:3,orderId:id},
                        dataType:"Json",
                        success:function(date){
                            alert($("b."+aa+"").html());
                            $("span[name="+aa+"]").attr("style","display:none");
                            $("b."+aa+"").html("交易成功");
                        }
                    })
                })
            }
            $(".que_qu").on("click",function(){
                $(".back_shouhuo").hide();
                $(".back").hide();
            })
//            $(".que_que").on("click",function(){
//                $(".back_shouhuo").hide();
//                $(".back").hide();
//            })

       </script>
</body>
</html>