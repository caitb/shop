<%--
  Created by IntelliJ IDEA.
  User: ZhaoLiang
  Date: 2016/2/18
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/js/easyui/themes/default/easyui.css">
    <script id="jquery_183" type="text/javascript" class="library" src="<%=basePath%>static/js/jquery.min.js"></script>
    <script id="others_jquery_easyui_131" type="text/javascript" class="library"
            src="<%=basePath%>static/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/ux/common_tree_panel.js"></script>
    <script>
        var treePanel;
        $(function () {
            //数据记录（是将数据库的记录数据直接转的json）
            treePanel = new CommonTreePanel({
                panelId: 'tree_panel',
                idKey: 'id',
                pidKey: 'parentId',
                textKey: 'name',
                hrefKey: 'url',
                nodeLinkBaseUrl: '/test',
                showRoot: false,
                checkbox: true,
                isAutoLink: false
            });
            treePanel.request({
                url: '/menu/getTreeData',
                initCheckedValuesStr: '${menuIds}'
            });
            $('#submit').bind("click", function () {
//                $('#res2').html(treePanel.getCheckedIdStringIncludeParent());
                $.ajax({
                    type: 'POST',
                    url: '/menu/save',
                    data: {userID: '${userID}', menuIDs: treePanel.getCheckedIdStringIncludeParent()},
                    dataType: 'json',
                    success: function (data) {
                        if (data) {
                            if (data.isError=="true") {
                                alert("保存失败");
                            } else {
                                alert("保存成功");
                            }
                        }
                    }
                });

            })
        });
    </script>
</head>
<body>
<div style="float:left;width:300px;">
    <div id="tree_panel"></div>
</div>
<div style="float:left;width:400px;">
    <%--1、不包含子节点未全选时的父节点--%>
    <%--<br/>--%>
    <%--<input type="button" value="获取勾选节点文本" onclick="$('#res1').html(treePanel.getCheckedTextArray().join(','))"/>--%>
    <%--<input type="button" value="获取勾选节点值" onclick="$('#res1').html(treePanel.getCheckedIdString())"/>--%>
    <%--<div id="res1"></div>--%>
    <%--<br/>--%>
    <%--<br/>--%>
    <%--2、包含子节点未全选时的父节点--%>
    <%--<br/>--%>
    <%--<input type="button" value="获取勾选节点values"--%>
           <%--onclick="$('#res2').html(treePanel.getCheckedIdStringIncludeParent())"/>--%>
    <div id="res2"></div>
    <br/>
    <input id="submit" type="button" value="提交"/>
</div>
</body>

