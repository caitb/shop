<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.css">
<link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-editable.css">
<link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/examples.css">

<script src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
<script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table.min.js"></script>

<script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/ga.js"></script>
<!-- Latest compiled and minified Locales -->
<script src="<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-zh-CN.min.js"></script>

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
                           data-url="/user/list.do"
                           data-response-handler="responseHandler">
                    </table>
                    <script>
                        var $table = $('#table'),
                                $remove = $('#remove'),
                                selections = [];

                        function initTable() {
                            $table.bootstrapTable({
                                //height: getHeight(),
                                striped: true,
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
                                        footerFormatter: totalTextFormatter
                                    }, {
                                        title: '详情',
                                        colspan: 8,
                                        align: 'center'
                                    }
                                    ],
                                    [
                                        {
                                            field: 'userName',
                                            title: '用户名',
                                            sortable: true,
                                            //editable: true,
                                            footerFormatter: totalNameFormatter,
                                            align: 'center'
                                        },
                                        {
                                            field: 'trueName',
                                            title: '姓名',
                                            sortable: true,
                                            //editable: true,
                                            footerFormatter: totalNameFormatter,
                                            align: 'center'
                                        },
                                        {
                                            field: 'password',
                                            title: '密码',
                                            //sortable: true,
                                            //editable: true,
                                            footerFormatter: totalNameFormatter,
                                            formatter: function(value, row, index){
                                                return '******';
                                            },
                                            align: 'center'
                                        },
                                        {
                                            field: 'email',
                                            title: '邮箱',
                                            sortable: true,
                                            //editable: true,
                                            footerFormatter: totalNameFormatter,
                                            align: 'center'
                                        },
                                        {
                                            field: 'sex',
                                            title: '性别',
                                            sortable: true,
                                            //editable: true,
                                            footerFormatter: totalNameFormatter,
                                            align: 'center'
                                        },
                                        {
                                            field: 'age',
                                            title: '年龄',
                                            sortable: true,
                                            //editable: true,
                                            footerFormatter: totalNameFormatter,
                                            align: 'center'
                                        },
                                        {
                                            field: 'phone',
                                            title: '电话',
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
                                if (index % 2 == 1) {
                                    $detail.html('Loading from ajax request...');
                                    $.get('/user/list.do', function (res) {
                                        $detail.html(res.replace(/\n/g, '<br>'));
                                    });
                                }
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
                                '&nbsp;<a class="like" href="javascript:void(0)" title="Like">授权',
                                //'<i class="glyphicon glyphicon-heart"></i>',
                                '</a>  ',
                                '<a class="remove" href="javascript:void(0)" title="Remove">冻结',
                                //'<i class="glyphicon glyphicon-remove"></i>',
                                '</a>'
                            ].join('');
                        }

                        window.operateEvents = {
                            'click .like': function (e, value, row, index) {
                                //alert('You click like action, row: ' + JSON.stringify(row));
                                $('#myModal .modal-body').empty();
                                $.ajax({
                                    url: '<%=basePath%>menu/treeMenu.shtml',
                                    data: {userId: row.id},
                                    success: function(data){
                                        //alert(data);
                                        $('#myModal .modal-body').html(data);
                                    }
                                });
                                $('#myModal').modal({
                                    show:true,
                                    backdrop:true
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

                        $('#add').on('click', function(){
                            $('#addModalLabel').html('添加管理员');
                            $('#addModal').modal({
                                show:true,
                                backdrop:true
                            });
                        });
                        $('#addSubmit').on('click', function(){
                            $.ajax({
                                url: '<%=basePath%>user/add.do',
                                type: 'post',
                                data: $('#userForm').serialize(),
                                success: function(data){
                                    alert(data);
                                    $('#addModal').modal({
                                        show:false,
                                        backdrop:false
                                    });
                                }
                            });
                        });
                    </script>


                </div><!-- /.table-responsive -->
            </div><!-- /span -->
        </div><!-- /row -->

    </div><!-- /.col -->
</div>

