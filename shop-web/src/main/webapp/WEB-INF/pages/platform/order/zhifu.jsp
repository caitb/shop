<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/zhifu.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
</head>
<body>
   <div class="wrap">
       <div class="box">
            <header class="xq_header">
                   <a href="zhuce2.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                    <p>支付订单</p>            
            </header>
            <main>
                <div class="drap">
                    <h2><img src="<%=path%>/static/images/yes.png" alt=""></h2>
                    <p>您已经成功注册麦链合伙人，需要您完成订单支付</p>
                </div>
                <div class="xinxi">
                    <p style="color:#999999">1.注册信息</p>
                    <p>2.支付订单</p>
                    <p>3.提交资料</p>
                </div>
                <div class="xinz">
                    <p><a href="<%=path%>/static/html/guanli.html">新增收货地址</a></p>
                </div>
                <section class="sec1">

                   <img src="<%=path%>/static/images/zhifu_ad.png" alt="">
                   <div>
                        <a href="#"><h2>收货人：<b>王平</b> <span>18611536163</span></h2></a>
                        <a href="#"><p>收货地址： <span>北京市 朝阳区 丰联广场A座809A</span><img src="../images/next.png" alt=""></p></a>
                    </div>

                </section>
                <section class="sec2">
                    <p class="photo">
                            <img src="<%=path%>/static/images/shenqing_1.png" alt="">
                    </p>
                    <div>
                        <h2>抗引力——快速瘦脸精华</h2>
                        <h3></h3>
                        <p><span>￥298</span><b style="float:right; margin-right:10px;font-size:12px;">x1</b></p>
                    </div>
                </section>
                <section class="sec3">
                    <p>运费<span>到付</span></p>
                    <h1>共<b>800</b>件商品　运费：<span>￥300</span><b>　合计：</b><span style="padding-right: 10px;">￥15500.00</span></h1>
                    <p>留言：<input type="text"></p>
                </section>

                <section class="sec4">
                    <p><b>合计：</b><span>￥3200</span></p>
                    <p><b>运费：</b><b style="text-align:left;text-indent:2px;">到付</b></p>
                    <p><b>需付：</b><span>￥2508.00</span></p>
                </section>

                <a href="<%=path%>/static/html/lingquzhengshu.html" class="weixin">微信支付</a>
                <a href="javascript:;" class="xianxia">线下支付</a>
            </main>
        </div>
    </div>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script>
        var myScroll = new IScroll(".wrap",{
                 preventDefault: false
            })
    </script>
</body>
</html>