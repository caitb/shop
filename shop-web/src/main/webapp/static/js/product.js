(function () {
    window.productJS = window.productJS || {
            skuId: $("#applyTrialId").val(),
            phone: null,
            initPage: function () {
                productJS.initContent();
                productJS.initClick();
            },
            initContent: function () {
                $(".back").attr("style", "display:none");
                $.ajax({
                    url: '/corder/isApplyTrial.do',
                    type: 'post',
                    async:false,
                    data: {"skuId": productJS.skuId},
                    success: function (data) {
                        var dataObj = eval("(" + data + ")");//转换为json对象
                        if (dataObj == null || dataObj == "") {
                            $("#applyTrial").attr("style", "display:block");
                            $("#trialed").attr("style", "display:none");
                        } else {
                            $("#applyTrial").attr("style", "display:none");
                            $("#trialed").attr("style", "display:block");
                        }
                    },
                });
            },
            initClick: function () {
                $("#validateNumberId").on("click", function () {
                    productJS.getValidateNumber();
                })
                $("#nextPageId").on("click", function () {
                    productJS.toNextPage();
                })
            },
            applyTrial: function () {
                var skuId = $("#applyTrialId").val();
                $.ajax({
                    url: '/user/isBindPhone.do',
                    type: 'post',
                    async: false,
                    success: function (data) {
                        if (!data) {
                            window.location.href = "/corder/confirmOrder.do?skuId=" + skuId;
                        } else {
                            //显示绑定手机号
                            $(".back").attr("style", "display:-webkit-box");
                        }
                    },
                    error: function () {
                        //显示绑定手机号
                        $(".back").attr("style", "display:-webkit-box");
                    }
                });
            },
            getValidateNumber: function () {
                productJS.phone = $("#phoneId").val();
                if (productJS.checkPhone(productJS.phone)) {
                    $.ajax({
                        type: "POST",
                        url: "/binding/securityCode.do",
                        data: "phone=" + productJS.phone,
                        dataType: "Json",
                        success: function (result) {
                            if (result) {
                                alert("短信发送成功,请注意查收!");
                                productJS.times();
                            } else {
                                alert("短信发送失败,请重试!");
                                productJS.times();
                            }
                        }
                    });
                }
            },
            checkPhone: function () {
                productJS.phone = $("#phoneId").val();
                if (productJS.phone == null || productJS.phone == "") {
                    $("#phoneErrorId").empty();
                    $("#phoneErrorId").html("手机号不能为空");
                    $("#phoneErrorId").attr("style","display:block");
                    return false;
                }
                if (!isMobile()) {
                    $("#phoneErrorId").empty();
                    $("#phoneErrorId").html("手机号格式输入不正确");
                    $("#phoneErrorId").attr("style","display:block");
                    return false;
                } else {
                    $("#phoneErrorId").empty();
                    return true;
                }
                function isMobile() {
                    var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                    if (!patrn.exec(productJS.phone)) {
                        return false;
                    }
                    $("#phoneErrorId").attr("style","display:none");
                    return true;
                }
            },
            s:60,
            t:"",
            times: function () {
                productJS.s--;
                $("#validateNumberId").html("剩余" + productJS.s + "s");
                $("#validateNumberId").attr("disabled", true);
                productJS.t = setTimeout(function () {
                    productJS.times();
                }, 1000);
                if (productJS.s <= 0) {
                    productJS.s = 60;
                    $("#validateNumberId").attr("disabled", false);
                    $("#validateNumberId").html("重新获取验证码");
                    clearTimeout(productJS.t);
                }
            },
            toNextPage: function () {
                productJS.checkPhone()?(productJS.isValidateNumber()?(productJS.toConfirmOrderPage()?"":""):false) :false;
            },
            isValidateNumber: function () {
                var verificationCode = $("#validateNumberDataId").val();
                $("#validateNameErrorId").attr("style","display:block");
                if (verificationCode==null||verificationCode==""){
                    $("#validateNameErrorId").html("验证码不能为空");
                    return false;
                }
                var bl = false;
                $.ajax({
                    type: "POST",
                    async: false,
                    url: "/binding/verificationCode.do",
                    data: "verificationCode=" + verificationCode + "&phone=" + productJS.phone,
                    dataType: "Json",
                    success: function (result) {
                        if (result) {
                            $("#validateNameErrorId").empty();
                            bl = true;
                        } else {
                            bl = false;
                            $("#validateNameErrorId").empty();
                            $("#validateNameErrorId").html("验证码输入错误");
                        }
                    }
                })
                return bl;
            },
            toConfirmOrderPage: function () {
                window.location.href = "/corder/confirmOrder.do?skuId=" + productJS.skuId;
            }
        }
})();