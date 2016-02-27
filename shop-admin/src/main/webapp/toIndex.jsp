<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=basePath%>static/class/jquery-easyui-1.4.4/themes/default/easyui.css" type="text/css" />

    <script type="text/javascript" src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>static/class/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
</head>
<body>
<%
    response.sendRedirect("/user/login.shtml");
%>
</body>
</html>
