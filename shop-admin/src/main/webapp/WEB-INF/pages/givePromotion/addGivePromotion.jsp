<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                        <form action="<%=basePath%>givePromotion/save.do" class="form-horizontal" role="form" id="giveForm" method="post">
                            <div class="form-group">
                                <input type="hidden" id="id" name="id" value="" />
                            </div>

                            <div style="background:cornflowerblue;overflow: hidden;">
                                <div style="float:left; height: 100px; width:20%;  background-color: #F2F2F2;"></div>
                                <div style="float:right;width:70%; background-color: #F2F2F2;">

                                    <div class="form-group">
                                        <label for="name" class="col-sm-2 control-label">活动名称</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="name" name="name" placeholder="" value="${gift.name}">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="introduceLink" class="col-sm-2 control-label">介绍链接</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="introduceLink" name="name" placeholder="" value="${gift.introduceLink}">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="beginTime" class="control-label col-sm-2">开始时间</label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" id="beginTime" name="beginTime" placeholder="开始日期" data-date-format="yyyy-mm-dd hh:ii">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="endTime" class="control-label col-sm-2">结束时间</label>
                                        <div class="col-sm-3">
                                            <input type="text" class="form-control" id="endTime" name="beginTime" placeholder="开始日期" data-date-format="yyyy-mm-dd hh:ii">
                                        </div>
                                    </div>

                                </div>
                                <div style="clear:both;display: block; content: '';"></div>
                            </div>

                            <%--<div class="form-group">--%>
                                <%--<label for="name" class="col-sm-2 control-label">活动名称</label>--%>
                                <%--<div class="col-sm-9">--%>
                                    <%--<input type="text" class="form-control" id="name" name="name" placeholder="" value="${gift.name}">--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="form-group">--%>
                                <%--<label for="introduceLink" class="col-sm-2 control-label">介绍链接</label>--%>
                                <%--<div class="col-sm-9">--%>
                                    <%--<input type="text" class="form-control" id="introduceLink" name="name" placeholder="" value="${gift.introduceLink}">--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="form-group">--%>
                                <%--<label for="beginTime" class="control-label col-sm-2">开始时间</label>--%>
                                <%--<div class="col-sm-3">--%>
                                    <%--<input type="text" class="form-control" id="beginTime" name="beginTime" placeholder="开始日期" data-date-format="yyyy-mm-dd hh:ii">--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="form-group">--%>
                                <%--<label for="endTime" class="control-label col-sm-2">结束时间</label>--%>
                                <%--<div class="col-sm-3">--%>
                                    <%--<input type="text" class="form-control" id="endTime" name="beginTime" placeholder="开始日期" data-date-format="yyyy-mm-dd hh:ii">--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <div class="form-group">
                                <label class="col-sm-2"></label>
                                <div class="col-sm-3" style="border-top:1px dashed gray"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">活动品牌</label>
                                <div class="col-sm-3">
                                    <select name="brandId">
                                        <option></option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="introduceLink" class="col-sm-2 control-label">活动级别</label>
                                <div class="col-sm-9">
                                    <label>
                                        <input type="checkbox" name="level" value="1" class="form-control"/>BOSS
                                    </label>
                                    <label>
                                        <input type="checkbox" name="level" value="2" class="form-control"/>联合创始人
                                    </label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="introduceLink" class="col-sm-2 control-label">赠送商品</label>
                                <div class="col-sm-9">
                                    <select name="give_sku_id" class="form-control">
                                        <option></option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">赠送数量</label>
                                <div class="col-sm-3">
                                    <input type="text" name="quantity" class="form-control" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="productValidTime" class="control-label col-sm-2">商品有效期</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="productValidTime" name="productValidTime" placeholder="商品有效期" data-date-format="yyyy-mm-dd hh:ii">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2">触发条件</label>
                                <div class="col-sm-3">
                                    <label>
                                        <input type="radio" name="triggerCondition" value="1"/>全部用户
                                    </label>
                                    <label>
                                        <input type="radio" name="triggerCondition" value="1"/>新注册用户
                                    </label>
                                    <label>
                                        <input type="radio" name="triggerCondition" value="1"/>注册 1 个月以上
                                    </label>
                                    <label>
                                        <input type="radio" name="triggerCondition" value="1"/>注册半年以上
                                    </label>
                                    <label>
                                        <input type="radio" name="triggerCondition" value="1"/>注册 1 年c上
                                    </label>

                                </div>
                            </div>

                        </form>




                        <row>
                            <label class="col-sm-5"></label>
                            <div class="col-sm-6">
                                <button type="submit" class="btn btn-lg btn-info" id="giftSave">保存</button>
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

        var giftImgBaseUrl = "${imgBaseUrl}";

        // 上传奖品图片
        $('#file').change(function()  {

            var file = $('#file')[0].files[0];
            var imgFormData = new FormData();
            imgFormData.append("file", file);

            $.ajax({
                url:"<%=basePath%>ueditor.do?action=uploadimage&osspath=static/gift/",
                type: 'POST',
                dataType : 'json',
                data: imgFormData,
                async: false,
                success: function (result) {
                    console.log(result);
                    $('#imgUrlLink').text(result.title);
                    $('#imgUrlLink').attr("href",giftImgBaseUrl+result.title);
                    $('#imgUrl').val(result.title);
                },
                cache: false,
                contentType: false,
                processData: false
            });
        });

        // 设置　富文本编辑器的内容
        var content = '${gift.content}';
        setTimeout(function() {
            UE.getEditor('contentUEditor').setContent(content)
        }, 1000);


        $('#giftSave').on('click', function(){
            $('#giftForm').submit();
        });

        // 必填字段
        var validFields = {
            name: {
                message: '必须填写!',
                validators: {
                    notEmpty: {}
                }
            }
        }
        if(!$('#imgUrl').val()) {
            validFields['file'] = {
                message : '必须填写!',
                validators : {
                    notEmpty : {}
                }
            }
        }

        $('#giftForm').bootstrapValidator({
            message: '必须填写',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: validFields
        }).on('success.form.bv', function(e) {
            e.preventDefault();
            $('#content').val(UE.getEditor('contentUEditor').getContent());

            var paramData = $('#giftForm').serialize();

            $.ajax({
                url: '<%=basePath%>gift/saveOrUpdate.do',
                type: 'post',
                dataType : 'json',
                data: paramData,
                async: false,
                success: function (result) {
                    if(result.status == 'success') {
                        $('#id').val(result.data.id);
                        alert('成功 !');
                    }
                }
            });
        });
    });


</script>
</body>
</html>
