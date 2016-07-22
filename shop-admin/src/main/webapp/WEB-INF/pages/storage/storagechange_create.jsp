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
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/tab.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/font-awesome.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/css/laydate.css" />

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
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/jquery.gritter.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=basePath%>static/ace2/css/ace-ie.min.css" />

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
    <style>
        .list-group-item:hover{
            background: #ccc;
        }
        .list-group-item:active{
            background:#428bca;
        }
        .li_selected{
            background:#428bca;
        }
        #nameList b{
            margin-right: 10px;
            text-indent: 1.2em;
            text-align: inherit;
        }
        #nameList li{
            height:40px;
        }
    </style>
</head>

<body class="no-skin" style="overflow-x: hidden;overflow-y: auto">

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
        <div class="page-content" >

            <!-- /section:settings.box -->
            <div class="page-content-area">

                <div class="row">
                    <div class="col-xs-10 col-xs-offset-0">
                        <!-- PAGE CONTENT BEGINS -->
                        <form class="form-horizontal" role="form" id="promotionForm">
                            <!-- #section:elements.form -->

                            <div class="form-group">
                                <label for="userName" class="control-label col-sm-2">变更人</label>
                                <div class="col-sm-2">
                                    <label id="userName" class="col-sm-2 form-control" ></label>
                                    <input type="hidden" id="userId" name="userId"/>
                                </div>
                                <div class="col-sm-1">
                                    <button type="button" id="useSelect" class="btn btn-success">选择</button>
                                </div>
                                <label for="billType" class="col-sm-3 control-label">单据类型</label>
                                <div class="col-sm-3">
                                    <input type="text" readonly="readonly" class="form-control" placeholder="入库">
                                    <input type="hidden" id="billType" name="billType" value="1"/>
                                </div>
                            </div>

                            <div id="skutitle" class="form-group">
                                <label class="control-label col-sm-2">添加商品</label>
                                <div class="col-sm-10" style="background-color: gainsboro">
                                    <div class="row">
                                        <label class="control-label col-sm-3" style="text-align: center;">商品名称</label>
                                        <label class="control-label col-sm-2" style="text-align: center;">上级合伙人</label>
                                        <label class="control-label col-sm-2" style="text-align: center;">数量</label>
                                        <label class="control-label col-sm-4" style="text-align: center;">备注</label>
                                        <label class="control-label col-sm-1" style="text-align: center;">操作</label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="addPro" class="control-label col-sm-2"></label>
                                <div class="col-sm-9">
                                    <button type="button" id="addPro" class="btn btn-success">添加</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="reason" class="control-label col-sm-2">单据说明</label>
                                <div class="col-sm-10">
                                    <textarea style="max-width: 100%" name="reason" id="reason" cols="50" rows="5" class="form-control"></textarea>
                                </div>
                            </div>
                        </form>

                        <div id="promotion-rule-template" class="form-group promotion-rule" style="display: none;">
                            <label class="control-label col-sm-2 rule-name"></label>
                            <div class="col-sm-10">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <select name="skuIds" class="form-control">
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <label type="text"class="form-control"></label>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="numbers" name="skuNums" class="form-control">
                                    </div>
                                    <div class="col-sm-4">
                                        <input type="text" name="skuRemarks" class="form-control">
                                    </div>
                                    <div class="col-sm-1">
                                        <button type="button" class="btn btn-warning removeRule">删除</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <row>
                            <label class="col-sm-6"></label>
                            <div class="col-sm-5">
                                <input type="button" class="btn btn-lg btn-info" id="promotionSave" value="保存"/>
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
                                            选择变更人
                                        </div>
                                    </div>

                                    <div class="modal-body no-padding">
                                        <form class="form-horizontal" role="form" id="library">
                                            <div class="form-group">
                                                <div class="col-xs-offset-1 col-sm-5">
                                                    <input id="uName" type="text" class="form-control" name="uName" placeholder="请输入用户姓名">
                                                </div>
                                                <label class="col-sm-0"></label>
                                                <div class="col-sm-5">
                                                    <input id="uPhone" class="form-control" type="text" placeholder="请输入用户手机号"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label col-sm-1"></label>
                                                <div class="col-sm-3">
                                                    <button id="quserid" type="button" class="btn btn-success">查询</button>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-xs-offset-1 col-sm-10" style="background-color: gainsboro">
                                                    <div class="form-group">
                                                        <label class="control-label col-sm-3" style="text-align: center;">姓名</label>
                                                        <label class="control-label col-sm-4" style="text-align: center;">手机号</label>
                                                        <label class="control-label col-sm-3" style="text-align: center;">微信昵称</label>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group" style="height: 170px;">
                                                <label class="control-label col-sm-1"></label>
                                                <div class="col-sm-10" style="height: 188px;overflow: auto;">
                                                    <ul class="list-group" id="nameList">
                                                    </ul>
                                                </div>
                                            </div>

                                        </form>
                                    </div>

                                    <div class="modal-footer no-margin-top">
                                        <div class="col-xs-5 col-sm-5 col-sm-offset-5">
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
<script src="<%=basePath%>static/ace2/js/uncompressed/bootbox.js"></script>
<script src="<%=basePath%>static/ace2/js/jquery.gritter.min.js"></script>
<script>

    $(document).ready(function() {
        addPromotionRule();

        $('button#addPro').click(addPromotionRule);

        // 删除规则
        $('button.removeRule').click(function() {
            var $rule = $(this).parents('.promotion-rule');

            if($('.promotion-rule').length < 3) {
                $.gritter.add({
                    title: '操作提示',
                    text: "最少要有一件商品 !",
                    class_name: 'gritter-error'
                });
                return;
            }

            var $select = $rule.find(".row").children("div").first().children("select");
            var so = $select.find("option:selected");
            skulist[skulist.length] = {
                id:so.val(),
                name:so.text(),
                pName:$rule.find(".row").children("div").first().next().children("label").text()
            };

            bootbox.confirm("确定删除？",function(result) {
                if(result) {
                    $rule.remove();
                }
                return true;
            });

        });

        $(".list-group").on("click", ".list-group-item", function(){
            if($(this).attr("id") == "list_0"){
                return;
            }
            $(this).addClass("li_selected").siblings().removeClass("li_selected");
        });

        $('#promotionSave').on("click", sumbit_func);

        $("#useSelect").on("click", function(){
            $("#uName").val(null);
            $("#uPhone").val(null);
            $("#nameList").empty();
            $("#nameList").append($("<li class=\"list-group-item\" id='list_0'>"
                    + "<div class=\"form-group\" >"
                    + "<label class=\"col-sm-10\" style=\"padding-left: 40%;\">请点击查询按钮</label>"
                    + "</div>"
                    + "</li>"));
            $("#modal-library").modal("show");
        });

        $("#quserid").on("click", queryUser);

        $("#saveLibrary").unbind("click").on("click", function(){
            var select_li = $("#nameList .li_selected");
            var userId = select_li.attr("id");
            if(userId == undefined || userId == null || userId == ""){
                $.gritter.add({
                    title: '操作提示',
                    text: "您没有选中任何人",
                    class_name: 'gritter-error'
                });
                return;
            }
            var options = {
                url:"${path}/storagechange/skulist.do",
                type:"post",
                dataType:"json",
                data:{
                    userId:userId
                },
                success:function(data){
                    if(data.resCode == "success"){
                        skulist = new Array();
                        skulistPro = new Array();
                        for(var i = 0; i < data.skus.length; i++){
                            skulist[i] = {
                                id:data.skus[i].id,
                                name:data.skus[i].name,
                                pName:data.skus[i].pName
                            };
                            skulistPro[i] = {
                                id:data.skus[i].id,
                                name:data.skus[i].name,
                                pName:data.skus[i].pName
                            }
                        }

                        var userId = $("#nameList .li_selected").attr("id");
                        var userName = $("#nameList .li_selected").find("b").first().html();
                        $("#userId").val(userId);
                        $("#userName").html(userName);

                        $("#modal-library").modal("hide");
                        // 清除旧的的商品项
                        clearPromotionRule();
                        // 给商品项模板赋值新的商品下拉选项
                        addPromotionRule();
                    } else {
                        alert(data.resMsg);
                    }
                }
            }

            $.ajax(options);
        });

        $(".promotion-rule .row select").on("change", function(){
            var sku = findInPro($(this).attr("attr"));
            var $select = $(this);
            var so = $select.find("option:selected");

            if(!isInSkulistById(so.val())){
                $select.children("option").each(function(){
                    $(this).attr("selected", false);
                    if(sku.id == $(this).val()){
                        $(this).attr("selected", true);
                    }
                });
                $.gritter.add({
                    title: '操作提示',
                    text: "该商品已选择",
                    class_name: 'gritter-error'
                });
                return;
            }

            skulist[skulist.length] = sku;
            $select.attr("attr", so.val());
            for(var i=0; i < skulist.length; i++){
                if(so.val() == skulist[i].id){
                    skulist.splice(i, 1);
                    continue;
                }
            }
            $(this).parent().next().children("label").text(so.attr("attr"));
        });

    });

    var skulist = new Array({id:-1,name:"",pName:""});
    var skulistPro = new Array({id:-1,name:"",pName:""});

    function sumbit_func(){
        var userId = $("#userId").val();
        if(userId == undefined || userId == null || userId.trim() == ""){
            $.gritter.add({
                title: '操作提示',
                text: "请选择变更人",
                class_name: 'gritter-error'
            });
            return;
        }
        var size = $(".promotion-rule").size();
        if(size <= 1){
            $.gritter.add({
                title: '操作提示',
                text: "请选择商品",
                class_name: 'gritter-error'
            });
            return;
        }
        if(!checkSkuItemIsOk()){
            return;
        }


        $('#promotionSave').unbind("click");
        var options = {
            url:"${path}/storagechange/create.do",
            type:"post",
            dataType:"json",
            data:$("#promotionForm").serialize(),
            success:function(data){
                if(data.resCode == "success"){
                    parent.window.$('#myTabbable').add("tab15-1", '库存变更列表', "${path}/storagechange/list.shtml");
                    window.close();
                } else if(data.resCode == "unsign"){
                    window.top.location.href = "${path}/user/login.shtml";
                }else {
                    alert(data.resMsg);
                    $('#promotionSave').on("click", sumbit_func);
                }
            },
            error:function(){
                $('#promotionSave').on("click", sumbit_func);
            }
        }
        $.ajax(options);
    }

    function checkSkuItemIsOk(){
        var flag = true;
        $(".promotion-rule").each(function(){
            if($(this).attr("id") == "promotion-rule-template"){
                flag = false;
                return false;
            }
            var skuName = $(this).find(".row div:eq(0)").children("select").find("option:selected").text();
            var num = $(this).find(".row div:eq(2)").children("input").val();
            if(num == undefined || num == null || num.trim() == ""){
                $.gritter.add({
                    title: '操作提示',
                    text: skuName + " 没有填写数量",
                    class_name: 'gritter-error'
                });
                flag = false;
                return false;
            }
        });
        return flag;
    }


    function  isInSkulistById(id){
        for(var i=0; i < skulist.length; i++){
            if(id == skulist[i].id){
                return true;
            }
        }
        return false;
    }

    function findInPro(id){
        for(var i=0; i < skulistPro.length; i++){
            if(id == skulistPro[i].id){
                return skulistPro[i];
            }
        }
        return undefined;
    }

    function queryUser(){
        $("#quserid").unbind("click");
        var options = {
            url:"${path}/storagechange/quser.do",
            type:"post",
            dataType:"json",
            data:{
                userName:$("#uName").val().toLocaleString(),
                userPhone:$("#uPhone").val().toLocaleString()
            },
            success:function(data){
                $("#nameList").empty();
                if(data.resCode == "success"){
                    if(data.users != undefined && data.users.length > 0){
                        for(var i=0; i < data.users.length; i++){
                            var ele = "<li class=\"list-group-item\" id='" + data.users[i].id + "'>"
                                        + "<div class=\"form-group\">"
                                            + "<b class=\"col-sm-3\">" + data.users[i].realName + "</b>"
                                            + "<b class=\"col-sm-4\">" + data.users[i].mobile + "</b>"
                                            + "<b class=\"col-sm-3\" style=\"text-indent: 0.5em;\">" + data.users[i].wxNkName + "</b>"
                                        + "</div>"
                                      "</li>";
                            $("#nameList").append($(ele));
                        }
                    } else {
                        $("#nameList").append($("<li class=\"list-group-item\" id='list_0'>"
                                                    + "<div class=\"form-group\">"
                                                        + "<label class=\"col-sm-10\" style=\"padding-left: 40%;\">暂无结果</label>"
                                                    + "</div>"
                                                + "</li>"));
                    }
                } else {
                    $("#nameList").append($("<li class=\"list-group-item\" id='list_0'>"
                            + "<div class=\"form-group\">"
                            + "<label class=\"col-sm-10\" style=\"padding-left: 40%;\">" + data.resMsg + "</label>"
                            + "</div>"
                            + "</li>"));
                }
                $("#quserid").on("click", queryUser);
            },
            error:function(){
                $("#nameList").empty();
                $("#nameList").append($("<li class=\"list-group-item\" id='list_0'>"
                        + "<div class=\"form-group\">"
                        + "<label class=\"col-sm-10\" style=\"padding-left: 40%;\">网络错误，请重试</label>"
                        + "</div>"
                        + "</li>"));
                $("#quserid").on("click", queryUser);
            }
        };

        $.ajax(options);
    }

    function clearPromotionRule(){
        $("#promotionForm .promotion-rule").each(function(){
            if($(this).attr("id") == "promotion-rule-template"){
                return;
            }
            $(this).remove();
        });
    }

    // 添加规则
    function addPromotionRule() {
        var $template = $('#promotion-rule-template');
        var newRule = $template.clone(true).removeAttr("id").show();
        if(skulist.length <= 0){
            $.gritter.add({
                title: '操作提示',
                text: "暂无可添加商品",
                class_name: 'gritter-error'
            });
            return;
        }
        var sku = skulist[0];
        var _select = newRule.find(".row").children("div").first().children("select");
        _select.empty();
        for(var i=0; i < skulistPro.length; i++){
            var op = "<option value='" + skulistPro[i].id + "' attr='" + skulistPro[i].pName + "'";
                if(skulistPro[i].id == sku.id){
                    op += " selected"
                }
                    op += ">"
                        + skulistPro[i].name
                    + "</option>";
            _select.append($(op));
        }
        _select.attr("attr", sku.id);
        newRule.find(".row").children("div").first().next().children("label").text(sku.pName);
        skulist.splice(0, 1);
        $("#skutitle").after(newRule);
    }
</script>
</body>
</html>
