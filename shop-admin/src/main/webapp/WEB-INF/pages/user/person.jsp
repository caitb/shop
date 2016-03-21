<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<table class="table">
    <input type="hidden" id="approveId" name ="id" value="${comUser.id}"/>
   <tr>
       <td><label class="col-sm-3 control-label no-padding-right">联系人姓名</label></td>
       <td>${comUser.realName}</td>
   </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">联系人手机号</label></td>
        <td>${comUser.mobile}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">微信号</label></td>
        <td>${comUser.wxId}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">联系人身份证</label></td>
        <td>${comUser.idCard}</td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">身份证扫描</label></td>
        <td><a href="#" data-toggle="modal" data-target="#cardModal" onclick="card('${comUser.idCardFrontUrl}')">正面</a>&nbsp;
            <a href="#" data-toggle="modal" data-target="#cardModal" onclick="card('${comUser.idCardBackUrl}')">反面</a></td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">账户余额</label></td>
        <td><!--？？？--></td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">结算中资金</label></td>
        <td><!--？？？--></td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">注册日期</label></td>
        <td> ${date} </td>
    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">分销记录</label></td>
        <td> <span><a href="javascript:void(0)" onclick="changeLeader('+certificateInfo.userId+')">点击查看</a></span></td>

    </tr>
    <tr>
        <td><label class="col-sm-3 control-label no-padding-right">地址管理</label></td>
        <td> <span><a href="javascript:void(0)" onclick="changeLeader('+certificateInfo.userId+')">点击查看</a></span></td>

    </tr>
    <%--<tr>
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


<!-- 身份证复印件 -->
<div class="modal fade" id="cardModal" tabindex="-1" role="dialog"
     aria-labelledby="orderModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: auto">
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
                <%-- <button type="button" class="btn btn-primary" id="Submit">
                     提交
                 </button>--%>
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
        //alert(url)
        $("#bodyimg").html('<img src="' + url + '" />');
        $("#cardModal").show;
    }


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