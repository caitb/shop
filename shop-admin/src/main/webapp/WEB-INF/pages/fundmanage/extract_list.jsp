<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

                               data-url="<%=basePath%>fundmanage/extract/list.do"

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
                                    queryParams: function(params){
                                        var pid = '${pid}';
                                        if(pid){
                                            params.pid = pid;
                                        }
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
                                            /* {
                                             checkbox: true,
                                             rowspan: 2,
                                             align: 'center',
                                             valign: 'middle'
                                             },
                                             {
                                             title: '序号',
                                             rowspan: 2,
                                             align: 'center',
                                             valign: 'middle',
                                             footerFormatter: totalTextFormatter,
                                             formatter: function(value, row, index){
                                             return index + 1;
                                             }
                                             },
                                             {
                                             title: '合伙证书编号',
                                             field: 'code',
                                             rowspan: 2,
                                             align: 'center',
                                             valign: 'middle',
                                             //sortable: true,
                                             footerFormatter: totalTextFormatter,
                                             formatter: function(value, row, index){
                                             if(code){
                                             return row.code;
                                             }
                                             return '-';
                                             }
                                             },*/
                                            {
                                                title: '详情',
                                                colspan: 10,
                                                align: 'center'
                                            }
                                        ],
                                        [
                                            {
                                                field: 'applyTime',
                                                title: '申请日期',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    return new Date(row.applyTime).pattern('yyyy-MM-dd HH:mm:ss');
                                                }
                                            },
                                            {
                                                //field: 'realName',
                                                title: '申请人',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    if(row.comUser){
                                                        return row.comUser.realName
                                                    }
                                                    return '-';
                                                }
                                            },
                                            {
                                                field: 'extractFee',
                                                title: '申请金额',
                                                //sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                /*formatter: function (value, row, index) {
                                                 if(row.comUser){
                                                 return row.comUser.wxId
                                                 }
                                                 return '-';
                                                 },*/
                                                align: 'center'
                                            },
                                            /* {
                                             field: 'skuName',
                                             title: '合伙商品',
                                             sortable: true,
                                             //editable: true,
                                             footerFormatter: totalNameFormatter,
                                             align: 'center',
                                             },*/
                                            {
                                                //field: 'name',
                                                title: '账户余额',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    if(row.comUserAccount){
                                                        return row.comUserAccount.extractableFee;
                                                    }
                                                    return "-";
                                                }
                                            },
                                            {
                                                field: 'extractWay',
                                                title: '提现方式',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    if(row.extractWay==1){
                                                        return "微信";
                                                    }else if(row.extractWay==2){
                                                        return "支付宝";
                                                    }else if(row.extractWay==3){
                                                        return "银行卡";
                                                    }
                                                }
                                            },
                                            {
                                                field: 'auditType',
                                                title: '状态',
                                                sortable: true,
                                                //editable: true,
                                                footerFormatter: totalNameFormatter,
                                                align: 'center',
                                                formatter: function(value, row, index){
                                                    if(row.auditType==0){
                                                        return "待审核";
                                                    }else if(row.auditType==1){
                                                        return "已拒绝";
                                                    }else if(row.auditType==2){
                                                        return "待打款";
                                                    }else if(row.auditType==3){
                                                        return "已付款";
                                                    }
                                                }

                                            },
                                            /*{
                                             field: 'isPay',
                                             title: '是否支付',
                                             sortable: true,
                                             //editable: true,
                                             footerFormatter: totalNameFormatter,
                                             align: 'center',
                                             formatter: function(value, row, index){
                                             if(row.isPay==0){
                                             return "否";
                                             }else if(row.isPay==1){
                                             return "是";
                                             }
                                             }
                                             },
                                             {
                                             field: 'lowerCount',
                                             title: '下级合伙人',
                                             sortable: true,
                                             //editable: true,
                                             footerFormatter: totalNameFormatter,
                                             align: 'center',
                                             formatter: function(value, row, index){
                                             //return '<a class="xiaji" href="javascript:void(0);">'+row.lowerCount+'人</a>';
                                             return '<a href="<%=basePath%>userSku/list.shtml?pid='+row.comUser.id+'">'+row.lowerCount+'人</a>';
                                         },
                                         events: {
                                         'click .xiaji': function(e, value, row, index){
                                         $('#table').bootstrapTable('destroy');
                                         window.location.open('<%=basePath%>userSku/list.shtml?pid=' + row.comUser.id);
                                         }
                                         }
                                         },
                                         {
                                         field: 'isCertificate',
                                         title: '是否申请证书',
                                         sortable: true,
                                         //editable: true,
                                         footerFormatter: totalNameFormatter,
                                         align: 'center',
                                         formatter: function(value, row, index){
                                         if(row.isCertificate==0){
                                         return "否";
                                         }else if(row.isCertificate==1){
                                         return "是";
                                         }
                                         }
                                         },*/
                                            {
                                                //field: 'operate',
                                                title: '操作项',
                                                align: 'center',
                                                events: {
                                                    'click .view': function(e, value, row, index){
                                                        $.ajax({
                                                            url: '<%=basePath%>fundmanage/extract/toaudit.do',
                                                            data: {id: row.id},
                                                            success: function(data){
                                                                $('#myModal1 .modal-body').html(data);
                                                                $('#myModal1').modal({
                                                                    show: true,
                                                                    backdrop: true
                                                                });
                                                            }
                                                        });
                                                    }
                                                },
                                                formatter: function(value, row, index){
                                                    return ['<a class="view" href="javascript:void(0);">查看</a>',
                                                        '&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="pay()" href="javascript:void(0);">确认收款</a>'
                                                    ].join('');
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
                                    $.get('/userSku/person.shtml', {id: row.comUser.id}, function (res) {
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
                                var sArr = [];
                                //alert(row.id);

                                sArr.push( '&nbsp;<a href="javascript:void(0)" onclick="cha('+row.id+')" title="Edit">查看</a>');
                                if(row.auditType==2){
                                    sArr.push( '&nbsp;&nbsp;|&nbsp;&nbsp;<a href="/fundmanage/extract/pay.do?id='+ row.id +'">确认付款</a>');
                                }
                                //sArr.push( '&nbsp;|<a href="javascript:void(0)" onclick="changeLeader('+row.id+')" title="Edit">更改上级</a>');

                                return sArr;
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
                                <%--var scripts = [--%>
                                <%--location.search.substring(1) || '<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table.min.js',--%>
                                <%--'<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-export.js',--%>
                                <%--'<%=basePath%>static/class/bootstrap-3.3.5-dist/js/tableExport.js',--%>
                                <%--'<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-table-editable.js',--%>
                                <%--'<%=basePath%>static/class/bootstrap-3.3.5-dist/js/bootstrap-editable.js'--%>
                                <%--],--%>
                                <%--eachSeries = function (arr, iterator, callback) {--%>
                                <%--callback = callback || function () {--%>
                                <%--};--%>
                                <%--if (!arr.length) {--%>
                                <%--return callback();--%>
                                <%--}--%>
                                <%--var completed = 0;--%>
                                <%--var iterate = function () {--%>
                                <%--iterator(arr[completed], function (err) {--%>
                                <%--if (err) {--%>
                                <%--callback(err);--%>
                                <%--callback = function () {--%>
                                <%--};--%>
                                <%--}--%>
                                <%--else {--%>
                                <%--completed += 1;--%>
                                <%--if (completed >= arr.length) {--%>
                                <%--callback(null);--%>
                                <%--}--%>
                                <%--else {--%>
                                <%--iterate();--%>
                                <%--}--%>
                                <%--}--%>
                                <%--});--%>
                                <%--};--%>
                                <%--iterate();--%>
                                <%--};--%>

                                <%--eachSeries(scripts, getScript, initTable);--%>

                                initTable();
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
    <%--提现申请1--%>
    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        申请信息
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="time"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="proposer"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="amount"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="balance"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="way"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" id="isOpendId" ></label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" id="audit">通过申请</button>
                    <button type="button" class="btn btn-danger" id="refuse">拒绝申请</button>
                    <%--<button type="button" class="btn btn-primary" id="userSubmit">
                        提交
                    </button>--%>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <%--提现申请2--%>
    <div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        申请信息
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class ="time"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="proposer"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="amount"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="balance"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="way"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="card"></label>
                        </div>
                    </div>


                    <div class="modal-footer">
                        <button type="button" class="btn btn-success">通过申请</button>
                        <button type="button" class="btn btn-danger">拒绝申请</button>
                        <%--<button type="button" class="btn btn-primary" id="userSubmit">
                            提交
                        </button>--%>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <%--提现申请3--%>
    <div class="modal fade" id="myModal3" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        申请信息
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="time"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="proposer"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="amount"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="balance"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="way"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" class="card"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" id="bankName"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" id="depositBank"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label class="col-sm-3 control-label no-padding-right" id="cardOwner"></label>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-success">通过申请</button>
                        <button type="button" class="btn btn-danger">拒绝申请</button>
                        <%--<button type="button" class="btn btn-primary" id="userSubmit">
                            提交
                        </button>--%>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>



    <script>

        /*function pass(id){
         $.ajax({
         type: "GET",
         url: '<%=basePath%>/fundmanage/extract/pass.do',
         data: {id: id},
         dataType: "json",
         success:function(data){
         $('#addModal').modal({
         close:true,
         });
         }
         })
         }*/
        //查看
        /*function cha(id){
         $.ajax({
         type: "GET",
         url: '<%=basePath%>extract/findById.do',
         data: {id: id},
         dataType: "json",
         success: function (data) {
         var extractWay = data["extract"].extractWay;

         $(".time").html("申请时间 : " +data["extract"].applyTime);
         $(".proposer").html("申请人 :  "+data["extract"].comUser.realName);
         $(".amount").html("申请金额 :  "+data["extract"].extractFee);
         $(".balance").html("账户余额 :  "+data["extract"].comUserAccount.extractableFee);
         $(".way").html("提现方式 :  "+extractWay);
         //判断状态
         if(extractWay==1){
         $("#isOpendId").html("微信绑定状态 :  "+data["extract"].comUser.openid);
         $('#myModal1').modal({
         show: true,
         backdrop: true
         });
         }
         if(extractWay==2){
         $(".card").html("支付宝账号 :  "+data["extract"].bankCard);
         $('#myModal2').modal({
         show: true,
         backdrop: true
         });
         }
         if(extractWay==3){
         $(".card").html("银行卡号 :  "+data["extract"].bankCard);
         $("#bankName").html("银行名称 :  "+data["extract"].bankName);
         $("#depositBank").html("开户行名称 :  "+data["extract"].depositBankName);
         $("#cardOwner").html("开户行名称 :  "+data["extract"].cardOwnerName);
         $('#myModal3').modal({
         show: true,
         backdrop: true
         });
         }*/

        //option属性
        /*if((data["certificateInfo"].comUserList).length>0 && data["certificateInfo"].comUserList[0]!=null){
         var comUserList = {upperList:data["certificateInfo"].comUserList};
         $("#userList").val(comUserList);
         $.each(data["certificateInfo"].comUserList,function(index,value){
         $('#userList').append("<option value='"+ value.id+"'>"+ value.realName +"</option>");
         });
         }else{
         $("#userSubmit").attr("disabled", true);
         }
         }
         });*/



        $('#userSubmit').on('click', function () {
            var id = $('#userSkuId').val();
            var pid = $("#userList").val();
            $.ajax({
                url: '<%=basePath%>certificate/updateUpper.do',
                type: 'post',
                data: {id:id,pid:pid},
                success: function (data) {
                    $('#myModal1').modal('hide');
                    alert(data);
                }
            });
        });
        //通过
        $('#audit').on('click', function(){
            var extractApplyId = $('#extractApplyId').val();
            //alert('extractApplyId: ' + extractApplyId);
            $.ajax({
                type: "GET",
                url: '<%=basePath%>/fundmanage/extract/pass.do',
                data: {id: extractApplyId},
                dataType: "json",
                success:function(data){
                    alert("审核通过成功");
                    document.location.reload();
                }
            })
        })
        //拒绝
        $('#refuse').on('click', function(){
            var extractApplyId = $('#extractApplyId').val();
            //alert('extractApplyId: ' + extractApplyId);
            $.ajax({
                type: "GET",
                url: '<%=basePath%>/fundmanage/extract/refuse.do',
                data: {id: extractApplyId},
                dataType: "json",
                success:function(data){
                    alert("审核拒绝");
                    document.location.reload();
                }
            })
        })
        //确认付款
        function pay(){
            var extractApplyId = $('#extractApplyId').val();
            //alert('extractApplyId: ' + extractApplyId);
            $.ajax({
                type: "GET",
                url: '<%=basePath%>/fundmanage/extract/pay.do',
                data: {id: extractApplyId},
                dataType: "json",
                success:function(data){
                    alert("付款成功");
                    window.location.reload();
                }
            })
        }
    </script>

</div>
</body>

</html>
