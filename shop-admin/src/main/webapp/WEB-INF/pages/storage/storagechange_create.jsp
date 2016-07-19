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
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>static/class/upload-plugin/core/zyFile.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>static/class/upload-plugin/control/js/zyUpload.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/upload-plugin/core/jq22.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/lang/zh-cn/zh-cn.js"></script>

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
                        <form class="form-horizontal" role="form" id="promotionForm">
                            <!-- #section:elements.form -->

                            <input type="hidden" name="promotionId" />

                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">活动名称</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="promotionName" placeholder="">
                                </div>
                                <label for="name" class="col-sm-2 control-label">活动名称</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="name" name="promotionName" placeholder="">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="remark" class="control-label col-sm-2">活动备注</label>
                                <div class="col-sm-3">
                                    <input type="text" id="remark" name="promotionRemark" class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="personType" class="col-sm-2 control-label">活动对象</label>
                                <div class="col-sm-3">
                                    <select class="form-control" id="personType" name="promotionPersonType">
                                        <option value="0">粉丝</option>
                                        <option value="1">代言人</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2"></label>
                                <div class="col-sm-10" style="background-color: gainsboro">
                                    <div class="row">
                                        <label class="control-label col-sm-3" style="text-align: center;">达到数量</label>
                                        <label class="control-label col-sm-3" style="text-align: center;">奖品</label>
                                        <label class="control-label col-sm-3" style="text-align: center;">奖励数量</label>
                                        <label class="control-label col-sm-2" style="text-align: center;">总数量</label>
                                        <label class="control-label col-sm-1" style="text-align: center;">操作</label>
                                    </div>
                                </div>
                            </div>

                            <div id="promotion-rule-template" class="form-group promotion-rule" style="display: none;">
                                <label class="control-label col-sm-2 rule-name">一阶规则</label>
                                <div class="col-sm-10">
                                    <div class="row">
                                        <input type="hidden" attr="ruleId" />
                                        <div class="col-sm-3"><input type="text"class="form-control" attr="ruleValue"> </div>
                                        <input type="hidden" attr="promotionGiftId" />
                                        <div class="col-sm-3">
                                            <select class="form-control" attr="giftValue">
                                                <option></option>
                                            </select>
                                        </div>
                                        <div class="col-sm-3"><input type="text"class="form-control" attr="quantity"> </div>
                                        <div class="col-sm-2"><input type="text"class="form-control" attr="upperQuantity"> </div>
                                        <div class="col-sm-1"><button type="button" class="btn btn-warning removeRule">删除</button></div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="addPromotionRule" class="control-label col-sm-2"></label>
                                <div class="col-sm-9">
                                    <button type="button" id="addPromotionRule" class="btn btn-success">添加</button>
                                </div>
                            </div>

                            <div class="form-group" style="margin-top:40px;">
                                <label id="timeLabel" class="control-label col-sm-2">有效时间</label>
                                <div class="col-sm-10 row" style="padding-left: 22px;">
                                    <div class="form-group col-sm-3">
                                        <input type="text" class="form-control" id="beginTime" name="promotionBeginTime" placeholder="开始日期" data-date-format="yyyy-mm-dd hh:ii">
                                    </div>

                                    <div class="form-group col-sm-3" style="margin-left:20px;;">
                                        <input type="text" class="form-control" id="endTime" name="promotionEndTime" placeholder="结束日期" data-date-format="yyyy-mm-dd hh:ii">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="introduction" class="control-label col-sm-2">活动介绍</label>
                                <div class="col-sm-5">
                                    <textarea name="promotionIntroduction" id="introduction" cols="50" rows="5" class="form-control"></textarea>
                                    <input type="hidden" name="user.name" value="luoyong" />
                                </div>
                            </div>

                        </form>

                        <row>
                            <label class="col-sm-5"></label>
                            <div class="col-sm-6">
                                <button type="submit" class="btn btn-lg btn-info" id="promotionSave">保存</button>
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

<link rel="stylesheet" href="<%=basePath%>static/css/laydate.css" />
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

    $(document).ready(function() {

        /*// 加载奖品
        (function() {
            $.ajax({
                url : "<%=basePath%>gift/list.do?pageSize=1000&pageNumber=0",
                dataType : 'json',
                async : false,
                success : function(result) {
                    for(var giftIndex in result.rows) {
                        var gift = result.rows[giftIndex];
                        var $option = $('<option value='+gift.id+'>'+gift.name+'</option>');
                        $('select[attr=giftValue]').append($option);
                    }
                }
            })
        })();*/

        var promotionId = '${param.promotionId}';
        if(promotionId) {
            loadPromotion(promotionId);
        }

        // 刷新规则名
        function reflushRuleNames() {
            var ruleNames = ['一', '二', '三', '四','五', '六', '七', '八', '九', '十'];
            var ruleNameIndex = 0;
            $('.rule-name:visible').each(function() {
                $(this).html(ruleNames[ruleNameIndex]+"阶规则");
                ruleNameIndex++;
            });
        }

        function reflushRuleNameAttrs() {
            var ruleIndex = 0;
            $('.promotion-rule:visible').each(function() {
                var $rule = $(this);
                $rule.find('[attr]').each(function() {
                    var $field = $(this);
                    var attrName = $field.attr('attr');
                    $field.attr('name', attrName);
                });

                ruleIndex++;
            });
        }

        // 添加规则
        function addPromotionRule() {
            var $template = $('#promotion-rule-template');
            var newRule = $template.clone(true).removeAttr("id").show();
            $template.before(newRule);
            reflushRuleNames();
            reflushRuleNameAttrs();
        }

        addPromotionRule();

        $('button#addPromotionRule').click(addPromotionRule);

        // 删除规则
        $('button.removeRule').click(function() {
            var $rule = $(this).parents('.promotion-rule');

            if($('.promotion-rule').length < 3) {
                alert("最少要有一个规则 !");
                return;
            }

            bootbox.confirm("确定删除？",function(result) {
                if(result) {
                    $rule.remove();
                    reflushRuleNames();
                    reflushRuleNameAttrs();
                }
            });

        });

        // 检查规则
        function checkRule() {
            var valid = true;
            $('.promotion-rule').each(function () {
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

        function checkTimeValue() {

            var isValid = true;

            var beginTimeStr = $('#beginTime').val();
            var endTimeStr = $('#endTime').val();

            var beginTime = new Date(beginTimeStr);
            var endTime   = new Date(endTimeStr);

            if(beginTime.getTime() >= endTime.getTime()) {
                alert("开始时间　不能大于　结束时间 ！");
                isValid = false;
            }

            $.ajax({
                url : "<%=basePath%>common/now",
                async : false,
                dataType : 'json',
                success : function(data) {
                    console.log(data);
                    console.log(endTime.getTime());
                    console.log(data.time);

                    if(endTime.getTime() < data.time) {
                        isValid = false;
                        alert("结束时间　不能小于　现在时间　！");
                    }
                }
            });

            var regex = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
            if(!regex.test(beginTimeStr) || !regex.test(endTimeStr)) {
                isValid = false;
                alert("时间格式错误！");
            }

            return isValid;

        }

        $('.promotion-rule [name]').on('keyup change',checkRule);

        $('#beginTime,#endTime').on('keyup change',checkTime);

        $('body').click(function() {
            if(!isTimeValid) {
                checkTime();
            }
        });


        // 保存表单
        $('#promotionSave').click(function() {
            $('#promotionForm').submit();
        })


        $('#promotionForm').bootstrapValidator({
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
                .on('success.form.bv', function(e) {
                    // Prevent form submission
                    e.preventDefault();

                    if(!checkRule()) {
                        alert('请填写完整 !');
                        return;
                    }

                    if(!checkTime()) {
                        alert('请填写开始，结束时间 ！');
                        return;
                    }

                    if(!checkTimeValue()) {
                        return;
                    }

                    // Get the form instance
                    var $form = $(e.target);

                    // Get the BootstrapValidator instance
                    var bv = $form.data('bootstrapValidator');

                    // Use Ajax to submit form data
                    $.ajax({
                        url: '<%=basePath%>promotion/saveOrUpdate.do',
                        type: 'post',
                        dataType:'json',
                        data: $('#promotionForm').serialize(),
                        success: function(msg){
                            if(msg.promotionId) {
                                loadPromotion(msg.promotionId);
                                alert("成功！");
                            }
                        }
                    });
                });
    });
</script>
</body>
</html>
