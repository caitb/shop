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
                            <button id="remove" class="btn btn-danger" disabled>
                                <i class="glyphicon glyphicon-remove"></i> 删除
                            </button>
                            <button id="add" class="btn btn-primary" id="add">
                                <i class="glyphicon glyphicon-add"></i> 添加
                            </button>
                        </div>
                        <table id="table"
                               data-toolbar="#toolbar"
                               data-search="true"
                               data-show-refresh="true"
                               data-show-toggle="true"
                        <%--data-show-columns="true"--%>
                        <%--data-show-export="true"--%>
                               data-detail-view="true"
                               data-detail-formatter="detailFormatter"
                               data-minimum-count-columns="2"
                        <%--data-show-pagination-switch="true"--%>
                               data-pagination="true"
                               data-id-field="id"
                               data-page-list="[10, 25, 50, 100, ALL]"
                               data-show-footer="false"
                               data-side-pagination="server"
                               data-url="/trial/list.do"
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
                                            title: '序号',
                                            field: 'id',
                                            rowspan: 2,
                                            align: 'center',
                                            valign: 'middle',
                                            sortable: true,
                                            footerFormatter: totalTextFormatter
                                        }, {
                                            title: '详情',
                                            colspan: 8,
                                            align: 'center'
                                        }
                                        ],
                                        [
                                            {
                                                field: 'name',
                                                title: '姓名',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center'
                                            },
                                            {
                                                field: 'mobile',
                                                title: '手机号码',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center'
                                            },
                                            {
                                                field: 'weixinId',
                                                title: '微信号',
                                                //sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center'
                                            },
                                            {
                                                field: 'skuName',
                                                title: '申请商品',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center'
                                            },
                                            {
                                                field: 'referrer',
                                                title: '推荐人',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center'
                                            },
                                            {
                                                title: '申请理由',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    return '<a href="http://www.baidu.com">查看</a>';
                                                }
                                            },
                                            {
                                                field: 'createTime',
                                                title: '申请时间',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center'
                                            },
//                                        {
//                                        field: 'price',
//                                        title: 'Item Price',
//                                        sortable: true,
//                                        align: 'center',
//                                        editable: {
//                                            type: 'text',
//                                            title: 'Item Price',
//                                            validate: function (value) {
//                                                value = $.trim(value);
//                                                if (!value) {
//                                                    return 'This field is required';
//                                                }
//                                                if (!/^$/.test(value)) {
//                                                    return 'This field needs to start width $.'
//                                                }
//                                                var data = $table.bootstrapTable('getData'),
//                                                        index = $(this).parents('tr').data('index');
//                                                console.log(data[index]);
//                                                return '';
//                                            }
//                                        },
//                                        footerFormatter: totalPriceFormatter
//                                    },
                                            {
                                                //field: 'operate',
                                                title: '操作项',
                                                align: 'center',
                                                events: operateEvents,
                                                formatter: operateFormatter
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
                                return [
                                    '&nbsp;<a class="edit detail-icon" href="javascript:void(0)" title="Edit">通过',
                                    '</a>',
                                    '&nbsp;<a class="like" href="javascript:void(0)" title="Like">拒绝',
                                    //'<i class="glyphicon glyphicon-heart"></i>',
                                    '</a>  ',
                                    '&nbsp;<a class="remove" href="javascript:void(0)" title="Remove">已通过',
                                    //'<i class="glyphicon glyphicon-remove"></i>',
                                    '</a>',
                                    '&nbsp;<a class="remove" href="javascript:void(0)" title="Remove">已拒绝',
                                    //'<i class="glyphicon glyphicon-remove"></i>',
                                    '</a>'
                                ].join('');
                            }

                            window.operateEvents = {
                                'click .like': function (e, value, row, index) {
                                    $.ajax({
                                        url: '<%=basePath%>menu/treeMenu.shtml',
                                        data: {userId: row.id},
                                        success: function (data) {
                                            //alert(data);
                                            $('#myModal .modal-body').html(data);
                                        }
                                    });
                                    $('#myModal').modal({
                                        show: true,
                                        backdrop: true
                                    });
                                },
                                'click .remove': function (e, value, row, index) {
                                    console.log('删除: ' + row.id);
                                    $table.bootstrapTable('remove', {
                                        field: 'id',
                                        values: [row.id]
                                    });
                                }
                            };

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

                            $('#add').on('click', function () {
                                $('#addModalLabel').html('添加管理员');
                                $('#addModal').modal({
                                    show: true,
                                    backdrop: true
                                });
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
                        模态框标题
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="userForm" action="<%=basePath%>user/add.do" method="post">
                        <div class="form-group">
                            <label for="userName" class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="userName" name="userName" placeholder="用户名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="trueName" class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="trueName" name="trueName" placeholder="姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="password" name="password" placeholder="密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control" id="email" name="email" placeholder="邮箱">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="sex" class="col-sm-2 control-label">性别</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="sex" name="sex" placeholder="性别">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="age" class="col-sm-2 control-label">年龄</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="age" name="age" placeholder="年龄">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phone" class="col-sm-2 control-label">电话</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="phone" name="phone" placeholder="电话">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="addSubmit">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <script>
        //保存授权信息
        $('#btnSubmit').on('click', function(){
            var zTree = $.fn.zTree.getZTreeObj("treeMenu");
            var treeNodes = zTree.getCheckedNodes(true);

            var menuIds = [];
            for(var i in treeNodes){
                menuIds.push(treeNodes[i].id);
            }

            $.ajax({
                url: '<%=basePath%>user/updateUserMenu.do',
                data: {userId: userId,pbMenuIds: menuIds},
                success: function(data){
                    alert(data);
                    $('#myModal').modal('hide');
                }
            });

        });

        //保存用户信息
        $('#addSubmit').on('click', function () {
            $.ajax({
                url: '<%=basePath%>user/add.do',
                type: 'post',
                data: $('#userForm').serialize(),
                success: function (data) {
                    alert(data);
                    $('#addModal').modal('hide');
                }
            });
        });
    </script>

</div>
</body>

</html>
