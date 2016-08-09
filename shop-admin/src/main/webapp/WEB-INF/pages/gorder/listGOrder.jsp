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

<div id="modal-delivery" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header no-padding">
                <div class="table-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <span class="white">&times;</span>
                    </button>
                    发货信息
                </div>
            </div>

            <div class="modal-body no-padding">
                <div>
                    <div id="user-profile-1" class="user-profile row">
                        <div class="col-xs-12 col-sm-12 col-sm-offset-0">

                            <!-- #section:pages/profile.info -->
                            <form id="deliveryForm" isSubmiting="false" action="<%=basePath%>order/order/delivery.do">
                                <div class="profile-user-info profile-user-info-striped">

                                    <input type="hidden" name="sfOrderId" >

                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 订单号 </div>

                                        <div class="profile-info-value">
                                            <span id="orderCodeV"></span>
                                        </div>
                                    </div>

                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 奖品 </div>

                                        <div class="profile-info-value">
                                            <span id="giftName"></span>
                                        </div>
                                    </div>

                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 收货人 </div>

                                        <div class="profile-info-value">
                                            <span id="consignee"></span>
                                        </div>
                                    </div>

                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 收货地址 </div>

                                        <div class="profile-info-value">
                                            <span id="address"></span>
                                        </div>
                                    </div>

                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 联系电话 </div>

                                        <div class="profile-info-value">
                                            <span id="mobile"></span>
                                        </div>
                                    </div>

                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 邮编 </div>

                                        <div class="profile-info-value">
                                            <span id="postcode"></span>
                                        </div>
                                    </div>

                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 快递名称 </div>

                                        <div class="profile-info-value">
                                            <input type="hidden" id="shipManName" name="shipManName" value="" />
                                            <select class="form-control" name="shipManId">
                                            </select>
                                        </div>
                                    </div>

                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 快递单号 </div>

                                        <div class="profile-info-value">
                                            <input type="text" class="form-control" id="freight" name="freight" placeholder="快递单号">
                                        </div>
                                    </div>

                                    <input type="hidden" name="sfGorderId" >

                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer no-margin-top">
                <div class="col-xs-5 col-sm-5 col-sm-offset-4">
                    <input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5">
                    <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal" onclick="clearDeliveryModal">
                        取消
                    </button>
                    <button class="btn btn-sm btn-info pull-left ok" id="submitDeliveryForm">
                        确认
                    </button>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- PAGE CONTENT ENDS -->


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
                                                <label for="isShip" class="control-label">状态</label>
                                            </div>
                                            <div class="form-group">
                                                <select id="isShip" name="isShip" class="form-control">
                                                    <option value="">全部</option>
                                                    <option value="0">未发货</option>
                                                    <option value="1">已发货</option>
                                                </select>
                                            </div>
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


    // 查看快递信息
//    function viewFreight(gorderId) {
//        var $viewFreightDialog = $('#viewFreightDialog');
//        $viewFreightDialog.modal();
//
//        $.ajax({
//
//        })
//    }


    var $sendDialog = $('#modal-delivery');

    +function loadShipMan() {
        var $shipManSelect = $('select[name=shipManId]');
        console.log($shipManSelect);
        if(!$shipManSelect.html().trim()) {
            $shipManSelect.append('<option></option>');
            $.ajax({
                url : '<%=basePath%>comshipman/list.do',
                dataType : 'json',
                success : function(data) {
                    console.log(data);
                    $.each(data, function() {
                        var $option= $('<option value="'+this.id+'">'+this.name+'</option>');
                        $shipManSelect.append($option);
                    })
                }
            });
        }
    }();

    $('select[name=shipManId]').change(function(){
        var shipManName = $(this).find('option:selected').text().trim();
        $('input[name=shipManName]').val(shipManName);
    });

    $('#submitDeliveryForm').click(function() {
        var $sendForm = $('#deliveryForm');

        var isValid = true;
        $sendForm.find('[name]:visible').each(function() {
            if(!$(this).val()) {
                $(this).css('border-color', '#a94442');
                $(this).focus();
                isValid = false;
            }
        });

        if(!isValid) {
            alert('您有未填的信息 ！');
            return;
        }

        $.ajax({
            url : '<%=basePath%>promotion/deliveryGift.do',
            type : 'post',
            data : $sendForm.serialize(),
            success : function(data) {
                if(data == 'success') {
                    $sendDialog.modal('hide');
                    bootbox.alert("发货成功");
                    $table.bootstrapTable('refresh');
                }
            }
        });

    });

    function clearDeliveryModal() {
        $('.profile-info-value span:visible').text("");
        $sendDialog.find('input').val('');
        $sendDialog.find('select').val('');
    }

    function initTable() {
        $table.bootstrapTable({
            url: '<%=basePath%>gorder/list.do',
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
                params.isShip = $('[name=isShip]').val();
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
                        title: '订单号',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        footerFormatter: totalTextFormatter,
                        formatter: function(value, row, index){
                            if(row.gorder && row.gorder.gorderCode){
                                return row.gorder.gorderCode;
                            }
                        }
                    },
                    {
                        field: 'user_name',
                        title: '订单日期',
                        sortable: true,
                        // editable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.gorder && row.gorder.createTime) {
                                return new Date(row.gorder.createTime).pattern("yyyy-MM-dd HH:mm:ss");
                            }
                        }
                    },
                    {
                        title: '奖品',
                        sortable: true,
                        // editable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.item && row.item.giftName) {
                                return row.item.giftName;
                            }
                        }
                    },
                    {
                        title: '收货人',
                        sortable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.consignee && row.consignee.consignee) {
                                return row.consignee.consignee;
                            }
                        }
                    },
                    {
                        title: '领奖人',
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.comUser) {
                                if(row.comUser.ruleName) {
                                    return row.comUser.ruleName;
                                } else {
                                    return row.comUser.wxNkName;
                                }
                            }
                        }
                    },
                    {
                        title: '物流状态',
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.gorder && row.gorder.isShip==1) {
                                return "已发货";
                            } else {
                                return "未发货";
                            }
                        }
                    },
                    {
                        title: '操作',
                        align: 'center',
                        formatter: function(value, row, index){
                            if(row.gorder && row.gorder.isShip==1) {

                                return '<a class="look" href="javascript:void(0);">查看</a>';
                            } else if(row.gorder && row.gorder.isShip==0){
                                return '<a class="send" href="javascript:void(0);">发货</a>';
                            }
                        },
                        events: {
                            'click .look': function(e, value, row, index) {
                                console.log(row);
                                clearDeliveryModal();
                                $('#submitDeliveryForm').hide();


                                var consignee = row.consignee
                                var address = consignee.provinceName+consignee.cityName+consignee.regionName+consignee.address;

                                $('input[name=sfGorderId]').val(row.gorder.id);
                                $('#orderCodeV').text(row.gorder.gorderCode);
                                $('#giftName').text(row.item.giftName);
                                $('#address').text(address);
                                $('#mobile').text(consignee.mobile);
                                $('#postcode').html(consignee.zip);
                                $('#consignee').html(consignee.consignee);
                                $('[name=shipManId]').val(row.gorder.shipManId);
                                $('[name=shipManName]').val(row.gorder.shipManName);
                                $('[name=freight]').val(row.freight.freight);

                                $('[name=shipManId]').attr('disabled', 'disabled');
                                $('[name=shipManName]').attr('disabled', 'disabled');
                                $('[name=freight]').attr('disabled', 'disabled');

                                var $dialog = $('#modal-delivery');

                                $dialog.modal('show');
                            },
                            'click .send' : function(e, value, row, index) {
                                clearDeliveryModal();
                                $('#submitDeliveryForm').show();

                                var consignee = row.consignee
                                var address = consignee.provinceName+consignee.cityName+consignee.regionName+consignee.address;

                                $('input[name=sfGorderId]').val(row.gorder.id);
                                $('#orderCodeV').text(row.gorder.gorderCode);
                                $('#giftName').text(row.item.giftName);
                                $('#address').text(address);
                                $('#mobile').text(consignee.mobile);
                                $('#postcode').html(consignee.zip);
                                $('#consignee').html(consignee.consignee);

                                $('[name=shipManId]').removeAttr('disabled', 'disabled');
                                $('[name=shipManName]').removeAttr('disabled', 'disabled');
                                $('[name=freight]').removeAttr('disabled', 'disabled');

                                var $dialog = $('#modal-delivery');
                                $dialog.modal('show');
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
