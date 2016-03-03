<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shenqing.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script src="<%=path%>/static/s/iscroll.js"></script>
</head>
<body>
<header class="xq_header">
    <a href="#" onclick="javascript:history.back(-1);"><img src="../images/xq_rt.png" alt=""></a>
    <p>申请合伙人</p>
</header>
<div class="kang">
    <p class="photo">
        <a href="../html/xiangqing.html">
            <img src="../images/shenqing_1.png" alt="">
        </a>
    </p>
    <div>
        <h2>抗引力——快速瘦脸精华</h2>
        <p>激活细胞内部胶原蛋白新生能量，抚平肌肤深层皱纹，增强弹性，
            焕醒肌肤年轻活力，重塑皮肤强劲支撑力。</p>
    </div>

</div>
<div class="he">
    <h1>合伙人特权</h1>
</div>
<main>
    <section class="sec1">
        <img src="../images/shenqing_3.png" alt="">
        <div>
            <h2>独立店铺</h2>
            <p>拥有自己的独立店铺进行推广装修等</p>
        </div>
    </section>
    <section class="sec1">
        <img src="../images/shenqing_4.png" alt="">
        <div>
            <h2>寻找合伙人</h2>
            <p>可以用采购价格购买商品，赚取利差</p>
        </div>
    </section>
    <section class="sec1">
        <img src="../images/shenqing_5.png" alt="">
        <div>
            <h2>推广渠道</h2>
            <p>平台提供多样的推广渠道编辑推广自己商品</p>
        </div>
    </section>
    <section class="sec1">
        <img src="../images/shenqing_6.png" alt="">
        <div>
            <h2>团队管理</h2>
            <p>提供完善的售后和团队管理工具</p>
        </div>
    </section>
    <section class="sec1">
        <img src="../images/shenqing_7.png" alt="">
        <div>
            <h2>平台补助</h2>
            <p style="margin-right:10px;">消费者分享商品可获得佣金，佣金来自于平台的补助</p>
        </div>
    </section>
    <section class="sec1 sec1_2">
        <div>
            <p>采购的商品可以选择厂家直接发货，或自己发货由厂家统一提供售后服务</p>
        </div>
    </section>
    <section class="sec2">
        <p><a href="../html/zhuce.html">继续</a></p>
    </section>
</main>
</body>
</html>