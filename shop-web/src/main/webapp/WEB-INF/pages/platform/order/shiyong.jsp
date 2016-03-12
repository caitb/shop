<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链商城</title>
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/shiyong.css">
</head>
<body>
<header class="xq_header">
    <a href="#" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png" alt=""></a>
    <p>申请试用</p>
</header>
<script type="text/javascript" src="<%=path%>/static/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/checkUtil.js"></script>
<script>
    var s = 60, t;
    function times(){
        s--;
        $("#codeId").val("剩余" + s + "s");
        $("#codeId").attr({"disabled":"disabled"});
        t = setTimeout(function (){times();}, 1000);
        if ( s <= 0 ){
            s = 60;
            $("#codeId").removeAttr("disabled");
            $("#codeId").val("获取验证码");
            clearTimeout(t);
        }
    }
    var isPhone;
    $(function() {
        $("#phoneId").blur(function(){
            isPhone= checkPhone($("#phoneId").val());
            if(!isPhone){
                   alert("手机号格式不对");
              //  $("#phoneTip").html("本系统暂时只接受中国大陆手机号码格式");
                return;
            }
        });
        $("#codeId").click(function () {
            isPhone= checkPhone($("#phoneId").val());
            if(!isPhone){
                alert("手机号格式不对");
                return;
            }
            times();
            $.ajax({
                type: "POST",
                async:false,
                url: "<%=path%>/binding/securityCode.do",
                data: "phone=" + $("#phoneId").val(),
                dataType: "Json",
                success: function (result) {
                    $("#codeValueId").val("短信发送成功,请注意查收!");
                },
                error:function(){
                    alert("发送验证码失败");
                }
            });
        });
    })
    function apply(){
        var name  = $("#nameId")[0].value;
        var phone = $("#phoneId")[0].value;
        var wechat = $("#wechatId")[0].value;
        var applyReason = $("#applyReasonId")[0].value;
        var spuId =$("#spuId")[0].value;
        var skuId = $("#skuId")[0].value;
        var isPhone = checkPhone(phone);
        if (!checkNumber(wechat)){
            alert("微信号格式不对");
            return;
        }
        if(!checkNumber(phone)||!isPhone){
            alert("手机号格式不对");
            return;
        }

        var $value = $("#codeValueId").val();
        if ($value == null || $value == "") {
            $("#codeValueId").val("验证码不能为空");
            return;
        }

        $.ajax({
            type:"POST",
            url : "<%=path%>/binding/verificationCode.do",
            data:"verificationCode="+$("#codeValueId").val(),
            dataType:"Json",
            success:function(result){
                $.post("/corder/trialApply.do",
                        {
                            "spuId":spuId,
                            "skuId":skuId,
                            "applyReason":applyReason,
                            "name" : name,
                            "phone" : phone,
                            "wechat" : wechat
                        },function(data) {
                            if(data == "success"){
                                window.location.href = "<%=path%>/corder/continueStroll"
                            }else{
                                alert("逻辑出错");
                            }
                        });
            },
            error:function(result){
                $("#codeValueId").val("验证码输入有误");
            }
        });
    }
</script>
<main>
    <section class="sec2">
        <p class="photo">
            <a href="../html/xiangqing.html">
                <img src="${skuDefaultImg}" alt="${skuImgAlt}">
            </a>
        </p>
        <div>
            <h2>${product.name}</h2>
            <h3>规格：<span>默认</span><b>x1</b></h3>
            <p>零售价： 非卖品</p>
            <p style="color:#333;">运费<span>${product.shipAmount}</span></p>
        </div>
    </section>
    <section class="sec3">
        <h1>共<b>1</b>件商品　运费：<span>￥${product.shipAmount}</span>　<b style="color:#333333">合计：</b><span>￥${product.shipAmount}</span></h1>
        <p>申请理由：<input type="text" id="applyReasonId"></p>
    </section>
    <h1 class="pople">
        申请人信息
    </h1>
    <section class="sec4">
        <input id="skuId" type="hidden" value="${product.id}"/>
        <input id="spuId" type="hidden" value="${product.spuId}"/>
        <p>姓名：<input id="nameId" type="text"></p>
        <p>手机号：<input id="phoneId" type="tel"></p>
        <p>验证码：<input id="codeValueId" type="text"><span id="codeId">获取验证码</span></p>
        <p>微信：<input id="wechatId" type="text"></p>
    </section>
    <a href="javascript:apply();"  class="sq">试用申请</a>
</main>
</body>
</html>