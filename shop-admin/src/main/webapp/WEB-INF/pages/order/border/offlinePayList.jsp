<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>Tables - Masiis</title>

    <meta name="description" content="Static &amp; Dynamic Tables" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/tab.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/font-awesome.min.css" />

    <!-- page specific plugin styles -->

    <!-- text fonts -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-fonts.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/uncompressed/ace.css" id="main-ace-style" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-part2.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-skins.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/jquery.gritter.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-ie.min.css" />
    <![endif]-->

    <!-- inline styles related to this page -->

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
    <div class="main-content" style="margin: 0;">

        <!-- /section:basics/content.breadcrumbs -->
        <div class="page-content">

            <!-- /section:settings.box -->
            <div class="page-content-area">

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="row">
                            <div class="col-xs-12">

                                <div>
                                    <div id="toolbar" style="display: none;">
                                        <form class="form-inline">
                                            <%--<button id="remove" class="btn btn-danger" disabled>--%>
                                            <%--<i class="glyphicon glyphicon-remove"></i> 删除--%>
                                            <%--</button>--%>
                                            <%--<a class="btn btn-info" id="add" href="<%=basePath%>product/add.shtml">--%>
                                            <%--<i class="glyphicon glyphicon-add"></i> 添加--%>
                                            <%--</a>--%>
                                            <div class="form-group">
                                                <label for="skuName">商品名称</label>
                                                <input type="text" class="form-control" id="skuName" name="skuName" placeholder="商品名称">
                                            </div>
                                            <div class="form-group">
                                                <label for="categoryName">商品分类</label>
                                                <input type="text" class="form-control" id="categoryName" name="categoryName" placeholder="商品分类">
                                            </div>
                                            <button type="button" class="btn btn-default" id="searchBtn">查询</button>
                                        </form>
                                    </div>
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer" id="table" role="grid" aria-describedby="sample-table-2_info"
                                           data-toolbar="#toolbar"
                                           data-search="true"
                                           data-show-refresh="true"
                                           data-show-toggle="true"
                                    <%--data-show-columns="true"--%>
                                    <%--data-show-export="true"--%>
                                           data-detail-view="false"
                                           data-detail-formatter="detailFormatter"
                                           data-minimum-count-columns="2"
                                    <%--data-show-pagination-switch="true"--%>
                                           data-pagination="true"
                                           data-id-field="id"
                                           data-page-list="[10, 25, 50, 100, ALL]"
                                           data-show-footer="false"
                                           data-side-pagination="server"
                                           data-response-handler="responseHandler">
                                    </table>

                                </div>
                            </div>
                        </div>


                        <div id="modal-receipt" class="modal fade" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header no-padding">
                                        <div class="table-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                <span class="white">&times;</span>
                                            </button>
                                            您确认收到代理商货款?
                                        </div>
                                    </div>

                                    <div class="modal-body no-padding">
                                        <div>
                                            <div id="user-profile-1" class="user-profile row">
                                                <div class="col-xs-12 col-sm-12 col-sm-offset-0">

                                                    <!-- #section:pages/profile.info -->
                                                    <div class="profile-user-info profile-user-info-striped">

                                                        <div class="profile-info-row" id="auditReason">
                                                            <div class="profile-info-name" id="jjT"> 银行流水号 </div>

                                                            <div class="profile-info-value" id="jjF">
                                                                <form id="auditForm">
                                                                    <input type="hidden" name="id" id="bOrderId" value="" />
                                                                    <input type="text" name="outOrderId" id="outOrderId" value="" placeholder="银行流水号" />
                                                                </form>
                                                            </div>
                                                        </div>

                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="modal-footer no-margin-top">
                                        <div class="col-xs-5 col-sm-5 col-sm-offset-4">
                                            <input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5">
                                            <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
                                                取消
                                            </button>
                                            <button class="btn btn-sm btn-info pull-left ok">
                                                确认
                                            </button>
                                        </div>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div><!-- PAGE CONTENT ENDS -->

                    </div><!-- /.col -->
                </div><!-- /.row -->
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
<script src="<%=basePath%>static/ace2/js/jquery.dataTables.min.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.dataTables.bootstrap.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>static/ace2/js/uncompressed/bootbox.js"></script>

<script src="<%=basePath%>static/js/date-util.js"></script>

<script>
    var $table = $('#table'),
            $remove = $('#remove'),
            selections = [];

    function initTable() {
        $table.bootstrapTable({
            url: '<%=basePath%>order/border/offline/list.do',
            //height: getHeight(),
            locale: 'zh-CN',
            striped: true,
            //multipleSearch: true,
            queryParamsType: 'pageNo',
            rowStyle: function rowStyle(value, row, index) {
                return {
                    classes: 'text-nowrap another-class',
                    css: {}
                };
            },
            formatShowingRows: function (pageFrom, pageTo, totalRows) {
                return '当前显示 ' + pageFrom + " 到 " + pageTo + ', 总共 ' + totalRows;
            },
            formatRecordsPerPage: function (pageNumber) {
                return '每页显示' + pageNumber + '条数据';
            },
            formatSearch: function () {
                return "请输入关键字";
            },
            formatNoMatches: function () {
                return "没有找到数据哦!";
            },
            columns: [
                [
                    {
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    },
                    {
                        title: 'ID',
                        field: 'id',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        footerFormatter: totalTextFormatter,
                        formatter: function(value, row, index){
                            if(row.pfBorder && row.pfBorder.id){
                                return row.pfBorder.id;
                            }
                        }
                    },
                    {
                        field: 'orderCode',
                        title: '订单号',
                        sortable: true,
                        //editable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.pfBorder && row.pfBorder.orderCode){
                                return row.pfBorder.orderCode;
                            }
                        }
                    },
                    {
                        field: 'categoryName',
                        title: '订单日期',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            return new Date(row.pfBorder.createTime).pattern('yyyy-MM-dd HH:mm:ss');
                        }
                    },
                    {
                        field: 'priceRetail',
                        title: '收货人',
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.pfBorderConsignee && row.pfBorderConsignee.consignee){
                                return row.pfBorderConsignee.consignee;
                            }
                        }
                    },
                    {
                        field: 'store',
                        title: '购买人',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.comUser && row.comUser.realName){
                                return row.comUser.realName;
                            }
                        }
                    },
                    {
                        field: 'upTime',
                        title: '订单金额',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.pfBorder){
                                return row.pfBorder.productAmount;
                            }
                        }
                    },
                    {
                        field: 'status',
                        title: '实付金额',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.pfBorder){
                                return row.pfBorder.payAmount;
                            }
                        }
                    },
                    {
                        field: 'orderStatus',
                        title: '订单状态',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.pfBorder && row.pfBorder.orderStatus == 0){
                                return '未处理';
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 1){
                                return '已付款';
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 2){
                                return '已取消';
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 3){
                                return '已完成';
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 4){
                                return '退款中';
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 5){
                                return '已退款';
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 6){
                                return '排单中';
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 7){
                                return '待发货';
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 8){
                                return '已发货';
                            }
                        }
                    },
                    {
                        field: 'payType',
                        title: '支付方式',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.pfBorderPayments){
                                var sHtm = '';
                                for(var i in row.pfBorderPayments){
                                    if(row.pfBorderPayments[i].payTypeId == 0) sHtm += '微信支付';
                                    if(row.pfBorderPayments[i].payTypeId == 9) sHtm += '线下支付';
                                }
                                return sHtm;
                            }
                        }
                    },
                    {
                        field: 'payStatus',
                        title: '支付状态',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.pfBorder && row.pfBorder.payStatus == 0){
                                return '待付款';
                            }
                            if(row.pfBorder && row.pfBorder.payStatus == 1){
                                return '已付款';
                            }
                        }
                    },
                    {
                        field: 'shipStatus',
                        title: '物流状态',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.pfBorder && row.pfBorder.shipStatus == 0){
                                return '未发货';
                            }
                            if(row.pfBorder && row.pfBorder.shipStatus == 5){
                                return '已发货';
                            }
                            if(row.pfBorder && row.pfBorder.shipStatus == 9){
                                return '已收货';
                            }
                        }
                    },
                    {
                        title: '操作项',
                        align: 'center',
                        formatter: function(value, row, index){
                            var arr = ['<a class="detail" href="javascript:void(0);">查看</a>'];
                            if(row.pfBorder && row.pfBorder.userPid == 0 && row.pfBorder.orderStatus == 6){
                                arr.push('&nbsp;&nbsp;<a class="scheduling" href="javascript:void(0);">处理订单</a>');
                            }
                            if(row.pfBorder && row.pfBorder.orderStatus == 9){
                                arr.push('&nbsp;&nbsp;<a class="receipt" href="javascript:void(0);">确认收款</a>');
                            }

                            return arr.join('');
                        },
                        events: {
                            'click .detail': function(e, value, row, index){
                                parent.window.$('#myTabbable').add('orderDetail', '合伙人订单明细', '<%=basePath%>order/border/detail.shtml?borderId='+ row.pfBorder.id);
                            },
                            'click .scheduling': function(e, value, row, index){
                                $.ajax({
                                    url: '<%=basePath%>order/border/scheduling.do',
                                    data: {borderId: row.pfBorder.id, sendType: row.comUser.sendType},
                                    success: function(msg){
                                        msg = msg=='success' ? '处理排单成功!' : '处理排单出错了!';
                                        $.gritter.add({
                                            title: '消息',
                                            text: msg,
                                            class_name: 'gritter-success' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
                                        });
                                        $('#table').bootstrapTable('refresh');
                                    }
                                })
                            },
                            'click .receipt': function(e, value, row, index){
                                $('#bOrderId').val(row.pfBorder.id);
                                $('#modal-receipt').modal('show');
                            }
                        }
                    }
                ]
            ]
        });
        // sometimes footer render error.
        setTimeout(function () {
            $table.bootstrapTable('resetView');
        }, 200);
        $table.on('check.bs.table uncheck.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);

            // save your data, here just save the current page
            selections = getIdSelections();
            // push or splice the selections if you want to save all data selections
        });
        $table.on('expand-row.bs.table', function (e, index, row, $detail) {
            $detail.html('数据加载中...');
            $.get('/user/load.shtml', {id: row.id}, function (res) {
                //$detail.html(res.replace(/\n/g, '<br>'));
                $detail.html(res);
            });
        });
        $table.on('all.bs.table', function (e, name, args) {
            console.log(name, args);
        });
        $remove.click(function () {
            var ids = getIdSelections();
            console.log('remove: ' + ids);
            $table.bootstrapTable('remove', {
                field: 'id',
                values: ids
            });
            $remove.prop('disabled', true);
        });
        $(window).resize(function () {
            $table.bootstrapTable('resetView', {
                height: getHeight()
            });
        });
    }

    function getIdSelections() {
        return $.map($table.bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

    function responseHandler(res) {
        $.each(res.rows, function (i, row) {
            row.state = $.inArray(row.id, selections) !== -1;
        });
        return res;
    }

    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>');
        });
        return html.join('');
    }

    function operateFormatter(value, row, index) {
        var arr = [];
        arr.push('&nbsp;<a class="edit" href="<%=basePath%>product/edit.shtml?skuId='+ row.comSku.id +'" title="Edit">编辑</a>');
        if(row.comSpu && row.comSpu.isSale == 0){
            arr.push('&nbsp;<a class="putaway" href="javascript:void(0)" title="Putaway">上架</a>');
        }else if(row.comSpu && row.comSpu.isSale == 1){
            arr.push('&nbsp;<a class="putaway" href="javascript:void(0)" title="Putaway">下架</a>');
        }

        return arr.join(' ');
    }

    function totalTextFormatter(data) {
        return 'Total';
    }

    function totalNameFormatter(data) {
        return data.length;
    }

    function totalPriceFormatter(data) {
        var total = 0;
        $.each(data, function (i, row) {
            total += +(row.price.substring(1));
        });
        return '$' + total;
    }

    function getHeight() {
        return $(window).height() - $('h1').outerHeight(true);
    }

    $(function () {
        var scripts = [
                    location.search.substring(1) || '<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table.min.js',
                    '<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-export.js',
                    '<%=basePath%>static/class/bootstrap-3.3.5-dist/js/tableExport.js',
                    '<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-editable.js',
                    '<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-editable.js'
                ],
                eachSeries = function (arr, iterator, callback) {
                    callback = callback || function () {
                            };
                    if (!arr.length) {
                        return callback();
                    }
                    var completed = 0;
                    var iterate = function () {
                        iterator(arr[completed], function (err) {
                            if (err) {
                                callback(err);
                                callback = function () {
                                };
                            }
                            else {
                                completed += 1;
                                if (completed >= arr.length) {
                                    callback(null);
                                }
                                else {
                                    iterate();
                                }
                            }
                        });
                    };
                    iterate();
                };

        eachSeries(scripts, getScript, initTable);
    });

    function getScript(url, callback) {
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.src = url;

        var done = false;
        // Attach handlers for all browsers
        script.onload = script.onreadystatechange = function () {
            if (!done && (!this.readyState ||
                    this.readyState == 'loaded' || this.readyState == 'complete')) {
                done = true;
                if (callback)
                    callback();

                // Handle memory leak in IE
                script.onload = script.onreadystatechange = null;
            }
        };

        head.appendChild(script);

        // We handle everything using the script element injection
        return undefined;
    }

    $('#searchBtn').on('click', function(){

    });

    $('.ok').on('click', function(){
        var outOrderId = $('input[name="outOrderId"]').val();

        if(!outOrderId){
            $.gritter.add({
                title: '温馨提示',
                text: '请填写银行流水号!',
                class_name: 'gritter-error' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        }

        $.ajax({
            url: '<%=basePath%>fundmanage/extract/audit.do',
            type: 'POST',
            data: $('#auditForm').serialize(),
            success: function(msg){
                if('success' == msg){
                    $('#modal-audit').modal('hide');
                }
                $.gritter.add({
                    title: '消息',
                    text: msg,
                    class_name: 'gritter-success' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
                });
                $('#table').bootstrapTable('refresh');
            }
        })
    });


</script>
</body>
</html>
