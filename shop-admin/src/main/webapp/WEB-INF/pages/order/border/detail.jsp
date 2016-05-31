<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>UI Elements - Ace Admin</title>

    <meta name="description" content="Common UI Features &amp; Elements" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/font-awesome.min.css" />

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/jquery-ui.custom.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/jquery.gritter.css" />

    <!-- text fonts -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-fonts.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace.min.css" id="main-ace-style" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-part2.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-skins.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-rtl.min.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-ie.min.css" />
    <![endif]-->

    <!-- inline styles related to this page -->
    <style>
        /* some elements used in demo only */
        .spinner-preview {
            width: 100px;
            height: 100px;
            text-align: center;
            margin-top: 60px;
        }

        .dropdown-preview {
            margin: 0 5px;
            display: inline-block;
        }
        .dropdown-preview  > .dropdown-menu {
            display: block;
            position: static;
            margin-bottom: 5px;
        }
    </style>

    <!-- ace settings handler -->
    <script src="<%=basePath%>static/ace2/js/uncompressed/ace-extra.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="<%=basePath%>static/ace2/js/html5shiv.min.js"></script>
    <script src="<%=basePath%>static/ace2/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="no-skin">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <!-- /section:basics/sidebar -->
    <div class="main-content">

        <!-- /section:basics/content.breadcrumbs -->
        <div class="page-content">

            <!-- /section:settings.box -->
            <div class="page-content-area">

                <div class="row">
                    <div class="col-xs-12 col-sm-12">

                        <div>
                            <div id="user-profile-1" class="user-profile row">
                                <div class="col-xs-12 col-sm-8 col-sm-offset-2">

                                    <!-- #section:pages/profile.info -->
                                    <div class="profile-user-info profile-user-info-striped">

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 订单号 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="orderCode">${order.pfBorder.orderCode}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 订单类型 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="orderType">
                                                    <c:if test="${order.pfBorder.orderType == 0}">代理订单</c:if>
                                                    <c:if test="${order.pfBorder.orderType == 1}">补货订单</c:if>
                                                    <c:if test="${order.pfBorder.orderType == 2}">拿货订单</c:if>
                                                </span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 订单状态 </div>

                                            <div class="profile-info-value">
                                                <c:if test="${order.pfBorder.orderStatus == 0}"><span class="" id="orderStatus">未处理</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 1}"><span class="" id="orderStatus">已付款</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 2}"><span class="" id="orderStatus">已取消</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 3}"><span class="" id="orderStatus">已完成</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 4}"><span class="" id="orderStatus">退款中</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 5}"><span class="" id="orderStatus">已退款</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 6}"><span class="" id="orderStatus">排单中</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 7}"><span class="" id="orderStatus">待发货</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 8}"><span class="" id="orderStatus">已发货</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 9}"><span class="" id="orderStatus">线下支付中</span></c:if>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 下单日期 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="createTime"><fmt:formatDate value="${order.pfBorder.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 支付日期 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="payTime"><fmt:formatDate value="${order.pfBorder.payTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 支付类型 </div>

                                            <div class="profile-info-value">
                                                <c:forEach items="${order.pfBorderPayments}" var="borderPayment">
                                                    ${borderPayment.payTypeName}
                                                </c:forEach>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 物流状态 </div>

                                            <div class="profile-info-value">
                                                <c:if test="${order.pfBorder.payStatus == 1 && order.pfBorder.shipStatus == 0 && order.pfBorder.sendType == 1 && order.pfBorder.orderType != 1}">
                                                    <span>未发货</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <span class="btn btn-info btn-sm popover-info" id="fahuo" data-toggle="collapse">发货</span>
                                                    <button type="button" class="btn btn-info" id="fahuo-" data-toggle="collapse" style="display: none;">
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
                                                                    <input type="hidden" name="pfBorderId" value="${order.pfBorder.id}" />
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
                                                </c:if>
                                                <c:if test="${order.pfBorder.shipStatus == 5}"><span>已发货</span></c:if>
                                                <c:if test="${order.pfBorder.shipStatus == 9}"><span>已收货</span></c:if>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 配送方式 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="extractableFee">
                                                    <c:forEach items="${order.pfBorderFreights}" var="pfBorderFreight">
                                                        ${pfBorderFreight.shipManName}
                                                    </c:forEach>
                                                </span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 发货时间 </div>

                                            <div class="profile-info-value">
                                                <c:forEach items="${order.pfBorderFreights}" var="pfBorderFreight">
                                                    <span class="" id="bfCreateTime"><fmt:formatDate value="${pfBorderFreight.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></span>
                                                </c:forEach>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 运单单号 </div>

                                            <div class="profile-info-value">
                                                <c:forEach items="${order.pfBorderFreights}" var="pfBorderFreight">
                                                    <span class="" id="freight">${pfBorderFreight.freight}</span>
                                                </c:forEach>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 收货人 </div>

                                            <div class="profile-info-value" style="border-top: 3px dotted #DCEBF7;">
                                                <span class="" id="consignee">${order.pfBorderConsignee.consignee}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 收货地址 </div>

                                            <div class="profile-info-value">
                                                <span class="bgarea" id="address">${order.pfBorderConsignee.provinceName}${order.pfBorderConsignee.cityName}${order.pfBorderConsignee.regionName}${order.pfBorderConsignee.address}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 联系电话 </div>

                                            <div class="profile-info-value">
                                                <span class="bgarea" id="mobile">${order.pfBorderConsignee.mobile}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 邮编 </div>

                                            <div class="profile-info-value">
                                                <span class="bgarea" id="zip">${order.pfBorderConsignee.zip}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 购买人 </div>

                                            <div class="profile-info-value">
                                                <span class="bgarea" id="realName">${order.comUser.realName}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 留言 </div>

                                            <div class="profile-info-value" style="border-top: 3px dotted #DCEBF7;">
                                                <span class="bgarea" id="remark">${order.pfBorder.userMessage}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 运费 </div>

                                            <div class="profile-info-value">
                                                <span class="bgarea" id="shipAmount">${order.pfBorder.shipAmount}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 商品总金额 </div>

                                            <div class="profile-info-value">
                                                <span class="bgarea" id="productAmount">${order.pfBorder.productAmount}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 实付金额 </div>

                                            <div class="profile-info-value">
                                                <span class="bgarea" id="payAmount">${order.pfBorder.payAmount}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 订单状态 </div>

                                            <div class="profile-info-value">
                                                <span class="bgarea" id="order_status">
                                                <c:if test="${order.pfBorder.orderStatus == 0}"><span class="" id="orderStatus">未处理</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 1}"><span class="" id="orderStatus">已付款</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 2}"><span class="" id="orderStatus">已取消</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 3}"><span class="" id="orderStatus">已完成</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 4}"><span class="" id="orderStatus">退款中</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 5}"><span class="" id="orderStatus">已退款</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 6}"><span class="" id="orderStatus">排单中</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 7}"><span class="" id="orderStatus">待发货</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 8}"><span class="" id="orderStatus">已发货</span></c:if>
                                                <c:if test="${order.pfBorder.orderStatus == 9}"><span class="" id="orderStatus">线下支付中</span></c:if>
                                                </span>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>

                    </div><!-- /.col -->

                </div><!-- /.row -->

                <div class="hr"></div>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-12 col-sm-8 col-sm-offset-2">
                                <table id="sample-table-1" class="table table-striped table-bordered table-hover">
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
                                    <c:forEach items="${order.productInfos}" var="product">
                                        <tr>
                                            <td>${product.comSku.name}</td>
                                            <td>${product.comSpu.artNo}</td>
                                            <td>-</td>
                                            <td>${product.comSku.priceMarket}</td>
                                            <td>${order.pfBorderItems[0].unitPrice}</td>
                                            <td>${order.pfBorderItems[0].quantity}</td>
                                            <td>${order.pfBorder.productAmount}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div><!-- /.span -->
                        </div><!-- /.row -->
                    </div><!-- /.col -->
                </div>

                <div></div>
                <%--<div class="row">--%>
                    <%--<div class="col-xs-12">--%>
                        <%--<div class="row">--%>
                            <%--<div class="col-xs-12 col-sm-8 col-sm-offset-2">--%>
                                <%--<table id="sample-table-2" class="table table-striped table-bordered table-hover">--%>
                                    <%--<thead>--%>
                                    <%--<tr>--%>
                                        <%--<th>操作者</th>--%>
                                        <%--<th>操作时间</th>--%>
                                        <%--<th>操作类型</th>--%>
                                        <%--<th>操作备注</th>--%>
                                    <%--</tr>--%>
                                    <%--</thead>--%>

                                    <%--<tbody>--%>
                                    <%--<tr>--%>
                                        <%--<td>Mark</td>--%>
                                        <%--<td>Otto</td>--%>
                                        <%--<td>@TwBootstrap</td>--%>
                                        <%--<td>@TwBootstrap</td>--%>
                                    <%--</tr>--%>
                                    <%--</tbody>--%>
                                <%--</table>--%>
                            <%--</div><!-- /.span -->--%>
                        <%--</div><!-- /.row -->--%>
                    <%--</div><!-- /.col -->--%>
                <%--</div>--%>

            </div><!-- /.page-content-area -->
        </div><!-- /.page-content -->
    </div><!-- /.main-content -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='<%=basePath%>static/ace2/js/jquery.min.js'>"+"<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='<%=basePath%>static/ace2/js/jquery1x.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>static/ace2/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script src="<%=basePath%>static/ace2/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="../assets/js/excanvas.min.js"></script>
<![endif]-->
<script src="<%=basePath%>static/ace2/js/jquery-ui.custom.min.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/bootstrap-validator/js/bootstrapValidator.js"></script>
<script src="<%=basePath%>static/ace2/js/bootbox.min.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.easypiechart.min.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>static/ace2/js/spin.min.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    jQuery(function($) {
        /**
         $('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				  console.log(e.target.getAttribute("href"));
				})

         $('#accordion').on('shown.bs.collapse', function (e) {
					console.log($(e.target).is('#collapseTwo'))
				});
         */

        $('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            if($(e.target).attr('href') == "#home") drawChartNow();
        })


        /**
         //go to next tab, without user clicking
         $('#myTab > .active').next().find('> a').trigger('click');
         */


        $('#accordion-style').on('click', function(ev){
            var target = $('input', ev.target);
            var which = parseInt(target.val());
            if(which == 2) $('#accordion').addClass('accordion-style2');
            else $('#accordion').removeClass('accordion-style2');
        });

        //$('[href="#collapseTwo"]').trigger('click');


        var oldie = /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase());
        $('.easy-pie-chart.percentage').each(function(){
            $(this).easyPieChart({
                barColor: $(this).data('color'),
                trackColor: '#EEEEEE',
                scaleColor: false,
                lineCap: 'butt',
                lineWidth: 8,
                animate: oldie ? false : 1000,
                size:75
            }).css('color', $(this).data('color'));
        });

        $('[data-rel=tooltip]').tooltip();
        $('[data-rel=popover]').popover({html:true});


        $('#gritter-regular').on(ace.click_event, function(){
            $.gritter.add({
                title: 'This is a regular notice!',
                text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="blue">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets+'/avatars/avatar1.png',
                sticky: false,
                time: '',
                class_name: (!$('#gritter-light').get(0).checked ? 'gritter-light' : '')
            });

            return false;
        });

        $('#gritter-sticky').on(ace.click_event, function(){
            var unique_id = $.gritter.add({
                title: 'This is a sticky notice!',
                text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="red">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets+'/avatars/avatar.png',
                sticky: true,
                time: '',
                class_name: 'gritter-info' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-without-image').on(ace.click_event, function(){
            $.gritter.add({
                // (string | mandatory) the heading of the notification
                title: 'This is a notice without an image!',
                // (string | mandatory) the text inside the notification
                text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="orange">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                class_name: 'gritter-success' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-max3').on(ace.click_event, function(){
            $.gritter.add({
                title: 'This is a notice with a max of 3 on screen at one time!',
                text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="green">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets+'/avatars/avatar3.png',
                sticky: false,
                before_open: function(){
                    if($('.gritter-item-wrapper').length >= 3)
                    {
                        return false;
                    }
                },
                class_name: 'gritter-warning' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-center').on(ace.click_event, function(){
            $.gritter.add({
                title: 'This is a centered notification',
                text: 'Just add a "gritter-center" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
                class_name: 'gritter-info gritter-center' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });

        $('#gritter-error').on(ace.click_event, function(){
            $.gritter.add({
                title: 'This is a warning notification',
                text: 'Just add a "gritter-light" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
                class_name: 'gritter-error' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $("#gritter-remove").on(ace.click_event, function(){
            $.gritter.removeAll();
            return false;
        });


        ///////


        $("#bootbox-regular").on(ace.click_event, function() {
            bootbox.prompt("What is your name?", function(result) {
                if (result === null) {

                } else {

                }
            });
        });

        $("#bootbox-confirm").on(ace.click_event, function() {
            bootbox.confirm("Are you sure?", function(result) {
                if(result) {
                    //
                }
            });
        });

        /**
         $("#bootbox-confirm").on(ace.click_event, function() {
					bootbox.confirm({
						message: "Are you sure?",
						buttons: {
						  confirm: {
							 label: "OK",
							 className: "btn-primary btn-sm",
						  },
						  cancel: {
							 label: "Cancel",
							 className: "btn-sm",
						  }
						},
						callback: function(result) {
							if(result) alert(1)
						}
					  }
					);
				});
         **/

        $("#bootbox-options").on(ace.click_event, function() {
            bootbox.dialog({
                message: "<span class='bigger-110'>I am a custom dialog with smaller buttons</span>",
                buttons:
                {
                    "success" :
                    {
                        "label" : "<i class='ace-icon fa fa-check'></i> Success!",
                        "className" : "btn-sm btn-success",
                        "callback": function() {
                            //Example.show("great success");
                        }
                    },
                    "danger" :
                    {
                        "label" : "Danger!",
                        "className" : "btn-sm btn-danger",
                        "callback": function() {
                            //Example.show("uh oh, look out!");
                        }
                    },
                    "click" :
                    {
                        "label" : "Click ME!",
                        "className" : "btn-sm btn-primary",
                        "callback": function() {
                            //Example.show("Primary button");
                        }
                    },
                    "button" :
                    {
                        "label" : "Just a button...",
                        "className" : "btn-sm"
                    }
                }
            });
        });



        $('#spinner-opts small').css({display:'inline-block', width:'60px'})

        var slide_styles = ['', 'green','red','purple','orange', 'dark'];
        var ii = 0;
        $("#spinner-opts input[type=text]").each(function() {
            var $this = $(this);
            $this.hide().after('<span />');
            $this.next().addClass('ui-slider-small').
            addClass("inline ui-slider-"+slide_styles[ii++ % slide_styles.length]).
            css({'width':'125px'}).slider({
                value:parseInt($this.val()),
                range: "min",
                animate:true,
                min: parseInt($this.data('min')),
                max: parseInt($this.data('max')),
                step: parseFloat($this.data('step')),
                slide: function( event, ui ) {
                    $this.val(ui.value);
                    spinner_update();
                }
            });
        });





        $.fn.spin = function(opts) {
            this.each(function() {
                var $this = $(this),
                        data = $this.data();

                if (data.spinner) {
                    data.spinner.stop();
                    delete data.spinner;
                }
                if (opts !== false) {
                    data.spinner = new Spinner($.extend({color: $this.css('color')}, opts)).spin(this);
                }
            });
            return this;
        };

        function spinner_update() {
            var opts = {};
            $('#spinner-opts input[type=text]').each(function() {
                opts[this.name] = parseFloat(this.value);
            });
            $('#spinner-preview').spin(opts);
        }



        $('#id-pills-stacked').removeAttr('checked').on('click', function(){
            $('.nav-pills').toggleClass('nav-stacked');
        });


    });
</script>
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
                        type: 'POST',
                        data: $form.serialize(),
                        success: function(msg){
                            if(msg == 'success'){
                                $('#delivery').collapse('hide');
                                $('#fahuo').hide();
                                $('#shiSta').html('已发货');
                                window.location.reload();
                            }else{
                                alert('发货失败了!');
                            }
                        }
                    });
                });
    });
</script>

<!-- the following scripts are used in demo only for onpage help and you don't need them -->
<link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace.onpage-help.css" />
<link rel="stylesheet" href="<%=basePath%>static/ace2/docs/assets/js/themes/sunburst.css" />

<script type="text/javascript"> ace.vars['base'] = '..'; </script>
<script src="<%=basePath%>static/ace2/js/elements.onpage-help.js"></script>
<script src="<%=basePath%>static/ace2/js/ace.onpage-help.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/rainbow.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/language/generic.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/language/html.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/language/css.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/language/javascript.js"></script>
</body>
</html>
