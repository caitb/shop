<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>UI Elements - Ace Admin</title>

    <meta name="description" content="Common UI Features &amp; Elements" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/font-awesome.min.css" />

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/jquery-ui.custom.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/jquery.gritter.css" />

    <!-- text fonts -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-fonts.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace.min.css" id="main-ace-style" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-part2.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-skins.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-rtl.min.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-ie.min.css" />
    <![endif]-->

    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/dropzone.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/uncompressed/my-dropzone.css"/>

    <!-- inline styles related to this page -->
    <style>
        /* some elements used in demo only */
        .spinner-preview {
            width: 100px;
            height: 100px;
            text-align: center;
            margin-top: 60px;
        }

        .dropdown-preview {
            margin: 0 5px;
            display: inline-block;
        }
        .dropdown-preview  > .dropdown-menu {
            display: block;
            position: static;
            margin-bottom: 5px;
        }
    </style>

    <!-- ace settings handler -->
    <script src="<%=basePath%>static/ace2/js/uncompressed/ace-extra.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="<%=basePath%>static/ace2/js/html5shiv.min.js"></script>
    <script src="<%=basePath%>static/ace2/js/respond.min.js"></script>
    <![endif]-->

    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/ueditor.all.js"></script>
</head>

<body class="no-skin">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <!-- /section:basics/sidebar -->
    <div class="main-content">

        <!-- /section:basics/content.breadcrumbs -->
        <div class="page-content">

            <!-- /section:settings.box -->
            <div class="page-content-area">

                <div class="row">
                    <div class="col-xs-12 col-sm-12">

                        <form class="form-horizontal" role="form" id="skuForm">
                        <input type="hidden" name="spuId" value="${productInfo.comSpu.id}" />
                        <input type="hidden" name="skuId" value="${productInfo.comSku.id}" />
                        <input type="hidden" name="skuExtensionId" value="${productInfo.comSkuExtension.id}" />
                        <div class="profile-user-info profile-user-info-striped">
                            <div class="profile-info-row">
                                <div class="profile-info-name">
                                    基本信息
                                </div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="username">

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

                                    </span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">
                                    商品信息
                                </div>

                                <div class="profile-info-value">
                                            <div class="form-group">
                                                <label for="name" class="col-sm-2 control-label">商品名称</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" id="name" name="name" value="${productInfo.comSku.name}" placeholder="商品名称">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="alias" class="col-sm-2 control-label">商品别名</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" id="alias" name="alias" value="${productInfo.comSku.alias}" placeholder="商品别名">
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="eName" class="col-sm-2 control-label">商品英文名</label>
                                                <div class="col-sm-9">
                                                    <input type="text" class="form-control" id="eName" name="eName" value="${productInfo.comSku.eName}" placeholder="英文名">
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

                                            <!-- 商品标志 -->
                                            <div class="row">
                                                <div class="col-xs-10 col-xs-offset-1">
                                                    <div>
                                                        <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/product/prototype/" class="dropzone" id="productIcon">
                                                            <div class="fallback">
                                                                <input name="file" type="file" multiple=""/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- 商品列表背景图 -->
                                            <div class="row">
                                                <div class="col-xs-10 col-xs-offset-1">
                                                    <div>
                                                        <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/product/prototype/" class="dropzone" id="dropzoneSkuBackgroundImg">
                                                            <div class="fallback">
                                                                <input name="file" type="file" multiple=""/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- 商品配图 -->
                                            <div class="row">
                                                <div class="col-xs-10 col-xs-offset-1">
                                                    <div>
                                                        <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/product/prototype/" class="dropzone" id="illustratingPictureImg">
                                                            <div class="fallback">
                                                                <input name="file" type="file" multiple=""/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- 商品海报图(发展合伙人) -->
                                            <div class="row">
                                                <div class="col-xs-10 col-xs-offset-1">
                                                    <div>
                                                        <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/user/background_poster/" class="dropzone" id="dropzoneDevelopPoster">
                                                            <div class="fallback">
                                                                <input name="file" type="file" multiple=""/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- 商品主图 -->
                                            <div class="row">
                                                <div class="col-xs-10 col-xs-offset-1">
                                                    <div>
                                                        <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/product/prototype/&scale=scale" class="dropzone" id="dropzone">
                                                            <div class="fallback">
                                                                <input name="file" type="file" multiple=""/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">
                                    价格设置
                                </div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="age">

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

                                    </span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name"> 其他 </div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="signup">

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
                                            <label class="col-sm-2 control-label">是否代理商发货</label>
                                            <div class="col-sm-9">
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="isOwnShip" id="isOwnShip1" value="1" <c:if test="${productInfo.comSpu.isOwnShip == 1}">checked</c:if> >
                                                        是
                                                    </label>
                                                </div>
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="isOwnShip" id="isOwnShip2" value="0" <c:if test="${productInfo.comSpu.isOwnShip == 0}">checked</c:if> >
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

                                    </span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name"> 商品详情 </div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="login">

                                        <div class="form-group">
                                            <label for="inShort" class="col-sm-2 control-label">一句话介绍</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" id="inShort" name="slogan" value="${productInfo.comSpu.slogan}" placeholder="一句话介绍">
                                            </div>
                                        </div>
                                        <textarea rows="500" cols="300" id="policy" name="policy" style="display: none;"></textarea>
                                        <textarea rows="500" cols="300" id="content" name="content" style="display: none;"></textarea>

                                        <!-- 商业政策 -->
                                        <div class="form-group">
                                            <div class="col-sm-10 col-sm-offset-1">
                                                <div class="alert alert-success" id="businessBar" role="alert" style="margin:20px 0 0 0;">
                                                    <strong>商业政策</strong>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-10 col-xs-offset-1">
                                                <div>
                                                    <script id="policyUEditor" type="text/plain" style="height: 500px"></script>
                                                    <script type="text/javascript">
                                                        $('#policyUEditor').width($('#businessBar').width()+32);
                                                        //实例化编辑器
                                                        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
                                                        var policyUEditor = UE.getEditor('policyUEditor');
                                                    </script>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- 商品详情 -->
                                        <div class="form-group">
                                            <div class="col-sm-10 col-sm-offset-1">
                                                <div class="alert alert-success" role="alert" style="margin:20px 0 0 0;">
                                                    <strong>商品详情</strong>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-10 col-xs-offset-1">
                                                <div>
                                                    <script id="contentUEditor" type="text/plain" style="height: 500px"></script>
                                                    <script type="text/javascript">
                                                        $('#contentUEditor').width($('#businessBar').width()+32);
                                                        //实例化编辑器
                                                        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
                                                        var contentUEditor = UE.getEditor('contentUEditor');
                                                    </script>
                                                </div>
                                            </div>
                                        </div>

                                    </span>
                                </div>
                            </div>

                        </div>
                        </form>
                    </div><!-- /.col -->

                </div><!-- /.row -->

                <row>
                    <label class="col-sm-5"></label>
                    <div class="col-sm-6">
                        <button type="reset" class="btn btn-lg btn-default">重置</button>
                        <button type="submit" class="btn btn-lg btn-info" id="skuSave">保存</button>
                    </div>
                </row>

            </div><!-- /.page-content-area -->
        </div><!-- /.page-content -->
    </div><!-- /.main-content -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='<%=basePath%>static/ace2/js/jquery.min.js'>"+"<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='<%=basePath%>static/ace2/js/jquery1x.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath%>static/ace2/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script src="<%=basePath%>static/ace2/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="../assets/js/excanvas.min.js"></script>
<![endif]-->
<script src="<%=basePath%>static/ace2/js/jquery-ui.custom.min.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/bootstrap-validator/js/bootstrapValidator.js"></script>
<script src="<%=basePath%>static/ace2/js/bootbox.min.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.easypiechart.min.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.gritter.min.js"></script>
<script src="<%=basePath%>static/ace2/js/spin.min.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    jQuery(function($) {
        /**
         $('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				  console.log(e.target.getAttribute("href"));
				})

         $('#accordion').on('shown.bs.collapse', function (e) {
					console.log($(e.target).is('#collapseTwo'))
				});
         */

        $('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            if($(e.target).attr('href') == "#home") drawChartNow();
        })


        /**
         //go to next tab, without user clicking
         $('#myTab > .active').next().find('> a').trigger('click');
         */


        $('#accordion-style').on('click', function(ev){
            var target = $('input', ev.target);
            var which = parseInt(target.val());
            if(which == 2) $('#accordion').addClass('accordion-style2');
            else $('#accordion').removeClass('accordion-style2');
        });

        //$('[href="#collapseTwo"]').trigger('click');


        var oldie = /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase());
        $('.easy-pie-chart.percentage').each(function(){
            $(this).easyPieChart({
                barColor: $(this).data('color'),
                trackColor: '#EEEEEE',
                scaleColor: false,
                lineCap: 'butt',
                lineWidth: 8,
                animate: oldie ? false : 1000,
                size:75
            }).css('color', $(this).data('color'));
        });

        $('[data-rel=tooltip]').tooltip();
        $('[data-rel=popover]').popover({html:true});


        $('#gritter-regular').on(ace.click_event, function(){
            $.gritter.add({
                title: 'This is a regular notice!',
                text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="blue">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets+'/avatars/avatar1.png',
                sticky: false,
                time: '',
                class_name: (!$('#gritter-light').get(0).checked ? 'gritter-light' : '')
            });

            return false;
        });

        $('#gritter-sticky').on(ace.click_event, function(){
            var unique_id = $.gritter.add({
                title: 'This is a sticky notice!',
                text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="red">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets+'/avatars/avatar.png',
                sticky: true,
                time: '',
                class_name: 'gritter-info' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-without-image').on(ace.click_event, function(){
            $.gritter.add({
                // (string | mandatory) the heading of the notification
                title: 'This is a notice without an image!',
                // (string | mandatory) the text inside the notification
                text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="orange">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                class_name: 'gritter-success' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-max3').on(ace.click_event, function(){
            $.gritter.add({
                title: 'This is a notice with a max of 3 on screen at one time!',
                text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="green">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets+'/avatars/avatar3.png',
                sticky: false,
                before_open: function(){
                    if($('.gritter-item-wrapper').length >= 3)
                    {
                        return false;
                    }
                },
                class_name: 'gritter-warning' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-center').on(ace.click_event, function(){
            $.gritter.add({
                title: 'This is a centered notification',
                text: 'Just add a "gritter-center" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
                class_name: 'gritter-info gritter-center' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });

        $('#gritter-error').on(ace.click_event, function(){
            $.gritter.add({
                title: 'This is a warning notification',
                text: 'Just add a "gritter-light" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
                class_name: 'gritter-error' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $("#gritter-remove").on(ace.click_event, function(){
            $.gritter.removeAll();
            return false;
        });


        ///////


        $("#bootbox-regular").on(ace.click_event, function() {
            bootbox.prompt("What is your name?", function(result) {
                if (result === null) {

                } else {

                }
            });
        });

        $("#bootbox-confirm").on(ace.click_event, function() {
            bootbox.confirm("Are you sure?", function(result) {
                if(result) {
                    //
                }
            });
        });

        /**
         $("#bootbox-confirm").on(ace.click_event, function() {
					bootbox.confirm({
						message: "Are you sure?",
						buttons: {
						  confirm: {
							 label: "OK",
							 className: "btn-primary btn-sm",
						  },
						  cancel: {
							 label: "Cancel",
							 className: "btn-sm",
						  }
						},
						callback: function(result) {
							if(result) alert(1)
						}
					  }
					);
				});
         **/

        $("#bootbox-options").on(ace.click_event, function() {
            bootbox.dialog({
                message: "<span class='bigger-110'>I am a custom dialog with smaller buttons</span>",
                buttons:
                {
                    "success" :
                    {
                        "label" : "<i class='ace-icon fa fa-check'></i> Success!",
                        "className" : "btn-sm btn-success",
                        "callback": function() {
                            //Example.show("great success");
                        }
                    },
                    "danger" :
                    {
                        "label" : "Danger!",
                        "className" : "btn-sm btn-danger",
                        "callback": function() {
                            //Example.show("uh oh, look out!");
                        }
                    },
                    "click" :
                    {
                        "label" : "Click ME!",
                        "className" : "btn-sm btn-primary",
                        "callback": function() {
                            //Example.show("Primary button");
                        }
                    },
                    "button" :
                    {
                        "label" : "Just a button...",
                        "className" : "btn-sm"
                    }
                }
            });
        });



        $('#spinner-opts small').css({display:'inline-block', width:'60px'})

        var slide_styles = ['', 'green','red','purple','orange', 'dark'];
        var ii = 0;
        $("#spinner-opts input[type=text]").each(function() {
            var $this = $(this);
            $this.hide().after('<span />');
            $this.next().addClass('ui-slider-small').
            addClass("inline ui-slider-"+slide_styles[ii++ % slide_styles.length]).
            css({'width':'125px'}).slider({
                value:parseInt($this.val()),
                range: "min",
                animate:true,
                min: parseInt($this.data('min')),
                max: parseInt($this.data('max')),
                step: parseFloat($this.data('step')),
                slide: function( event, ui ) {
                    $this.val(ui.value);
                    spinner_update();
                }
            });
        });





        $.fn.spin = function(opts) {
            this.each(function() {
                var $this = $(this),
                        data = $this.data();

                if (data.spinner) {
                    data.spinner.stop();
                    delete data.spinner;
                }
                if (opts !== false) {
                    data.spinner = new Spinner($.extend({color: $this.css('color')}, opts)).spin(this);
                }
            });
            return this;
        };

        function spinner_update() {
            var opts = {};
            $('#spinner-opts input[type=text]').each(function() {
                opts[this.name] = parseFloat(this.value);
            });
            $('#spinner-preview').spin(opts);
        }



        $('#id-pills-stacked').removeAttr('checked').on('click', function(){
            $('.nav-pills').toggleClass('nav-stacked');
        });


    });
</script>

<!-- the following scripts are used in demo only for onpage help and you don't need them -->
<link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace.onpage-help.css" />
<link rel="stylesheet" href="<%=basePath%>static/ace2/docs/assets/js/themes/sunburst.css" />

<script type="text/javascript"> ace.vars['base'] = '..'; </script>
<script src="<%=basePath%>static/ace2/js/elements.onpage-help.js"></script>
<script src="<%=basePath%>static/ace2/js/ace.onpage-help.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/rainbow.js"></script>
<%--<script src="<%=basePath%>static/ace2/docs/assets/js/language/generic.js"></script>--%>
<%--<script src="<%=basePath%>static/ace2/docs/assets/js/language/html.js"></script>--%>
<%--<script src="<%=basePath%>static/ace2/docs/assets/js/language/css.js"></script>--%>
<%--<script src="<%=basePath%>static/ace2/docs/assets/js/language/javascript.js"></script>--%>
<script src="<%=basePath%>static/ace2/js/dropzone.min.js"></script>

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
            console.log('e: ' + e);
            alert('Dropzone.js does not support older browsers!');
        }
    }

    var dictMessage1 = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>等级图标</span> <br /> \
                                     <span class="smaller-80 grey">按顺序上传(由高到低)</span> <br /> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
    var productIcon  = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>商品标志</span> <br /> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
    var dropzoneSkuBackgroundImg = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>商品列表背景图</span> <br /> \
                                                 <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                                 <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
    var illustratingPictureImg   = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>商品配图</span> <br /> \
                                                 <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                                 <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
    var dropzoneDevelopPoster    = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>发展合伙人海报</span> <br /> \
                                                 <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                                 <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';

    initDropzone('#productIcon', productIcon, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="iconUrl" value="'+res.url+'" />');
        $('#skuForm').append('<input type="hidden" name="icon" value="'+res.title+'" />');
    });
    initDropzone('#dropzoneSkuBackgroundImg', dropzoneSkuBackgroundImg, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="skuBackgroundImgUrl" value="'+res.url+'" />');
        $('#skuForm').append('<input type="hidden" name="skuBackgroundImg" value="'+res.title+'" />');
    });
    initDropzone('#illustratingPictureImg', illustratingPictureImg, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="illustratingPictureUrl" value="'+res.url+'" />');
        $('#skuForm').append('<input type="hidden" name="illustratingPicture" value="'+res.title+'" />');
    });
    initDropzone('#dropzoneDevelopPoster', dropzoneDevelopPoster, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="posterUrl" value="'+res.url+'" />');
        $('#skuForm').append('<input type="hidden" name="poster" value="'+res.title+'" />');
    });
    initDropzone('#dropzone', null, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="imgUrls" value="'+res.title+'" />');
        $('#skuForm').append('<input type="hidden" name="imgNames" value="'+res.original+'" />');
    });




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
                    $('#policy').val(UE.getEditor('policyUEditor').getContent());
                    $('#content').val(UE.getEditor('contentUEditor').getContent());
                    $.ajax({
                        url: '<%=basePath%>product/add.do',
                        type: 'post',
                        data: $('#skuForm').serialize(),
                        success: function(msg){
                            if(msg == 'success'){
                                parent.window.$('#myTabbable').closeTab('tab2-2');
                                parent.window.$('#myTabbable').add('tab2-2', '商品列表', '<%=basePath%>product/list.shtml');
                                parent.window.$('#myTabbable').closeTab('tab2-1');
                            }else{
                                alert('添加商品失败');
                            }
                        }
                    });
                });
    });
</script>
</body>
</html>
