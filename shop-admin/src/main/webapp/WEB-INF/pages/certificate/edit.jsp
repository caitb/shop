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
    <link href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-table.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-editable.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/buttons.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/examples.css">
    <script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table.min.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/ga.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-zh-CN.min.js"></script>
</head>
<body>
<table class="table">
    <input type="hidden" id="approveId" name ="id" value="${certificateInfo.id}"/>
    <tr>
        <td width="50%"><label class="col-sm-3 control-label no-padding-right">合伙商品</label></td>
        <td colspan="2">${certificateInfo.skuName}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">申请证书级别</label></td>
        <td colspan="2">${certificateInfo.agentLevelId}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">申请人</label></td>
        <td colspan="2">${certificateInfo.comUser.realName}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">手机号码</label></td>
        <td colspan="2">${certificateInfo.pfUserCertificateInfo.mobile}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">微信号</label></td>
        <td colspan="2">${certificateInfo.pfUserCertificateInfo.wxId}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">推荐人</label></td>
        <td colspan="2">${certificateInfo.upperName} <span><a href="javascript:void(0)" onclick="changeLeader()">更改上级</a></span></td>

    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">申请时间</label></td>
        <td colspan="2">${certificateInfo.beginTime}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">订单状态</label></td>
        <td colspan="2"><c:choose>
            <c:when test="${certificateInfo.pfBorder.payStatus==0}">
                未付款
            </c:when>
            <c:otherwise>
                已付款
            </c:otherwise>
        </c:choose></td>

    </tr>
    <%--<tr>--%>
    <%--<td><label class="col-sm-3 control-label no-padding-right">联系地址</label></td>--%>
    <%--<td>${certificateInfo.pfUserCertificateInfo.beginTime}</td>--%>
    <%--</tr>--%>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">身份证号</label></td>
        <td colspan="2">${certificateInfo.comUser.idCard}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">身份证号照片</label></td>
        <td><img src="${certificateInfo.comUser.idCardFrontUrl}" alt=""></td>
        <td><img src="${certificateInfo.comUser.idCardBackUrl}" alt=""></td>
    </tr>
    <tr>
        <td><c:choose>
            <c:when test="${certificateInfo.pfUserCertificateInfo.status==0}">
                <a href="javascript:void(0)" class="button button-glow button-rounded button-raised button-primary" id="update">确认生成证书</a>
            </c:when>
            <c:otherwise>
                <a href="javascript:void(0)"class="button button-glow button-rounded button-raised button-primary" aria-disabled="true">已经审核</a>
            </c:otherwise>
        </c:choose>
        </td>
        <td>
            <c:choose>
            <c:when test="${certificateInfo.pfUserCertificateInfo.status==0}">
            <a href="javascript:void(0)" class="button button-glow button-rounded button-raised detail-icon" id="reject">拒绝</a></td>
        </c:when>
            <%--<c:otherwise>--%>
            <%--<a href="javascript:void(0)" class="btn btn-primary" aria-disabled="true">拒绝</a></td>--%>
            <%--</c:otherwise>--%>
        </c:choose>
    </tr>
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
                    <div class="col-sm-11">
                        <label class="col-sm-3 control-label no-padding-right" id="userInfo"></label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-11">
                        <label class="col-sm-4 control-label no-padding-right" id="upperName"></label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <label class="col-sm-12 control-label no-padding-right" id="skuName"></label>
                    </div>
                </div>
                <form class="form-horizontal" id="reasonForm" >
                    <div class="form-group">
                        <label class="col-sm-2 control-label">更换上级:</label>
                        <div class="controls col-sm-3">
                            <select class="form-control" id="userList">
                                <option>请选择</option>
                            </select>
                        </div>
                    </div>
                    <input name="id" type="hidden" id="userSkuId"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary" id="userSubmit">
                            提交
                        </button>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 拒绝理由 -->
<div class="modal fade" id="rejectModal" tabindex="-1" role="dialog"
     aria-labelledby="rejectModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="rejectModalLabel">
                    填写拒绝理由
                </h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="rejectForm" >
                    <div class="form-group">
                        <label class="col-sm-2 control-label">拒绝理由:</label>
                        <div class="controls col-sm-3">
                            <textarea class="form-control" rows="3" style="width: 396px; height: 99px;" id="reason"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary" id="rejectSubmit">
                            提交
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
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
    //批准
    $('#update').on('click', function(){
        var approveId = $("#approveId").val();
        $.ajax({
            url: '<%=basePath%>certificate/update.do',
            data: {status: 1, id: approveId},
            success: function(data){
                alert(data);
            }
        });
    });
    // 拒绝
    $('#rejectSubmit').on('click', function () {
        var approveId = $("#approveId").val();
        var reason = $("#reason").val();
        if (reason == null || reason == "") {
            alert("请填写拒绝理由");
        } else {
            var reasonCode = encodeURI(reason);
            $.ajax({
                url: '<%=basePath%>certificate/update.do',
                data: {
                    status: 2,
                    id: approveId,
                    reason: reasonCode
                },
                success: function (data) {
                    $('#rejectModal').modal("hide");
                    alert(data);
                    location.reload(true);
                }
            });
        }

    });
    //拒绝理由
    $('#reject').on('click', function () {
        $('#rejectModal').modal({
            show: true,
            backdrop: true
        });
    });
    //更改上级
    function changeLeader() {
        var approveId = $("#approveId").val();
        $.ajax({
            type: "GET",
            url: '<%=basePath%>certificate/listUpper.do',
            data: {id: approveId},
            dataType: "json",
            success: function (data) {
                $("#userInfo").html("用户 : " + data["certificateInfo"].comUser.realName);
                $("#skuName").html("合伙商品 :  " + data["certificateInfo"].skuName);
                $("#userSkuId").val(data["certificateInfo"].id);
                //option属性
                if (data["certificateInfo"].comUserList != null && data["certificateInfo"].comUserList[0] != null) {
                    $("#upperName").html("当前上级 :  " + data["certificateInfo"].upperName);
                    var comUserList = {upperList: data["certificateInfo"].comUserList};
                    $("#userList").val(comUserList);
                    $.each(data["certificateInfo"].comUserList, function (index, value) {
                        $('#userList').append("<option value='" + value.id + "'>" + value.realName + "</option>");
                    });
                } else {
                    $("#upperName").html("当前上级 :  -");
                    $("#userSubmit").attr("disabled", true);
                }
                $('#myModal').modal({
                    show: true,
                    backdrop: true
                });
            }
        });
    }
    $('#userSubmit').on('click', function () {
        var id = $('#userSkuId').val();
        var pid = $("#userList").val();
        $.ajax({
            url: '<%=basePath%>certificate/updateUpper.do',
            type: 'post',
            data: {id: id, pid: pid},
            success: function (data) {
                $('#myModal').modal('hide');
                alert(data);
            }
        });
    });
</script>
</body>
</html>
