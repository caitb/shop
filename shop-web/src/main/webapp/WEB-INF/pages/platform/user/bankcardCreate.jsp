<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/xinjianka.css">
</head>
<body>
<input type="hidden" id="userId" name="userId" value="${userId}"/>
<div class="wrap">
    <header class="xq_header">
        <a href="<%=basePath%>extractwayinfo/findByUserId.do"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
        <p>添加银行卡</p>
    </header>
    <main>
        <p>新增银行卡信息</p>
        <h1>银行卡号：<input type="text" id="bankcard" placeholder="填写您的卡号"></h1>
        <h1>银行名称：<select id="bankid" name="bankid">
            <option value="">请选择银行</option>
            <c:forEach var="bank" items="${bankList}">
                <option value="${bank.id}">${bank.bankName}</option>
            </c:forEach>
        </select></h1>
        <h1>开户行名称 ：<input type="text" id="depositbankname" placeholder="输入您的开户行名称"></h1>
        <h1>持卡人姓名：<input type="text" id="cardownername" placeholder="输入持卡人姓名"></h1>
        <botton onclick="submitClick()">
            提交
        </botton>
    </main>
</div>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script>
    function submitClick() {
        var bankcard = $("#bankcard").val();
        var bankid = $("#bankid").val();
        var depositbankname = $("#depositbankname").val();
        var cardownername = $("#cardownername").val();
        if (bankcard == "") {
            alert("请输入银行卡号");
            return;
        }
        if(bankid == ""){
            alert("请选择银行名称");
            return;
        }
        if (depositbankname == "") {
            alert("请输入开户行名称");
            return;
        }
        if (cardownername == "") {
            alert("请输入持卡人姓名");
            return;
        }
        $.ajax({
            type:"POST",
            async:false,
            url : "<%=path%>/extractwayinfo/add.do",
            data:{bankcard:bankcard,bankid:bankid,depositbankname:depositbankname,cardownername:cardownername},
            dataType:"Json",
            beforeSend:function(){

            },
            success:function(data){
                if(data.isTrue == "false"){
                    alert(data.message);
                }else {
                    window.location.href="<%=basePath%>extractwayinfo/findByUserId.do";
                }
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
//                alert(XMLHttpRequest.responseText);
//                alert(textStatus);
            },
            error: function(){
                //请求出错处理
                alert("请求出错，请稍后再试");
            }
        });
    }
</script>
</body>
</html>