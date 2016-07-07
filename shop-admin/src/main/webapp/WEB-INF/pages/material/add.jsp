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
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/uncompressed/ace.css"/>
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
                        <form class="form-horizontal" role="form" id="materialForm">

                            <div class="form-group">
                                <label for="lId" class="col-sm-2 control-label">素材库</label>
                                <div class="col-sm-5">
                                    <select class="form-control" id="lId" name="materialLibraryId">
                                    </select>
                                </div>
                                <div class="col-sm-4">
                                    <%--<a href="#modal-library" role="button" class="btn btn-success" data-toggle="modal">添加素材库</a>--%>
                                    <button class="btn btn-success" id="addLibrary" type="button">添加素材库</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="gId" class="col-sm-2 control-label">素材组</label>
                                <div class="col-sm-5">
                                    <select class="form-control" id="gId" name="materialGroupId">
                                    </select>
                                </div>
                                <div class="col-sm-4">
                                    <button class="btn btn-success" id="addGroup" type="button">添加素材组</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="title" class="col-sm-2 control-label">标题</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="title" name="title" placeholder="标题">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="title" class="col-sm-2 control-label">文字</label>
                                <div class="col-sm-5">
                                    <textarea id="content" name="content" rows="6" cols="51"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="materialImg" class="col-sm-2 control-label">图片素材</label>
                                <div class="col-sm-9">
                                    <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/material/" class="dropzone" id="materialImg">
                                        <div class="fallback">
                                            <input name="file" type="file" multiple=""/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>

                        <row>
                            <label class="col-sm-5"></label>
                            <div class="col-sm-6">
                                <button type="reset" class="btn btn-lg btn-default">重置</button>
                                <button type="submit" class="btn btn-lg btn-info" id="saveMaterial">保存</button>
                            </div>
                        </row>

                        <div id="modal-library" class="modal fade" tabindex="-1" aria-hidden="true" style="display: none;">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header no-padding">
                                        <div class="table-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                <span class="white">×</span>
                                            </button>
                                            添加素材库
                                        </div>
                                    </div>

                                    <div class="modal-body no-padding">
                                        <form class="form-horizontal" role="form" id="library">

                                            <div class="form-group">
                                                <label for="sku" class="col-sm-3 control-label">商品名称</label>
                                                <div class="col-sm-6">
                                                    <select class="form-control" id="sku" name="skuId">
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="title" class="col-sm-3 control-label">标题</label>
                                                <div class="col-sm-6">
                                                    <input type="text" class="form-control" id="lName" name="lName" placeholder="标题">
                                                </div>
                                            </div>
                                        </form>
                                    </div>

                                    <div class="modal-footer no-margin-top">
                                        <div class="col-xs-5 col-sm-5 col-sm-offset-4">
                                            <button class="btn btn-sm btn-danger pull-left" id="cancelSave" type="button" data-dismiss="modal">
                                                取消
                                            </button>
                                            <button class="btn btn-sm btn-info pull-left" id="saveLibrary" type="button">
                                                保存
                                            </button>
                                        </div>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div>

                        <div id="modal-group" class="modal fade" tabindex="-1" aria-hidden="true" style="display: none;">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header no-padding">
                                        <div class="table-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                <span class="white">×</span>
                                            </button>
                                            添加素材组
                                        </div>
                                    </div>

                                    <div class="modal-body no-padding">
                                        <form class="form-horizontal" role="form" id="group">

                                            <div class="form-group">
                                                <label for="libraryId" class="col-sm-3 control-label">素材库</label>
                                                <div class="col-sm-6">
                                                    <select class="form-control" id="libraryId" name="libraryId">
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="gName" class="col-sm-3 control-label">标题</label>
                                                <div class="col-sm-6">
                                                    <input type="text" class="form-control" id="gName" name="gName" placeholder="标题">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="gName" class="col-sm-3 control-label">显示位置</label>
                                                <div class="col-sm-6">
                                                    <label>
                                                        <input name="isBShow" value="1" type="checkbox" class="ace">
                                                        <span class="lbl"> B端 </span>
                                                    </label>
                                                    <label>
                                                        <input name="isCShow" value="1" type="checkbox" class="ace">
                                                        <span class="lbl"> C端 </span>
                                                    </label>
                                                </div>
                                            </div>
                                        </form>
                                    </div>

                                    <div class="modal-footer no-margin-top">
                                        <div class="col-xs-5 col-sm-5 col-sm-offset-4">
                                            <button class="btn btn-sm btn-danger pull-left" type="button" data-dismiss="modal">
                                                取消
                                            </button>
                                            <button class="btn btn-sm btn-info pull-left" id="saveGroup" type="button">
                                                保存
                                            </button>
                                        </div>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div>

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
        var dictDefaultMessage = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>图片素材</span> <br /> \
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
                    $('#materialForm').find('input[value="'+0+'"]').remove();
                    $('#materialForm').find('input[value="'+res.original+'"]').remove();
                    $('#materialForm').find('input[value="'+res.title+'"]').remove();
                    $('#materialForm').find('input[value="'+res.type+'"]').remove();
                    $('#materialForm').find('input[value="'+res.size+'"]').remove();

                    return this._updateMaxFilesReachedClass();
                }
            });

            return myDropzone;
        } catch (e) {
            alert('Dropzone.js does not support older browsers!');
        }
    }

    initDropzone('#materialImg', null, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#materialForm').append('<input type="hidden" name="types" value="'+0+'" />');
        $('#materialForm').append('<input type="hidden" name="fileNames" value="'+res.original+'" />');
        $('#materialForm').append('<input type="hidden" name="fileUrls" value="'+res.title+'" />');
        $('#materialForm').append('<input type="hidden" name="fileSuffixs" value="'+res.type+'" />');
        $('#materialForm').append('<input type="hidden" name="fileSizes" value="'+res.size+'" />');
    });
</script>
</body>
<script>
    var librarys = null;
    $(function(){
        loadGroup('#lId', '#gId');
    });
    //添加素材库
    $('#addLibrary').on('click', function(){
        $.ajax({
            url: '<%=basePath%>material/loadSku.do',
            success: function(data) {
                data = window.eval('('+data+')');
                var sHtml = '';
                for(var i in data){
                    sHtml += '<option value="'+data[i].id+','+data[i].spuId+'">'+data[i].name+'</option>';
                }
                $('#sku').html(sHtml);
                $('#modal-library').modal('show');
            },
            error: function(msg) {
                alert(msg);
            }
        });
    });

    //添加素材组
    $('#addGroup').on('click', function(){
        loadLibrary('#libraryId');
    });

    function loadLibrary(selector) {
        $.ajax({
            url: '<%=basePath%>material/loadLibrary.do',
            success: function(data) {
                data = window.eval('('+data+')');
                var sHtml = '';
                for(var i in data){
                    sHtml += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
                }
                $(selector).html(sHtml);
                $('#modal-group').modal('show');
            },
            error: function(msg) {
                alert(msg);
            }
        });
    }

    function loadGroup(lSelector, gSelector) {
        $.ajax({
            url: '<%=basePath%>material/loadGroup.do',
            success: function(data) {
                data = window.eval('('+data+')');
                librarys = data;
                var libraryHtml = '';
                var groupHtml   = '';
                for(var i in data){
                    libraryHtml += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
                }
                for(var i in data[0].materialGroups){
                    groupHtml += '<option value="'+data[0].materialGroups[i].id+'">'+data[0].materialGroups[i].name+'</option>';
                }

                $(lSelector).html(libraryHtml);
                $(gSelector).html(groupHtml);
            }
        })
    }

    //保存素材库
    $('#saveLibrary').on('click', function(){
        var skuId = $('#library select').val().split(',')[0];
        var spuId = $('#library select').val().split(',')[1];
        var name  = $('#library input[name="lName"]').val();

        $.ajax({
            url: '<%=basePath%>material/saveLibrary.do',
            data: {skuId: skuId, spuId: spuId, name: name},
            type: 'post',
            success: function (result) {
                result = window.eval('('+result+')');
                if(result.code == 'success'){
                    loadGroup('#lId', '#gId');
                    $('#modal-library').modal('hide');
                }

                $.gritter.add({
                    title: '消息',
                    text: result.msg,
                    class_name: 'gritter-success' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
                });
            }
        })
    });

    //保存素材组
    $('#saveGroup').on('click', function(){
        var libraryId = $('#group select').val();
        var name      = $('#group input[name="gName"]').val();
        var isBShow   = $('#group input[name="isBShow"]:checked').val();
            isBShow   = isBShow ? isBShow : 0;
        var isCShow   = $('#group input[name="isCShow"]:checked').val();
            isCShow   = isCShow ? isCShow : 0;

        $.ajax({
            url: '<%=basePath%>material/saveGroup.do',
            data: {materialLibraryId: libraryId, name: name, isBShow: isBShow, isCShow: isCShow},
            type: 'post',
            success: function(result) {
                result = window.eval('('+result+')');
                if(result.code == 'success'){
                    loadGroup('#lId', '#gId');
                    $('#modal-group').modal('hide');
                }

                $.gritter.add({
                    title: '消息',
                    text: result.msg,
                    class_name: 'gritter-success' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
                });
            }
        })
    });

    $('#lId').change(function(){
        var lId = $(this).val();
        var groupHtml = '';
        for(var l in librarys){
            if(lId == librarys[l].id){
                for(var g in librarys[l].materialGroups)
                groupHtml += '<option value="'+librarys[l].materialGroups[g].id+'">'+librarys[l].materialGroups[g].name+'</option>';
            }
        }
        $('#gId').html(groupHtml);
    });

    $('#saveMaterial').on('click', function(){
        $.ajax({
            url: '<%=basePath%>material/saveMaterial.do',
            data: $('#materialForm').serialize(),
            type: 'post',
            success: function(result){
                result = window.eval('('+result+')');
                if(result.code == 'success'){
                    parent.window.$('#myTabbable').closeTab('materialItem');
                    parent.window.$('#myTabbable').add('materialItem', result.materialGroup.name+' 的素材', '<%=basePath%>material/listMaterial.shtml?mgId='+ result.materialGroup.id);
                    parent.window.$('#myTabbable').closeTab('tab14-1');
                }
            }
        })
    });
</script>
</html>
