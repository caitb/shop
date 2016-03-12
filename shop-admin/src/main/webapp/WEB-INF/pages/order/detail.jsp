<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>麦士商城 - 后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- Latest compiled and minified CSS -->
    <link href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-table.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-editable.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/buttons.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/examples.css">


    <script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table.min.js"></script>

    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/ga.js"></script>
    <!-- Latest compiled and minified Locales -->
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-zh-CN.min.js"></script>
    <script src="<%=basePath%>static/js/date-util.js"></script>

</head>
<body>
    <div class="container">

        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>Subheading</h4>
            </div>

            <div class="col-lg-3">
                <p>Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
            </div>
            <div class="col-lg-3"></div>
        </div>

        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>Subheading</h4>
            </div>

            <div class="col-lg-3">
                <p>Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
            </div>
            <div class="col-lg-3"></div>
        </div>

        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>Subheading</h4>
            </div>

            <div class="col-lg-3">
                <p>Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
            </div>
            <div class="col-lg-3"></div>
        </div>

        <div class="row marketing">
            <div class="col-lg-3"></div>
            <div class="col-lg-3">
                <h4>Subheading</h4>
            </div>

            <div class="col-lg-3">
                <p>Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
            </div>
            <div class="col-lg-3"></div>
        </div>

    </div> <!-- /container -->
</body>
</html>
