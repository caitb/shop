<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/common.css">
    <link rel="stylesheet" href="<%=path%>/static/css/dropload.css">
    <link rel="stylesheet" href="<%=path%>/static/css/tixianjilu.css">
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
            <p>提现申请</p>
        </header>
        <main>
            <div class="sec1" id="sec1">
                <p>提现记录：<label for="beginTime"><b>2016</b>年<b>1</b>月</label><input  id="beginTime" class="kbtn" style="display:none;"/></p>
                <div id="divall">
                    <c:forEach items="${exList}" var="ex">
                        <div>
                            <p><span class="sd">${ex.applyTimeView}</span><span>-${ex.extractFee}</span></p>
                            <h1><span>银行卡号</span><span>${ex.bankCardView}</span><b>${ex.auditTypeView}</b></h1>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>
    </div>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/date.js" ></script>
<script type="text/javascript" src="<%=path%>/static/js/iscroll.js" ></script>
<script src="<%=path%>/static/js/dropload.min.js"></script>
<script type="text/javascript">
    $(function(){
        $('#beginTime').date();
        $('#endTime').date({theme:"datetime"});

        $('main').dropload({
            scrollArea : window,

            domDown : {
                domClass   : 'dropload-down',
                domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
                domUpdate  : '<div class="dropload-update">↓释放加载</div>',
                domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
                domNoData  : '<div class="dropload-noData">没东西了</div>'
            },
            loadDownFn : function(me){
                $.ajax({
                    type: 'GET',
                    url: 'json/more.json',
                    dataType: 'json',
                    success: function(data){
                        alert(data);
                    },
                    error: function(xhr, type){
                        //alert('Ajax error!');
                    },
                    complete:function(){
                        me.resetload();
                    }
                });
            }
        });
    });
</script>
</body>
</html>
