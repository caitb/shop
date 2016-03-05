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
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>static/class/ueditor/lang/zh-cn/zh-cn.js"></script>

    <style type="text/css">
        div {
            width: 100%;
        }
    </style>


    <!-- Le styles -->
    <link href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap.css" rel="stylesheet">
    <link href="<%=basePath%>static/class/bootstrap-3.3.5-dist/css/bootstrap-responsive.css" rel="stylesheet">
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->


</head>

<body>
    <form class="form-horizontal">
        <div class="form-group">
            <label for="skuName" class="col-sm-4 control-label">商品名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="skuName" placeholder="商品名称">
            </div>
        </div>
        <div class="form-group">
            <label for="skuNo" class="col-sm-4 control-label">商品货号</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="skuNo" placeholder="商品货号">
            </div>
        </div>
        <div class="form-group">
            <label for="skuCode" class="col-sm-4 control-label">商品条码</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="skuCode" placeholder="商品条码">
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
                <select class="form-control" id="skuC3" name="skuC3">
                </select>
            </div>
            <script>
                var categories = window.eval('(' + '${categories}' + ')');
                var categoryArr = [[],[],[]];
                for(var i in categories){
                    if(categories[i].level == 1){
                        categoryArr[0].push(categories[i]);
                    }
                    if(categories[i].level == 2){
                        categoryArr[1].push(categories[i]);
                    }
                    if(categories[i].level == 3){
                        categoryArr[2].push(categories[i]);
                    }

//                    categories[i].children = [];
//                    for(var s in categories){
//                        if(categories[s].pid = categories[i].id) categories[i].children.push(categories[s]);
//                    }
                }

                var $skuC1 = $('#skuC1');
                var $skuC2 = $('#skuC2');
                var $skuC3 = $('#skuC3');
                $skuC1.html('<option value="-1">请选择</option>');
                for(var i in categoryArr[0]){
                    $skuC1.append('<option value="' + categoryArr[0][i].id + '">' + categoryArr[0][i].name + '</option>');
                }

                $skuC1.change(function(){
                    $skuC2.empty().html('<option value="-1">请选择</option>');
                    $skuC3.empty().html('<option value="-1">请选择</option>');
                    for(var i in categoryArr[1]){
                        if(categoryArr[1][i].pid = $(this).val()){
                            for(var sub in categoryArr[1][i].children){
                                $skuC3.append('<option value="' + categoryArr[1][i].children[sub].id + '">' + categoryArr[1][i].children[sub].name + '</option>');
                            }
                        }
                    }
                });

                $skuC2.change(function(){
                    $skuC3.empty().html('<option value="-1">请选择</option>');
                    for(var i in categoryArr[1]){
                        if(categoryArr[2][i].pid = $(this).val()){
                            for(var sub in categoryArr[2][i].children){
                                $skuC3.append('<option value="' + categoryArr[2][i].children[sub].id + '">' + categoryArr[2][i].children[sub].name + '</option>');
                            }
                        }
                    }
                });


            </script>
        </div>
        <div class="form-group">
            <label for="brandId" class="col-sm-4 control-label">商品品牌</label>
            <div class="col-sm-4">
                <select class="form-control" id="brandId" name="brandId">
                    <c:forEach items="${brands}" var="brand">
                    <option value="${brand.id}">${brand.cname}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-3"></div>
            <div class="col-sm-5">
                <div class="alert alert-success" role="alert">
                    <strong>价格设置</strong>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="costPrice" class="col-sm-4 control-label">成本价</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="costPrice" placeholder="成本价">
            </div>
        </div>
        <div class="form-group">
            <label for="marketPrice" class="col-sm-4 control-label">市场价</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="marketPrice" placeholder="市场价">
            </div>
        </div>
        <div class="form-group">
            <label for="retailPrice" class="col-sm-4 control-label">零售价</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="retailPrice" placeholder="零售价">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">是否允许试用</label>
            <div class="col-sm-4">
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
                        是
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                        否
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="freight" class="col-sm-4 control-label">运费设置</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="freight" placeholder="运费设置">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-3"></div>
            <div class="col-sm-5">
                <div class="alert alert-success" role="alert">
                    <strong>合伙人设置</strong>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="advanced" class="col-sm-4 control-label">高级</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="advanced" placeholder="">
            </div>
            <label class="col-sm-2">每件商品100元</label>
            <label for="advancedCount" class="col-sm-1 control-label">拿货数量</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="advancedCount" placeholder="">
            </div>
        </div>
        <div class="form-group">
            <label for="intermediate" class="col-sm-4 control-label">中级</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="intermediate" placeholder="">
            </div>
            <label class="col-sm-2">每件商品100元</label>
            <label for="intermediateCount" class="col-sm-1 control-label">拿货数量</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="intermediateCount" placeholder="">
            </div>
        </div>
        <div class="form-group">
            <label for="primary" class="col-sm-4 control-label">初级</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="primary" placeholder="">
            </div>
            <label class="col-sm-2">每件商品100元</label>
            <label for="primaryCount" class="col-sm-1 control-label">拿货数量</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="primaryCount" placeholder="">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-3"></div>
            <div class="col-sm-5">
                <div class="alert alert-success" role="alert">
                    <strong>分销设置</strong>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="reciprocal1" class="col-sm-4 control-label">倒数第一</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="reciprocal1" placeholder="">
            </div>
            <label class="col-sm-2">每件商品100元</label>
        </div>
        <div class="form-group">
            <label for="reciprocal2" class="col-sm-4 control-label">倒数第二</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="reciprocal2" placeholder="">
            </div>
            <label class="col-sm-2">每件商品100元</label>
        </div>
        <div class="form-group">
            <label for="reciprocal3" class="col-sm-4 control-label">倒数第三</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="reciprocal3" placeholder="">
            </div>
            <label class="col-sm-2">每件商品100元</label>
        </div>

        <hr/>

        <div class="form-group">
            <label for="inShort" class="col-sm-4 control-label">一句话介绍</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="inShort" placeholder="一句话介绍">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">商品介绍</label>
            <div class="col-sm-4">

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

    </form>
</body>
</html>