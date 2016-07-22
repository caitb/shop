<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" href="<%=basePath%>static/css/laydate.css" />

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
                                        <div class="form-inline">
                                            <div class="form-group">
                                                <label for="code">变更单编码</label>
                                                <input type="text" class="form-control" id="code" name="code" placeholder="变更单编码">
                                            </div>
                                            <div class="form-group">
                                                <label for="code">变更人姓名</label>
                                                <input type="text" class="form-control" id="realNamelike" name="realNamelike" placeholder="变更人姓名">
                                            </div>
                                            <div class="form-group">
                                                <label for="code">变更人手机号</label>
                                                <input type="text" class="form-control" id="mobilelike" name="mobilelike" placeholder="变更人手机号">
                                            </div>
                                            <button type="button" class="btn btn-default" id="searchBtn">查询</button>
                                        </div>
                                    </div>
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer" id="table" role="grid" aria-describedby="sample-table-2_info"
                                           data-toolbar="#toolbar"
                                           data-detail-view="false"
                                           data-detail-formatter="detailFormatter"
                                           data-minimum-count-columns="2"
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


                        <div id="modal-library" class="modal fade" tabindex="-1" aria-hidden="true" style="display: none;">

                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header no-padding">
                                        <div class="table-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                <span class="white">×</span>
                                            </button>
                                            变更人详细信息
                                        </div>
                                    </div>

                                    <%-----------------------------------------%>
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
                                                                            <div class="profile-info-name"> 手机号码 </div>

                                                                            <div class="profile-info-value">
                                                                                <span class="" id="mobile"></span>
                                                                            </div>
                                                                        </div>

                                                                        <div class="profile-info-row">
                                                                            <div class="profile-info-name"> 真实姓名 </div>

                                                                            <div class="profile-info-value">
                                                                                    <span class="" id="realName"></span>
                                                                            </div>
                                                                        </div>

                                                                        <div class="profile-info-row">
                                                                            <div class="profile-info-name"> 平台昵称 </div>

                                                                            <div class="profile-info-value">
                                                                                <span class="editable editable-click" id="wxNkName"></span>
                                                                            </div>
                                                                        </div>

                                                                    </div>

                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div><!-- /.col -->

                                                </div><!-- /.row -->

                                            </div><!-- /.page-content-area -->
                                        </div><!-- /.page-content -->
                                    </div><!-- /.main-content -->
                                    <%---------------------------------------------------------%>

                                    <div class="modal-footer no-margin-top">
                                        <div class="col-xs-5 col-sm-5 col-sm-offset-5">
                                            <button class="btn btn-sm btn-danger pull-left" id="cancelSave" type="button" data-dismiss="modal">
                                                关闭
                                            </button>
                                        </div>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div>

                        <%-------------------------------------------------------------------%>
                        <div id="modal-library-detail" class="modal fade" tabindex="-1" aria-hidden="true" style="display: none;">

                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header no-padding">
                                        <div class="table-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                <span class="white">×</span>
                                            </button>
                                            商品详情
                                        </div>
                                    </div>

                                    <%-------------%>
                                    <!-- /section:basics/sidebar -->
                                    <div class="main-content">

                                        <!-- /section:basics/content.breadcrumbs -->
                                        <div class="page-content">

                                            <!-- /section:settings.box -->
                                            <div class="page-content-area">

                                                <div class="row">
                                                    <div class="col-xs-12 col-sm-12">

                                                        <div>
                                                            <div class="user-profile row">
                                                                <div class="col-xs-12 col-sm-8 col-sm-offset-2">

                                                                    <!-- #section:pages/profile.info -->
                                                                    <div id="itemDetail" class="profile-user-info profile-user-info-striped">



                                                                    </div>

                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div><!-- /.col -->

                                                </div><!-- /.row -->

                                            </div><!-- /.page-content-area -->
                                        </div><!-- /.page-content -->
                                    </div><!-- /.main-content -->
                                    <%-------------%>

                                    <div class="modal-footer no-margin-top">
                                        <div class="col-xs-5 col-sm-5 col-sm-offset-5">
                                            <button class="btn btn-sm btn-danger pull-left" id="cancelSave2" type="button" data-dismiss="modal">
                                                关闭
                                            </button>
                                        </div>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div>
                        <%-------------------------------------------------------------------%>

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
<script src="<%=basePath%>static/js/laydate.js"></script>
<script>
    laydate({
        elem: '#beginTime'
    });
    laydate({
        elem: '#endTime'
    });
</script>

<script>
    var $table = $('#table'),
            $remove = $('#remove'),
            selections = [];

    function initTable() {
        $table.bootstrapTable({
            url: '<%=basePath%>storagechange/list.do',
            //height: getHeight(),
            locale: 'zh-CN',
            striped: true,
            queryParamsType: 'pageNo',
            queryParams: function(params){
                if($('#code').val()) params.code = $('#code').val();
                if($('#realNamelike').val()) params.realNamelike = $('#realNamelike').val();
                if($('#mobilelike').val()) params.mobilelike = $('#mobilelike').val();
                return params;
            },
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
                        //sortable: true,
                        footerFormatter: totalTextFormatter,
                        formatter: function(value, row, index){
                            if(row && row.id){
                                return row.id;
                            }
                        }
                    },
                    {
                        field: 'create_time',
                        title: '创建时间',
                        //sortable: true,
                        //editable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row && row.create_time){
                                return new Date(row.create_time).pattern('yyyy-MM-dd HH:mm:ss');
                            }
                        }
                    },
                    {
                        field: 'create_man',
                        title: '创建人',
                        //sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row && row.create_man_name){
                                return row.create_man_name;
                            }
                        }
                    },
                    {
                        field: 'bill_reason',
                        title: '单据创建原因',
                        //sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row && row.bill_reason){
                                return row.bill_reason;
                            }
                        }
                    },
                    {
                        field: 'code',
                        title: '变更单编码',
                        //sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row && row.code){
                                return row.code;
                            }
                        }
                    },
                    {
                        field: 'user_id',
                        title: '变更人',
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row && row.user_id_name){
                                return '<a class="detailUser" href="javascript:void(0);" userid="'+row.user_id+'">'+row.user_id_name+'</a>';
                            }
                        },
                        events: {
                            'click .detailUser': function(e, value, row, index){
                                //alert($(this).attr("userid"));
                                $.ajax({
                                    url: '<%=basePath%>storagechange/detail.do?id=' + $(this).attr("userid"),
                                    type: 'get',
                                    dataType:'json',
                                    success: function(data){
                                        if(data.state == "success"){
                                            var user = data.user;
                                            $("#mobile").html(user.mobile);
                                            $("#realName").html(user.realName);
                                            $("#wxNkName").html(user.wxNkName);
                                            $("#modal-library").modal("show");
                                        }
                                    }
                                });
                            }
                        }
                    },
                    {
                        field: 'product_quantity',
                        title: '商品总数量',
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row && row.product_quantity){
                                return row.product_quantity;
                            }
                        }
                    },
                    {
                        title: '商品详情',
                        align: 'center',
                        formatter: function(value, row, index){
                            return '<a class="detailStorage" href="javascript:void(0);" billid="'+row.id+'">查看</a>'
                        },
                        events: {
                            'click .detailStorage': function(e, value, row, index){
                                $.ajax({
                                    url: '<%=basePath%>storagechange/detailItem.do?id=' + $(this).attr("billid"),
                                    type: 'get',
                                    dataType:'json',
                                    success: function(data){
                                        if(data.state == "success"){
                                            var rows = data.rows;
                                            if(rows.length){
                                                var tempHtml = '';
                                                for(var i = 0; i < rows.length; i++){
                                                    var obj = rows[i];
                                                    tempHtml += '<div class="profile-info-row">' +
                                                                    '<div class="profile-info-name"> 真实姓名 </div>' +
                                                                    '<div class="profile-info-value">' +
                                                                        '<span> ' + (obj.realName?obj.realName:"-") + ' </span>' +
                                                                    '</div>' +
                                                                '</div>' +
                                                                '<div class="profile-info-row">' +
                                                                    '<div class="profile-info-name"> 微信昵称 </div>' +
                                                                    '<div class="profile-info-value">' +
                                                                    '<span> ' + (obj.nkName?obj.nkName:"-") + ' </span>' +
                                                                    '</div>' +
                                                                '</div>' +
                                                                '<div class="profile-info-row">' +
                                                                    '<div class="profile-info-name"> sku </div>' +
                                                                    '<div class="profile-info-value">' +
                                                                    '<span> ' + (obj.skuName?obj.skuName:"-") + ' </span>' +
                                                                    '</div>' +
                                                                '</div>' +
                                                                '<div class="profile-info-row">' +
                                                                    '<div class="profile-info-name"> 数量 </div>' +
                                                                    '<div class="profile-info-value">' +
                                                                        '<span> ' + (obj.quantity?obj.quantity:"-") + ' </span>' +
                                                                    '</div>' +
                                                                '</div>';
                                                }
                                                $("#itemDetail").empty().append(tempHtml);
                                                $("#modal-library-detail").modal("show");
                                            }else{
                                                alert('没有查询到数据');
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    },
                    {
                        field: 'status',
                        title: '状态',
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.status == 0){
                                return '未处理';
                            }else if(row.status == 1){
                                return '已审核';
                            }else if(row.status == 2){
                                return '已处理';
                            }else if(row.status == 3){
                                return '已取消';
                            }
                        }
                    },
                    /*{
                        field: 'type',
                        title: '类型',
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            //if(row && row.type){
                                return row.type;
                            //}
                        }
                    },*/
                    {
                        field: 'remark',
                        title: '备注',
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row && row.remark){
                                return row.remark;
                            }
                        }
                    },
                    {
                        title: '操作项',
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row && row.status==0){
                                return '<a class="detail" href="javascript:void(0);">审核</a>';
                            }else if(row && row.status==1){
                                return '<a class="detail" href="javascript:void(0);">处理</a>';
                            }
                        },
                        events: {
                            'click .detail': function(e, value, row, index){

                                $("#modal-library").modal("show");
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

        $('#searchBtn').on('click', function(){
            $table.bootstrapTable('refresh');
        });
        $('#orderType').change(function(){
            $table.bootstrapTable('refresh');
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


</script>
</body>
</html>
