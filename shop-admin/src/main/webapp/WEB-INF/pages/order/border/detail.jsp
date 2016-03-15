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
                    <fmt:formatDate value="${order.pfBorder.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" />
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">支付方式</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">物流状态</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">配送方式</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">发货时间</td>
                <td class="meta-attributes__attr-detail">

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
                    ${order.pfBorder.orderCode}
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">收获地址</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">联系电话</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">邮编</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">购买人</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            </tbody>
        </table>
        <hr/>

        <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
            <tbody>
            <tr>
                <td class="meta-attributes__attr-name">订单商品</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfBorder.orderCode}
                </td>
            </tr>
            <tr>
                <td class="meta-attributes__attr-name">购买数量</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>

            <tr>
                <td class="meta-attributes__attr-name">商品单价</td>
                <td class="meta-attributes__attr-detail">

                </td>
            </tr>
            </tbody>
        </table>
        <hr/>

        <table class="meta-attributes__table" cellspacing="0" cellpadding="0" border="0">
            <tbody>
            <tr>
                <td class="meta-attributes__attr-name">运费</td>
                <td class="meta-attributes__attr-detail">
                    ${order.pfBorder.orderCode}
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

        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Username</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td>Mark</td>
                <td>Otto</td>
                <td>@mdo</td>
            </tr>
            <tr>
                <td>2</td>
                <td>Jacob</td>
                <td>Thornton</td>
                <td>@fat</td>
            </tr>
            <tr>
                <td>3</td>
                <td>Larry</td>
                <td>the Bird</td>
                <td>@twitter</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
