<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>完整demo</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/upload-plugin/core/zyFile.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/upload-plugin/control/js/zyUpload.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/upload-plugin/core/jq22.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/lang/zh-cn/zh-cn.js"></script>


    <!-- Le styles -->
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-responsive.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/upload-plugin/control/css/zyUpload.css">
    <link rel="stylesheet" href="<%=basePath%>static/class/bootstrap-validator/css/bootstrapValidator.css">
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->

    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/bootstrap-validator/js/bootstrapValidator.js"></script>
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>

<body>
    <form class="form-horizontal" id="skuForm">
        <div class="form-group">
            <label for="name" class="col-sm-4 control-label">商品名称</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="name" name="name" placeholder="商品名称">
            </div>
        </div>
        <div class="form-group">
            <label for="artNo" class="col-sm-4 control-label">商品货号</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="artNo" name="artNo" placeholder="商品货号">
            </div>
        </div>
        <div class="form-group">
            <label for="barCode" class="col-sm-4 control-label">商品条码</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="barCode" name="barCode" placeholder="商品条码">
            </div>
        </div>
        <div class="form-group">
            <label for="skuC1" class="col-sm-4 control-label">商品分类</label>
            <div class="col-sm-1">
                <select class="form-control" id="skuC1" name="skuC1">
                </select>
            </div>
            <div class="col-sm-1">
                <select class="form-control" id="skuC2" name="skuC2">
                </select>
            </div>
            <div class="col-sm-1">
                <input type="hidden" id="categoryName" name="categoryName" value="">
                <select class="form-control" id="skuC3" name="categoryId">
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
            <label for="brandId" class="col-sm-4 control-label">商品品牌</label>
            <div class="col-sm-5">
                <select class="form-control" id="brandId" name="brandId">
                    <c:forEach items="${brands}" var="brand">
                    <option value="${brand.id}">${brand.cname}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="brandId" class="col-sm-4 control-label">计量单位</label>
            <div class="col-sm-5">
                <select class="form-control" id="unitId" name="unit">
                    <c:forEach items="${unitMeasures}" var="unit">
                        <option value="${unit.id}">${unit.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="alert alert-success" role="alert">
                    <strong>价格设置</strong>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="priceCost" class="col-sm-4 control-label">成本价</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="priceCost" name="priceCost" placeholder="成本价">
            </div>
        </div>
        <div class="form-group">
            <label for="priceMarket" class="col-sm-4 control-label">市场零售价</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="priceMarket" name="priceMarket" placeholder="市场零售价">
            </div>
        </div>
        <div class="form-group">
            <label for="priceRetail" class="col-sm-4 control-label">微信零售价</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="priceRetail" name="priceRetail" placeholder="微信零售价">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">是否允许试用</label>
            <div class="col-sm-6">
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
            <label for="shipAmount" class="col-sm-4 control-label">运费设置</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="shipAmount" name="shipAmount" placeholder="运费设置">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="alert alert-success" role="alert">
                    <strong>合伙人设置</strong>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-6 col-sm-offset-3">
                <div class="col-xs-12 col-sm-4 text-success">
                    <div class="widget-box">
                        <div class="widget-header">
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <div>
                                    <label for="advanced">
                                        高级&nbsp;
                                    </label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="advanced" name="discounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                    每件商品<small class="text-info dfenrun"></small>元
                                </div>

                                <hr>

                                <div>
                                    <label for="advancedCount">
                                        拿货数量&nbsp;
                                    </label>
                                    <div>
                                        <input type="text" class="form-control" id="advancedCount" name="quantitys" placeholder="">
                                    </div>
                                    金额门槛<small class="text-info threshold"></small>元
                                </div>

                                <hr>

                                <div>
                                    <label for="advancedBail">
                                        保证金
                                    </label>
                                    <div>
                                        <input type="text" class="form-control" id="advancedBail" name="bails" placeholder="">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-4 text-warning">
                    <div class="widget-box">
                        <div class="widget-header">
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <div>
                                    <label for="advanced">
                                        中级&nbsp;
                                    </label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="intermediate" name="discounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                    每件商品<small class="text-info dfenrun"></small>元
                                </div>

                                <hr>

                                <div>
                                    <label for="advancedCount">
                                        拿货数量&nbsp;
                                    </label>
                                    <div>
                                        <input type="text" class="form-control" id="intermediateCount" name="quantitys" placeholder="">
                                    </div>
                                    金额门槛<small class="text-info threshold"></small>元
                                </div>

                                <hr>

                                <div>
                                    <label for="intermediateBail">
                                        保证金
                                    </label>
                                    <div>
                                        <input type="text" class="form-control" id="intermediateBail" name="bails" placeholder="">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-4">
                    <div class="widget-box">
                        <div class="widget-header">
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <div>
                                    <label for="advanced">
                                        初级&nbsp;
                                    </label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="primary" name="discounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                    每件商品<small class="text-info dfenrun"></small>元
                                </div>

                                <hr>

                                <div>
                                    <label for="advancedCount">
                                        拿货数量&nbsp;
                                    </label>
                                    <div>
                                        <input type="text" class="form-control" id="primaryCount" name="quantitys" placeholder="">
                                    </div>
                                    金额门槛<small class="text-info threshold"></small>元
                                </div>

                                <hr>

                                <div>
                                    <label for="primaryBail">
                                        保证金
                                    </label>
                                    <div>
                                        <input type="text" class="form-control" id="primaryBail" name="bails" placeholder="">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="alert alert-success" role="alert">
                    <strong>分销设置</strong>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-6 col-sm-offset-3">
                <div class="col-xs-12 col-sm-4 text-success">
                    <div class="widget-box">
                        <div class="widget-header">
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <div>
                                    <label for="advanced">
                                        倒数第一&nbsp;
                                    </label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="reciprocal1" name="distributionDiscounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                    返利<small class="text-info ffenrun"></small>元
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-4 text-warning">
                    <div class="widget-box">
                        <div class="widget-header">
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <div>
                                    <label for="advanced">
                                        倒数第二&nbsp;
                                    </label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="reciprocal2" name="distributionDiscounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                    返利<small class="text-info ffenrun"></small>元
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-4">
                    <div class="widget-box">
                        <div class="widget-header">
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <div>
                                    <label for="advanced">
                                        倒数第三&nbsp;
                                    </label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="reciprocal3" name="distributionDiscounts" placeholder="">
                                        <span class="input-group-addon">
                                            %
                                        </span>
                                    </div>
                                    返利<small class="text-info ffenrun"></small>元
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <hr/>

        <div class="form-group">
            <label class="col-sm-3 control-label">主图</label>
            <div class="col-sm-4 upload-widget" id="upload-widget"></div>
        </div>

        <div class="form-group">
            <label for="inShort" class="col-sm-4 control-label">一句话介绍</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="inShort" name="slogan" placeholder="一句话介绍">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-4 control-label">商品介绍</label>
            <div class="col-sm-4">
                <textarea rows="500" cols="300" id="content" name="content" style="display: none;"></textarea>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3"></label>
            <div class="col-sm-8">
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

                $('#priceRetail, input[name="discounts"], input[name="quantitys"], input[name="distributionDiscounts"]').keyup(function(){
                    var priceRetail = $('#priceRetail').val() ? $('#priceRetail').val() : 0 ;
                    $('input[name="quantitys"]').each(function(i,o){
                        var discount = $($('input[name="discounts"]').get(i)).val();
                            discount = discount==null||discount=='undefined' ? 0.00 : '0.'+discount;
                        var quantity = $(o).val() ? $(o).val() : 0;
                        var distributionDiscount = $($('input[name="distributionDiscounts"]').get(i)).val();
                            distributionDiscount = distributionDiscount==null||distributionDiscount=='undefined' ? 0.00 : '0.'+distributionDiscount;
                        $($('.dfenrun').get(i)).html((priceRetail*discount).toFixed(2));
                        $($('.threshold').get(i)).html((priceRetail*discount*quantity).toFixed(2));
                        $($('.ffenrun').get(i)).html((priceRetail*distributionDiscount).toFixed(2));
                    });
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

    </form>
</body>
</html>