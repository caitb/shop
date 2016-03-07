<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
        $(function(){
            var isPassword;
            $("#phoneId").blur(function(){
                var phone= $("#phoneId").val();
                var isPhone= checkPhone(phone);
                if(!isPhone){
                    alert("手机号格式不对");
                    return;
                }
            });

            $("#passwordId").blur(function(){
                var password = $("#passwordId").val();
                isPassword= isWordAndNum(password);
                //alert(password);
                if(isPassword){
                    alert("密码只支持数字或字母");
                    //return;
                }
            });
            if(isPassword){
                alert("gdfgdfgd");
                return;
            }
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
            <input type="text" id="phoneId" name="phone" value="">
        </section>
        <section class="input_t">
            <p>验证码：</p>
            <input type="text" name="verificationCode" value="">
            <h4>获取验证码</h4>
        </section>
        <section class="input_t mima">
            <p>密码：</p>
            <input type="password" id="passwordId" name="password" value="">
        </section>
        <p class="rodia">
            <input type="checkbox" id="fu" checked>
                <label for="fu">同意《代理商注册协议》</label>
        </p>
        <a href="bangdingchenggong.html" class="bd">绑定帐号</a>
    </div>
</body>
</html>