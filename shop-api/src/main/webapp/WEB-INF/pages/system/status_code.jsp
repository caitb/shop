<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>
</head>
<body>
<center><span>shop-api请求返回码说明</span></center>
<br/>
<br/>
<table align="center" style="border: 1px solid black" border="1">
    <c:forEach items="${list}" var="s">
        <tr>
            <td>${s}</td>
            <td>${map[s]}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
