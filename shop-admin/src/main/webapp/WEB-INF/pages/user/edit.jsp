<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<form class="form-horizontal" role="form">

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="userName">用户名</label>

        <div class="col-sm-9">
            <input type="text" id="userName" name="userName" value="${pbUser.userName}" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="space-4"></div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="trueName">用户名</label>

        <div class="col-sm-9">
            <input type="text" id="trueName" name="trueName" value="${pbUser.trueName}" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="space-4"></div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="password">密码</label>

        <div class="col-sm-9">
            <input type="password" id="password" name="password" value="${pbUser.password}" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="space-4"></div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="email">邮箱</label>

        <div class="col-sm-9">
            <input type="text" id="email" name="email" value="${pbUser.email}" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="space-4"></div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="sex">性别</label>

        <div class="col-sm-9">
            <input type="text" id="sex" name="sex" value="${pbUser.sex}" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="space-4"></div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="age">年龄</label>

        <div class="col-sm-9">
            <input type="text" id="age" name="age" value="${pbUser.age}" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="space-4"></div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="phone">电话</label>

        <div class="col-sm-9">
            <input type="text" id="phone" name="phone" value="${pbUser.phone}" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="space-4"></div>

</form>