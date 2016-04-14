<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<%-- 公用变量,用${}来获取 --%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${path}/" />
<link rel="stylesheet" href="${path}/static/css/base.css">
<link rel="stylesheet" href="${path}/static/css/reset.css">
<link rel="stylesheet" href="${path}/static/css/header.css">
<link rel="stylesheet" href="${path}/static/css/loading.css"/>