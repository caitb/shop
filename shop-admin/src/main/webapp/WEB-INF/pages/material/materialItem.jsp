<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">


    <title>透明层跟随鼠标移动的jQuery图片特效_酷站代码 www.5icool.org</title>
    <link href="<%=basePath%>static/css/material/materialItem.css" type="text/css" rel="stylesheet">
    <script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
</head>
<body>
<div class="boxBor"></div>
<c:forEach items="${pageMap.rows}" var="m">
    <div class="lanrenzhijia">
        <c:forEach items="${m.materialItems}" var="mi">
            <div class="box"><a href="javascript:void(0);"></a><img width="100" height="100" src="http://file.qc.masiis.com/static/material/${mi.fileUrl}" alt="" /></div>
        </c:forEach>
    </div>
</c:forEach>
</body></html>
