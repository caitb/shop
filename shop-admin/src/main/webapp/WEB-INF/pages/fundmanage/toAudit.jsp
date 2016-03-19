<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


    <style>
        * {
            box-sizing: border-box;
        }
        .box {
            /*background-color: #fafafa;*/
            color: #666;
            margin-bottom: 20px;
            padding: 5px;
            border-radius: 4px;
            /*border: 1px solid #e1e8ed;*/
        }
        .box.-radius-all {
            border-radius: 4px;
        }
        .meta-attributes {
            color: #999;
        }
        table {
            border-collapse: collapse;
            border-spacing: 0;
            margin: 0 auto;
            width: 500px;
        }
        .meta-attributes tr td {
            font-size: 14px;
            padding-bottom: 15px;
            vertical-align: top;
        }
        .meta-attributes__attr-name {
            color: #454545;
            font-weight: bold;
            padding-right: 10px;
            width: 140px;
        }

        .my-table th, .my-table td {
            text-align: center;
            height:38px;
        }
    </style>
<div class="box -radius-all">
    <div class="meta-attributes">
        <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
            <tbody>

            <tr>
                <td class="meta-attributes__attr-name">订单号</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfCorder.orderCode}
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">下单日期</td>
                <td class="meta-attributes__attr-detail">
                    <fmt:formatDate value="${order.pfCorder.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">支付日期</td>
                <td class="meta-attributes__attr-detail">
                    <fmt:formatDate value="${order.pfCorder.payTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">支付方式</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfCorderPayment.payTypeName}
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">物流状态</td>
                <td class="meta-attributes__attr-detail">
                    <c:if test="${order.pfCorder.shipStatus == 0}">
                        <span id="shiSta">未发货</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-info" id="fahuo" data-toggle="collapse" >
                            发货
                        </button>
                        <div id="delivery" class="collapse" aria-expanded="false" style="height: 0px;">
                            <form class="form-horizontal" id="deliForm" action="<%=basePath%>order/corder/delivery.do">
                                <div class="form-group" style="margin-top: 10px; margin-left: -81px;">
                                    <label for="shipName" class="col-sm-4 control-label">快递名称</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="shipName" name="shipManId">
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" style="margin-left: -81px;">
                                    <label for="freight" class="col-sm-4 control-label">快递单号</label>
                                    <div class="col-sm-4">
                                        <input type="hidden" name="pfCorderId" value="${order.pfCorder.id}" />
                                        <input type="hidden" id="shipManName" name="shipManName" value="" />
                                        <input type="text" class="form-control" id="freight" name="freight" placeholder="快递单号">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2"></label>
                                    <div class="col-sm-6">
                                        <button type="submit" class="btn btn-info" id="skuSave">确定</button>
                                        <button type="button" class="btn">取消</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <script>
                            $('#fahuo').on('click', function(){
                                $.ajax({
                                    url: '<%=basePath%>comshipman/list.do',
                                    success: function(data){
                                        data = window.eval('(' + data + ')');
                                        var sOptions = '<option>请选择</option>';
                                        for(var i in data){
                                            sOptions += '<option value="' + data[i].id + '">' + data[i].name + '</option>'
                                        }
                                        $('#shipName').html(sOptions);
                                        $('#delivery').collapse('show');
                                    }
                                });
                            });

                            $('#shipName').change(function(){
                                $('#shipManName').val($(this).find('option[value="'+$(this).val()+'"]').html());
                            });

                            $(document).ready(function() {
                                $('#deliForm').bootstrapValidator({
                                            message: '必须填写',
                                            feedbackIcons: {
                                                valid: 'glyphicon glyphicon-ok',
                                                invalid: 'glyphicon glyphicon-remove',
                                                validating: 'glyphicon glyphicon-refresh'
                                            },
                                            fields: {
                                                shipManId: {
                                                    message: '请选择一个快递!',
                                                    validators: {
                                                        notEmpty: {}
                                                    }
                                                },
                                                freight: {
                                                    message: '请填写快递单号!',
                                                    validators: {
                                                        notEmpty: {}
                                                    }
                                                }
                                            }
                                        })
                                        .on('success.form.bv', function(e) {
                                            // Prevent form submission
                                            e.preventDefault();

                                            // Get the form instance
                                            var $form = $(e.target);

                                            // Get the BootstrapValidator instance
                                            var bv = $form.data('bootstrapValidator');

                                            // Use Ajax to submit form data
                                            $.ajax({
                                                url: '<%=basePath%>order/corder/delivery.do',
                                                type: 'post',
                                                data: $('#deliForm').serialize(),
                                                success: function(msg){
                                                    if(msg == 'success'){
                                                        $('#delivery').collapse('hide');
                                                        $('#fahuo').hide();
                                                        $('#shiSta').html('已发货');
                                                        return;
                                                    }
                                                    alert(msg);
                                                }
                                            });
                                        });
                            });
                        </script>
                    </c:if>
                    <c:if test="${order.pfCorder.shipStatus == 5}">已发货</c:if>
                    <c:if test="${order.pfCorder.shipStatus == 9}">已收货</c:if>
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">配送方式</td>
                <td class="meta-attributes__attr-detail">
                    <%--<c:if test="${order.pfCorder.shipType == 0}">物流配送</c:if>--%>
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">发货时间</td>
                <td class="meta-attributes__attr-detail">
                    <fmt:formatDate value="${order.pfCorder.deliveryTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">发货单号</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfCorderFreights[0].freight}
                </td>
            </tr>
            </tbody>
        </table>
        <hr/>

        <div class="modal-footer">
            <button type="button" class="btn btn-success">通过申请</button>
            <button type="button" class="btn btn-danger">拒绝申请</button>
            <%--<button type="button" class="btn btn-primary" id="userSubmit">
                提交
            </button>--%>
        </div>
    </div>
</div>
