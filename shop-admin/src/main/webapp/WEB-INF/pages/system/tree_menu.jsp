<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<ul id="treeMenu" class="ztree"></ul>

<link rel="stylesheet" href="<%=basePath%>static/class/zTree/css/zTreeStyle/zTreeStyle.css" />
<script src="<%=basePath%>static/class/zTree/js/jquery.ztree.all-3.5.js"></script>
<script>
    var pbMenus = window.eval('(' + '${pbMenusJson}' + ')');
    var pbUserMenus = window.eval('(' + '${pbUserMenusJson}' + ')');
    var userId = ${userId};
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: 0
            }
        },
        check: {
            enable: true,
            chkStyle: 'checkbox',
            chkboxType: { "Y": "ps", "N": "ps" }
        }
    };

    for(var i in pbMenus){
        for(var j in pbUserMenus){
            if(pbMenus[i].id == pbUserMenus[j].pbMenuId) pbMenus[i].checked = true;
        }
    }


    $.fn.zTree.init($("#treeMenu"), setting, pbMenus);

</script>
