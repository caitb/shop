(function () {
    window.validateCodeJS = window.validateCodeJS || {
            skuId: $("#skuId").val(),
            phone: null,
            skipPageId: $("#skipPageId").val(),
            bindPhoneStatus: true,
            bindPhoneSkipBasePath: "/user/bingPhoneStatusToPage.shtml",
            bindPhoneSkipParam: "",
            initPage: function () {
                validateCodeJS.initClick();
            },
            initClick: function () {
                $("#validateNumberId").on("click", function () {
                    validateCodeJS.getValidateNumber();
                })
                $("#nextPageId").on("click", function () {
                    validateCodeJS.toNextPage();
                })
            },
            applyTrial: function () {
                var skuId = $("#skuId").val();
                $.ajax({
                    url: '/user/isBindPhone.do',
                    type: 'post',
                    async: false,
                    success: function (data) {
                        if (data == "true") {
                            switch (validateCodeJS.skipPageId) {
                                case "register":
                                    var pUserId = $("#pUserId").val();
                                    var type = $("#type").val();
                                    window.location.href = "/userApply/register.shtml?skuId=" + validateCodeJS.skuId + "&pUserId=" + pUserId + "&type=" + type;
                                    break;
                                case "trial":
                                    window.location.href = "/corder/confirmOrder.do?skuId=" + validateCodeJS.skuId;
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            //显示绑定手机号
                            $(".back").show();
                            $(".back_j").show();
                        }
                    },
                    error: function () {
                        //显示绑定手机号
                        $(".back").show();
                        $(".back_j").show();
                    }
                });
            },
            getValidateNumber: function () {
                validateCodeJS.phone = $("#phoneId").val();
                if (validateCodeJS.checkPhone(validateCodeJS.phone)&&validateCodeJS.isBindedPhone()){
                    $.ajax({
                        type: "POST",
                        url: "/binding/securityCode.do",
                        data: "phone=" + validateCodeJS.phone,
                        async:false,
                        dataType: "text",
                        success: function (result) {
                            if (result) {
                                alert("短信发送成功,请注意查收!");
                                validateCodeJS.times();
                            } else {
                                alert("短信发送失败,请重试!");
                            }
                        }
                    });
                }else {
                    alert("未进入发送手机号");
                }
            },
            isBindedPhone:function(){
                validateCodeJS.phone = $("#phoneId").val();
                var bl = true;
                $.ajax({
                    type: "POST",
                    url: "/user/isBindedPhone.do",
                    data: "phone=" + validateCodeJS.phone,
                    async:false,
                    dataType: "Json",
                    success: function (result) {
                        if (result && result.isError == true) {
                            alert(result.msg);
                            bl = false;
                        }else{
                            bl = true;
                        }
                    },
                    error:function(){
                        alert("error");
                        bl = false;
                    }
                });
                return bl;
            },
            checkPhone: function () {
                validateCodeJS.phone = $("#phoneId").val();
                if (validateCodeJS.phone == null || validateCodeJS.phone == "") {
                    $("#errorMessageId").empty();
                    $("#errorMessageId").html("手机号不能为空");
                    $("#errorMessageId").attr("style", "display:block");
                    return false;
                }
                if (!isMobile()) {
                    $("#errorMessageId").empty();
                    $("#errorMessageId").html("手机号格式输入不正确");
                    $("#errorMessageId").attr("style", "display:block");
                    return false;
                } else {
                    $("#phoneErrorId").empty();
                    return true;
                }
                function isMobile() {
                    var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                    if (!patrn.exec(validateCodeJS.phone)) {
                        return false;
                    }
                    $("#errorMessageId").attr("style", "display:none");
                    return true;
                }
            },
            s: 60,
            t: "",
            times: function () {
                validateCodeJS.s--;
                $("#validateNumberId").html("剩余" + validateCodeJS.s + "s");
                $("#validateNumberId").unbind("click");
                validateCodeJS.t = setTimeout(function () {
                    validateCodeJS.times();
                }, 1000);
                if (validateCodeJS.s <= 0) {
                    validateCodeJS.s = 60;
                    $("#validateNumberId").bind("click", function () {
                        validateCodeJS.getValidateNumber();
                    });
                    $("#validateNumberId").html("重新获取验证码");
                    clearTimeout(validateCodeJS.t);
                }
            },
            toNextPage: function () {
                //validateCodeJS.bindPhone();
                //validateCodeJS.skipPage();
                validateCodeJS.checkPhone() ? (validateCodeJS.isValidateNumber() ? (validateCodeJS.bindPhone() ? "" : "") : false) : false;
            },
            isValidateNumber: function () {
                var verificationCode = $("#validateNumberDataId").val();
                $("#errorMessageId").attr("style", "display:block");
                if (verificationCode == null || verificationCode == "") {
                    $("#errorMessageId").html("验证码不能为空");
                    return false;
                }
                var bl = false;
                $.ajax({
                    type: "POST",
                    async: false,
                    url: "/binding/verificationCode.do",
                    data: "verificationCode=" + verificationCode + "&phone=" + validateCodeJS.phone,
                    dataType: "text",
                    success: function (result) {
                        if (result == "true") {
                            $("#errorMessageId").empty();
                            bl = true;
                        } else {
                            bl = false;
                            $("#errorMessageId").empty();
                            $("#errorMessageId").html("验证码输入错误");
                        }
                    }
                })
                return bl;
            },
            bindPhone: function () {
                var para = {};
                validateCodeJS.phone = $("#phoneId").val();
                para.phone = validateCodeJS.phone;
                $.ajax({
                    type: "POST",
                    async: false,
                    url: "/user/bindPhone.do",
                    data: para,
                    dataType: "JSON",
                    success: function (result) {
                        if (result && result.isError == false) {
                            validateCodeJS.skipPage();
                        } else {
                            alert(result.msg);
                            return false;
                        }
                    }
                })
            },
            skipPage: function () {
                var path;
                switch (validateCodeJS.skipPageId) {
                    case "register":
                        var type = $("#type").val();
                        path = "/userApply/register.shtml?skuId=" + validateCodeJS.skuId + "&pUserId=" + pUserId + "&type=" + type;
                        validateCodeJS.bindPhoneSkipParam = "?skipPage=register&status=success&path=" + path
                        break;
                    case "trial":
                        path = "/corder/confirmOrder.do?skuId=" + validateCodeJS.skuId;
                        validateCodeJS.bindPhoneSkipParam = "?skipPage=trial&status=success&path=" + path;
                        break;
                    default:
                        break;
                }
                window.location.href = validateCodeJS.bindPhoneSkipBasePath + validateCodeJS.bindPhoneSkipParam;
            }
        }
})();