<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<table class="table">
    <input type="hidden" id="approveId" name ="id" value="${certificateInfo.id}"/>
   <tr>
       <td width="50%"><label class="col-sm-3 control-label no-padding-right">合伙商品</label></td>
       <td colspan="2">${certificateInfo.skuName}</td>
   </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">申请证书级别</label></td>
        <td colspan="2">${certificateInfo.pfUserCertificateInfo.agentLevelId}</td>
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
        <td colspan="2">${certificateInfo.pfUserCertificateInfo.mobile}</td>
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
        <td colspan="2">${certificateInfo.pfBorder.orderStatus}<span> <a href="javascript:void(0)" onclick="orderList('+certificateInfo.userId+')">查看订单</a></span></td>

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
                    <label class="col-sm-3 control-label no-padding-right">用户</label>
                    <div class="col-sm-10">
                        <%--${upperList.comUser.realName}--%>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right">当前上级</label>
                    <div class="col-sm-10">
                        <%--${upperList.upperName}--%>
                    </div>
                </div>
                <form class="form-horizontal" id="reasonForm" >
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right">更换上级</label>
                        <div class="col-sm-10">
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

<%--<div class="modal fade" id="orderModal" tabindex="-1" role="dialog"--%>
     <%--aria-labelledby="orderModalLabel" aria-hidden="true">--%>
    <%--<div class="modal-dialog">--%>
        <%--<div class="modal-content">--%>
            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close"--%>
                        <%--data-dismiss="modal" aria-hidden="true">--%>
                    <%--&times;--%>
                <%--</button>--%>
                <%--<h4 class="modal-title" id="orderModalLabel">--%>
                    <%--订单详情--%>
                <%--</h4>--%>
            <%--</div>--%>
            <%--<div class="modal-body">--%>
            <%--</div>--%>
            <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-default"--%>
                        <%--data-dismiss="modal">关闭--%>
                <%--</button>--%>
                <%--<button type="button" class="btn btn-primary" id="Submit">--%>
                    <%--提交--%>
                <%--</button>--%>
            <%--</div>--%>
        <%--</div><!-- /.modal-content -->--%>
    <%--</div><!-- /.modal -->--%>

<%--</div>--%>
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
    $('#reject').on('click', function(){
        var approveId = $("#approveId").val();
        $.ajax({
            url: '<%=basePath%>certificate/update.do',
            data: {status: 2, id: approveId},
            success: function(data){
                alert(data);
            }
        });
    });
    //更改上级
    function changeLeader(){
        var approveId = $("#approveId").val();
        $.ajax({
            type: "GET",
            url: '<%=basePath%>certificate/listUpper.do',
            data: {id: approveId},
            dataType: "json",
            success: function (data) {
                alert(data["certificateInfo"]);
                $('#myModal').modal({
                    show: true,
                    backdrop: true
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

    <%--$('#btnSubmit').on('click', function () {--%>
        <%--var data = $('#reasonForm').serialize();--%>
        <%--$.ajax({--%>
            <%--url: '<%=basePath%>trial/reason.do',--%>
            <%--type: 'post',--%>
            <%--data: data,--%>
            <%--success: function (data) {--%>
                <%--//alert(data);--%>
                <%--$('#myModal').modal('hide');--%>
            <%--}--%>
        <%--});--%>
        <%--location.reload();--%>
    <%--})--%>
</script>