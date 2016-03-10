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
        <td>${certificateInfo.skuName} <span><a href="javascript:void(0)" onclick="changeLeader('+certificateInfo.userId+')">更改上级</a></span></td>

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
        <td> <a href="javascript:void(0)" class="button button-glow button-rounded button-raised button-primary" id="update">确认生成证书</a></td>
        <td> <a href="javascript:void(0)" class="button button-glow button-rounded button-raised detail-icon">拒绝</a></td>
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
                    请填写拒绝理由:
                </h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="reasonForm" >
                    <div class="form-group">
                        <div class="col-sm-10">
                            <input type="hidden" id="trialId" name="id" />
                            <textarea class="form-control" cols="5" rows="10" name="remark" placeholder="理由"></textarea>
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
<script>
    $('#update').on('click', function(){
        $.ajax({
            url: '<%=basePath%>certificate/update.do',
            type: 'post',
            data: {id: certificateInfo.id},
            success: function(data){
                alert(data);
            }
        });
    });

    function changeLeader(id){
        $('#trialId').val(id);
        $('#myModal').modal({
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