<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- 公用变量,用${}来获取 --%>
<c:set var="path" value="${request.contextPath}" />
<c:set var="basepath" value="${request.scheme}://${request.serverName}:${request.serverPort()}${path}/" />