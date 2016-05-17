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
                        <form class="form-horizontal" role="form" id="skuForm">
                            <!-- #section:elements.form -->
                            <input type="hidden" name="id" value="${brand.id}">
                            <div class="form-group">
                                <label for="cname" class="col-sm-2 control-label">中文名称</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="cname" name="cname" value="${brand.cname}" placeholder="中文名称">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="ename" class="col-sm-2 control-label">英文名称</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="ename" name="ename" value="${brand.ename}" placeholder="英文名称">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="dropzone2" class="col-sm-2 control-label">Logo</label>
                                <div class="col-sm-9">
                                    <div action="<%=basePath%>ueditor.do?action=uploadimage" class="dropzone" id="dropzone2">
                                        <div class="fallback">
                                            <input name="file" type="file" multiple=""/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="remark" class="col-sm-2 control-label">备注</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="remark" name="remark" value="${brand.remark}" placeholder="一句话介绍">
                                </div>
                            </div>
                            <textarea rows="500" cols="300" id="content" name="content" style="display: none;"></textarea>
                        </form>

                        <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-1">
                                <div class="alert alert-success" role="alert" style="margin:20px 0 0 0;">
                                    <strong>品牌介绍</strong>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-10 col-xs-offset-1">
                                <div>
                                    <script id="contentUEditor" type="text/plain" style="height: 500px"></script>
                                    <script type="text/javascript">
                                        //实例化编辑器
                                        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
                                        var contentUEditor = UE.getEditor('contentUEditor');
                                    </script>
                                </div>
                            </div>
                        </div>

                        <row>
                            <label class="col-sm-5"></label>
                            <div class="col-sm-6">
                                <button type="reset" class="btn btn-lg btn-default">重置</button>
                                <button type="submit" class="btn btn-lg btn-info" id="skuSave">保存</button>
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

<script type="text/javascript">

    Dropzone.autoDiscover = false;
    function initDropzone(selector,dictMessage, callback){
        var dictDefaultMessage = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>品牌Logo</span> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
        try {
            var myDropzone = new Dropzone(selector, {
                paramName: "file", // The name that will be used to transfer the file
                maxFilesize: 3, // MB

                addRemoveLinks: true,
                dictDefaultMessage: dictMessage ? dictMessage : dictDefaultMessage,
                dictResponseError: '上传文件出错了!',

                //change the previewTemplate to use Bootstrap progress bars
                previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
                success: function(file){
                    console.log('file: ' + file.name);

                    if((typeof callback)=='function'){
                        callback(file);
                    }
                    if (file.previewElement) {
                        return file.previewElement.classList.add("dz-success");
                    }
                },
                removedfile: function(file) {
                    var _ref;
                    if (file.previewElement) {
                        if ((_ref = file.previewElement) != null) {
                            _ref.parentNode.removeChild(file.previewElement);
                        }
                    }

                    var res = file.xhr.response ? window.eval('(' + file.xhr.response + ')') : '';
                    $('#skuForm').find('input[value="'+res.url+'"]').remove();
                    $('#skuForm').find('input[value="'+res.title+'"]').remove();
                    $('#skuForm').find('input[value="'+res.original+'"]').remove();

                    return this._updateMaxFilesReachedClass();
                }
            });

            return myDropzone;
        } catch (e) {
            alert('Dropzone.js does not support older browsers!');
        }
    }

    initDropzone('#dropzone2', null, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="logoUrl" value="'+res.url+'" />');
        $('#skuForm').append('<input type="hidden" name="logoName" value="'+res.title+'" />');
    });
</script>
<script>


    $('#skuSave').on('click', function(){
        $('#skuForm').submit();
    });

    $(document).ready(function() {

        $('#skuForm').bootstrapValidator({
                    message: '必须填写',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        cname: {
                            message: '必须填写!',
                            validators: {
                                notEmpty: {}
                            }
                        },
                        ename: {
                            message: '必须填写!',
                            validators: {
                                notEmpty: {}
                            }
                        },
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
                    $('#content').val(UE.getEditor('contentUEditor').getContent());
                    $.ajax({
                        url: '<%=basePath%>brand/update.do',
                        type: 'post',
                        data: $('#skuForm').serialize(),
                        success: function(msg){
                            alert(msg);
                        }
                    });
                });
    });
</script>
</body>
</html>
