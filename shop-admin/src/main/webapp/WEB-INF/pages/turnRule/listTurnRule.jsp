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
    <link rel="stylesheet" href="<%=basePath%>static/css/laydate.css" />

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
                                        <div class="form-inline pull-right">
                                            <div class="form-group">
                                                <label for="beginTime">创建日期：</label>
                                            </div>
                                            <div class="form-group">
                                                <input type="text" class="form-control" id="beginTime" name="beginTime" placeholder="开始日期" data-date-format="yyyy-mm-dd hh:ii">
                                            </div>
                                            <div class="form-group">
                                                <input type="text" class="form-control" id="endTime" name="endTime" placeholder="结束日期" data-date-format="yyyy-mm-dd hh:ii">
                                            </div>

                                            <button type="button" class="btn btn-default" id="searchBtn">查询</button>
                                        </div>
                                    </div>
                                    <table class="table table-striped table-bordered table-hover dataTable no-footer" id="table" role="grid" aria-describedby="sample-table-2_info"
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

<script src="<%=basePath%>static/js/laydate.js"></script>
<script src="<%=basePath%>static/js/date-util.js"></script>
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

    var turnTableInfo = getDataJson('<%=basePath%>turn/list.do?pageNumber=1&pageSize=1000');
    var turnTables = turnTableInfo.rows;

    function getDataJson(url, data) {

        var result;

        var request = {
            url:url,
            dataType: 'json',
            async: false,
            success : function(data) {
                result = data;
            }
        };

        if(data) {
            request.data = data;
        }

        $.ajax(request);

        return result;


    }

    function changeTurnRuleStatus(turnRule, target) {
        bootbox.confirm('确定'+target.text()+'抽奖活动？', function(result) {
            if(result) {
                $.ajax({
                    url : '<%=basePath%>turnRule/changeStatus',
                    data : {
                        id : turnRule.id,
                        status : target.attr('status')
                    },
                    success : function(data) {
                        if(data == 'success') {
                            $table.bootstrapTable('refresh');
                        } else {
                            alert('失败');
                        }
                    }
                })
            }
        });
    }

    function initTable() {
        $table.bootstrapTable({
            url: '<%=basePath%>turnRule/list.do',
            //height: getHeight(),
            locale: 'zh-CN',
            striped: true,
            //multipleSearch: true,
            queryParamsType: 'pageNo',
            queryParams: function(params){
                if($('#beginTime').val()){
                    params.beginTime = $('#beginTime').val();
                }
                if($('#endTime').val()){
                    params.endTime = $('#endTime').val();
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
                    {
                        title: '活动名称',
                        align: 'center',
                        valign: 'middle',
                        footerFormatter: totalTextFormatter,
                        formatter: function(value, row, index){
                            if(row.name){
                                return row.name;
                            }
                        }
                    },
                    {
                        title: '活动对象',
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.type){
                                if(row.type == 0) {
                                    return 'B 端';
                                }
                                if(row.type == 1) {
                                    return 'C 端';
                                }
                                return '-';
                            }
                        }
                    },
                    {
                        title:'抽奖方式',
                        formatter: function (value, row, index) {
                            for(var i in turnTables) {
                                var turnTable = turnTables[i];
                                if(turnTable.id == row.turnTableId) {
                                    return turnTable.name;
                                }
                            }

                            return row.turnTableId;
                        }
                    },
                    {
                        title : '状态',
                        formatter : function(value, row, index) {
                            if(row.status == 0) {
                                return '结束';
                            } else if(row.status == 1) {
                                return '进行中';
                            }
                            return '-';
                        }
                    },
                    {
                        title: '创建时间',
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.createTime) {
                                return new Date(row.createTime).pattern("yyyy-MM-dd HH:mm:ss");
                            }
                        }
                    },
                    {
                        title : '备注',
                        formatter : function(value, row, index) {
                            return row.remark;
                        }
                    },
                    {
                        title: '操作项',
                        align: 'center',
                        formatter: function(value, row, index){
                            var operateElement;
                            if(row.status == 0) {
                                operateElement = '<a class="operate" status="1" href="javascript:void(0);">开启</a>';
                            } else if(row.status == 1) {
                                operateElement = '<a class="operate" status="0" href="javascript:void(0);">结束</a>';
                            }

                            return '<a class="edit" href="javascript:void(0);">修改</a> &nbsp;&nbsp;&nbsp;&nbsp;'+operateElement;
                        },
                        events: {
                            'click .edit': function(e, value, row, index){
                                parent.window.$('#myTabbable').closeTab('turnTableRule-edit');
                                parent.window.$('#myTabbable').add('turnTableRule-edit', '修改抽奖活动', '<%=basePath%>turnRule/add.shtml?id='+row.id);
                            },
                            'click .operate' : function (e, value, row, index) {
                                changeTurnRuleStatus(row, $(this));
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
        //物流状态
        if(res.wuliuList !=null){
            var $select = $('#shipStatus');
            $select.empty();
            $select.append('<option value=\"\" selected=\"selected\">全部</option>');
            for(var i=0, len = res.wuliuList.length;i<len;i++){
                $select.append('<option value="'+res.wuliuList[i].key+'">'+res.wuliuList[i].value+'</option>');
            }
        }
        //订单状态
        if(res.orderStatusList !=null){
            var $select = $('#orderStatus');
            $select.empty();
            $select.append('<option value=\"\" selected=\"selected\">全部</option>');
            for(var i=0, len = res.orderStatusList.length;i<len;i++){
                $select.append('<option value="'+res.orderStatusList[i].key+'">'+res.orderStatusList[i].value+'</option>');
            }
        }
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

    $('.audit').on('click', function(){
        var auditType = $(this).attr('audit-status');
        var auditCause = $('textarea[name="auditCause"]').val();

        $('#auditType').val(auditType);
        if(!auditCause){
            $.gritter.add({
                title: '温馨提示',
                text: '请填写审核记录!',
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
