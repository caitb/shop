<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link href="<%=basePath%>static/css/normalize.css" rel="stylesheet" />
<link href="<%=basePath%>static/css/jquery-ui.css" rel="stylesheet" />
<link href="<%=basePath%>static/css/jquery.idealforms.min.css" rel="stylesheet" />

<div class="row">

    <div class="eightcol last">

        <!-- Begin Form -->

        <form id="my-form">


                <div><label>用户名:</label><input id="username" name="username" type="text"/></div>
                <div><label>密码:</label><input id="pass" name="password" type="password"/></div>
                <div><label>邮箱:</label><input id="email" name="email" data-ideal="required email" type="email"/></div>
                <div><label>出生日期:</label><input name="date" class="datepicker" data-ideal="date" type="text" placeholder="月/日/年"/></div>
                <%--<div><label>上传头像:</label><input id="file" name="file" multiple type="file"/></div>--%>
                <%--<div><label>个人主页:</label><input name="website" data-ideal="url" type="text"/></div>--%>

            <div><hr/></div>

            <div>
                <button type="submit">保存</button>
            </div>

        </form>

        <!-- End Form -->

    </div>

</div>


<script type="text/javascript" src="<%=basePath%>static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery.idealforms.js"></script>
<script type="text/javascript">
    var options = {

        onFail: function(){
            alert( $myform.getInvalid().length +' invalid fields.' )
        },

        inputs: {
            'password': {
                filters: 'required pass',
            },
            'username': {
                filters: 'required username',
                data: {
                    //ajax: { url:'validate.php' }
                }
            },
            'file': {
                filters: 'extension',
                data: { extension: ['jpg'] }
            },
            'comments': {
                filters: 'min max',
                data: { min: 50, max: 200 }
            },
            'states': {
                filters: 'exclude',
                data: { exclude: ['default'] },
                errors : {
                    exclude: '选择国籍.'
                }
            },
            'langs[]': {
                filters: 'min max',
                data: { min: 2, max: 3 },
                errors: {
                    min: 'Check at least <strong>2</strong> options.',
                    max: 'No more than <strong>3</strong> options allowed.'
                }
            }
        }

    };

    var $myform = $('#my-form').idealforms(options).data('idealforms');

    $('#reset').click(function(){
        $myform.reset().fresh().focusFirst()
    });

    $myform.focusFirst();
</script>
