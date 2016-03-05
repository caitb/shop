<%--
  Created by IntelliJ IDEA.
  User: muchaofeng
  Date: 2016/3/4
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
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
        <main>
            <section class="sec1">
                <c:forEach items="${indexComSkus}" var="indexComSkus">
                <div>
                    <a href="/product/${indexComSkus.id}"><img src="${indexComSkus.imgUrl}" alt=""></a>
                    <h2>${indexComSkus.comSku.name}</h2>
                    <h1>￥${indexComSkus.comSku.priceRetail} <span>${indexComSkus.discountLevel}</span></h1>
                    <h3>
                        <p>超过<span>${indexComSkus.agentNum}</span>人代理</p>
                        <a href="shenqing.html" class="he">我要合伙</a>
                        <a href="javascript:;" class="ok">您已合伙</a>
                    </h3>
                </div>
                </c:forEach>
                <%--<div>--%>
                    <%--<a href="xiangqing.html"><img src="../images/cp_1.png" alt=""></a>--%>
                    <%--<h2>抗引力—快速瘦脸精华</h2>--%>
                    <%--<h1>￥328 <span>成为合伙人可查看</span></h1>--%>
                    <%--<h3>--%>
                        <%--<p>超过<span>9999</span>人代理</p>--%>
                        <%--<a href="shenqing.html" class="he">我要合伙</a>--%>
                        <%--<a href="javascript:;" class="ok">您已合伙</a>--%>
                    <%--</h3>--%>
                <%--</div>--%>
            </section>
            <%--<section class="sec1">--%>
                <%--<div>--%>
                    <%--<a href="xiangqing.html"><img src="../images/cp_1.png" alt=""></a>--%>
                    <%--<h2>抗引力—快速瘦脸精华</h2>--%>
                    <%--<h1>￥328 <span>成为合伙人可查看</span></h1>--%>
                    <%--<h3>--%>
                        <%--<p>超过<span>9999</span>人代理</p>--%>
                        <%--<a href="shenqing.html" class="he">我要合伙</a>--%>
                        <%--<a href="javascript:;" class="ok">您已合伙</a>--%>
                    <%--</h3>--%>
                <%--</div>--%>
                <%--<div>--%>
                    <%--<a href="xiangqing.html"><img src="../images/cp_1.png" alt=""></a>--%>
                    <%--<h2>抗引力—快速瘦脸精华</h2>--%>
                    <%--<h1>￥328 <span>成为合伙人可查看</span></h1>--%>
                    <%--<h3>--%>
                        <%--<p>超过<span>9999</span>人代理</p>--%>
                        <%--<a href="shenqing.html" class="he">我要合伙</a>--%>
                        <%--<a href="javascript:;" class="ok">您已合伙</a>--%>
                    <%--</h3>--%>
                <%--</div>--%>
            <%--</section>--%>
        </main>
    </div>
</div>
</body>
</html>
