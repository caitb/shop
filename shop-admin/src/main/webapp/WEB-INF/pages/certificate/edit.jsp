<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<table class="table">
   <tr>
       <td><label class="col-sm-3 control-label no-padding-right">合伙商品</label></td>
       <td>${certificateInfo.skuName}</td>
   </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">申请证书级别</label></td>
        <td>${certificateInfo.pfUserCertificateInfo.agentLevelId}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">申请人</label></td>
        <td>${certificateInfo.comUser.realName}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">手机号码</label></td>
        <td>${certificateInfo.pfUserCertificateInfo.mobile}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">微信号</label></td>
        <td>${certificateInfo.pfUserCertificateInfo.mobile}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">推荐人</label></td>
        <td>${certificateInfo.skuName} <span><a href="#">更改上级</a></span></td>

    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">申请时间</label></td>
        <td>${certificateInfo.pfUserCertificateInfo.beginTime}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">订单状态</label></td>
        <td>${certificateInfo.pfBorder.orderStatus}<span> <a href="#">查看订单</a></span></td>

    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">联系地址</label></td>
        <td>${certificateInfo.pfUserCertificateInfo.beginTime}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">身份证号</label></td>
        <td>${certificateInfo.comUser.idCard}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">身份证号照片</label></td>
        <td>${certificateInfo.comUser.idCardFrontUrl}</td>
        <td>${certificateInfo.comUser.idCardBackUrl}</td>
    </tr>
    <tr>
        <td> <a href="javascript:void(0)" class="button button-glow button-rounded button-raised button-primary" id="save">确认生成证书</a></td>
        <td> <a href="javascript:void(0)" class="button button-glow button-rounded button-raised detail-icon">拒绝</a></td>
    </tr>
</table>
<script>
    $('#save').on('click', function(){
        $.ajax({
            url: '<%=basePath%>user/add.do',
            type: 'post',
            data: $('#uForm').serialize(),
            success: function(data){
                alert(data);
            }
        });
    });
</script>