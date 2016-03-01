<%--
  Created by IntelliJ IDEA.
  User: muchaofeng
  Date: 2016/3/1
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%-- 提交申请 --%>
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
    <link rel="stylesheet" href="<%=path%>/static/css/tijiaosq.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>

</head>
<body>
<header class="xq_header">
    <a href="<%=path%>/pages/platform/index.jsp"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>授权书申请 </p>
</header>
<p class="cp" style="margin-top:10px;">
    合伙产品：<span>抗引力—瘦脸精华</span>
</p>
<p class="cp" style="margin-bottom:15px">姓名：<span>王平</span></p>
<div class="sf">
    身份证号：
    <input type="text">
</div>
<div class="address">
    联系地址：
    <select id="s_province" name="s_province"></select>
    <select id="s_city" name="s_city" ></select>
    <select id="s_county" name="s_county"></select>
</div>
<div class="sf" style="margin-top:15px;">
    请输入详细地址
    <input type="text">
</div>
<div class="sf" style="margin-bottom:0;">
    手机号：
    <input type="text">
</div>
<p class="zhushi">
    *此地址将作为您的店铺发货地址
</p>
<p>
    身份证照片
</p>
<div class="sfphoto"></div>


<script class="resources library" src="<%=path%>/static/js/area.js" type="text/javascript"></script>
<script type="text/javascript">_init_area();</script>
<script type="text/javascript">

    var showArea = function(){
        $('show').innerHTML = "<h3>省" + Gid('s_province').value + " - 市" +
                $('s_city').value + " - 县/区" +
                $('s_county').value + "</h3>"
    }
    $('s_county').setAttribute('onchange','showArea()');
</script>
</body>
</html>
