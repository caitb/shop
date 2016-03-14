<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/bangding.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <script src="<%=path%>/static/js/iscroll.js"></script>
    <script src="<%=path%>/static/js/checkUtil.js"></script>
    <script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
        var s = 60, t;
        function times(){
            s--;
            $("#codeId").val("剩余" + s + "s");
            $("#codeId").attr({"disabled":"disabled"});
            t = setTimeout(function (){times();}, 1000);
            if ( s <= 0 ){
                s = 60;
                $("#codeId").removeAttr("disabled");
//                $("#codeId").prop("disabled", false);
                $("#codeId").val("获取验证码");
                clearTimeout(t);

            }
        }


        var phone;
        var $value;
        var password;
        var isPassword;
        var isPhone;
        $(function(){
            $("#phoneId").blur(function(){
                Iphone= $("#phoneId").val();
                isPhone= checkPhone(Iphone);
                if(!isPhone){
//                    alert("手机号格式不对");
                    $("#phoneTip").html("本系统暂时只接受中国大陆手机号码格式");
                    return;
                }else{
                    $("#phoneTip").html("");
                }
            });
            $("#codeId").click(function(){
                times();
//                alert($("#phoneId").val());
                $.ajax({
                    type:"POST",
                    url : "<%=path%>/binding/securityCode.do",
                    data:"phone="+$("#phoneId").val(),
                    dataType:"Json",
                    success:function(result){
                        if(result){
                            $("#codeValueId").val("短信发送成功,请注意查收!");
                        }else{
                            $("#codeValueId").val("短信发送失败,请重试!");
                        }
                    }
                });
            });

            $("#codeValueId").blur(function(){
                $value= $("#codeValueId").val();
                if($value==null || $value==""){
                    $("#codeValueId").val("验证码不能为空");
                    return;
                }
                $.ajax({
                    type:"POST",
                    url : "<%=path%>/binding/verificationCode.do",
                    data:"verificationCode="+$("#codeValueId").val(),
                    dataType:"Json",
                    success:function(result){
                        if(result){
                            $("#codeValueId").val("验证成功!");
                        }else{
                            $("#codeValueId").val("验证失败,请重试!");
                        }
                    }
                });
            });

            $("#passwordId").blur(function(){
                password = $("#passwordId").val();
                isPassword= isWordAndNum(password);
                if(isPassword){
                    $("#passwordTip").html("密码只能包含数字字母");
                    return;
                }else if(password==null || password==""){
                    $("#passwordTip").html("密码不能为空");
                    return;
                }else{
                    $("#passwordTip").html("");
                }
            });
            $(".bd").click(function(){
                password = $("#passwordId").val();
                phone= $("#phoneId").val();
                isPhone= checkPhone(phone);
                isPassword= isWordAndNum(password);
                if(!isPhone ){
                    $("#phoneTip").html("只接受中国大陆号码");
                    return;
                }else if(isPassword){
                    $("#passwordTip").html("密码只能包含数字字母");
                    return;
                }else if(password==null || password==""){
                    $("#passwordTip").html("密码不能为空");
                    return;
                }else{
                    $.ajax({
                        type:"POST",
                        url : "<%=path%>/binding/verificationCode.do",
                        data:"verificationCode="+$("#codeValueId").val(),
                        dataType:"Json",
                        success:function(result){
                            location.href="<%=path%>/binding/bindingComUse.html";
                        },
                        error:function(result){
                            $("#codeValueId").val("验证码输入有误");
                        }
                    });
                }

            });

        });
    </script>
</head>
<body>
   
    <div class="wrap">

        <header class="xq_header">
              <a href="index.html"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
                <p>绑定帐号</p>            
        </header>
        <div class="banner">
            <p><img src="<%=path%>/static/images/shouye_nav.png" alt=""></p>
            <h1>王平</h1>
            <h2>绑定账号后，您可以使用您的手机号码登录麦链商城</h2>
        </div>
        <section class="input_t phone">
            <p>手机号：</p>
            <input type="text" id="phoneId" name="phone" ><span id="phoneTip" style="color-rendering:#a5142f "></span>
        </section>
        <section class="input_t">
            <p>验证码：</p>
            <input type="text" id="codeValueId" name="verificationCode" value="">
            <h4><input type="button"  id="codeId" value="获取验证码"/></h4>
        </section>
        <section class="input_t mima">
            <p>密码：</p>
            <input type="password" id="passwordId" name="password"><span id="passwordTip"></span>
        </section>
        <p class="rodia">
            <input type="checkbox" id="fu" checked>
                <label for="fu">同意《代理商注册协议》</label>
        </p>
        <a href="javascript:;" class="bd">绑定帐号</a>
    </div>
</body>
</html>