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
                                    <div id="toolbar">
                                        <%--<div class="form-inline">--%>
                                            <%--<div class="form-group">--%>
                                                <%--<label for="orderCode">订单号</label>--%>
                                                <%--<input type="text" class="form-control" id="orderCode" name="orderCode" placeholder="订单号">--%>
                                            <%--</div>--%>
                                            <%--<button type="button" class="btn btn-default" id="searchBtn">查询</button>--%>
                                            <button type="button" class="btn btn-default" id="batchShipSetup">批量设置运费</button>
                                        </div>
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


                        <div id="ship-setup" class="modal fade" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header no-padding">
                                        <div class="table-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                <span class="white">&times;</span>
                                            </button>
                                            运费设置
                                        </div>
                                    </div>

                                    <div class="modal-body no-padding">
                                        <div>
                                            <div id="user-profile-1" class="user-profile row">
                                                <div class="col-xs-12 col-sm-12 col-sm-offset-0">

                                                    <!-- #section:pages/profile.info -->
                                                    <div class="profile-user-info profile-user-info-striped">

                                                        <form id="shipSetupForm">
                                                            <input type="hidden" name="id" id="shopId" value="" />

                                                            <div class="profile-info-row">
                                                                <div class="profile-info-name"> 店主 </div>

                                                                <div class="profile-info-value">
                                                                    <span class="" id="shopName"> </span>
                                                                </div>
                                                            </div>

                                                            <div class="profile-info-row">
                                                                <div class="profile-info-name"> 承担运费方 </div>

                                                                <div class="profile-info-value">
                                                                    <div class="radio">
                                                                        <label>
                                                                            <input type="radio" name="shipType" id="shipType" value="0" />
                                                                            <span class="lbl"> 消费者 </span>
                                                                        </label>
                                                                    </div>
                                                                    <div class="radio">
                                                                        <label>
                                                                            <input type="radio" name="shipType" id="shipType2" value="1" />
                                                                            <span class="lbl"> 代理商 </span>
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="profile-info-row">
                                                                <div class="profile-info-name"> 消费者运费金额 </div>

                                                                <div class="profile-info-value">
                                                                    <input type="text" name="shipAmount" id="shipAmount" value="" />
                                                                </div>
                                                            </div>

                                                            <div class="profile-info-row">
                                                                <div class="profile-info-name"> 代理运费金额 </div>

                                                                <div class="profile-info-value">
                                                                    <input type="text" name="agentShipAmount" id="agentShipAmount" value="" />
                                                                </div>
                                                            </div>
                                                        </form>

                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="modal-footer no-margin-top">
                                        <div class="col-xs-5 col-sm-5 col-sm-offset-4">
                                            <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
                                                取消
                                            </button>
                                            <button class="btn btn-sm btn-info pull-left" id="submitBtn" >
                                                提交
                                            </button>
                                        </div>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div><!-- PAGE CONTENT ENDS -->

                        <div id="batch-ship-setup" class="modal fade" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header no-padding">
                                    <div class="table-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                            <span class="white">&times;</span>
                                        </button>
                                        批量运费设置
                                    </div>
                                </div>

                                <div class="modal-body no-padding">
                                    <div>
                                        <div id="user-profile-01" class="user-profile row">
                                            <div class="col-xs-12 col-sm-12 col-sm-offset-0">

                                                <!-- #section:pages/profile.info -->
                                                <div class="profile-user-info profile-user-info-striped">

                                                    <form id="batchShipSetupForm">

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> 店主 </div>

                                                            <div class="profile-info-value">
                                                                <span class="" id="shopNames"> </span>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> 承担运费方 </div>

                                                            <div class="profile-info-value">
                                                                <div class="radio">
                                                                    <label>
                                                                        <input type="radio" name="shipTypes" id="shipTypes" value="0" />
                                                                        <span class="lbl"> 消费者 </span>
                                                                    </label>
                                                                </div>
                                                                <div class="radio">
                                                                    <label>
                                                                        <input type="radio" name="shipTypes" id="shipTypes2" value="1" />
                                                                        <span class="lbl"> 代理商 </span>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> 消费者运费金额 </div>

                                                            <div class="profile-info-value">
                                                                <input type="text" name="shipAmounts" id="shipAmounts" value="0" />
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> 代理运费金额 </div>

                                                            <div class="profile-info-value">
                                                                <input type="text" name="agentShipAmounts" id="agentShipAmounts" value="0" />
                                                            </div>
                                                        </div>
                                                    </form>

                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="modal-footer no-margin-top">
                                    <div class="col-xs-5 col-sm-5 col-sm-offset-4">
                                        <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
                                            取消
                                        </button>
                                        <button class="btn btn-sm btn-info pull-left" id="submitBtns" >
                                            提交
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
            url: '<%=basePath%>shop/list.do',
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
                            if(row.sfShop && row.sfShop.id){
                                return row.sfShop.id;
                            }
                        }
                    },
                    {
                        field: 'createTime',
                        title: '生成日期',
                        sortable: true,
                        //editable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.sfShop && row.sfShop.createTime){
                                return new Date(row.sfShop.createTime).pattern('yyyy-MM-dd HH:mm:ss');
                            }
                        }
                    },
                    {
                        field: 'userName',
                        title: '店主',
                        sortable: true,
                        //editable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.comUser && row.comUser.realName){
                                return row.comUser.realName;
                            }
                        }
                    },
                    {
                        field: 'shopName',
                        title: '店铺名称',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.sfShop && row.sfShop.name){
                                return row.sfShop.name;
                            }
                        }
                    },
                    {
                        field: 'pageviews',
                        title: '浏览量',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.sfShop){
                                return row.sfShop.pageviews;
                            }
                        }
                    },
                    {
                        align: 'center',
                        field: 'shoutNum',
                        title: '呐喊量',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        formatter: function(value, row, index){
                            if(row.sfShop){
                                return row.sfShop.shoutNum;
                            }
                        }
                    },
                    {
                        align: 'center',
                        field: 'bankName',
                        title: '成交量',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        formatter: function(value, row, index){
                            if(row.comUserExtractApply && row.comUserExtractApply.bankName){
                                return row.comUserExtractApply.bankName;
                            }
                        }
                    },
                    {
                        field: 'status',
                        title: '状态',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.sfShop && row.sfShop.status == 0) {
                                return '<span class="label label-sm label-grey">关店</span>';
                            }
                            if(row.sfShop && row.sfShop.status == 1){
                                return '<span class="label label-sm label-success">开店</span>';
                            }
                        }
                    },
                    {
                        title: '操作项',
                        align: 'center',
                        formatter: function(value, row, index){
                            var sArr = ['<a class="v-detail" href="javascript:void(0);">查看</a>'];
                                sArr.push('&nbsp;&nbsp;<a class="ship-setup" href="javascript:void(0);">设置运费</a>')

                            return sArr.join('');
                        },
                        events: {
                            'click .v-detail': function(e, value, row, index){
                                parent.window.$('#myTabbable').add('shop-'+row.sfShop.id, '店铺信息', '<%=basePath%>shop/detail.shtml?shopId='+row.sfShop.id);
                            },
                            'click .ship-setup': function(e, value, row, index){
                                $.ajax({
                                    url: '<%=basePath%>shop/getShop.do',
                                    data: {shopId: row.sfShop.id},
                                    success: function(shop){
                                        shop = window.eval('('+shop+')');
                                        $('#shopId').val(shop.id);
                                        $('#shopName').html(shop.name);
                                        $('input[name="shipAmount"]').val(shop.shipAmount);
                                        $('input[name="agentShipAmount"]').val(shop.agentShipAmount);
                                        $('input[name="shipType"]').removeProp('checked');
                                        $('input[name="agentShipAmount"]').removeAttr('disabled');
                                        $('input[name="shipAmount"]').removeAttr('disabled');
                                        $('input[name="shipType"]').each(function(i, obj){
                                            if($(this).val() == shop.shipType) $(this).prop('checked', 'checked');
                                            if(shop.shipType == 0){
                                                $('input[name="agentShipAmount"]').val(0);
                                                $('input[name="agentShipAmount"]').prop('disabled', 'disabled');
                                            }
                                            if(shop.shipType == 1){
                                                $('input[name="shipAmount"]').val(0);
                                                $('input[name="shipAmount"]').prop('disabled', 'disabled');
                                            }
                                        });
                                    }
                                });
                                $('#ship-setup').modal('show');
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

    $('#submitBtn').on('click', function(){
        $.ajax({
            url: '<%=basePath%>shop/update.do',
            type: 'POST',
            data: $('#shipSetupForm').serialize(),
            success: function(msg){
                if('success' == msg){
                    $('#ship-setup').modal('hide');
                }
                msg = msg=='success'?'运费设置成功':'运费设置失败';
                $.gritter.add({
                    title: '消息',
                    text: msg,
                    class_name: 'gritter-success'
                });
                $('#table').bootstrapTable('refresh');
            }
        })
    });

    var ids = [];
    $('#batchShipSetup').on('click', function(){
        var selectRows = $table.bootstrapTable('getSelections');
        if(selectRows.length == 0){
            $.gritter.add({
                title: '消息',
                text: '请选择要设置的店铺!',
                class_name: 'gritter-info'
            });
            return;
        }


        var shopNames = selectRows[0].sfShop.name + ',';
        var shop = [];
        for(var i=0; i<selectRows.length; i++){
            shopNames += selectRows[i].sfShop.name+',';

            ids.push(selectRows[i].sfShop.id);
            ids.push(',');
        }
        ids.pop();
        $('#shopName').html(shopNames);
        $('#batch-ship-setup').modal('show');
    });

    $('#submitBtns').on('click', function(){
        $.ajax({
            url: '<%=basePath%>shop/batchUpdate.do',
            type: 'POST',
            data: {ids: ids.join(''), shipTypes: $('input[name="shipTypes"]:checked').val(), shipAmounts: $('input[name="shipAmounts"]').val(), agentShipAmounts: $('input[name="agentShipAmounts"]').val()},
            success: function(msg){
                if('success' == msg){
                    $('#batch-ship-setup').modal('hide');
                }
                msg = msg=='success'?'运费设置成功':'运费设置失败';
                $.gritter.add({
                    title: '消息',
                    text: msg,
                    class_name: 'gritter-success'
                });
                $('#table').bootstrapTable('refresh');
            }
        })
    });


    $('input[name="shipType"]').change(function(){
        if($(this).val() == 0){
            $('input[name="agentShipAmount"]').val(0);
            $('input[name="agentShipAmount"]').prop('disabled', 'disabled');
            $('input[name="shipAmount"]').removeAttr('disabled');
        }
        if($(this).val() == 1){
            $('input[name="shipAmount"]').val(0);
            $('input[name="shipAmount"]').prop('disabled', 'disabled');
            $('input[name="agentShipAmount"]').removeAttr('disabled');
        }
    });

    $('input[name="shipTypes"]').change(function(){
        if($(this).val() == 0){
            $('input[name="agentShipAmounts"]').val(0);
            $('input[name="agentShipAmounts"]').prop('disabled', 'disabled');
            $('input[name="shipAmounts"]').removeAttr('disabled');
        }
        if($(this).val() == 1){
            $('input[name="shipAmounts"]').val(0);
            $('input[name="shipAmounts"]').prop('disabled', 'disabled');
            $('input[name="agentShipAmounts"]').removeAttr('disabled');
        }
    });
</script>
</body>
</html>
