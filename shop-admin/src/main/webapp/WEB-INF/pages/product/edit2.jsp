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
                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">商品名称</label>
                                <div class="col-sm-9">
                                    <input type="hidden" name="spuId" value="${productInfo.comSpu.id}" />
                                    <input type="hidden" name="skuId" value="${productInfo.comSku.id}" />
                                    <input type="text" class="form-control" id="name" name="name" value="${productInfo.comSku.name}" placeholder="商品名称">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="brandId" class="col-sm-2 control-label">商品标志</label>
                                <div class="col-sm-9">
                                    <div action="<%=basePath%>ueditor.do?action=uploadimage" class="dropzone" id="dropzone2">
                                        <div class="fallback">
                                            <input name="file" type="file" multiple=""/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="artNo" class="col-sm-2 control-label">商品货号</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="artNo" name="artNo" value="${productInfo.comSpu.artNo}" placeholder="商品货号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="barCode" class="col-sm-2 control-label">商品条码</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="barCode" name="barCode" value="${productInfo.comSku.barCode}" placeholder="商品条码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="skuC1" class="col-sm-2 control-label">商品分类</label>
                                <div class="col-sm-3">
                                    <select class="form-control" id="skuC1" name="skuC1">
                                    </select>
                                </div>
                                <div class="col-sm-3">
                                    <select class="form-control" id="skuC2" name="skuC2">
                                    </select>
                                </div>
                                <div class="col-sm-3">
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
                                    var c1id = '${c1id}';
                                    var c2id = '${c2id}';
                                    var c3id = '${c3id}';

                                    $skuC1.html('<option value="-1">请选择</option>');
                                    for(var sub in c1['sub'+0]){
                                        if(c1['sub'+0][sub].id == c1id){
                                            $skuC1.append('<option value="' + c1['sub'+0][sub].id + '" selected>' + c1['sub'+0][sub].name + '</option>');
                                        }else{
                                            $skuC1.append('<option value="' + c1['sub'+0][sub].id + '">' + c1['sub'+0][sub].name + '</option>');
                                        }
                                    }

                                    for(var sub in c2['sub'+c1id]){
                                        if(c2['sub'+c1id][sub].id == c2id){
                                            $skuC2.append('<option value="'+ c2['sub'+c1id][sub].id +'" selected>'+ c2['sub'+c1id][sub].name+'</option>');
                                        }else{
                                            $skuC2.append('<option value="'+ c2['sub'+c1id][sub].id +'">'+ c2['sub'+c1id][sub].name+'</option>');
                                        }
                                    }

                                    for(var sub in c3['sub'+c2id]){
                                        if(c3['sub'+c2id][sub].id == c3id){
                                            $skuC3.append('<option value="'+ c3['sub'+c2id][sub].id +'" selected>'+ c3['sub'+c2id][sub].name+'</option>');
                                        }else{
                                            $skuC3.append('<option value="'+ c3['sub'+c2id][sub].id +'">'+ c3['sub'+c2id][sub].name+'</option>');
                                        }
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
                                <label for="brandId" class="col-sm-2 control-label">商品品牌</label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="brandId" name="brandId">
                                        <option>请选择</option>
                                        <c:forEach items="${brands}" var="brand">
                                            <option value="${brand.id}" <c:if test="${productInfo.comSpu.brandId == brand.id}">selected</c:if> >${brand.cname}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="brandId" class="col-sm-2 control-label">计量单位</label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="unitId" name="unitId">
                                        <c:forEach items="${comUnitMeasures}" var="comUnitMeasure">
                                            <option value="${comUnitMeasure.id}" <c:if test="${productInfo.comSpu.unit == comUnitMeasure.id}">selected</c:if> >${comUnitMeasure.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-10 col-sm-offset-1">
                                    <div class="alert alert-success" role="alert">
                                        <strong>价格设置</strong>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="priceCost" class="col-sm-2 control-label">成本价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="priceCost" name="priceCost" value="${productInfo.comSku.priceCost}" placeholder="成本价">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="priceMarket" class="col-sm-2 control-label">市场零售价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="priceMarket" name="priceMarket" value="${productInfo.comSku.priceMarket}" placeholder="市场零售价">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="priceRetail" class="col-sm-2 control-label">微信零售价</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="priceRetail" name="priceRetail" value="${productInfo.comSku.priceRetail}" placeholder="微信零售价">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否允许试用</label>
                                <div class="col-sm-9">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="isTrial" id="isTrial1" value="1" <c:if test="${productInfo.comSpu.isTrial == 1}">checked</c:if> >
                                            是
                                        </label>
                                    </div>
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="isTrial" id="isTrial2" value="0" <c:if test="${productInfo.comSpu.isTrial == 0}">checked</c:if> >
                                            否
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="shipAmount" class="col-sm-2 control-label">运费设置</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="shipAmount" name="shipAmount" value="${productInfo.comSpu.shipAmount}" placeholder="运费设置">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-10 col-sm-offset-1">
                                    <div class="alert alert-success" role="alert">
                                        <strong>合伙人设置</strong>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="brandId" class="col-sm-2 control-label">代理等级数</label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="levelCount" name="levelCount" disabled>
                                        <option value="">${productInfo.pfSkuAgents.size()}</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="brandId" class="col-sm-2 control-label">代理等级图标</label>
                                <div class="col-sm-9" id="levelIcon">
                                    <div action="<%=basePath%>ueditor.do?action=uploadimage" class="dropzone" id="dropzone1">
                                        <div class="fallback">
                                            <input name="file" type="file" multiple=""/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-10 col-sm-offset-1">
                                    <div class="col-xs-12 col-sm-4">
                                        <div class="widget-box">
                                            <div class="widget-header">
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main" id="discounts">
                                                    <c:forEach items="${productInfo.pfSkuAgents}" var="pfSkuAgent">
                                                    <div>
                                                        <c:forEach items="${agentLevels}" var="agentLevel">
                                                            <c:if test="${agentLevel.id == pfSkuAgent.agentLevelId}"><label for="advanced">${agentLevel.name}</label></c:if>
                                                        </c:forEach>
                                                        <div class="input-group">
                                                            <input type="hidden" name="skuAgentIds" value="${pfSkuAgent.id}" />
                                                            <input type="text" class="form-control" id="advanced" name="discounts" value="${pfSkuAgent.discount}" placeholder="">
                                                            <span class="input-group-addon">%</span>
                                                        </div>
                                                        每件商品<small class="text-info dfenrun"></small>元
                                                    </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-4">
                                        <div class="widget-box">
                                            <div class="widget-header">
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main" id="quantitys">
                                                    <c:forEach items="${productInfo.pfSkuAgents}" var="pfSkuAgent">
                                                    <div>
                                                        <label for="advancedCount">
                                                            拿货数量&nbsp;
                                                        </label>
                                                        <div>
                                                            <input type="text" class="form-control" id="advancedCount" name="quantitys" value="${pfSkuAgent.quantity}" placeholder="">
                                                        </div>
                                                        金额门槛<small class="text-info threshold"></small>元
                                                    </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-4">
                                        <div class="widget-box">
                                            <div class="widget-header">
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main" id="bails">
                                                    <c:forEach items="${productInfo.pfSkuAgents}" var="pfSkuAgent">
                                                    <div>
                                                        <label for="advancedBail">
                                                            保证金
                                                        </label>
                                                        <div>
                                                            <input type="text" class="form-control" id="advancedBail" name="bails" value="${pfSkuAgent.bail}" placeholder="">
                                                        </div>
                                                        &nbsp;
                                                    </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-10 col-sm-offset-1">
                                    <div class="alert alert-success" role="alert">
                                        <strong>分销设置</strong>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-10 col-sm-offset-1">
                                    <c:forEach items="${productInfo.sfSkuDistributions}" var="sfSkuDistribution">
                                    <div class="col-xs-12 col-sm-4 text-success">
                                        <div class="widget-box">
                                            <div class="widget-header">
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main">
                                                    <div>
                                                        <label for="distribution1">
                                                            <c:if test="${sfSkuDistribution.sort == 1}">倒数第一</c:if>
                                                            <c:if test="${sfSkuDistribution.sort == 2}">倒数第二</c:if>
                                                            <c:if test="${sfSkuDistribution.sort == 3}">倒数第三</c:if>
                                                        </label>
                                                        <div class="input-group">
                                                            <input type="hidden" name="skuDistributionIds" value="${sfSkuDistribution.id}" />
                                                            <input type="text" class="form-control" id="distribution1" name="distributionDiscounts" value="${sfSkuDistribution.discount}" placeholder="">
                                                            <span class="input-group-addon">%</span>
                                                        </div>
                                                        返利<small class="text-info ffenrun"></small>元
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="inShort" class="col-sm-2 control-label">一句话介绍</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="inShort" name="slogan" value="${productInfo.comSpu.slogan}" placeholder="一句话介绍">
                                </div>
                            </div>
                            <textarea rows="500" cols="300" id="content" name="content"
                                      style="display: none;"></textarea>
                        </form>

                        <div class="row">
                            <div class="col-xs-10 col-xs-offset-1">
                                <div>
                                    <form action="<%=basePath%>ueditor.do?action=uploadimage" class="dropzone" id="dropzone">
                                        <div class="fallback">
                                            <input name="file" type="file" multiple=""/>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-10 col-xs-offset-1">
                                <div>
                                    <script id="editor" type="text/plain" style="height: 500px"></script>
                                    <script type="text/javascript">

                                        //实例化编辑器
                                        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
                                        var ue = UE.getEditor('editor');


                                        function isFocus(e) {
                                            alert(UE.getEditor('editor').isFocus());
                                            UE.dom.domUtils.preventDefault(e)
                                        }
                                        function setblur(e) {
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

                                        function getLocalData() {
                                            alert(UE.getEditor('editor').execCommand("getlocaldata"));
                                        }

                                        function clearLocalData() {
                                            UE.getEditor('editor').execCommand("clearlocaldata");
                                            alert("已清空草稿箱")
                                        }
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
    window.jQuery || document.write("<script src='<%=basePath%>static/ace2/js/jquery.min.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='<%=basePath%>static/ace2/js/jquery1x.min.js'>" + "<" + "/script>");
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
        var dictDefaultMessage = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>商品主图</span> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
        try {
            var myDropzone = new Dropzone(selector, {
                paramName: "file", // The name that will be used to transfer the file
                maxFilesize: 0.5, // MB

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
                                     <span class="smaller-80 grey">按顺序上传(由高到低)</span> <br /> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
    var dictMessage2 = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>商品标志</span> <br /> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
    initDropzone('#dropzone', null, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="mainImgUrls" value="'+res.url+'" />');
        $('#skuForm').append('<input type="hidden" name="mainImgNames" value="'+res.title+'" />');
        $('#skuForm').append('<input type="hidden" name="mainImgOriginalNames" value="'+res.original+'" />');
    });
    initDropzone('#dropzone1', dictMessage1, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="iconImgUrls" value="'+res.url+'" />');
        $('#skuForm').append('<input type="hidden" name="iconImgNames" value="'+res.title+'" />');
    });
    initDropzone('#dropzone2', dictMessage2, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="proIconUrl" value="'+res.url+'" />');
        $('#skuForm').append('<input type="hidden" name="proIconName" value="'+res.title+'" />');
    });
</script>
<script>

    $('#priceRetail, input[name="discounts"], input[name="quantitys"], input[name="distributionDiscounts"]').keyup(calculate);

    function calculate(){
        var priceRetail = $('#priceRetail').val() ? $('#priceRetail').val() : 0 ;
        $('input[name="quantitys"]').each(function(i,o){
            var discount = $($('input[name="discounts"]').get(i)).val();
            discount = discount==null||discount=='undefined' ? 0.00 : discount*0.01;
            var quantity = $(o).val() ? $(o).val() : 0;
            var distributionDiscount = $($('input[name="distributionDiscounts"]').get(i)).val();
            distributionDiscount = distributionDiscount==null||distributionDiscount=='undefined' ? 0.00 : distributionDiscount*0.01;
            $($('.dfenrun').get(i)).html((priceRetail*discount).toFixed(2));
            $($('.threshold').get(i)).html((priceRetail*discount*quantity).toFixed(2));
            $($('.ffenrun').get(i)).html((priceRetail*distributionDiscount).toFixed(2));
        });
    }

    $('#skuSave').on('click', function(){
        $('#skuForm').submit();
    });

    $(document).ready(function() {

        $('input[name="discounts"]').each(function(i,o){
            $(this).val($(this).val()*100);
        });
        $('input[name="distributionDiscounts"]').each(function(i,o){
            $(this).val($(this).val()*100);
        });
        calculate();

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
                        url: '<%=basePath%>product/update.do',
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