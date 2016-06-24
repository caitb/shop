<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<%-- 公用变量,用${}来获取 --%>
<c:set var="path" value="<%=path%>" />
<c:set var="basepath" value="<%=basePath%>" />
<link rel="stylesheet" href="${path}/static/css/pageCss/base.css">
<link rel="stylesheet" href="${path}/static/css/pageCss/reset.css">
<link rel="stylesheet" href="${path}/static/css/pageCss/header.css">
<link rel="stylesheet" href="${path}/static/css/devCss/loading.css">