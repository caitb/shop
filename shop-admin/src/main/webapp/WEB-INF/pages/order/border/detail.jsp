<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>合伙人订单明细</title>
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

</head>
<body>
<div class="box -radius-all">
    <div class="meta-attributes">
        <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
            <tbody>

            <tr>
                <td class="meta-attributes__attr-name">订单号</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfBorder.orderCode}
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">订单状态</td>
                <td class="meta-attributes__attr-detail">
                    <c:if test="${order.pfBorder.orderStatus == 0}">未处理</c:if>
                    <c:if test="${order.pfBorder.orderStatus == 1}">已付款</c:if>
                    <c:if test="${order.pfBorder.orderStatus == 2}">已取消</c:if>
                    <c:if test="${order.pfBorder.orderStatus == 3}">已完成</c:if>
                    <c:if test="${order.pfBorder.orderStatus == 4}">退款中</c:if>
                    <c:if test="${order.pfBorder.orderStatus == 5}">已退款</c:if>
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">下单日期</td>
                <td class="meta-attributes__attr-detail">
                    <fmt:formatDate value="${order.pfBorder.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">支付日期</td>
                <td class="meta-attributes__attr-detail">
                    <fmt:formatDate value="${order.pfBorder.payTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">支付方式</td>
                <td class="meta-attributes__attr-detail">
                    <c:if test="${order.pfBorderPayment.payTypeId == 1}">微信</c:if>
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">物流状态</td>
                <td class="meta-attributes__attr-detail">
                    <c:if test="${order.pfCorder.shipStatus == 0}">
                        未发货&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-info" id="fahuo" data-toggle="collapse" >
                            发货
                        </button>
                        <div id="delivery" class="collapse" aria-expanded="false" style="height: 0px;">
                            <form class="form-horizontal" id="deliForm" action="<%=basePath%>order/border/delivery.do">
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
                                        <input type="hidden" name="pfCorderId" value="${order.pfBorder.id}" />
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
                                        var sOptions = '<option value="-1">请选择</option>';
                                        for(var i in data){
                                            sOptions += '<option value="' + data[i].id + '">' + data[i].name + '</option>'
                                        }
                                        $('#shipName').html(sOptions);
                                    }
                                });
                                $('#delivery').collapse(true);
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
                                                url: '<%=basePath%>order/border/delivery.do',
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
                    <c:if test="${order.pfBorder.shipStatus == 5}">已发货</c:if>
                    <c:if test="${order.pfBorder.shipStatus == 9}">已收货</c:if>
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
                    <fmt:formatDate value="${order.pfBorder.deliveryTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">发货单号</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            </tbody>
        </table>
        <hr/>

        <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
            <tbody>
            <tr>
                <td class="meta-attributes__attr-name">收货人</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfBorderConsignee.consignee}
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">收获地址</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfBorderConsignee.provinceName}+${order.pfBorderConsignee.cityName}+${order.pfBorderConsignee.regionName}+${order.pfBorderConsignee.address}
                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">联系电话</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfBorderConsignee.mobile}
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">邮编</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfBorderConsignee.zip}
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">购买人</td>
                <td class="meta-attributes__attr-detail">
                    ${order.comUser.realName}
                </td>
            </tr>
            </tbody>
        </table>
        <hr/>

        <table class="table table-bordered my-table">
            <thead>
            <tr>
                <th>商品名称</th>
                <th>货号</th>
                <th>商品属性</th>
                <th>市场价</th>
                <th>购买价格</th>
                <th>购买数量</th>
                <th>小计</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Mark</td>
                <td>Otto</td>
                <td>@TwBootstrap</td>
                <td>@TwBootstrap</td>
                <td>@TwBootstrap</td>
                <td>@TwBootstrap</td>
                <td>@TwBootstrap</td>
            </tr>
            </tbody>
        </table>
        <hr/>

        <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
            <tbody>
            <tr>
                <td class="meta-attributes__attr-name">运费</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfCorder.orderCode}
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">商品总金额</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">优惠</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">实付金额</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">订单状态</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            </tbody>
        </table>
        <hr/>

        <div style="align-content: center; width: 200px; margin: 0 auto;">
            <button class="btn btn-info">确认收货</button>
            <button class="btn btn-detault">取消订单</button>
        </div>
        <hr/>

        <table class="table table-bordered my-table">
            <thead>
            <tr>
                <th>操作者</th>
                <th>操作时间</th>
                <th>操作类型</th>
                <th>操作备注</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Mark</td>
                <td>Otto</td>
                <td>@TwBootstrap</td>
                <td>@TwBootstrap</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
