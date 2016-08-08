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
                        <form class="form-horizontal" role="form" id="ruleForm" method="post">
                            <!-- #section:elements.form -->
                            <input type="hidden" name="id" >

                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">抽奖活动名称</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="name" name="name" placeholder="" value="${rule.name}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="remark" class="control-label col-sm-2">备注</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="remark" name="remark" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="type" class="control-label col-sm-2">活动对象</label>
                                <div class="col-sm-5">
                                    <select id="type" name="type" class="form-control">
                                        <option></option>
                                        <option value="0">B 端</option>
                                        <option value="1">C 端</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="times" class="control-label col-sm-2">抽奖次数</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="times" name="times" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="turnTableId" class="control-label col-sm-2">抽奖方式</label>
                                <div class="col-sm-5">
                                    <select id="turnTableId" name="turnTableId" class="form-control">
                                        <option></option>
                                    </select>
                                </div>
                            </div>

                        </form>

                        <row>
                            <label class="col-sm-5"></label>
                            <div class="col-sm-6">
                                <button type="submit" class="btn btn-lg btn-info" id="saveRule">保存</button>
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

<script>

    $(document).ready(function() {

        // load turnTable
        +function() {
            $.ajax({
                url : '<%=basePath%>turn/all.do',
                dataType : 'json',
                async : false,
                success : function(data) {
                    for(var i in data) {
                        var turnTable = data[i];
                        var option = $('<option value="'+turnTable.id+'">'+turnTable.name+'</option>');
                        $('select#turnTableId').append(option);
                    }
                }
            })
        }();

        $('#saveRule').on('click', function(){
            $('#ruleForm').submit();
            return false;
        });

        // 必填字段
        var validFields = {
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
            type: {
                message: '必须填写!',
                validators: {
                    notEmpty: {}
                }
            },
            times: {
                message: '必须填写!',
                validators: {
                    notEmpty: {}
                }
            },
            turnTableId: {
                message: '必须填写!',
                validators: {
                    notEmpty: {}
                }
            }
        }


        $('#ruleForm').bootstrapValidator({
                    message: '必须填写',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: validFields
        }).on('success.form.bv', function(e) {
            e.preventDefault();

            var paramData = $('#ruleForm').serialize();

            $.ajax({
                url: '<%=basePath%>turnRule/add.do',
                type: 'post',
                dataType : 'json',
                data: paramData,
                async: false,
                success: function (result) {
                    if(result.status == 'success') {
                        $('[name=id]').val(result.id);
                        alert('成功 !');
                    }
                }
            });
        });
    });

    if(${param.id}+'') {
        $.ajax({
            url : '<%=basePath%>turnRule/getTurnRule?id=${param.id}',
            dataType : 'json',
            success : function(data) {
                $('[name=id]').val(data.id);
                $('[name=name]').val(data.name);
                $('[name=remark]').val(data.remark);
                $('[name=type]').val(data.type);
                $('[name=times]').val(data.times);
                $('[name=turnTableId]').val(data.turnTableId);
            }
        });
    }

</script>
</body>
</html>
