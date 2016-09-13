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
                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                            <input type="hidden" name="pfSkuAgents[${status.index}].id" value="${skuAgent.id}">
                            <input type="hidden" name="pfSkuAgents[${status.index}].skuId" value="${skuAgent.skuId}">
                            <input type="hidden" name="pfSkuAgents[${status.index}].agentLevelId" value="${skuAgent.agentLevelId}">
                        </c:forEach>

                            <div class="profile-user-info profile-user-info-striped">
                                <div class="profile-info-row">
                                    <div class="profile-info-name">
                                        合伙人设置
                                    </div>

                                    <div class="profile-info-value">

                                        <div class="form-group">
                                            <label for="agentLevelCount" class="col-sm-0 control-label">个代理等级</label>
                                            <div class="col-sm-9">
                                                <select class="form-control" id="agentLevelCount" <c:if test="${pfSkuAgents != null && pfSkuAgents.size() > 0}">disabled</c:if> >
                                                    <c:forEach items="${agentLevels2}" var="level">
                                                        <option value="${level.id}" <c:if test="${pfSkuAgents.size() == level.id}">selected</c:if> >${level.id}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-0 control-label">&nbsp;</label>
                                            <div class="col-sm-12">
                                                <div class="col-xs-11 col-sm-11 pricing-span-body">
                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-red3">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">等级</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="levelNameUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <c:forEach items="${agentLevels2}" var="level">
                                                                                <c:if test="${skuAgent.agentLevelId == level.id}"><li>${level.name}</li></c:if>
                                                                            </c:forEach>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-orange">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">单价</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="unitPriceUl" >
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <c:if test="${status.index % 2 == 0}"><li> <input type="text" name="pfSkuAgents[${status.index}].unitPrice" value="${skuAgent.unitPrice}" style="width:100px;height:13px;border:0px;" > </li></c:if>
                                                                            <c:if test="${status.index % 2 == 1}"><li> <input type="text" name="pfSkuAgents[${status.index}].unitPrice" value="${skuAgent.unitPrice}" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li></c:if>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-blue">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">拿货数量</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="quantityUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <c:if test="${status.index % 2 == 0}"><li> <input type="text" name="pfSkuAgents[${status.index}].quantity" value="${skuAgent.quantity}" style="width:100px;height:13px;border:0px;" > </li></c:if>
                                                                            <c:if test="${status.index % 2 == 1}"><li> <input type="text" name="pfSkuAgents[${status.index}].quantity" value="${skuAgent.quantity}" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li></c:if>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-green">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">保证金</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="bailUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <c:if test="${status.index % 2 == 0}"><li> <input type="text" name="pfSkuAgents[${status.index}].bail" value="${skuAgent.bail}" style="width:100px;height:13px;border:0px;" > </li></c:if>
                                                                            <c:if test="${status.index % 2 == 1}"><li> <input type="text" name="pfSkuAgents[${status.index}].bail" value="${skuAgent.bail}" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li></c:if>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-grey">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">门槛</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="totalPriceUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <li> ${skuAgent.getUnitPrice().multiply(skuAgent.getQuantity()).add(skuAgent.getBail())} </li>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-orange">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">是否允许升级</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="isUpgradeUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <li>
                                                                                <input type="radio" name="pfSkuAgents[${status.index}].isUpgrade" value="1" <c:if test="${skuAgent.isUpgrade == 1}">checked</c:if> >
                                                                                是
                                                                                <input type="radio" name="pfSkuAgents[${status.index}].isUpgrade" value="0" <c:if test="${skuAgent.isUpgrade == 0}">checked</c:if> >
                                                                                否
                                                                            </li>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-orange">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">前台是否展示</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="isShowUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <li>
                                                                                <input type="radio" name="pfSkuAgents[${status.index}].isShow" value="1" <c:if test="${skuAgent.isShow == 1}">checked</c:if> >
                                                                                是
                                                                                <input type="radio" name="pfSkuAgents[${status.index}].isShow" value="0" <c:if test="${skuAgent.isShow == 0}">checked</c:if> >
                                                                                否
                                                                            </li>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-blue">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">小白上限数量</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="freemanUpperNumUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <c:if test="${status.index % 2 == 0}"><li> <input type="text" name="pfSkuAgents[${status.index}].freemanUpperNum" value="${skuAgent.freemanUpperNum}" style="width:100px;height:13px;border:0px;" > </li></c:if>
                                                                            <c:if test="${status.index % 2 == 1}"><li> <input type="text" name="pfSkuAgents[${status.index}].freemanUpperNum" value="${skuAgent.freemanUpperNum}" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li></c:if>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- 代理等级图标 -->
                                                    <div class="form-group">
                                                        <label for="dropzoneIcon" class="col-sm-2 control-label">&nbsp;</label>
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
                                                        <label for="dropzoneBackImg" class="col-sm-0 control-label">&nbsp;</label>
                                                        <div class="col-sm-9">
                                                            <div action="<%=basePath%>ueditor.do?action=uploadimage&osspath=static/product/agent_icon/" class="dropzone" id="dropzoneBackImg">
                                                                <div class="fallback">
                                                                    <input name="file" type="file" multiple=""/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name">
                                        推荐设置
                                    </div>

                                    <div class="profile-info-value">
                                        <div class="form-group">
                                            <label class="col-sm-0 control-label">&nbsp;</label>
                                            <div class="col-sm-10">
                                                <div class="col-xs-8 col-sm-9 pricing-span-body">
                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-red3">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">等级</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="recommendLevelNameUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent">
                                                                            <c:forEach items="${agentLevels2}" var="level">
                                                                                <c:if test="${skuAgent.agentLevelId == level.id}"><li>${level.name}</li></c:if>
                                                                            </c:forEach>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>

                                                    <%--<div class="pricing-span">--%>
                                                        <%--<div class="widget-box pricing-box-small widget-color-orange">--%>
                                                            <%--<div class="widget-header">--%>
                                                                <%--<h5 class="widget-title bigger lighter">允许平级推荐</h5>--%>
                                                            <%--</div>--%>

                                                            <%--<div class="widget-body">--%>
                                                                <%--<div class="widget-main no-padding">--%>
                                                                    <%--<ul class="list-unstyled list-striped pricing-table" id="isRecommendUl">--%>
                                                                        <%--&lt;%&ndash;<c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">&ndash;%&gt;--%>
                                                                            <%--&lt;%&ndash;<li>&ndash;%&gt;--%>
                                                                                <%--&lt;%&ndash;<input type="radio" name="pfSkuAgents[${status.index}].lateral" value="1" <c:if test="${skuAgent.lateral == 1}">checked</c:if> >&ndash;%&gt;--%>
                                                                                <%--&lt;%&ndash;是&ndash;%&gt;--%>
                                                                                <%--&lt;%&ndash;<input type="radio" name="pfSkuAgents[${status.index}].lateral" value="0" <c:if test="${skuAgent.lateral == 0}">checked</c:if> >&ndash;%&gt;--%>
                                                                                <%--&lt;%&ndash;否&ndash;%&gt;--%>
                                                                            <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
                                                                        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                                                                    <%--</ul>--%>
                                                                <%--</div>--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-blue">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">推荐奖励</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table" id="rewardUnitPriceUl">
                                                                        <c:forEach items="${pfSkuAgents}" var="skuAgent" varStatus="status">
                                                                            <c:if test="${status.index % 2 == 0}"><li> <input type="text" name="pfSkuAgents[${status.index}].rewardUnitPrice" value="${skuAgent.rewardUnitPrice}" style="width:100px;height:13px;border:0px;" > </li></c:if>
                                                                            <c:if test="${status.index % 2 == 1}"><li> <input type="text" name="pfSkuAgents[${status.index}].rewardUnitPrice" value="${skuAgent.rewardUnitPrice}" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li></c:if>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name">
                                        分销设置
                                    </div>

                                    <div class="profile-info-value">
                                        <div class="form-group">
                                            <label class="col-sm-0 control-label">&nbsp;</label>
                                            <div class="col-sm-10">
                                                <div class="col-xs-8 col-sm-9 pricing-span-body">
                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-red3">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">等级</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <ul class="list-unstyled list-striped pricing-table">
                                                                        <li> 倒数第一级 </li>
                                                                        <li> 倒数第二级 </li>
                                                                    </ul>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="pricing-span">
                                                        <div class="widget-box pricing-box-small widget-color-orange">
                                                            <div class="widget-header">
                                                                <h5 class="widget-title bigger lighter">单价</h5>
                                                            </div>

                                                            <div class="widget-body">
                                                                <div class="widget-main no-padding">
                                                                    <c:if test="${sfSkuDistributions != null && sfSkuDistributions.size() > 0}">
                                                                        <c:forEach items="${sfSkuDistributions}" var="sd" varStatus="status">
                                                                            <input type="hidden" name="sfSkuDistributions[${status.index}].id" value="${sd.id}" >
                                                                            <input type="hidden" name="sfSkuDistributions[${status.index}].skuId" value="${sd.skuId}" >
                                                                            <input type="hidden" name="sfSkuDistributions[${status.index}].sort" value="${sd.sort}" >
                                                                        </c:forEach>
                                                                        <ul class="list-unstyled list-striped pricing-table">
                                                                            <c:forEach items="${sfSkuDistributions}" var="sd" varStatus="status">
                                                                                <c:if test="${status.index % 2 == 0}"><li> <input type="text" name="sfSkuDistributions[${status.index}].discount" value="${sd.discount}" style="width:100px;height:13px;border:0px;" > </li></c:if>
                                                                                <c:if test="${status.index % 2 == 1}"><li> <input type="text" name="sfSkuDistributions[${status.index}].discount" value="${sd.discount}" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li></c:if>
                                                                            </c:forEach>
                                                                        </ul>
                                                                    </c:if>

                                                                    <c:if test="${sfSkuDistributions == null || sfSkuDistributions.size() == 0}">
                                                                        <input type="hidden" name="sfSkuDistributions[0].skuId" value="${comSku.id}" >
                                                                        <input type="hidden" name="sfSkuDistributions[1].skuId" value="${comSku.id}" >
                                                                        <input type="hidden" name="sfSkuDistributions[0].sort" value="0" >
                                                                        <input type="hidden" name="sfSkuDistributions[1].sort" value="1" >
                                                                        <ul class="list-unstyled list-striped pricing-table">
                                                                            <li> <input type="text" name="sfSkuDistributions[0].discount" value="" style="width:100px;height:13px;border:0px;" > </li>
                                                                            <li> <input type="text" name="sfSkuDistributions[1].discount" value="" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li>
                                                                        </ul>
                                                                    </c:if>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
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

    var dropzoneIcon = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>等级图标</span> <br /> \
                                     <span class="smaller-80 grey">按顺序上传(由高到低)</span> <br /> \
                                     <span class="smaller-80 grey">拖拽(或者点击)上传图片</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';
    var dropzoneBackImg  = '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>证书模板</span> <br /> \
                                     <span class="smaller-80 grey">按顺序上传(由高到低)</span> <br /> \
                                     <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>';

    var iconIndex = 0;
    var backImgIndex = 0;
    initDropzone('#dropzoneIcon', dropzoneIcon, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="pfSkuAgents['+(iconIndex++)+'].icon" value="'+res.title+'" />');
    });
    initDropzone('#dropzoneBackImg', dropzoneBackImg, function(file){
        var res = window.eval('(' + file.xhr.response + ')');
        $('#skuForm').append('<input type="hidden" name="pfSkuAgents['+(backImgIndex++)+'].backImg" value="'+res.title+'" />');
    });


    var isSetup = window.eval('(${isSetup})');
    var agentLevels = window.eval('(${agentLevels})');
    var agentLevelOptions = '';

    $('#agentLevelCount').change(function(){
        changePartnerSetup();
        changeRecommendSetup();
    });

    if(!isSetup){
        for(var i in agentLevels){
            agentLevelOptions += '<option value="'+agentLevels[i].id+'">'+agentLevels[i].id+'</option>';
        }
        $('#agentLevelCount').html(agentLevelOptions);

        changePartnerSetup();
        changeRecommendSetup();
    }

    function changePartnerSetup(){
        var levelNameLi  = '';
        var unitPriceLi  = '';
        var quantityLi   = '';
        var bailLi       = '';
        var totalPriceLi = '';
        var isUpgradeLi = '';
        var isShowLi = '';
        var freemanUpperNumLi = '';
        var levelCount = $('#agentLevelCount').val();

        for(var i=0; i<levelCount; i++){
            //等级
            levelNameLi += '<li><input type="hidden" name="pfSkuAgents['+i+'].agentLevelId" value="'+agentLevels[i].id+'" /> '+agentLevels[i].name+'</li>';

            //单价
            if(i%2 == 0)
                unitPriceLi += '<li> <input type="text" name="pfSkuAgents['+i+'].unitPrice" value="" style="width:100px;height:13px;border:0px;" > </li>';
            else
                unitPriceLi += '<li> <input type="text" name="pfSkuAgents['+i+'].unitPrice" value="" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li>';

            //拿货数量
            if(i%2 == 0)
                quantityLi += '<li> <input type="text" name="pfSkuAgents['+i+'].quantity" value="" style="width:100px;height:13px;border:0px;" > </li>';
            else
                quantityLi += '<li> <input type="text" name="pfSkuAgents['+i+'].quantity" value="" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li>';

            //保证金
            if(i%2 == 0)
                bailLi += '<li> <input type="text" name="pfSkuAgents['+i+'].bail" value="" style="width:100px;height:13px;border:0px;" > </li>';
            else
                bailLi += '<li> <input type="text" name="pfSkuAgents['+i+'].bail" value="" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li>';

            //门槛
            totalPriceLi += '<li>'+agentLevels[i].name+'</li>';

            //是否允许升级
            isUpgradeLi += '<li>'
                    +    '<input type="radio" name="pfSkuAgents['+i+'].isUpgrade" value="1" >&nbsp;是&nbsp;&nbsp;'
                    +    '<input type="radio" name="pfSkuAgents['+i+'].isUpgrade" value="0" >&nbsp;否&nbsp;'
                    +  '</li>';

            //前台是否展示
            isShowLi += '<li>'
                    +    '<input type="radio" name="pfSkuAgents['+i+'].isShow" value="1" >&nbsp;是&nbsp;&nbsp;'
                    +    '<input type="radio" name="pfSkuAgents['+i+'].isShow" value="0" >&nbsp;否&nbsp;'
                    +  '</li>';

            //小白上限数量
            if(i%2 == 0)
                freemanUpperNumLi += '<li> <input type="text" name="pfSkuAgents['+i+'].freemanUpperNum" value="" style="width:100px;height:13px;border:0px;" > </li>';
            else
                freemanUpperNumLi += '<li> <input type="text" name="pfSkuAgents['+i+'].freemanUpperNum" value="" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li>';
        }


        $('#levelNameUl').html(levelNameLi);
        $('#unitPriceUl').html(unitPriceLi);
        $('#quantityUl').html(quantityLi);
        $('#bailUl').html(bailLi);
        $('#totalPriceUl').html(totalPriceLi);
        $('#isUpgradeUl').html(isUpgradeLi);
        $('#isShowUl').html(isShowLi);
        $('#freemanUpperNumUl').html(freemanUpperNumLi);
    }

    function changeRecommendSetup(){
        var recommendLevelNameLi = '';
        var isRecommendLi        = '';
        var rewardUnitPriceLi    = '';
        var levelCount = $('#agentLevelCount').val();

        for(var i=0; i<levelCount; i++){
            //等级
            recommendLevelNameLi += '<li>'+agentLevels[i].name+'</li>';

            //允许平级推荐
            isRecommendLi += '<li>'
                    +    '<input type="radio" name="pfSkuAgents['+i+'].lateral" value="1" >&nbsp;是&nbsp;&nbsp;'
                    +    '<input type="radio" name="pfSkuAgents['+i+'].lateral" value="0" >&nbsp;否&nbsp;'
                    + '</li>';

            //推荐奖励
            if(i%2 == 0)
                rewardUnitPriceLi += '<li> <input type="text" name="pfSkuAgents['+i+'].rewardUnitPrice" value="" style="width:100px;height:13px;border:0px;" > </li>';
            else
                rewardUnitPriceLi += '<li> <input type="text" name="pfSkuAgents['+i+'].rewardUnitPrice" value="" style="width:100px;height:13px;border:0px;background-color:#F2F3EB;" > </li>';
        }

        $('#recommendLevelNameUl').html(recommendLevelNameLi);
        $('#isRecommendUl').html(isRecommendLi);
        $('#rewardUnitPriceUl').html(rewardUnitPriceLi);
    }



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

                    $.ajax({
                        url: '<%=basePath%>skuAgent/add.do',
                        type: 'post',
                        data: $('#skuForm').serialize(),
                        success: function(result){
                            result = window.eval('('+result+')');

                            if(result.result_code == 'success'){
                                parent.window.$('#myTabbable').closeTab('tab2-1');
                                parent.window.$('#myTabbable').add('tab2-1', '商品列表', '<%=basePath%>product/list.shtml');
                                parent.window.$('#myTabbable').closeTab('product-agent-setup');
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
