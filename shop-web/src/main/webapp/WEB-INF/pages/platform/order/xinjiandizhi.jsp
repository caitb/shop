<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>麦链合伙人</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" href="${path}/static/css/xinjiandizhi.css">
</head>
<body>
<main>
    <div class="wrap">
        <div class="box">
            <header class="xq_header">
                <a href="javascript:;" onClick="javascript:history.back(-1);"><img
                        src="<%=path%>/static/images/xq_rt.png"
                        alt=""></a>
                <p>新建收货地址</p>
            </header>
            <div id="d_box">
                <p class="sf">
                    收货人姓名
                    <input type="text" class="name" id="name">
                    <span class="onc"></span>
                    <b class="gao">手机号输入错误</b>
                </p>
                <p class="sf">
                    手机号码
                    <input type="tel" class="tel" id="phone">
                    <span class="onc"></span>
                    <b class="gao"></b>
                </p>
                <p class="sf">
                    邮政编码
                    <input type="tel" class="postcode" id="postcode">
                    <span class="onc"></span>
                    <b class="gao"></b>
                </p>
                <div class="address">
                    联系地址
                    <select class="sel" id="s_province" name="s_province">
                        <option value='-1'>--省份--</option>
                    </select>
                    <select class="sel" id="s_city" name="s_city">
                        <option value='-1'>--地级市--</option>
                    </select>
                    <select class="sel" id="s_county" name="s_county">
                        <option value='-1'>--县/区--</option>
                    </select>
                </div>
                <p class="sf">
                    详细地址
                    <input type="text" class="dizhi" id="detailAddress">
                    <span class="onc"></span>
                    <b class="gao"></b>
                </p>
            </div>
        </div>
        <input type="text" id="operateTypeId" style="display: none" value="save"/>
        <input type="text" id="addAddressJumpTypeId" style="display: none" value="${addAddressJumpType}"/>
        <a onclick="saveAddress()" class="baocun">
            保存
        </a>
    </div>
</main>
<%@ include file="/WEB-INF/pages/common/foot.jsp" %>
<script src="${path}/static/js/checkUtil.js"></script>
<script src="${path}/static/js/definedAlertWindow.js"></script>
<script src="${path}/static/js/comArea.js"></script>
<script src="${path}/static/js/address.js"></script>
<script>
    comAreaJS.init("add");
    addressJS.init();
</script>
<script>
    /*    var promise =  $.Deferred().promise();
     function saveAddress() {
     var paramJson = addressJS.getJsonParam();
     if (promise.state()=="pending"){
     promise = $.ajax({
     url: '/userAddress/addOrUpdateAddress.do',
     type: 'post',
     async: false,
     data: paramJson,
     success: function (data) {
     if (data == "false") {
     alert("新增地址失败");
     } else {
     window.location.href = data;
     }
     },
     error: function () {
     }
     });
     }
     promise.then()
     }*/


    function saveAddress() {
        var paramJson = addressJS.getJsonParam();
        if (addressJS.validateAddressInfo(paramJson)) {
            $.ajax({
                url: '/userAddress/addOrUpdateAddress.do',
                type: 'post',
                async: false,
                data: paramJson,
                success: function (data) {
                    if (data == "false") {
                        alert("新增地址失败");
                    } else {
                        window.location.href = data;
                    }
                },
                error: function () {
                }
            })
        }
    }
</script>
</body>
</html>