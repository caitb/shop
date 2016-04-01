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
                                                <p>
                                                    <span class="btn btn-info btn-sm popover-info" data-rel="popover" data-placement="left" data-original-title="正面" data-content="<span><img id='idCardF' src='${user.comUser.idCardFrontUrl}' width='245px' height='200px' /><span>">正面</span>
                                                    <span class="btn btn-info btn-sm popover-info" data-rel="popover" data-placement="right" data-original-title="反面" data-content="<span><img id='idCardB' src='${user.comUser.idCardBackUrl}' width='245px' height='200px' /></span>">反面</span>
                                                </p>
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

                    </div><!-- /.col -->

                </div><!-- /.row -->

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
<script src="<%=basePath%>static/ace2/docs/assets/js/language/generic.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/language/html.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/language/css.js"></script>
<script src="<%=basePath%>static/ace2/docs/assets/js/language/javascript.js"></script>
</body>
</html>
