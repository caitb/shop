<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <link rel="stylesheet" href="<%=path%>/static/css/base.css">
    <link rel="stylesheet" href="<%=path%>/static/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/static/css/tijiaosq.css">
    <link rel="stylesheet" href="<%=path%>/static/css/header.css">
    <link rel="stylesheet" href="<%=path%>/static/css/loading.css"/>
</head>
<body>
<div class="wrap">
    <div class="box">
        <header class="xq_header">
            <a href="javascript:;" onClick="javascript :history.go(-1);"><img src="<%=path%>/static/images/xq_rt.png"
                                                                              alt=""></a>
            <p>授权书申请 </p>
        </header>
        <div class="xinxi">
            <p>注册信息</p>
            <p>支付订单</p>
            <p>选择拿货方式</p>
        </div>
        <p class="cp">
            合伙产品：<span>${skuName}</span>
        </p>

        <main>
            <p class="sf" style="color:#333333;text-indent:10px; font-size:14px;">姓名：<span
                    style="font-size:14px;">${name}</span></p>
            <div class="sf">
                身份证号：
                <input type="text" id="idCard" name="idCard" value="${idCard}">
            </div>
            <div class="sf" style="border-bottom:none;">
                身份证照片：
            </div>
            <div class="sfphoto">
                <input type="file" id="idCardImg" name="idCardImg" onchange="uploadIdCardImg()"
                       style="display: none;">
                <label class="zheng">
                    <c:if test="${idCardFrontUrl==''}">
                        <img src="<%=path%>/static/images/shenfen.png" alt="" id="idCardFront" name="idCardPre"
                             onclick="F_Open_dialog(0)">
                    </c:if>
                    <c:if test="${idCardFrontUrl!=''}">
                        <img src="${idCardFrontUrl}" alt="" id="idCardFront" name="idCardPre"
                             onclick="F_Open_dialog(0)">
                    </c:if>
                </label>
                <label class="fan" style="margin-left:10px;">
                    <c:if test="${idCardBackUrl==''}">
                        <img src="<%=path%>/static/images/shenfenf.png" alt="" id="idCardBack" name="idCardPre"
                             onclick="F_Open_dialog(1)">
                    </c:if>
                    <c:if test="${idCardBackUrl!=''}">
                        <img src="${idCardBackUrl}" alt="" id="idCardBack" name="idCardPre"
                             onclick="F_Open_dialog(1)">
                    </c:if>
                </label>
                <%--<img style="width:100%;display:block" class="block" id="idCardFront" name="idCardPre"--%>
                <%--src="<%=path%>/static/images/default_pic.png"--%>
                <%--onclick="F_Open_dialog(0)">--%>
                <%--<img style="width:100%;display:block" class="block" id="idCardBack" name="idCardPre"--%>
                <%--src="<%=path%>/static/images/default_pic.png"--%>
                <%--onclick="F_Open_dialog(1)">--%>

            </div>

        </main>
        <a href="javascript:;" class="tijiao" onclick="submit();">提交申请</a>
    </div>
</div>
</body>
<script src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/static/js/commonAjax.js"/>
<script src="<%=path%>/static/js/iscroll.js"></script>
<script src="<%=path%>/static/js/ajaxfileupload.js"></script>
<script>
    var checkImg = 0;
    function F_Open_dialog(data) {
        if (data == 0) {
            checkImg = 0;
        } else {
            checkImg = 1;
        }
        document.getElementById("idCardImg").click();
    }
    function uploadIdCardImg() {
        $.ajaxFileUpload({
            url: "<%=basePath%>userCertificate/idCardImgUpload.do",
            data: "",
            type: "POST",
            secureuri: false,
            fileElementId: ['idCardImg'],
            dataType: "json",
            success: function (rdata) {
                var data = JSON.parse(rdata);
                if (data.code == 1) {
                    if (checkImg == 0) {
                        $("#idCardFront").attr("src", "<%=path%>" + data.imgPath);
                    } else {
                        $("#idCardBack").attr("src", "<%=path%>" + data.imgPath);
                    }
                } else {
                    alert(data.msg);
                }
            }
        });
    }
    function submit() {
        var cd = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;//定义身份证匹配算法
        var legalIdCard = $("#idCard").val();//获取前台界面字段值
        if (legalIdCard != null && legalIdCard != "") {//运用匹配方法直接判断
            if (cd.test(legalIdCard) == false) {
                alert("身份证号不合法!");
                return;
            }
        }
        var fCardUrl = $("#idCardFront").attr("src");
        var bCardUrl = $("#idCardBack").attr("src");
        fCardUrl = fCardUrl.substr(fCardUrl.lastIndexOf('/') + 1);
        bCardUrl = bCardUrl.substr(bCardUrl.lastIndexOf('/') + 1);
        var paraData = {};
        paraData.userSkuId = "${userSkuId}";
        paraData.name = "${name}";
        paraData.idCard = legalIdCard;
        paraData.idCardFrontUrl = fCardUrl;
        paraData.idCardBackUrl = bCardUrl;
        $.ajax({
            url: "<%=basePath%>userCertificate/add.do",
            type: "post",
            data: paraData,
            dataType: "json",
            success: function (data) {
                if (data.isError == false) {
                    window.location.href = "<%=basePath%>userApply/applyOK.shtml";
                }
                else {
                    alert(data.message);
                }
            }
        });
    }
</script>
</html>