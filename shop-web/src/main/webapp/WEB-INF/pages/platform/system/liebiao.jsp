<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/liebiao.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
    <div class="wrap">
        <div class="box">
                <header class="xq_header" style="margin-bottom:0;">
              <a href="<%=path%>/index"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                <p>所有商品</p>            
                </header>
                <main><c:forEach begin="0" end="${ComSize}" step="2" var="i">
                     <section class="sec1">
                         <c:forEach items="${indexComSkus}"  begin="${i}" end="${i+1}" var="indexComSkus">
                        <div>
                           <a href="/product/${indexComSkus.id}"><img src="${indexComSkus.imgUrl}" alt=""></a>
                            <h2>${indexComSkus.comSku.name}</h2>
                            <h1>￥${indexComSkus.comSku.priceRetail} <span>${indexComSkus.discountLevel}</span></h1>
                            <h3>保证金：${indexComSkus.bail}元</h3>
                            <h4>试用费用：${indexComSkus.shipAmount}元</h4>
                            <h5>
                                <p>超过<span>${indexComSkus.agentNum}</span>人代理</p>
                                <%--<c:choose><c:when test="${indexComSkus.isPartner==1 && indexComSkus.isPay==1}"><a href="javascript:;" >您已合伙</a></c:when>--%>
                                <%--<c:otherwise><a href="shenqing.html" class="he">我要合伙</a></c:otherwise></c:choose>--%>
                            </h5>
                        </div>
                         </c:forEach>
                    </section>
                </c:forEach>
                       <!--<section class="sec1">-->
                        <!--<div>-->
                            <!--<a href="xiangqing.html"><img src="<%=path%>/static/images/cp_1.png" alt=""></a>-->
                            <!--<h2>抗引力—快速瘦脸精华</h2>-->
                            <!--<h1>￥328 <span>成为合伙人可查看</span></h1>-->
                            <!--<h3>-->
                                <!--<p>超过<span>9999</span>人代理</p>-->
                                <!--<a href="shenqing.html" class="he">我要合伙</a>-->
                                <!--<a href="javascript:;" class="ok">您已合伙</a>-->
                            <!--</h3>-->
                        <!--</div>-->
                        <!--<div>-->
                            <!--<a href="xiangqing.html"><img src="<%=path%>/static/images/cp_1.png" alt=""></a>-->
                            <!--<h2>抗引力—快速瘦脸精华</h2>-->
                            <!--<h1>￥328 <span>成为合伙人可查看</span></h1>-->
                            <!--<h3>-->
                                <!--<p>超过<span>9999</span>人代理</p>-->
                                <!--<a href="shenqing.html" class="he">我要合伙</a>-->
                                <!--<a href="javascript:;" class="ok">您已合伙</a>-->
                            <!--</h3>-->
                        <!--</div>-->
                    <!--</section>-->
                 </main>
        </div>
    </div>
</body>
</html>