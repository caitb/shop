<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.masiis.shop.scheduler.task.mall.SfOrderTask" %>
<%@ page import="com.masiis.shop.scheduler.utils.ApplicationContextUtil" %><%--
  Created by IntelliJ IDEA.
  User: lzh
  Date: 2016/4/20
  Time: 16:27
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
    SfOrderTask task = (SfOrderTask) ac.getBean("sfOrderTask");
    task.unPaySfOrderCancelJob();
%>
</body>
</html>
