<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/dropload.css">
    <link rel="stylesheet" href="${path}/static/css/material/Library.css">
</head>
<body>

<div class="wrap">
    <header class="xq_header">
        <a href="index.html"><img src="${path}/static/images/xq_rt.png" alt=""></a>

        <p>素材库</p>
    </header>
    <main>
        <c:forEach var="Library" items="${LibraryList}">
            <p>
                <span><img src="${Library.remark}" alt=""></span>
                <c:if test="${Library.isSubscript==0}">
                    <span class="add" id="${Library.id}" onclick="subAdd('${Library.id}',this.id)"><b>+添加订阅</b> | 1234</span>
                </c:if>
                <c:if test="${Library.isSubscript==1}">
                    <span class="add on" id="${Library.id}" onclick="subAdd('${Library.id}',this.id)"><b>取消订阅</b> | 1234</span>
                </c:if>
            </p>
        </c:forEach>

    </main>
    <img src="${path}/static/images/material/FAB.png" alt="" onclick="clickShow()">

    <div id="datePlugin"></div>
</div>
<div class="black">
    <div class="back_b"></div>
    <div class="b_t">
        <h1>亲爱的代理，</h1>

        <p>
            线下素材的图片像素比较大，请您留下您的邮箱地址，系统稍后会将线下素材的下载链接发到您的邮箱，请注意查收！
        </p>
        <input type="text" placeholder="请输入邮箱地址" id="email">
        <button onclick="saveEmail()">留下邮箱</button>
        <b class="off" onclick="clickHide()">×</b>
    </div>
</div>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script type="text/javascript" src="${path}/static/js/iscroll.js"></script>
<script type="text/javascript" src="${path}/static/js/dropload.min.js"></script>
<script>
    var loginWidtn = $(".wrap").width() / 2 - 1;
var index;
    $("main p").width(loginWidtn);
    function subAdd(id,index) {
        if ($("#"+index).hasClass("on") != true) {
            //添加订阅
            $.ajax({
                url: '${basePath}subscribeB/do',
                type: 'post',
                data: {status: 1, materialId: id},
                dataType: 'json',
                success: function (data) {
                    if (data.isError == false) {
                        alert("订阅成功！");
                    }
                }
            });
            $("#"+index).addClass("on");
            $("#"+index).find("b").html("取消订阅");
        } else if ($("#"+index).hasClass("on") == true) {
            //取消订阅
            $.ajax({
                url: '${basePath}subscribeB/do',
                type: 'post',
                data: {status: 0, materialId: id},
                dataType: 'json',
                success: function (data) {
                    if (data.isError == false) {
                        alert("取消成功！");
                    }
                }
            });
            $("#"+index).removeClass("on");
            $("#"+index).find("b").html("+添加订阅");
        }
    }
    function clickShow() {
        $("#email").val("");
        $(".black").show();
    }
    function clickHide() {
        $(".black").hide();
    }

    function saveEmail() {
        var email = $("#email").val();
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        if (email == null || email == "") {
            alert("邮箱不能为空");
            return false;
        }
        if (!reg.test(email)) {
            alert("请输入正确的邮箱格式");
            return false;
        }
        $.ajax({
            url: '${basePath}materielApply/addEmail.do',
            type: 'post',
            data: {email: email},
            dataType: 'json',
            success: function (data) {
                if (data.isError == false) {
                    alert("邮箱上传成功，请注意查收邮件");
                    $(".black").hide();
                }
            }
        });
    }
    //   下拉
    $('body').dropload({
        scrollArea: window,
        loadDownFn: function (me) {
            $.ajax({
                type: 'GET',
                url: 'json/more.json',
                dataType: 'json',
                success: function (data) {
                    // 代码执行后必须重置
                },
                error: function (xhr, type) {
                    me.resetload();
                }
            });
        }
    });
</script>
</body>
</html>