<%--
  Created by IntelliJ IDEA.
  User: muchaofeng
  Date: 2016/3/1
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%-- 注册 第一步 --%>
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
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zhuce.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>

<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="<%=path%>/pages/platform/ptnerQuote.jsp"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>申请合伙人</p>
        </header>
        <div class="xinxi">
            <p>1.注册信息</p>
            <p>2.支付订单</p>
            <p>3.支付订单</p>
        </div>
        <p class="xuanze">
            选择商品：<span>抗引力—快速瘦脸精华</span>
        </p>
        <main>
            <section class="sec1">
                <div>
                    <p>申请人信息： 这是您在麦链商城的登陆账户</p>
                </div>
                <div>
                    <p>姓名： <input type="text"></p>
                </div>

                <div>
                    <p>手机号： <input type="text"></p>
                </div>
                <div>
                    <p>验证码： <input type="text"><span>获取验证码</span></p>
                </div>

                <div>
                    <p>微信号：<input type="text"></p>
                </div>
                <div>
                    <p>上级合伙人电话： <input type="text"></p>
                </div>
            </section>
            <section class="sec2">
                <h2>选择合伙人等级</h2>
                <p>
                    高级合伙人<span>商品数量： 800  金额： 15000</span>
                </p>
                <p>
                    中级合伙人<span>商品数量： 600  金额： 5000</span>
                </p>
                <p>
                    初级合伙人<span>商品数量： 100  金额： 1000</span>
                </p>
            </section>
            <section class="sec3">
                <p>
                    <input type="checkbox" id="fu" checked>
                    <label for="fu">我已同意《代理商注册协议》</label>
                </p>
            </section>
            <section class="sec4">
                <a href="<%=path%>/pages/platform/Registration-T.jsp">下一步</a>
            </section>
        </main>
    </div>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script>
    $(".sec2 p").on("click",function(){
        $(".sec2 p").removeClass("on")
        $(this).addClass("on");
    })
    var myScroll = new IScroll(".wrap",{

        preventDefault: false
    })
</script>
</body>
</html>
