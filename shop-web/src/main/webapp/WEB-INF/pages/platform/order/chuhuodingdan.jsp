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
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/wodedingdan.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
       <div class="wrap">
           <div class="box">
                <header class="xq_header"><a href="<%=path%>/borderManage/borderManagement.html">
                    <img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                        <p>下级合伙人订单</p>
                </header>
                <nav>
                    <ul>
                        <li><a href="javascript:;" class="on">全部</a></li>
                        <li><a href="javascript:;">排单中</a></li>
                        <li><a href="javascript:;">待付款</a></li>
                        <li><a href="javascript:;">已完成</a></li>
                        <li><a href="javascript:;">已取消</a></li>
                    </ul>
                    <img src="${paht}/static/images/youdao.png" alt="" class="you">
                </nav>
               <p><img src="${path}/static/images/laba.png" alt="">您只可以查看直接下级的订单</p>
                <main>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}');">
                                <h2>
                                    订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                                        <b>${pb.orderStatusDes}</b>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <div>
                                            <h2><span>${pbi.skuName}</span><span>x${pbi.quantity}</span></h2>
                                            <h3><b>合计：￥${pb.orderAmount}</b></h3>
                                        </div>
                                    </div></c:forEach>
                                <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" />
                                    <span class="jixu">购买人：${pb.userName.realName}</span>
                                </p>
                            </section>
                        </c:forEach>
                    </div>
                        <div class="all">
                            <c:forEach items="${pfBorders}" var="pb">
                                <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}');">
                                    <h2>
                                        订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                                        <b>${pb.orderStatusDes}</b>
                                    </h2>
                                    <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                        <div class="shangpin">
                                            <div>
                                                <h2><span>${pbi.skuName}</span><span>x${pbi.quantity}</span></h2>
                                                <h3><b>合计：￥${pb.orderAmount}</b></h3>
                                            </div>
                                        </div></c:forEach>
                                    <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" />
                                        <span class="jixu">购买人：${pb.userName.realName}</span>
                                    </p>
                                </section>
                            </c:forEach>
                        </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}');">
                                <h2>
                                    订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                                    <b>${pb.orderStatusDes}</b>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <div>
                                            <h2><span>${pbi.skuName}</span><span>x${pbi.quantity}</span></h2>
                                            <h3><b>合计：￥${pb.orderAmount}</b></h3>
                                        </div>
                                    </div></c:forEach>
                                <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" />
                                    <span class="jixu">购买人：${pb.userName.realName}</span>
                                </p>
                            </section>
                        </c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}');">
                                <h2>
                                    订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                                    <b>${pb.orderStatusDes}</b>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <div>
                                            <h2><span>${pbi.skuName}</span><span>x${pbi.quantity}</span></h2>
                                            <h3><b>合计：￥${pb.orderAmount}</b></h3>
                                        </div>
                                    </div></c:forEach>
                                <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" />
                                    <span class="jixu">购买人：${pb.userName.realName}</span>
                                </p>
                            </section>
                        </c:forEach>
                    </div>
                    <div class="all">
                        <c:forEach items="${pfBorders}" var="pb">
                            <section class="sec1" onclick="javascript:window.location.replace('<%=path%>/borderManage/deliveryBorderDetils.html?id=${pb.id}');">
                                <h2>
                                    订单号：<span>${pb.orderCode}(<span>${pb.orderTypeDes}</span>)</span>
                                    <b>${pb.orderStatusDes}</b>
                                </h2>
                                <c:forEach items="${pb.pfBorderItems}" var="pbi">
                                    <div class="shangpin">
                                        <div>
                                            <h2>
                                                <span>${pbi.skuName}</span><span>x${pbi.quantity}</span>
                                            </h2>
                                            <h3><b>合计：￥${pb.orderAmount}</b></h3>
                                        </div>
                                    </div>
                                </c:forEach>
                                <p>时间： <fmt:formatDate value="${pb.createTime}" pattern="yyyy-MM-dd HH:mm" />
                                    <span class="jixu">购买人：${pb.userName.realName}</span>
                                </p>
                            </section>
                        </c:forEach>
                    </div>
                </main>
           </div>
       </div>
       <link rel="stylesheet" href="<%=path%>/static/css/loading.css">
       <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
       <script src="<%=path%>/static/js/commonAjax.js"></script>
       <script src="<%=path%>/static/js/jinhuoshijian.js"></script>
       <script src="<%=path%>/static/js/definedAlertWindow.js"></script>
       <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
       <script src="<%=path%>/static/js/hideWXShare.js"></script>
       <script>
               $(document).ready(function(){
                   var index=${index};
                   $("li").children("a").removeClass("on")
                   $("li").eq(index).children("a").addClass("on");
                   $(".all").eq(index).show().siblings().hide();
               });
               $(function(){
                   $("li").on("click",function(){
                       var index=$(this).index();
                       $(".all").html("");
                       $(".all").eq(index).show().siblings().hide();
                       $("li").children("a").removeClass("on");
                       $(this).children("a").addClass("on");
                       $.ajax({
                           type:"POST",
                           url : "<%=path%>/borderManage/clickdeliverType.do",
                           data:{index:index},
                           dataType:"Json",
                           success:function(data){
                               var trHtml = "";
                               $.each(data, function(i, pfBorder) {
                                   var ordertime = new Date(pfBorder.createTime).Format("yyyy-MM-dd hh:mm");
                                   trHtml+="<section class=\"sec1\" onclick=\"javascript:window.location.replace('<%=path%>/borderManage/deliveryBorderDetils.html?id="+pfBorder.id+"');\">";
                                   trHtml+="<h2>订单号: <span>"+pfBorder.orderCode +"("+pfBorder.orderTypeDes+")</span>";
                                   trHtml+="<b>"+pfBorder.orderStatusDes+"</b>";
                                   trHtml+="</h2>";
                                   $.each(pfBorder.pfBorderItems, function(i, pfBorderItem) {
                                       trHtml+="<div class=\"shangpin\"><div>";
                                       trHtml+="<h2><span>"+pfBorderItem.skuName+"</span>";
                                       trHtml+="<span>x"+pfBorderItem.quantity+"</span>";
                                       trHtml+="<h3><b>合计：￥"+pfBorder.orderAmount+"</b></h3>";
                                       trHtml+="</h2>";
                                       trHtml+="</div></div>";
                                   });
                                   trHtml+="<p>时间："+ordertime;
                                   trHtml+="<span class=\"jixu\">购买人："+pfBorder.userName.realName+"</span>";
                                   trHtml+="</p>";
                                   trHtml+="</section>";
                               });
                               $(".all").eq(index).html(trHtml);
                           }
                       })
                   })
               })
               $(".close").on("click",function(){
                   $(".shouhuo").hide();
                   $(".back").hide();
               })
               $(".back").on("click",function(){
                   $(".back_que").hide();
                   $(".back").hide();
                   $(".shouhuo").hide();
               })
       </script>
</body>
</html>