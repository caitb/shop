<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<table class="table">
    <input type="hidden" id="approveId" name ="id" value="${pfUserSku.id}"/>
   <tr>
       <td style="align-content: center"><label class="col-sm-3 control-label no-padding-right">代理编号:</label></td>
       <td>${pfUserSku.code}</td>
   </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">联系人姓名:</label></td>
        <td>${pfUserSku.comUser.realName}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">联系人手机号:</label></td>
        <td>${pfUserSku.comUser.mobile}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">联系人身份证:</label></td>
        <td>${pfUserSku.comUser.idCard}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">身份证扫描:</label></td>
        <td><a href="#" data-toggle="modal" data-target="#cardModal" onclick="card('+${pfUserSku.comUser.idCardFrontUrl}+')">正面</a>&nbsp;<a href="#" onclick="card('+${pfUserSku.comUser.idCardBackUrl}+')">反面</a></td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">合伙商品:</label></td>
        <td>${pfUserSku.skuName}</td>

    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">合伙人等级:</label></td>
        <td>${pfUserSku.comAgentLevel.name}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">上级合伙人:</label></td>
        <td>${pfUserSku.pRealName}</td>

    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">是否交代理款:</label></td>
        <td>
            <c:if test="${pfUserSku.isPay==0}" >
                未完成
            </c:if>
            <c:if test="${pfUserSku.isPay==1}"  >
                已完成
            </c:if>
        </td>

    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">授权证书:</label></td>
        <td><c:if test="${pfUserSku.isCertificate==0}" >
            未完成
        </c:if>
            <c:if test="${pfUserSku.isCertificate==1}"  >
                已完成
            </c:if>
        </td>
    </tr>
    <%--<tr>
        <td><label class="col-sm-3 control-label no-padding-right">地址管理</label></td>
        <td> <span><a href="javascript:void(0)" onclick="changeLeader('+certificateInfo.userId+')">点击查看</a></span></td>

    </tr>
    <tr>
        <td> <a href="javascript:void(0)" class="button button-glow button-rounded button-raised button-primary" id="update">确认生成证书</a></td>
        <td> <a href="javascript:void(0)" class="button button-glow button-rounded button-raised detail-icon" id="reject">拒绝</a></td>
    </tr>--%>
</table>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    更改上级合伙人
                </h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">用户</label>
                    <div class="col-sm-10">
                        王平
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">当前上级</label>
                    <div class="col-sm-10">
                        王平
                    </div>
                </div>
                <form class="form-horizontal" id="reasonForm" >
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right">更换上级</label>
                        <div class="col-sm-10">
                            <input type="hidden" id="trialId" name="id" />
                            <select class="form-control">
                                <option>1</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="btnSubmit">
                    提交
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="cardModal" tabindex="-1" role="dialog"
     aria-labelledby="orderModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="orderModalLabel">
                    身份证复印件
                </h4>
            </div>
            <div class="modal-body" >
                <div id="bodyimg"></div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="Submit">
                    提交
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->

</div>
<div>
    <script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table.min.js"></script>

    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/ga.js"></script>
    <!-- Latest compiled and minified Locales -->
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-zh-CN.min.js"></script>
</div>
<script>
    //身份证
    function card(url){
        $("#bodyimg").html('<img src="url" />');
        $("#cardModal").show;
    }
    //更改上级
    function changeLeader(){
        var approveId = $("#approveId").val();
        $.ajax({
            url: '<%=basePath%>certificate/listUpper.do',
            data: {id: approveId},
            success: function (data) {
                $('#myModal').modal({
                    show: true,
                    backdrop: true,
                    upperList:data
                });
            }
        });

    }
    //订单列表
    function orderList(id){
        $('#trialId').val(id);
        $('#orderModal').modal({
            show: true,
            backdrop: true
        });
    }

    $('#btnSubmit').on('click', function () {
        var data = $('#reasonForm').serialize();
        $.ajax({
            url: '<%=basePath%>trial/reason.do',
            type: 'post',
            data: data,
            success: function (data) {
                //alert(data);
                $('#myModal').modal('hide');
            }
        });
        location.reload();
    })
</script>
</body>

</html>