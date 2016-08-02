<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>Tables - Masiis</title>

    <meta name="description" content="Static &amp; Dynamic Tables"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/dropzone.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/uncompressed/my-dropzone.css"/>


    <!-- text fonts -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-fonts.css"/>

    <!-- ace styles -->
    <%--<link rel="stylesheet" href="<%=basePath%>static/ace2/css/uncompressed/ace.css" id="main-ace-style" />--%>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-part2.min.css"/>
    <![endif]-->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-skins.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/jquery.gritter.css"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-ie.min.css"/>
    <![endif]-->

    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.config.js"></script>--%>
    <%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.all.js"></script>--%>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>static/class/upload-plugin/core/zyFile.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>static/class/upload-plugin/control/js/zyUpload.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/upload-plugin/core/jq22.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/lang/zh-cn/zh-cn.js"></script>--%>

    <!-- ace settings handler -->
    <script src="<%=basePath%>static/ace2/js/uncompressed/ace-extra.js"></script>


    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>static/class/bootstrap-validator/js/bootstrapValidator.js"></script>

    <!--[if lte IE 8]>
    <script src="<%=basePath%>static/ace2/js/html5shiv.min.js"></script>
    <script src="<%=basePath%>static/ace2/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="no-skin">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <!-- /section:basics/sidebar -->
    <div class="main-content">

        <!-- /section:basics/content.breadcrumbs -->
        <div class="page-content">

            <!-- /section:settings.box -->
            <div class="page-content-area">

                <div class="row">
                    <div class="col-xs-10 col-xs-offset-1">
                        <!-- PAGE CONTENT BEGINS -->
                        <form class="form-horizontal" role="form" id="turnForm">
                            <!-- #section:elements.form -->

                            <input type="hidden" name="turnTableId"/>

                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">转盘名称</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="name" placeholder="">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="remark" class="control-label col-sm-2">转盘备注</label>
                                <div class="col-sm-3">
                                    <input type="text" id="remark" name="remark" class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2"></label>
                                <div class="col-sm-10" style="background-color: gainsboro">
                                    <div class="row">
                                        <label class="control-label col-sm-3" style="text-align: center;">序号</label>
                                        <label class="control-label col-sm-3" style="text-align: center;">奖品</label>
                                        <label class="control-label col-sm-3" style="text-align: center;">概率</label>
                                        <label class="control-label col-sm-2" style="text-align: center;">总数量</label>
                                        <label class="control-label col-sm-1" style="text-align: center;">操作</label>
                                    </div>
                                </div>
                            </div>

                            <div id="turn-rule-template" class="form-group turn-rule" style="display: none;">
                                <label class="control-label col-sm-2 rule-name">奖品项</label>
                                <div class="col-sm-10">
                                    <div class="row">
                                        <input type="hidden" name="turnTableGiftId" />
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" attr="sort">
                                        </div>
                                        <div class="col-sm-3">
                                            <select class="form-control" attr="giftId">
                                                <option></option>
                                            </select>
                                        </div>
                                        <div class="col-sm-3 ">
                                            <div class="input-group">
                                                <input type="text" class="form-control" attr="probability">
                                                <div class="input-group-addon">%</div>
                                            </div>

                                        </div>
                                        <div class="col-sm-2"><input type="text" class="form-control" attr="totalQuantity"></div>
                                        <div class="col-sm-1">
                                            <button type="button" class="btn btn-warning removeRule">删除</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="addTurnRule" class="control-label col-sm-2"></label>
                                <div class="col-sm-9">
                                    <button type="button" id="addTurnRule" class="btn btn-success">添加</button>
                                </div>
                            </div>

                            <div class="form-group" style="margin-top:40px;">
                                <label id="timeLabel" class="control-label col-sm-2">有效时间</label>
                                <div class="col-sm-10 row" style="padding-left: 22px;">
                                    <div class="form-group col-sm-3">
                                        <input type="text" class="form-control" id="beginTime" name="beginTime"
                                               placeholder="开始日期" data-date-format="yyyy-mm-dd hh:ii">
                                    </div>

                                    <div class="form-group col-sm-3" style="margin-left:20px;;">
                                        <input type="text" class="form-control" id="endTime" name="endTime"
                                               placeholder="结束日期" data-date-format="yyyy-mm-dd hh:ii">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="describe" class="control-label col-sm-2">转盘介绍</label>
                                <div class="col-sm-5">
                                    <textarea name="describe" id="describe" cols="50" rows="5"
                                              class="form-control"></textarea>
                                </div>
                            </div>

                        </form>

                        <row>
                            <label class="col-sm-5"></label>
                            <div class="col-sm-6">
                                <button type="submit" class="btn btn-lg btn-info" id="turnSave">保存</button>
                            </div>
                        </row>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content-area -->
        </div><!-- /.page-content -->
    </div><!-- /.main-content -->

</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='<%=basePath%>static/js/jquery-2.2.0.min.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='<%=basePath%>static/js/jquery-2.2.0.min.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>static/ace2/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="<%=basePath%>static/ace2/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->
<script src="<%=basePath%>static/ace2/js/dropzone.min.js"></script>
<script src="<%=basePath%>static/ace2/js/uncompressed/bootbox.js"></script>

<link rel="stylesheet" href="<%=basePath%>static/css/laydate.css"/>
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

    $(document).ready(function () {

        // 加载奖品
        (function () {
            $.ajax({
                url: "<%=basePath%>gift/list.do?pageSize=1000&pageNumber=0",
                dataType: 'json',
                async: false,
                success: function (result) {
                    for (var giftIndex in result.rows) {
                        var gift = result.rows[giftIndex];
                        var $option = $('<option value=' + gift.id + '>' + gift.name + '</option>');
                        $('select[attr=giftId]').append($option);
                    }
                }
            })
        })();


        function reflushRuleNameAttrs() {
            var ruleIndex = 0;
            $('.turn-rule:visible').each(function () {
                var $rule = $(this);
                $rule.find('[attr]').each(function () {
                    var $field = $(this);
                    var attrName = $field.attr('attr');
                    $field.attr('name', attrName);
                });

                ruleIndex++;
            });
        }

        // 添加礼品项
        function addTurnGift() {
            var $template = $('#turn-rule-template');
            var newGift = $template.clone(true).removeAttr("id").show();
            $template.before(newGift);
            reflushRuleNameAttrs();
            return newGift;
        }

        addTurnGift();

        $('button#addTurnRule').click(addTurnGift);

        // 删除规则
        $('button.removeRule').click(function () {
            var $rule = $(this).parents('.turn-rule');

            if ($('.turn-rule').length < 3) {
                alert("最少要有一个奖品项 !");
                return;
            }

            bootbox.confirm("确定删除？", function (result) {
                if (result) {
                    $rule.remove();
                    reflushRuleNameAttrs();
                }
            });

        });

        // 检查规则
        function checkRule() {
            var valid = true;
            $('.turn-rule').each(function () {
                var $rule = $(this);
                $rule.find('.rule-name').css('color', '');
                $rule.find("[name]:visible").each(function () {
                    var isNumber = /^\d*$/.test($(this).val());
                    if (!$(this).val() || !isNumber) {
                        $(this).css("border-color", "#a94442");
                        $rule.find('.rule-name').css('color', '#a94442');
                        valid = false;
                    } else {
                        $(this).css("border-color", "");
                    }
                })
            });
            return valid;
        }

        var isTimeValid = true;

        function checkTime() {

            $('#timeLabel').css('color', '');
            $('#beginTime').css('border-color', '');
            $('#endTime').css('border-color', '');

            isTimeValid = true;

            if (!$('#beginTime').val()) {
                $('#timeLabel').css('color', '#a94442');
                $('#beginTime').css('border-color', '#a94442');
                isTimeValid = false;
            }

            if (!$('#endTime').val()) {
                $('#timeLabel').css('color', '#a94442');
                $('#endTime').css('border-color', '#a94442');
                isTimeValid = false;
            }

            return isTimeValid;
        }

        function checkTimeValue() {

            var isValid = true;

            var beginTimeStr = $('#beginTime').val();
            var endTimeStr = $('#endTime').val();

            var beginTime = new Date(beginTimeStr);
            var endTime = new Date(endTimeStr);

            if (beginTime.getTime() >= endTime.getTime()) {
                alert("开始时间　不能大于　结束时间 ！");
                isValid = false;
            }

            $.ajax({
                url: "<%=basePath%>common/now",
                async: false,
                dataType: 'json',
                success: function (data) {
                    if (endTime.getTime() < data.time) {
                        isValid = false;
                        alert("结束时间　不能小于　现在时间　！");
                    }
                }
            });

            var regex = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
            if (!regex.test(beginTimeStr) || !regex.test(endTimeStr)) {
                isValid = false;
                alert("时间格式错误！");
            }

            return isValid;

        }

        $('.turn-rule [name], .turn-rule [attr]').on('keyup change', checkRule);

        $('#beginTime,#endTime').on('keyup change', checkTime);

        $('body').click(function () {
            if (!isTimeValid) {
                checkTime();
            }
        });


        // 保存表单
        $('#turnSave').click(function () {
            $('#turnForm').submit();
        })


        $('#turnForm').bootstrapValidator({
                    message: '必须填写',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        name: {
                            message: '必须填写!',
                            validators: {
                                notEmpty: {}
                            }
                        },
                        remark: {
                            message: '必须填写!',
                            validators: {
                                notEmpty: {}
                            }
                        },
                        personType: {
                            message: '必须填写!',
                            validators: {
                                notEmpty: {}
                            }
                        }

                    }
                })
                .on('success.form.bv', function (e) {
                    e.preventDefault();

                    if (!checkRule()) {
                        alert('请填写完整 !');
                        return;
                    }

                    if (!checkTime()) {
                        alert('请填写开始，结束时间 ！');
                        return;
                    }

                    if (!checkTimeValue()) {
                        return;
                    }

                    // Get the form instance
                    var $form = $(e.target);

                    // Get the BootstrapValidator instance
                    var bv = $form.data('bootstrapValidator');

                    // Use Ajax to submit form data
                    $.ajax({
                        url: '<%=basePath%>turn/add.do',
                        type: 'post',
                        dataType: 'json',
                        data: $('#turnForm').serialize(),
                        success: function (data) {
                            if (data.turnTableId) {
                                loadTurnTable(data.turnTableId);
                                alert("成功！");
                            }
                        }
                    });
                });

        function loadTurnTable(id) {
            if(!id) {
                alert("The function loadTurnTable must have parameter id");
            }

            $('#turnForm')[0].reset();
            $('#turnForm .turn-rule:visible').remove();
            $('#turnForm .removeRule').remove();
            $('#addTurnRule').remove();

            $.ajax({
                url : '<%=basePath%>turn/getTurnTable?id='+id,
                dataType : 'json',
                success : function(data) {
                    console.log(data);
                    var turnTable = data.turnTable;
                    $('input[name=turnTableId]').val(turnTable.id);
                    $('input[name=name]').val(turnTable.name);
                    $('input[name=remark]').val(turnTable.remark);
                    $('input[name=beginTime]').val(new Date(turnTable.beginTime).pattern('yyyy-MM-dd HH:mm:ss'));
                    $('input[name=endTime]').val(new Date(turnTable.endTime).pattern('yyyy-MM-dd HH:mm:ss'));
                    $('textarea[name=describe]').val(turnTable.describe);

                    for(var i in data.turnTableGifts) {
                        var gift = data.turnTableGifts[i];
                        var $gift = addTurnGift();
                        $gift.find('input[name=turnTableGiftId]').val(gift.id);
                        $gift.find('input[name=sort]').val(gift.sort);
                        $gift.find('select[name=giftId]').val(gift.giftId);
                        $gift.find('input[name=probability]').val(gift.probability);
                        $gift.find('input[name=totalQuantity]').val(gift.toatalQuantity);

                        $gift.find('input[name=sort]').attr('readonly', 'readonly');
                        $gift.find('select[name=giftId]').attr('readonly', 'readonly');
                    }
                }

            })

        }

        if(${param.turnTableId}+'') {
            loadTurnTable(${param.turnTableId});
        }
    });
</script>
</body>
</html>
