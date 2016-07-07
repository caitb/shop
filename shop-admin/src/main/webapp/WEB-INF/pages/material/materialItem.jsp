<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>透明层跟随鼠标移动的jQuery图片特效_酷站代码 www.5icool.org</title>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/uncompressed/ace.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/dropzone.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/uncompressed/my-dropzone.css"/>


    <!-- text fonts -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-fonts.css"/>

    <!-- ace styles -->
    <%--<link rel="stylesheet" href="<%=basePath%>static/ace2/css/uncompressed/ace.css" id="main-ace-style" />--%>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-part2.min.css"/>
    <![endif]-->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-skins.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/jquery.gritter.css"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-ie.min.css"/>
    <![endif]-->
    <link href="<%=basePath%>static/css/material/materialItem.css" type="text/css" rel="stylesheet">
    <script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
</head>
<body>
<c:forEach items="${pageMap.rows}" var="m">
    <div class="img-container">
        <h5 style="width: 100%; text-align: center">${m.title}</h5>
    <div class="boxBor"></div>
    <c:forEach items="${m.materialItems}" var="mi">
    <div class="lanrenzhijia">
        <div class="box"><a href="javascript:void(0);"></a><img width="100" height="100" src="http://file.qc.masiis.com/static/material/${mi.fileUrl}" alt="" /></div>
    </div>
    </c:forEach>
        <div class="btn-container">
            <button class="btn btn-app btn-danger btn-xs btn btn-xs deleteMaterial" type="button" mId="${m.id}">
                删除
            </button>
        </div>
    </div>
</c:forEach>
</body>
<script>
    $('.deleteMaterial').on('click', function(){
        $.ajax({
            url: '<%=basePath%>material/deleteMaterial.do',
            data: {mId: $(this).attr('mId')},
            success: function(result){
                result = window.eval('('+result+')');

                if(result.code == 'success'){
                    window.location.replace(window.location.href);
                }
            },
            error: function(result){
                alert(result);
            }
        })
    });
</script>
</html>
