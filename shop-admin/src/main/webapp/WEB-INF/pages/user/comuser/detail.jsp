<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->
    <script src="<%=basePath%>static/ace2/js/uncompressed/ace-extra.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

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

                        <div>
                            <div id="user-profile-1" class="user-profile row">
                                <div class="col-xs-12 col-sm-8 col-sm-offset-2">

                                    <!-- #section:pages/profile.info -->
                                    <div class="profile-user-info profile-user-info-striped">

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 手机号 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="mobile">${user.comUser.mobile}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 微信号 </div>

                                            <div class="profile-info-value">
                                                <c:forEach items="${user.wxAgentPro}" var="m">
                                                    <span class="" id="weixin1">${m.key}:&nbsp;${m.value}</span>

                                                    <div class="br"></div>
                                                </c:forEach>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 实名认证 </div>

                                            <div class="profile-info-value">
                                                <c:if test="${user.comUser.auditStatus == 2}">
                                                    <span class="" id="auditStatus">已通过</span>
                                                </c:if>
                                                <c:if test="${user.comUser.auditStatus != 2}">
                                                    <span class="" id="auditStatus">不通过</span>
                                                </c:if>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 姓名 </div>

                                            <div class="profile-info-value">
                                                <span class="editable editable-click" id="linkman">${user.comUser.realName}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 身份证 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="linkmanIDCard">${user.comUser.idCard}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 身份证扫描 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="idCardFront"><a href="javascript:void(0);">正面</a></span>
                                                <span class="" id="idCardBack"><a href="javascript:void(0);">反面</a></span>

                                                <div class="br"></div>
                                                <img id="idCardF" src="${user.comUser.idCardFrontUrl}" />
                                                <img id="idCardB" src="${user.comUser.idCardBackUrl}" />
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 账户余额 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="extractableFee">${user.comUserAccount.extractableFee}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 结算中资金 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="countingFee">${user.comUserAccount.countingFee}</span>
                                            </div>
                                        </div>

                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 注册日期 </div>

                                            <div class="profile-info-value">
                                                <span class="" id="createTime"><fmt:formatDate value="${user.comUser.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></span>
                                            </div>
                                        </div>

                                        <%--<div class="profile-info-row">--%>
                                            <%--<div class="profile-info-name"> 分销记录 </div>--%>

                                            <%--<div class="profile-info-value">--%>
                                                <%--<span class="" id="distribution">4509948338838</span>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>



                                        <%--<div class="profile-info-row">--%>
                                            <%--<div class="profile-info-name"> 地理管理 </div>--%>

                                            <%--<div class="profile-info-value">--%>
                                                <%--<span class="bgarea" id="address">北京</span>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>

                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="hide">
                            <div id="user-profile-2" class="user-profile">
                                <div class="tabbable">
                                    <ul class="nav nav-tabs padding-18">
                                        <li class="active">
                                            <a data-toggle="tab" href="#home">
                                                <i class="green ace-icon fa fa-user bigger-120"></i>
                                                Profile
                                            </a>
                                        </li>

                                        <li>
                                            <a data-toggle="tab" href="#feed">
                                                <i class="orange ace-icon fa fa-rss bigger-120"></i>
                                                Activity Feed
                                            </a>
                                        </li>

                                        <li>
                                            <a data-toggle="tab" href="#friends">
                                                <i class="blue ace-icon fa fa-users bigger-120"></i>
                                                Friends
                                            </a>
                                        </li>

                                        <li>
                                            <a data-toggle="tab" href="#pictures">
                                                <i class="pink ace-icon fa fa-picture-o bigger-120"></i>
                                                Pictures
                                            </a>
                                        </li>
                                    </ul>

                                    <div class="tab-content no-border padding-24">
                                        <div id="home" class="tab-pane in active">
                                            <div class="row">
                                                <div class="col-xs-12 col-sm-3 center">
															<span class="profile-picture">
																<img class="editable img-responsive" alt="Alex's Avatar" id="avatar2" src="../assets/avatars/profile-pic.jpg">
															</span>

                                                    <div class="space space-4"></div>

                                                    <a href="#" class="btn btn-sm btn-block btn-success">
                                                        <i class="ace-icon fa fa-plus-circle bigger-120"></i>
                                                        <span class="bigger-110">Add as a friend</span>
                                                    </a>

                                                    <a href="#" class="btn btn-sm btn-block btn-primary">
                                                        <i class="ace-icon fa fa-envelope-o bigger-110"></i>
                                                        <span class="bigger-110">Send a message</span>
                                                    </a>
                                                </div><!-- /.col -->

                                                <div class="col-xs-12 col-sm-9">
                                                    <h4 class="blue">
                                                        <span class="middle">Alex M. Doe</span>

																<span class="label label-purple arrowed-in-right">
																	<i class="ace-icon fa fa-circle smaller-80 align-middle"></i>
																	online
																</span>
                                                    </h4>

                                                    <div class="profile-user-info">
                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> Username </div>

                                                            <div class="profile-info-value">
                                                                <span>alexdoe</span>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> Location </div>

                                                            <div class="profile-info-value">
                                                                <i class="fa fa-map-marker light-orange bigger-110"></i>
                                                                <span>Netherlands</span>
                                                                <span>Amsterdam</span>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> Age </div>

                                                            <div class="profile-info-value">
                                                                <span>38</span>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> Joined </div>

                                                            <div class="profile-info-value">
                                                                <span>2010/06/20</span>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> Last Online </div>

                                                            <div class="profile-info-value">
                                                                <span>3 hours ago</span>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="hr hr-8 dotted"></div>

                                                    <div class="profile-user-info">
                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name"> Website </div>

                                                            <div class="profile-info-value">
                                                                <a href="#" target="_blank">www.alexdoe.com</a>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name">
                                                                <i class="middle ace-icon fa fa-facebook-square bigger-150 blue"></i>
                                                            </div>

                                                            <div class="profile-info-value">
                                                                <a href="#">Find me on Facebook</a>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name">
                                                                <i class="middle ace-icon fa fa-twitter-square bigger-150 light-blue"></i>
                                                            </div>

                                                            <div class="profile-info-value">
                                                                <a href="#">Follow me on Twitter</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div><!-- /.col -->
                                            </div><!-- /.row -->

                                            <div class="space-20"></div>

                                            <div class="row">
                                                <div class="col-xs-12 col-sm-6">
                                                    <div class="widget-box transparent">
                                                        <div class="widget-header widget-header-small">
                                                            <h4 class="widget-title smaller">
                                                                <i class="ace-icon fa fa-check-square-o bigger-110"></i>
                                                                Little About Me
                                                            </h4>
                                                        </div>

                                                        <div class="widget-body">
                                                            <div class="widget-main">
                                                                <p>
                                                                    My job is mostly lorem ipsuming and dolor sit ameting as long as consectetur adipiscing elit.
                                                                </p>
                                                                <p>
                                                                    Sometimes quisque commodo massa gets in the way and sed ipsum porttitor facilisis.
                                                                </p>
                                                                <p>
                                                                    The best thing about my job is that vestibulum id ligula porta felis euismod and nullam quis risus eget urna mollis ornare.
                                                                </p>
                                                                <p>
                                                                    Thanks for visiting my profile.
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-xs-12 col-sm-6">
                                                    <div class="widget-box transparent">
                                                        <div class="widget-header widget-header-small header-color-blue2">
                                                            <h4 class="widget-title smaller">
                                                                <i class="ace-icon fa fa-lightbulb-o bigger-120"></i>
                                                                My Skills
                                                            </h4>
                                                        </div>

                                                        <div class="widget-body">
                                                            <div class="widget-main padding-16">
                                                                <div class="clearfix">
                                                                    <div class="grid3 center">
                                                                        <!-- #section:plugins/charts.easypiechart -->
                                                                        <div class="easy-pie-chart percentage" data-percent="45" data-color="#CA5952" style="height: 72px; width: 72px; line-height: 71px; color: rgb(202, 89, 82);">
                                                                            <span class="percent">45</span>%
                                                                            <canvas height="144" width="144" style="height: 72px; width: 72px;"></canvas></div>

                                                                        <!-- /section:plugins/charts.easypiechart -->
                                                                        <div class="space-2"></div>
                                                                        Graphic Design
                                                                    </div>

                                                                    <div class="grid3 center">
                                                                        <div class="center easy-pie-chart percentage" data-percent="90" data-color="#59A84B" style="height: 72px; width: 72px; line-height: 71px; color: rgb(89, 168, 75);">
                                                                            <span class="percent">90</span>%
                                                                            <canvas height="144" width="144" style="height: 72px; width: 72px;"></canvas></div>

                                                                        <div class="space-2"></div>
                                                                        HTML5 &amp; CSS3
                                                                    </div>

                                                                    <div class="grid3 center">
                                                                        <div class="center easy-pie-chart percentage" data-percent="80" data-color="#9585BF" style="height: 72px; width: 72px; line-height: 71px; color: rgb(149, 133, 191);">
                                                                            <span class="percent">80</span>%
                                                                            <canvas height="144" width="144" style="height: 72px; width: 72px;"></canvas></div>

                                                                        <div class="space-2"></div>
                                                                        Javascript/jQuery
                                                                    </div>
                                                                </div>

                                                                <div class="hr hr-16"></div>

                                                                <!-- #section:pages/profile.skill-progress -->
                                                                <div class="profile-skills">
                                                                    <div class="progress">
                                                                        <div class="progress-bar" style="width:80%">
                                                                            <span class="pull-left">HTML5 &amp; CSS3</span>
                                                                            <span class="pull-right">80%</span>
                                                                        </div>
                                                                    </div>

                                                                    <div class="progress">
                                                                        <div class="progress-bar progress-bar-success" style="width:72%">
                                                                            <span class="pull-left">Javascript &amp; jQuery</span>

                                                                            <span class="pull-right">72%</span>
                                                                        </div>
                                                                    </div>

                                                                    <div class="progress">
                                                                        <div class="progress-bar progress-bar-purple" style="width:70%">
                                                                            <span class="pull-left">PHP &amp; MySQL</span>

                                                                            <span class="pull-right">70%</span>
                                                                        </div>
                                                                    </div>

                                                                    <div class="progress">
                                                                        <div class="progress-bar progress-bar-warning" style="width:50%">
                                                                            <span class="pull-left">Wordpress</span>

                                                                            <span class="pull-right">50%</span>
                                                                        </div>
                                                                    </div>

                                                                    <div class="progress">
                                                                        <div class="progress-bar progress-bar-danger" style="width:38%">
                                                                            <span class="pull-left">Photoshop</span>

                                                                            <span class="pull-right">38%</span>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <!-- /section:pages/profile.skill-progress -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div><!-- /#home -->

                                        <div id="feed" class="tab-pane">
                                            <div class="profile-feed row">
                                                <div class="col-sm-6">
                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <img class="pull-left" alt="Alex Doe's avatar" src="../assets/avatars/avatar5.png">
                                                            <a class="user" href="#"> Alex Doe </a>
                                                            changed his profile photo.
                                                            <a href="#">Take a look</a>

                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                an hour ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <img class="pull-left" alt="Susan Smith's avatar" src="../assets/avatars/avatar1.png">
                                                            <a class="user" href="#"> Susan Smith </a>

                                                            is now friends with Alex Doe.
                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                2 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <i class="pull-left thumbicon fa fa-check btn-success no-hover"></i>
                                                            <a class="user" href="#"> Alex Doe </a>
                                                            joined
                                                            <a href="#">Country Music</a>

                                                            group.
                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                5 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <i class="pull-left thumbicon fa fa-picture-o btn-info no-hover"></i>
                                                            <a class="user" href="#"> Alex Doe </a>
                                                            uploaded a new photo.
                                                            <a href="#">Take a look</a>

                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                5 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <img class="pull-left" alt="David Palms's avatar" src="../assets/avatars/avatar4.png">
                                                            <a class="user" href="#"> David Palms </a>

                                                            left a comment on Alex's wall.
                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                8 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div><!-- /.col -->

                                                <div class="col-sm-6">
                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <i class="pull-left thumbicon fa fa-pencil-square-o btn-pink no-hover"></i>
                                                            <a class="user" href="#"> Alex Doe </a>
                                                            published a new blog post.
                                                            <a href="#">Read now</a>

                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                11 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <img class="pull-left" alt="Alex Doe's avatar" src="../assets/avatars/avatar5.png">
                                                            <a class="user" href="#"> Alex Doe </a>

                                                            upgraded his skills.
                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                12 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <i class="pull-left thumbicon fa fa-key btn-info no-hover"></i>
                                                            <a class="user" href="#"> Alex Doe </a>

                                                            logged in.
                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                12 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <i class="pull-left thumbicon fa fa-power-off btn-inverse no-hover"></i>
                                                            <a class="user" href="#"> Alex Doe </a>

                                                            logged out.
                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                16 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>

                                                    <div class="profile-activity clearfix">
                                                        <div>
                                                            <i class="pull-left thumbicon fa fa-key btn-info no-hover"></i>
                                                            <a class="user" href="#"> Alex Doe </a>

                                                            logged in.
                                                            <div class="time">
                                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                16 hours ago
                                                            </div>
                                                        </div>

                                                        <div class="tools action-buttons">
                                                            <a href="#" class="blue">
                                                                <i class="ace-icon fa fa-pencil bigger-125"></i>
                                                            </a>

                                                            <a href="#" class="red">
                                                                <i class="ace-icon fa fa-times bigger-125"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div><!-- /.col -->
                                            </div><!-- /.row -->

                                            <div class="space-12"></div>

                                            <div class="center">
                                                <button type="button" class="btn btn-sm btn-primary btn-white btn-round">
                                                    <i class="ace-icon fa fa-rss bigger-150 middle orange2"></i>
                                                    <span class="bigger-110">View more activities</span>

                                                    <i class="icon-on-right ace-icon fa fa-arrow-right"></i>
                                                </button>
                                            </div>
                                        </div><!-- /#feed -->

                                        <div id="friends" class="tab-pane">
                                            <!-- #section:pages/profile.friends -->
                                            <div class="profile-users clearfix">
                                                <div class="itemdiv memberdiv">
                                                    <div class="inline pos-rel">
                                                        <div class="user">
                                                            <a href="#">
                                                                <img src="../assets/avatars/avatar4.png" alt="Bob Doe's avatar">
                                                            </a>
                                                        </div>

                                                        <div class="body">
                                                            <div class="name">
                                                                <a href="#">
                                                                    <span class="user-status status-online"></span>
                                                                    Bob Doe
                                                                </a>
                                                            </div>
                                                        </div>

                                                        <div class="popover">
                                                            <div class="arrow"></div>

                                                            <div class="popover-content">
                                                                <div class="bolder">Content Editor</div>

                                                                <div class="time">
                                                                    <i class="ace-icon fa fa-clock-o middle bigger-120 orange"></i>
                                                                    <span class="green"> 20 mins ago </span>
                                                                </div>

                                                                <div class="hr dotted hr-8"></div>

                                                                <div class="tools action-buttons">
                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-facebook-square blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-google-plus-square red bigger-150"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="itemdiv memberdiv">
                                                    <div class="inline pos-rel">
                                                        <div class="user">
                                                            <a href="#">
                                                                <img src="../assets/avatars/avatar1.png" alt="Rose Doe's avatar">
                                                            </a>
                                                        </div>

                                                        <div class="body">
                                                            <div class="name">
                                                                <a href="#">
                                                                    <span class="user-status status-offline"></span>
                                                                    Rose Doe
                                                                </a>
                                                            </div>
                                                        </div>

                                                        <div class="popover">
                                                            <div class="arrow"></div>

                                                            <div class="popover-content">
                                                                <div class="bolder">Graphic Designer</div>

                                                                <div class="time">
                                                                    <i class="ace-icon fa fa-clock-o middle bigger-120 grey"></i>
                                                                    <span class="grey"> 30 min ago </span>
                                                                </div>

                                                                <div class="hr dotted hr-8"></div>

                                                                <div class="tools action-buttons">
                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-facebook-square blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-google-plus-square red bigger-150"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="itemdiv memberdiv">
                                                    <div class="inline pos-rel">
                                                        <div class="user">
                                                            <a href="#">
                                                                <img src="../assets/avatars/avatar.png" alt="Jim Doe's avatar">
                                                            </a>
                                                        </div>

                                                        <div class="body">
                                                            <div class="name">
                                                                <a href="#">
                                                                    <span class="user-status status-busy"></span>
                                                                    Jim Doe
                                                                </a>
                                                            </div>
                                                        </div>

                                                        <div class="popover">
                                                            <div class="arrow"></div>

                                                            <div class="popover-content">
                                                                <div class="bolder">SEO &amp; Advertising</div>

                                                                <div class="time">
                                                                    <i class="ace-icon fa fa-clock-o middle bigger-120 red"></i>
                                                                    <span class="grey"> 1 hour ago </span>
                                                                </div>

                                                                <div class="hr dotted hr-8"></div>

                                                                <div class="tools action-buttons">
                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-facebook-square blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-google-plus-square red bigger-150"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="itemdiv memberdiv">
                                                    <div class="inline pos-rel">
                                                        <div class="user">
                                                            <a href="#">
                                                                <img src="../assets/avatars/avatar5.png" alt="Alex Doe's avatar">
                                                            </a>
                                                        </div>

                                                        <div class="body">
                                                            <div class="name">
                                                                <a href="#">
                                                                    <span class="user-status status-idle"></span>
                                                                    Alex Doe
                                                                </a>
                                                            </div>
                                                        </div>

                                                        <div class="popover">
                                                            <div class="arrow"></div>

                                                            <div class="popover-content">
                                                                <div class="bolder">Marketing</div>

                                                                <div class="time">
                                                                    <i class="ace-icon fa fa-clock-o middle bigger-120 orange"></i>
                                                                    <span class=""> 40 minutes idle </span>
                                                                </div>

                                                                <div class="hr dotted hr-8"></div>

                                                                <div class="tools action-buttons">
                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-facebook-square blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-google-plus-square red bigger-150"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="itemdiv memberdiv">
                                                    <div class="inline pos-rel">
                                                        <div class="user">
                                                            <a href="#">
                                                                <img src="../assets/avatars/avatar2.png" alt="Phil Doe's avatar">
                                                            </a>
                                                        </div>

                                                        <div class="body">
                                                            <div class="name">
                                                                <a href="#">
                                                                    <span class="user-status status-online"></span>
                                                                    Phil Doe
                                                                </a>
                                                            </div>
                                                        </div>

                                                        <div class="popover">
                                                            <div class="arrow"></div>

                                                            <div class="popover-content">
                                                                <div class="bolder">Public Relations</div>

                                                                <div class="time">
                                                                    <i class="ace-icon fa fa-clock-o middle bigger-120 orange"></i>
                                                                    <span class="green"> 2 hours ago </span>
                                                                </div>

                                                                <div class="hr dotted hr-8"></div>

                                                                <div class="tools action-buttons">
                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-facebook-square blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-google-plus-square red bigger-150"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="itemdiv memberdiv">
                                                    <div class="inline pos-rel">
                                                        <div class="user">
                                                            <a href="#">
                                                                <img src="../assets/avatars/avatar3.png" alt="Susan Doe's avatar">
                                                            </a>
                                                        </div>

                                                        <div class="body">
                                                            <div class="name">
                                                                <a href="#">
                                                                    <span class="user-status status-online"></span>
                                                                    Susan Doe
                                                                </a>
                                                            </div>
                                                        </div>

                                                        <div class="popover">
                                                            <div class="arrow"></div>

                                                            <div class="popover-content">
                                                                <div class="bolder">HR Management</div>

                                                                <div class="time">
                                                                    <i class="ace-icon fa fa-clock-o middle bigger-120 orange"></i>
                                                                    <span class="green"> 20 mins ago </span>
                                                                </div>

                                                                <div class="hr dotted hr-8"></div>

                                                                <div class="tools action-buttons">
                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-facebook-square blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-google-plus-square red bigger-150"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="itemdiv memberdiv">
                                                    <div class="inline pos-rel">
                                                        <div class="user">
                                                            <a href="#">
                                                                <img src="../assets/avatars/avatar1.png" alt="Jennifer Doe's avatar">
                                                            </a>
                                                        </div>

                                                        <div class="body">
                                                            <div class="name">
                                                                <a href="#">
                                                                    <span class="user-status status-offline"></span>
                                                                    Jennifer Doe
                                                                </a>
                                                            </div>
                                                        </div>

                                                        <div class="popover">
                                                            <div class="arrow"></div>

                                                            <div class="popover-content">
                                                                <div class="bolder">Graphic Designer</div>

                                                                <div class="time">
                                                                    <i class="ace-icon fa fa-clock-o middle bigger-120 grey"></i>
                                                                    <span class="grey"> 2 hours ago </span>
                                                                </div>

                                                                <div class="hr dotted hr-8"></div>

                                                                <div class="tools action-buttons">
                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-facebook-square blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-google-plus-square red bigger-150"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="itemdiv memberdiv">
                                                    <div class="inline pos-rel">
                                                        <div class="user">
                                                            <a href="#">
                                                                <img src="../assets/avatars/avatar3.png" alt="Alexa Doe's avatar">
                                                            </a>
                                                        </div>

                                                        <div class="body">
                                                            <div class="name">
                                                                <a href="#">
                                                                    <span class="user-status status-offline"></span>
                                                                    Alexa Doe
                                                                </a>
                                                            </div>
                                                        </div>

                                                        <div class="popover">
                                                            <div class="arrow"></div>

                                                            <div class="popover-content">
                                                                <div class="bolder">Accounting</div>

                                                                <div class="time">
                                                                    <i class="ace-icon fa fa-clock-o middle bigger-120 grey"></i>
                                                                    <span class="grey"> 4 hours ago </span>
                                                                </div>

                                                                <div class="hr dotted hr-8"></div>

                                                                <div class="tools action-buttons">
                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-facebook-square blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                                                                    </a>

                                                                    <a href="#">
                                                                        <i class="ace-icon fa fa-google-plus-square red bigger-150"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- /section:pages/profile.friends -->
                                            <div class="hr hr10 hr-double"></div>

                                            <ul class="pager pull-right">
                                                <li class="previous disabled">
                                                    <a href="#">← Prev</a>
                                                </li>

                                                <li class="next">
                                                    <a href="#">Next →</a>
                                                </li>
                                            </ul>
                                        </div><!-- /#friends -->

                                        <div id="pictures" class="tab-pane">
                                            <ul class="ace-thumbnails">
                                                <li>
                                                    <a href="#" data-rel="colorbox">
                                                        <img alt="150x150" src="../assets/images/gallery/thumb-1.jpg">
                                                        <div class="text">
                                                            <div class="inner">Sample Caption on Hover</div>
                                                        </div>
                                                    </a>

                                                    <div class="tools tools-bottom">
                                                        <a href="#">
                                                            <i class="ace-icon fa fa-link"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-paperclip"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-pencil"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </a>
                                                    </div>
                                                </li>

                                                <li>
                                                    <a href="#" data-rel="colorbox">
                                                        <img alt="150x150" src="../assets/images/gallery/thumb-2.jpg">
                                                        <div class="text">
                                                            <div class="inner">Sample Caption on Hover</div>
                                                        </div>
                                                    </a>

                                                    <div class="tools tools-bottom">
                                                        <a href="#">
                                                            <i class="ace-icon fa fa-link"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-paperclip"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-pencil"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </a>
                                                    </div>
                                                </li>

                                                <li>
                                                    <a href="#" data-rel="colorbox">
                                                        <img alt="150x150" src="../assets/images/gallery/thumb-3.jpg">
                                                        <div class="text">
                                                            <div class="inner">Sample Caption on Hover</div>
                                                        </div>
                                                    </a>

                                                    <div class="tools tools-bottom">
                                                        <a href="#">
                                                            <i class="ace-icon fa fa-link"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-paperclip"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-pencil"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </a>
                                                    </div>
                                                </li>

                                                <li>
                                                    <a href="#" data-rel="colorbox">
                                                        <img alt="150x150" src="../assets/images/gallery/thumb-4.jpg">
                                                        <div class="text">
                                                            <div class="inner">Sample Caption on Hover</div>
                                                        </div>
                                                    </a>

                                                    <div class="tools tools-bottom">
                                                        <a href="#">
                                                            <i class="ace-icon fa fa-link"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-paperclip"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-pencil"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </a>
                                                    </div>
                                                </li>

                                                <li>
                                                    <a href="#" data-rel="colorbox">
                                                        <img alt="150x150" src="../assets/images/gallery/thumb-5.jpg">
                                                        <div class="text">
                                                            <div class="inner">Sample Caption on Hover</div>
                                                        </div>
                                                    </a>

                                                    <div class="tools tools-bottom">
                                                        <a href="#">
                                                            <i class="ace-icon fa fa-link"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-paperclip"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-pencil"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </a>
                                                    </div>
                                                </li>

                                                <li>
                                                    <a href="#" data-rel="colorbox">
                                                        <img alt="150x150" src="../assets/images/gallery/thumb-6.jpg">
                                                        <div class="text">
                                                            <div class="inner">Sample Caption on Hover</div>
                                                        </div>
                                                    </a>

                                                    <div class="tools tools-bottom">
                                                        <a href="#">
                                                            <i class="ace-icon fa fa-link"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-paperclip"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-pencil"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </a>
                                                    </div>
                                                </li>

                                                <li>
                                                    <a href="#" data-rel="colorbox">
                                                        <img alt="150x150" src="../assets/images/gallery/thumb-1.jpg">
                                                        <div class="text">
                                                            <div class="inner">Sample Caption on Hover</div>
                                                        </div>
                                                    </a>

                                                    <div class="tools tools-bottom">
                                                        <a href="#">
                                                            <i class="ace-icon fa fa-link"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-paperclip"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-pencil"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </a>
                                                    </div>
                                                </li>

                                                <li>
                                                    <a href="#" data-rel="colorbox">
                                                        <img alt="150x150" src="../assets/images/gallery/thumb-2.jpg">
                                                        <div class="text">
                                                            <div class="inner">Sample Caption on Hover</div>
                                                        </div>
                                                    </a>

                                                    <div class="tools tools-bottom">
                                                        <a href="#">
                                                            <i class="ace-icon fa fa-link"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-paperclip"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-pencil"></i>
                                                        </a>

                                                        <a href="#">
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </a>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div><!-- /#pictures -->
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="hide">
                            <div id="user-profile-3" class="user-profile row">
                                <div class="col-sm-offset-1 col-sm-10">
                                    <div class="well well-sm">
                                        <button type="button" class="close" data-dismiss="alert">×</button>
                                        &nbsp;
                                        <div class="inline middle blue bigger-110"> Your profile is 70% complete </div>

                                        &nbsp; &nbsp; &nbsp;
                                        <div style="width:200px;" data-percent="70%" class="inline middle no-margin progress progress-striped active">
                                            <div class="progress-bar progress-bar-success" style="width:70%"></div>
                                        </div>
                                    </div><!-- /.well -->

                                    <div class="space"></div>

                                    <form class="form-horizontal">
                                        <div class="tabbable">
                                            <ul class="nav nav-tabs padding-16">
                                                <li class="active">
                                                    <a data-toggle="tab" href="#edit-basic">
                                                        <i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
                                                        Basic Info
                                                    </a>
                                                </li>

                                                <li>
                                                    <a data-toggle="tab" href="#edit-settings">
                                                        <i class="purple ace-icon fa fa-cog bigger-125"></i>
                                                        Settings
                                                    </a>
                                                </li>

                                                <li>
                                                    <a data-toggle="tab" href="#edit-password">
                                                        <i class="blue ace-icon fa fa-key bigger-125"></i>
                                                        Password
                                                    </a>
                                                </li>
                                            </ul>

                                            <div class="tab-content profile-edit-tab-content">
                                                <div id="edit-basic" class="tab-pane in active">
                                                    <h4 class="header blue bolder smaller">General</h4>

                                                    <div class="row">
                                                        <div class="col-xs-12 col-sm-4">
                                                            <label class="ace-file-input ace-file-multiple"><input type="file"><span class="ace-file-container" data-title="Change avatar"><span class="ace-file-name" data-title="No File ..."><i class=" ace-icon ace-icon fa fa-picture-o"></i></span></span><a class="remove" href="#"><i class=" ace-icon fa fa-times"></i></a></label>
                                                        </div>

                                                        <div class="vspace-12-sm"></div>

                                                        <div class="col-xs-12 col-sm-8">
                                                            <div class="form-group">
                                                                <label class="col-sm-4 control-label no-padding-right" for="form-field-username">Username</label>

                                                                <div class="col-sm-8">
                                                                    <input class="col-xs-12 col-sm-10" type="text" id="form-field-username" placeholder="Username" value="alexdoe">
                                                                </div>
                                                            </div>

                                                            <div class="space-4"></div>

                                                            <div class="form-group">
                                                                <label class="col-sm-4 control-label no-padding-right" for="form-field-first">Name</label>

                                                                <div class="col-sm-8">
                                                                    <input class="input-small" type="text" id="form-field-first" placeholder="First Name" value="Alex">
                                                                    <input class="input-small" type="text" id="form-field-last" placeholder="Last Name" value="Doe">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <hr>
                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-date">Birth Date</label>

                                                        <div class="col-sm-9">
                                                            <div class="input-medium">
                                                                <div class="input-group">
                                                                    <input class="input-medium date-picker" id="form-field-date" type="text" data-date-format="dd-mm-yyyy" placeholder="dd-mm-yyyy">
																			<span class="input-group-addon">
																				<i class="ace-icon fa fa-calendar"></i>
																			</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="space-4"></div>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right">Gender</label>

                                                        <div class="col-sm-9">
                                                            <label class="inline">
                                                                <input name="form-field-radio" type="radio" class="ace">
                                                                <span class="lbl middle"> Male</span>
                                                            </label>

                                                            &nbsp; &nbsp; &nbsp;
                                                            <label class="inline">
                                                                <input name="form-field-radio" type="radio" class="ace">
                                                                <span class="lbl middle"> Female</span>
                                                            </label>
                                                        </div>
                                                    </div>

                                                    <div class="space-4"></div>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-comment">Comment</label>

                                                        <div class="col-sm-9">
                                                            <textarea id="form-field-comment"></textarea>
                                                        </div>
                                                    </div>

                                                    <div class="space"></div>
                                                    <h4 class="header blue bolder smaller">Contact</h4>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-email">Email</label>

                                                        <div class="col-sm-9">
																	<span class="input-icon input-icon-right">
																		<input type="email" id="form-field-email" value="alexdoe@gmail.com">
																		<i class="ace-icon fa fa-envelope"></i>
																	</span>
                                                        </div>
                                                    </div>

                                                    <div class="space-4"></div>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-website">Website</label>

                                                        <div class="col-sm-9">
																	<span class="input-icon input-icon-right">
																		<input type="url" id="form-field-website" value="http://www.alexdoe.com/">
																		<i class="ace-icon fa fa-globe"></i>
																	</span>
                                                        </div>
                                                    </div>

                                                    <div class="space-4"></div>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-phone">Phone</label>

                                                        <div class="col-sm-9">
																	<span class="input-icon input-icon-right">
																		<input class="input-medium input-mask-phone" type="text" id="form-field-phone">
																		<i class="ace-icon fa fa-phone fa-flip-horizontal"></i>
																	</span>
                                                        </div>
                                                    </div>

                                                    <div class="space"></div>
                                                    <h4 class="header blue bolder smaller">Social</h4>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-facebook">Facebook</label>

                                                        <div class="col-sm-9">
																	<span class="input-icon">
																		<input type="text" value="facebook_alexdoe" id="form-field-facebook">
																		<i class="ace-icon fa fa-facebook blue"></i>
																	</span>
                                                        </div>
                                                    </div>

                                                    <div class="space-4"></div>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-twitter">Twitter</label>

                                                        <div class="col-sm-9">
																	<span class="input-icon">
																		<input type="text" value="twitter_alexdoe" id="form-field-twitter">
																		<i class="ace-icon fa fa-twitter light-blue"></i>
																	</span>
                                                        </div>
                                                    </div>

                                                    <div class="space-4"></div>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-gplus">Google+</label>

                                                        <div class="col-sm-9">
																	<span class="input-icon">
																		<input type="text" value="google_alexdoe" id="form-field-gplus">
																		<i class="ace-icon fa fa-google-plus red"></i>
																	</span>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div id="edit-settings" class="tab-pane">
                                                    <div class="space-10"></div>

                                                    <div>
                                                        <label class="inline">
                                                            <input type="checkbox" name="form-field-checkbox" class="ace">
                                                            <span class="lbl"> Make my profile public</span>
                                                        </label>
                                                    </div>

                                                    <div class="space-8"></div>

                                                    <div>
                                                        <label class="inline">
                                                            <input type="checkbox" name="form-field-checkbox" class="ace">
                                                            <span class="lbl"> Email me new updates</span>
                                                        </label>
                                                    </div>

                                                    <div class="space-8"></div>

                                                    <div>
                                                        <label class="inline">
                                                            <input type="checkbox" name="form-field-checkbox" class="ace">
                                                            <span class="lbl"> Keep a history of my conversations</span>
                                                        </label>

                                                        <label class="inline">
                                                            <span class="space-2 block"></span>

                                                            for
                                                            <input type="text" class="input-mini" maxlength="3">
                                                            days
                                                        </label>
                                                    </div>
                                                </div>

                                                <div id="edit-password" class="tab-pane">
                                                    <div class="space-10"></div>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-pass1">New Password</label>

                                                        <div class="col-sm-9">
                                                            <input type="password" id="form-field-pass1">
                                                        </div>
                                                    </div>

                                                    <div class="space-4"></div>

                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-pass2">Confirm Password</label>

                                                        <div class="col-sm-9">
                                                            <input type="password" id="form-field-pass2">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="clearfix form-actions">
                                            <div class="col-md-offset-3 col-md-9">
                                                <button class="btn btn-info" type="button">
                                                    <i class="ace-icon fa fa-check bigger-110"></i>
                                                    Save
                                                </button>

                                                &nbsp; &nbsp;
                                                <button class="btn" type="reset">
                                                    <i class="ace-icon fa fa-undo bigger-110"></i>
                                                    Reset
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div><!-- /.span -->
                            </div><!-- /.user-profile -->
                        </div>

                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div>
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

</body>
</html>
