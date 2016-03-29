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
    <title>Tables - Ace Admin</title>

    <meta name="description" content="Static &amp; Dynamic Tables" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/tab.css" />
    <link rel="stylesheet" href="<%=basePath%>static/class/upload-plugin/control/css/zyUpload.css">
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

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-ie.min.css" />
    <![endif]-->

    <!-- Le styles -->
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-responsive.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/upload-plugin/control/css/zyUpload.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-validator/css/bootstrapValidator.css">
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->


    <!-- ace settings handler -->
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript">
        if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>static/ace2/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
    </script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/upload-plugin/core/zyFile.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/upload-plugin/control/js/zyUpload.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/upload-plugin/core/jq22.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/bootstrap-validator/js/bootstrapValidator.js"></script>
    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <script src="<%=basePath%>static/ace2/js/bootstrap.min.js"></script>

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
                        <form class="form-horizontal" id="skuForm" role="form">
                            <!-- #section:elements.form -->
                            <div class="form-group">
                                <label for="name" class="col-sm-3 control-label no-padding-right">商品名称</label>
                                <div class="col-sm-9">
                                    <input type="text" class="col-xs-10 col-sm-5" id="name" name="name" placeholder="商品名称">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="artNo" class="col-sm-3 control-label no-padding-right">商品货号</label>
                                <div class="col-sm-9">
                                    <input type="text" class="col-xs-10 col-sm-5" id="artNo" name="artNo" placeholder="商品货号">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="barCode" class="col-sm-3 control-label no-padding-right">商品条码</label>
                                <div class="col-sm-9">
                                    <input type="text" class="col-xs-10 col-sm-5" id="barCode" name="barCode" placeholder="商品条码">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="skuC1" class="col-sm-3 control-label no-padding-right">商品分类</label>
                                <div class="col-sm-3">
                                    <select class="col-xs-10 col-sm-5" id="skuC1" name="skuC1">
                                    </select>
                                </div>
                                <div class="col-sm-3">
                                    <select class="col-xs-10 col-sm-5" id="skuC2" name="skuC2">
                                    </select>
                                </div>
                                <div class="col-sm-3">
                                    <input type="hidden" id="categoryName" name="categoryName" value="">
                                    <select class="col-xs-10 col-sm-5" id="skuC3" name="categoryId">
                                    </select>
                                </div>
                                <script>
                                    var categories = window.eval('(' + '${categories}' + ')');
                                    var c1 = {};//一级类别
                                    var c2 = {};//二级类别
                                    var c3 = {};//三级类别

                                    c1['sub'+0] = [];
                                    for(var i in categories){
                                        if(categories[i].level == 1){

                                            c1['sub'+0].push(categories[i]);

                                            c2['sub'+categories[i].id] = [];
                                            for(var sub in categories){
                                                if(categories[sub].pid == categories[i].id) c2['sub'+categories[i].id].push(categories[sub]);
                                            }

                                            for(var sub in c2['sub'+categories[i].id]){
                                                c3['sub'+c2['sub'+categories[i].id][sub].id] = [];
                                                for(var ss in categories){
                                                    if(categories[ss].pid == c2['sub'+categories[i].id][sub].id) c3['sub'+c2['sub'+categories[i].id][sub].id].push(categories[ss]);
                                                }
                                            }

                                        }
                                    }


                                    var $skuC1 = $('#skuC1');
                                    var $skuC2 = $('#skuC2');
                                    var $skuC3 = $('#skuC3');

                                    $skuC1.html('<option value="-1">请选择</option>');
                                    for(var sub in c1['sub'+0]){
                                        $skuC1.append('<option value="' + c1['sub'+0][sub].id + '">' + c1['sub'+0][sub].name + '</option>');
                                    }

                                    $skuC1.change(function(){
                                        $skuC2.empty().html('<option value="-1">请选择</option>');
                                        $skuC3.empty().html('<option value="-1">请选择</option>');

                                        for(var sub in c2['sub'+$(this).val()]){
                                            $skuC2.append('<option value="'+ c2['sub'+$(this).val()][sub].id +'">'+ c2['sub'+$(this).val()][sub].name+'</option>');
                                        }
                                    });

                                    $skuC2.change(function(){
                                        $skuC3.empty().html('<option value="-1">请选择</option>');

                                        for(var sub in c3['sub'+$(this).val()]){
                                            $skuC3.append('<option value="'+ c3['sub'+$(this).val()][sub].id +'">'+ c3['sub'+$(this).val()][sub].name+'</option>');
                                        }
                                    });

                                    $skuC3.change(function(){
                                        $('#categoryName').val($(this).find('option[value="'+$(this).val()+'"]').html());
                                    });
                                </script>
                            </div>

                            <div class="form-group">
                                <label for="brandId" class="col-sm-3 control-label no-padding-right">商品品牌</label>
                                <div class="col-sm-9">
                                    <select class="col-xs-10 col-sm-5" id="brandId" name="brandId">
                                        <c:forEach items="${brands}" var="brand">
                                            <option value="${brand.id}">${brand.cname}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="priceCost" class="col-sm-3 control-label no-padding-right">成本价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="col-xs-10 col-sm-5" id="priceCost" name="priceCost" placeholder="成本价">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="priceMarket" class="col-sm-3 control-label no-padding-right">市场价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="col-xs-10 col-sm-5" id="priceMarket" name="priceMarket" placeholder="市场价">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="priceRetail" class="col-sm-3 control-label no-padding-right">零售价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="col-xs-10 col-sm-5" id="priceRetail" name="priceRetail" placeholder="零售价">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">是否允许试用</label>
                                <div class="col-sm-9">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="isTrial" id="isTrial1" value="1" checked>
                                            是
                                        </label>
                                    </div>
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="isTrial" id="isTrial2" value="0">
                                            否
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="shipAmount" class="col-sm-3 control-label no-padding-right">运费设置</label>
                                <div class="col-sm-9">
                                    <input type="text" class="col-xs-10 col-sm-5" id="shipAmount" name="shipAmount" placeholder="运费设置">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="advanced" class="col-sm-3 control-label no-padding-right">高级</label>
                                <div class="col-sm-2">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="advanced" name="discounts" placeholder="">
                                    <span class="input-group-addon">
                                        %
                                    </span>
                                    </div>
                                </div>
                                <label for="advancedCount" class="col-sm-1 control-label">拿货数量</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="advancedCount" name="quantitys" placeholder="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="intermediate" class="col-sm-3 control-label no-padding-right">中级</label>
                                <div class="col-sm-2">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="intermediate" name="discounts" placeholder="">
                                    <span class="input-group-addon">
                                        %
                                    </span>
                                    </div>
                                </div>
                                <label for="intermediateCount" class="col-sm-1 control-label">拿货数量</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="intermediateCount" name="quantitys" placeholder="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="primary" class="col-sm-3 control-label no-padding-right">初级</label>
                                <div class="col-sm-2">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="primary" name="discounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                </div>
                                <label for="primaryCount" class="col-sm-1 control-label">拿货数量</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="primaryCount" name="quantitys" placeholder="">
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="reciprocal1" class="col-sm-3 control-label no-padding-right">倒数第一</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="reciprocal1" name="distributionDiscounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="reciprocal2" class="col-sm-3 control-label no-padding-right">倒数第二</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="reciprocal2" name="distributionDiscounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="reciprocal3" class="col-sm-3 control-label no-padding-right">倒数第三</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="reciprocal3" name="distributionDiscounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">主图</label>
                                <div class="col-sm-4 upload-widget" id="upload-widget"></div>
                            </div>


                            <div class="form-group">
                                <label for="inShort" class="col-sm-3 control-label no-padding-right">一句话介绍</label>
                                <div class="col-sm-9">
                                    <input type="text" class="col-xs-10 col-sm-5" id="inShort" name="slogan" placeholder="一句话介绍">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">商品介绍</label>
                                <div class="col-sm-9">
                                    <textarea class="col-xs-10 col-sm-5" rows="500" cols="300" id="content" name="content" style="display: none;"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-9 col-lg-offset-2">
                                    <script id="editor" type="text/plain" style="height: 500px"></script>
                                    <script type="text/javascript">

                                        //实例化编辑器
                                        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
                                        var ue = UE.getEditor('editor');


                                        function isFocus(e){
                                            alert(UE.getEditor('editor').isFocus());
                                            UE.dom.domUtils.preventDefault(e)
                                        }
                                        function setblur(e){
                                            UE.getEditor('editor').blur();
                                            UE.dom.domUtils.preventDefault(e)
                                        }
                                        function insertHtml() {
                                            var value = prompt('插入html代码', '');
                                            UE.getEditor('editor').execCommand('insertHtml', value)
                                        }
                                        function createEditor() {
                                            enableBtn();
                                            UE.getEditor('editor');
                                        }
                                        function getAllHtml() {
                                            alert(UE.getEditor('editor').getAllHtml())
                                        }
                                        function getContent() {
                                            var arr = [];
                                            arr.push("使用editor.getContent()方法可以获得编辑器的内容");
                                            arr.push("内容为：");
                                            arr.push(UE.getEditor('editor').getContent());
                                            alert(arr.join("\n"));
                                        }
                                        function getPlainTxt() {
                                            var arr = [];
                                            arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
                                            arr.push("内容为：");
                                            arr.push(UE.getEditor('editor').getPlainTxt());
                                            alert(arr.join('\n'))
                                        }
                                        function setContent(isAppendTo) {
                                            var arr = [];
                                            arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
                                            UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
                                            alert(arr.join("\n"));
                                        }
                                        function setDisabled() {
                                            UE.getEditor('editor').setDisabled('fullscreen');
                                            disableBtn("enable");
                                        }

                                        function setEnabled() {
                                            UE.getEditor('editor').setEnabled();
                                            enableBtn();
                                        }

                                        function getText() {
                                            //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
                                            var range = UE.getEditor('editor').selection.getRange();
                                            range.select();
                                            var txt = UE.getEditor('editor').selection.getText();
                                            alert(txt)
                                        }

                                        function getContentTxt() {
                                            var arr = [];
                                            arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
                                            arr.push("编辑器的纯文本内容为：");
                                            arr.push(UE.getEditor('editor').getContentTxt());
                                            alert(arr.join("\n"));
                                        }
                                        function hasContent() {
                                            var arr = [];
                                            arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
                                            arr.push("判断结果为：");
                                            arr.push(UE.getEditor('editor').hasContents());
                                            alert(arr.join("\n"));
                                        }
                                        function setFocus() {
                                            UE.getEditor('editor').focus();
                                        }
                                        function deleteEditor() {
                                            disableBtn();
                                            UE.getEditor('editor').destroy();
                                        }
                                        function disableBtn(str) {
                                            var div = document.getElementById('btns');
                                            var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
                                            for (var i = 0, btn; btn = btns[i++];) {
                                                if (btn.id == str) {
                                                    UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
                                                } else {
                                                    btn.setAttribute("disabled", "true");
                                                }
                                            }
                                        }
                                        function enableBtn() {
                                            var div = document.getElementById('btns');
                                            var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
                                            for (var i = 0, btn; btn = btns[i++];) {
                                                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
                                            }
                                        }

                                        function getLocalData () {
                                            alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
                                        }

                                        function clearLocalData () {
                                            UE.getEditor('editor').execCommand( "clearlocaldata" );
                                            alert("已清空草稿箱")
                                        }
                                    </script>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-5"></label>
                                <div class="col-sm-6">
                                    <button type="reset" class="btn btn-lg btn-default">重置</button>
                                    <button type="submit" class="btn btn-lg btn-info" id="skuSave">保存</button>
                                </div>
                                <script>
                                    <%--$('#skuSave').on('click', function(){--%>
                                    <%--$('#content').val(UE.getEditor('editor').getAllHtml());--%>
                                    <%--$.ajax({--%>
                                    <%--url: '<%=basePath%>product/add.do',--%>
                                    <%--type: 'post',--%>
                                    <%--data: $('#skuForm').serialize(),--%>
                                    <%--success: function(msg){--%>
                                    <%--alert(msg);--%>
                                    <%--}--%>
                                    <%--});--%>
                                    <%--});--%>

                                    $(document).ready(function() {
                                        $('#skuForm').bootstrapValidator({
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
                                                        artNo: {
                                                            message: '必须填写!',
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        barCode: {
                                                            message: '必须填写!',
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        categoryName: {
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        categoryId: {
                                                            message: '请选择商品类别!',
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        brandId: {
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        priceCost: {
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        priceMarket: {
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        priceRetail: {
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        discounts: {
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        'quantitys[]': {
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        },
                                                        'distributionDiscounts[]': {
                                                            validators: {
                                                                notEmpty: {}
                                                            }
                                                        }
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
                                                    $('#content').val(UE.getEditor('editor').getContent());
                                                    $.ajax({
                                                        url: '<%=basePath%>product/add.do',
                                                        type: 'post',
                                                        data: $('#skuForm').serialize(),
                                                        success: function(msg){
                                                            alert(msg);
                                                        }
                                                    });
                                                });
                                    });
                                </script>
                            </div>

                            <div class="clearfix form-actions">
                                <div class="col-md-offset-3 col-md-9">
                                    <button class="btn btn-info" type="button">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        Submit
                                    </button>

                                    &nbsp; &nbsp; &nbsp;
                                    <button class="btn" type="reset">
                                        <i class="ace-icon fa fa-undo bigger-110"></i>
                                        Reset
                                    </button>
                                </div>
                            </div>

                        </form>

                        <div id="modal-form" class="modal" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">×</button>
                                        <h4 class="blue bigger">Please fill the following form fields</h4>
                                    </div>

                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-5">
                                                <div class="space"></div>

                                                <label class="ace-file-input ace-file-multiple"><input type="file"><span class="ace-file-container" data-title="Drop files here or click to choose"><span class="ace-file-name" data-title="No File ..."><i class=" ace-icon ace-icon fa fa-cloud-upload"></i></span></span><a class="remove" href="#"><i class=" ace-icon fa fa-times"></i></a></label>
                                            </div>

                                            <div class="col-xs-12 col-sm-7">
                                                <div class="form-group">
                                                    <label for="form-field-select-3">Location</label>

                                                    <div>
                                                        <select class="chosen-select" data-placeholder="Choose a Country..." style="display: none;">
                                                            <option value="">&nbsp;</option>
                                                            <option value="AL">Alabama</option>
                                                            <option value="AK">Alaska</option>
                                                            <option value="AZ">Arizona</option>
                                                            <option value="AR">Arkansas</option>
                                                            <option value="CA">California</option>
                                                            <option value="CO">Colorado</option>
                                                            <option value="CT">Connecticut</option>
                                                            <option value="DE">Delaware</option>
                                                            <option value="FL">Florida</option>
                                                            <option value="GA">Georgia</option>
                                                            <option value="HI">Hawaii</option>
                                                            <option value="ID">Idaho</option>
                                                            <option value="IL">Illinois</option>
                                                            <option value="IN">Indiana</option>
                                                            <option value="IA">Iowa</option>
                                                            <option value="KS">Kansas</option>
                                                            <option value="KY">Kentucky</option>
                                                            <option value="LA">Louisiana</option>
                                                            <option value="ME">Maine</option>
                                                            <option value="MD">Maryland</option>
                                                            <option value="MA">Massachusetts</option>
                                                            <option value="MI">Michigan</option>
                                                            <option value="MN">Minnesota</option>
                                                            <option value="MS">Mississippi</option>
                                                            <option value="MO">Missouri</option>
                                                            <option value="MT">Montana</option>
                                                            <option value="NE">Nebraska</option>
                                                            <option value="NV">Nevada</option>
                                                            <option value="NH">New Hampshire</option>
                                                            <option value="NJ">New Jersey</option>
                                                            <option value="NM">New Mexico</option>
                                                            <option value="NY">New York</option>
                                                            <option value="NC">North Carolina</option>
                                                            <option value="ND">North Dakota</option>
                                                            <option value="OH">Ohio</option>
                                                            <option value="OK">Oklahoma</option>
                                                            <option value="OR">Oregon</option>
                                                            <option value="PA">Pennsylvania</option>
                                                            <option value="RI">Rhode Island</option>
                                                            <option value="SC">South Carolina</option>
                                                            <option value="SD">South Dakota</option>
                                                            <option value="TN">Tennessee</option>
                                                            <option value="TX">Texas</option>
                                                            <option value="UT">Utah</option>
                                                            <option value="VT">Vermont</option>
                                                            <option value="VA">Virginia</option>
                                                            <option value="WA">Washington</option>
                                                            <option value="WV">West Virginia</option>
                                                            <option value="WI">Wisconsin</option>
                                                            <option value="WY">Wyoming</option>
                                                        </select><div class="chosen-container chosen-container-single" style="width: 0px;" title=""><a class="chosen-single" tabindex="-1"><span>&nbsp;</span><div><b></b></div></a><div class="chosen-drop"><div class="chosen-search"><input type="text" autocomplete="off"></div><ul class="chosen-results"></ul></div></div>
                                                    </div>
                                                </div>

                                                <div class="space-4"></div>

                                                <div class="form-group">
                                                    <label for="form-field-username">Username</label>

                                                    <div>
                                                        <input class="input-large" type="text" id="form-field-username" placeholder="Username" value="alexdoe">
                                                    </div>
                                                </div>

                                                <div class="space-4"></div>

                                                <div class="form-group">
                                                    <label for="form-field-first">Name</label>

                                                    <div>
                                                        <input class="input-medium" type="text" id="form-field-first" placeholder="First Name" value="Alex">
                                                        <input class="input-medium" type="text" id="form-field-last" placeholder="Last Name" value="Doe">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="modal-footer">
                                        <button class="btn btn-sm" data-dismiss="modal">
                                            <i class="ace-icon fa fa-times"></i>
                                            Cancel
                                        </button>

                                        <button class="btn btn-sm btn-primary">
                                            <i class="ace-icon fa fa-check"></i>
                                            Save
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div><!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content-area -->
        </div>

    </div><!-- /.main-content -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

</body>
</html>
