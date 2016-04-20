<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.masiis.shop.scheduler.utils.ApplicationContextUtil" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.masiis.shop.scheduler.task.platform.PfOrderTask" %><%--
  Created by IntelliJ IDEA.
  User: lzh
  Date: 2016/4/18
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    ApplicationContext ac = ApplicationContextUtil.getContext();
    PfOrderTask task = (PfOrderTask) ac.getBean("pfOrderTask");
    task.billCountJob();
%>
</body>
</html>
