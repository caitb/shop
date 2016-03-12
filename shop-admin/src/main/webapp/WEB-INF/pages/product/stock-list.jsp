<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>麦士商城 - 后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- Latest compiled and minified CSS -->
    <link href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-table-1.10.12-dist/bootstrap-table.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-editable.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-validator/css/bootstrapValidator.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/buttons.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/examples.css">


    <script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="<%=basePath%>static/class/bootstrap-table-1.10.12-dist/bootstrap-table.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-table-1.10.12-dist/extensions/multiple-search/bootstrap-table-multiple-search.js"></script>

    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/bootstrap-validator/js/bootstrapValidator.js"></script>
    <script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/ga.js"></script>
    <!-- Latest compiled and minified Locales -->
    <script src="<%=basePath%>static/class/bootstrap-table-1.10.12-dist/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="<%=basePath%>static/js/date-util.js"></script>

</head>

<body>
<div class="container-fluid">

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">

                    <div class="table-responsive">
                        <div id="toolbar">
                            <form class="form-inline">
                                <button id="remove" class="btn btn-danger" disabled>
                                    <i class="glyphicon glyphicon-remove"></i> 删除
                                </button>
                                <a class="btn btn-info" id="add" href="<%=basePath%>product/add.shtml">
                                    <i class="glyphicon glyphicon-add"></i> 添加
                                </a>
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
                        <table id="table"
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
                               data-url="/stock/list.do"
                               data-response-handler="responseHandler">
                        </table>
                        <script>
                            var $table = $('#table'),
                                    $remove = $('#remove'),
                                    selections = [];

                            function initTable() {
                                $table.bootstrapTable({
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
                                                rowspan: 2,
                                                align: 'center',
                                                valign: 'middle'
                                            }, {
                                            title: 'ID',
                                            field: 'id',
                                            rowspan: 2,
                                            align: 'center',
                                            valign: 'middle',
                                            sortable: true,
                                            footerFormatter: totalTextFormatter,
                                            formatter: function(value, row, index){
                                                return row.pfSkuStock.id;
                                            }
                                        }, {
                                            title: '详情',
                                            colspan: 5,
                                            align: 'center'
                                        }
                                        ],
                                        [
                                            {
                                                field: 'skuName',
                                                title: '商品名称',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    return row.comSku.name;
                                                }
                                            },
                                            {
                                                field: 'artNo',
                                                title: '货号',
                                                sortable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    if(row.comSpu && row.comSpu.artNo){
                                                        return row.comSpu.artNo;
                                                    }
                                                }
                                            },
                                            {
                                                field: 'uom',
                                                title: '计量单位',
                                                footerFormatter: totalNameFormatter,
                                                formatter: function (value, row, index) {
                                                    return row.comSku.uom;
                                                },
                                                align: 'center'
                                            },
                                            {
                                                field: 'store',
                                                title: '当前库存',
                                                sortable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    if(row.pfSkuStock){
                                                        return row.pfSkuStock.stock;
                                                    }
                                                }
                                            },
                                            {
                                                title: '增加库存',
                                                sortable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    if(row.pfSkuStock.frozenStock == 0){
                                                        return ['&nbsp;&nbsp;<a class="add" href="javascript:void(0)">增加库存</a>',
                                                                '&nbsp;&nbsp;<a class="frozen-stock" href="javascript:void(0)" >冻结库存</a>'
                                                        ].join(' ');
                                                    }else if(row.pfSkuStock.frozenStock == 1){
                                                        return '<a class="frozen-stock" href="javascript:void(0)" >激活库存</a>';
                                                    }
                                                },
                                                events: {
                                                    'click .add': function(e, value, row, index){
                                                        $('#stockId').val(row.pfSkuStock.id);
                                                        $('#addModal').modal({
                                                            show: true,
                                                            backdrop: true
                                                        });
                                                    },
                                                    'click .frozen-stock': function(e, value, row, index){
                                                        $.ajax({
                                                            url: '<%=basePath%>stock/update.do',
                                                            data: {id: row.pfSkuStock.id, frozenStock: row.pfSkuStock.frozenStock==0?1:0},
                                                            success: function(msg){
                                                                if(msg == 'success') $table.bootstrapTable('refresh');
                                                            }
                                                        });
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

                        </script>


                    </div><!-- /.table-responsive -->
                </div><!-- /span -->
            </div><!-- /row -->

        </div><!-- /.col -->
    </div>

    <!-- 授权模态框（Modal） -->
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
                        授权列表
                    </h4>
                </div>
                <div class="modal-body">
                    在这里添加一些文本
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <!-- 添加管理员模态框（Modal） -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="addModalLabel">
                        增加库存
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="stockForm" action="<%=basePath%>stock/update.do" method="post">
                        <div class="form-group">
                            <label for="stock" class="col-sm-3 control-label">追加库存量</label>
                            <div class="col-sm-8">
                                <input type="hidden" id="stockId" name="id" value="" />
                                <input type="text" class="form-control" id="stock" name="stock" placeholder="">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">关闭
                            </button>
                            <button type="submit" class="btn btn-info" id="addSubmit">
                                提交更改
                            </button>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <script>
        $(document).ready(function() {
            $('#stockForm').bootstrapValidator({
                        message: '必须填写',
                        feedbackIcons: {
                            valid: 'glyphicon glyphicon-ok',
                            invalid: 'glyphicon glyphicon-remove',
                            validating: 'glyphicon glyphicon-refresh'
                        },
                        fields: {
                            stock: {
                                message: '请输入一个数字',
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
                            url: '<%=basePath%>stock/update.do',
                            type: 'post',
                            data: $('#stockForm').serialize(),
                            success: function(msg){
                                $('#addModal').modal("hide");
                                $table.bootstrapTable('refresh');
                            }
                        });
                    });
        });
    </script>

</div>
</body>

</html>
