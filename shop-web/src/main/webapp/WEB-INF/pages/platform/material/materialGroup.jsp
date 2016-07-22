<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/material/Library.css">
    <link rel="stylesheet" href="${path}/static/css/alert.css">
</head>
<body>

<div class="wrap">
    <header class="xq_header">
        <p>素材库</p>
    </header>
    <main id="divall">
        <c:forEach var="Library" items="${LibraryList}">
            <p>
                <span onclick="javascript:window.location.href = '${path}/materielList/groupInfoB/?mlId=${Library.id}&random='+Math.random();"><img
                        src="${Library.remark}" alt=""></span>
                <c:if test="${Library.isSubscript==0}">
                    <span class="add" id="${Library.id}"
                          onclick="subAdd('${Library.id}',this.id)"><b>+添加订阅</b> | ${Library.subscriptionNum}</span>
                </c:if>
                <c:if test="${Library.isSubscript==1}">
                    <span class="add on" id="${Library.id}"
                          onclick="subAdd('${Library.id}',this.id)"><b>取消订阅</b> | ${Library.subscriptionNum}</span>
                </c:if>
            </p>
        </c:forEach>
    </main>
    <div class="nobady" style="display: none">
        <img src="${path}/static/images/material/nodady.png" alt="">

        <p>暂无上传素材</p>
    </div>
    <img src="${path}/static/images/material/FAB.png" alt="" onclick="clickShow()">
</div>
<div class="black">
    <div class="backb"></div>
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
<c:if test="${countLibrary>4}">
    <img src="${path}/static/images/material/downloading.png" alt="" class="downloading">
</c:if>
<script src="${path}/static/js/jquery-1.8.3.min.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script src="${path}/static/js/hideWXShare.js"></script>
<script>
    var loginWidtn = $(".wrap").width() / 2 - 1;
    var index;
    $("main p").width(loginWidtn);
    function subAdd(id, index) {
        if ($("#" + index).hasClass("on") != true) {
            //添加订阅
            $.ajax({
                url: '${path}/subscribeB/do',
                type: 'post',
                data: {status: 1, materialId: id},
                dataType: 'json',
                async: false,
                cache:false,
                success: function (data) {
                    if (data.isError == false) {
                        window.location.href='${basePath}materielList/infoB?random='+Math.random();
                    }
                }
            });
            $("#" + index).addClass("on");
            $("#" + index).find("b").html("取消订阅");
        } else if ($("#" + index).hasClass("on") == true) {
            //取消订阅
            $.ajax({
                url: '${path}/subscribeB/do',
                type: 'post',
                data: {status: 0, materialId: id},
                dataType: 'json',
                async: false,
                cache:false,
                success: function (data) {
                    if (data.isError == false) {
                        window.location.href='${basePath}/materielList/infoB?random='+Math.random();
                    }
                }
            });
            $("#" + index).removeClass("on");
            $("#" + index).find("b").html("+添加订阅");
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
        var reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
        if (email == null || email == "") {
            alert("邮箱不能为空");
            return false;
        }
        if (!reg.test(email)) {
            alert("请输入正确的邮箱格式");
            return false;
        }
        $.ajax({
            url: '${path}/materielApply/addEmail.do',
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
    // 分页查询
    var pagelimit = 2;
    $(".downloading").on("click", function () {
        var _contain = $("#divall");
        var Path = '${path}';
        $.ajax({
            type: 'post',
            url: '${path}/materielList/infoBPagenation',
            data: {currentPage: pagelimit},
            dataType: 'json',
            success: function (data) {
                if (data.LibraryList == null || data.LibraryList.length <= 0) {
                    alert("没有更多了");
                }
                if (data.isError == false && data.LibraryList.length > 0) {
                    $.each(data.LibraryList, function (i, Library) {
                        var imgHtml = "";
                        if (Library.isSubscript == 0) {
                            imgHtml += "<span class=\"add\" id=" + Library.id + " onclick=\"subAdd(" + Library.id + ",this.id)\"><b>+添加订阅</b> | " + Library.subscriptionNum + "</span>";
                        }
                        if (Library.isSubscript == 1) {
                            imgHtml += "<span class=\"add on\" id=" + Library.id + " onclick=\"subAdd(" + Library.id + ",this.id)\"><b>取消订阅</b> | " + Library.subscriptionNum + "</span>";
                        }
                        _contain.append("<p>" +
                        "<span onclick=\"javascript:window.location.replace('" + Path + "/materielList/groupInfoB/?mlId=" + Library.id + "&random=" + Math.random() + "');\"><img src=\"" + Library.remark + "\" alt=\"\">" +
                        "</span>" + imgHtml + "</p>");
                    });
                    pagelimit++;
                }
                $("main p").width(loginWidtn);
            }
        });
    })
</script>
</body>
</html>