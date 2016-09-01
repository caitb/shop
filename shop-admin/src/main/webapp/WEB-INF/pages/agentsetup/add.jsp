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
                            <input type="hidden" name="id" value="${skuAgent.id}" >
                            <div class="form-group">
                                <label for="skuId" class="col-sm-2 control-label">商品</label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="skuId" name="skuId" <c:if test="${skuAgent != null}">disabled</c:if> >
                                        <c:forEach items="${skus}" var="sku">
                                            <option value="${sku.id}" <c:if test="${sku.id == skuAgent.skuId}">selected</c:if> >${sku.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="agentLevelId" class="col-sm-2 control-label">代理等级</label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="agentLevelId" name="agentLevelId" <c:if test="${skuAgent != null}">disabled</c:if> >
                                        <c:forEach items="${agentLevels}" var="level">
                                            <option value="${level.id}" <c:if test="${level.id == skuAgent.agentLevelId}">selected</c:if> >${level.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="unitPrice" class="col-sm-2 control-label">单价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="unitPrice" name="unitPrice" value="${skuAgent.unitPrice}" placeholder="单价">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="totalPrice" class="col-sm-2 control-label">总价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="totalPrice" name="totalPrice" value="${skuAgent.totalPrice}" placeholder="总价">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="rewardUnitPrice" class="col-sm-2 control-label">推荐人奖励单价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="rewardUnitPrice" name="rewardUnitPrice" value="${skuAgent.rewardUnitPrice}" placeholder="推荐人奖励单价">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="bail" class="col-sm-2 control-label">保证金</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="bail" name="bail" value="${skuAgent.bail}" placeholder="保证金">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="quantity" class="col-sm-2 control-label">拿货数量</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${skuAgent.quantity}" placeholder="拿货数量">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="freemanUpperNum" class="col-sm-2 control-label">招募小白上限数量</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="freemanUpperNum" name="freemanUpperNum" value="${skuAgent.freemanUpperNum}" placeholder="招募小白上限数量">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否展示在前台</label>
                                <div class="col-sm-9">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="isShow" id="isShow" value="1" <c:if test="${skuAgent == null || skuAgent.isShow == 1}">checked</c:if> >
                                            是
                                        </label>
                                    </div>
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="isShow" id="isShow2" value="0" <c:if test="${skuAgent.isShow == 0}">checked</c:if> >
                                            否
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否支持升级</label>
                                <div class="col-sm-9">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="isUpgrade" id="isUpgrade" value="1" <c:if test="${skuAgent == null || skuAgent.isUpgrade == 1}">checked</c:if> >
                                            是
                                        </label>
                                    </div>
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="isUpgrade" id="isUpgrade2" value="0" <c:if test="${skuAgent.isUpgrade == 0}">checked</c:if> >
                                            否
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="dropzoneIcon" class="col-sm-2 control-label">代理等级图标</label>
                                <div class="col-sm-9">
                                    <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/product/agent_icon/" class="dropzone" id="dropzoneIcon">
                                        <div class="fallback">
                                            <input name="file" type="file" multiple=""/>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <!-- 证书背景图 -->
                            <div class="form-group">
                                <label for="dropzoneBackImg" class="col-sm-2 control-label">证书背景图</label>
                                <div class="col-sm-9">
                                    <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/product/agent_icon/" class="dropzone" id="dropzoneBackImg">
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
<script src="<%=basePath%>static/ace2/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>static/ace2/js/uncompressed/bootbox.js"></script>

<script type="text/javascript">

    Dropzone.autoDiscover = false;
    function initDropzone(selector,dictMessage, callback){
        var dictDefaultMessage = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>商品主图</span> <br /> \
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

    var dictMessage1 = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>等级图标</span> <br /> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
    var dictMessage2 = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>证书背景图</span> <br /> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';

    initDropzone('#dropzoneIcon', dictMessage1, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="icon" value="'+res.title+'" />');
    });
    initDropzone('#dropzoneBackImg', dictMessage2, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="backImg" value="'+res.title+'" />');
    });
</script>
<script>

    $('#skuSave').on('click', function(){
        $('#skuForm').submit();
    });

    $(document).ready(function() {
        $('body').on('keyup', '#priceRetail, input[name="unitPrices"], input[name="quantitys"], input[name="distributionDiscounts"]', function(){
            var priceRetail = $('#priceRetail').val() ? $('#priceRetail').val() : 0 ;
            $('input[name="quantitys"]').each(function(i,o){
                var unitPrices = $($('input[name="unitPrices"]').get(i)).val();
                unitPrices = unitPrices==null||unitPrices=='undefined' ? 0.00 : unitPrices;
                var quantity = $(o).val() ? $(o).val() : 0;
                var distributionDiscount = $($('input[name="distributionDiscounts"]').get(i)).val();
                distributionDiscount = distributionDiscount==null||distributionDiscount=='undefined' ? 0.00 : distributionDiscount*0.01;
                $($('.dfenrun').get(i)).html(unitPrices);
                $($('.threshold').get(i)).html(unitPrices*quantity);
                $($('input[name="totalPrices"]').get(i)).val(unitPrices*quantity);
                $($('.ffenrun').get(i)).html((priceRetail*distributionDiscount).toFixed(2));
            });
        });

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
                        levelCount: {
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
                    $.ajax({
                        url: '<%=basePath%>skuAgent/add.do',
                        type: 'post',
                        data: $('#skuForm').serialize(),
                        success: function(result){
                            result = window.eval('('+result+')');

                            if(result.code == 'success'){
                                parent.window.$('#myTabbable').closeTab('tab22-0');
                                parent.window.$('#myTabbable').add('tab22-0', '商品代理设置列表', '<%=basePath%>skuAgent/list.shtml');
                                parent.window.$('#myTabbable').closeTab('agentsetup-');
                            }else{
                                $.gritter.add({
                                    title: result.msg,
                                    text: result.msg,
                                    class_name: 'gritter-error'
                                });
                            }
                        }
                    });
                });
    });
</script>
</body>
</html>
