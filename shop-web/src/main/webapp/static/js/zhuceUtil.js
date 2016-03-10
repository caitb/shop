$(function () {
    var nameCheckFun = function (data) {
        if ($(data).val() == "") {
            $(data).parents("p").addClass("pon");
            $(data).css({"color": "#F74A11"})
            return false;
        }
        if (!isCardName($(data).val())) {
            $(data).parents("p").addClass("pon");
            $(data).css({"color": "#F74A11"})
            return false;
        }
        return true;
        //检验姓名：姓名是2-15字的汉字
        function isCardName(s) {
            var patrn = /^\s*[\u4e00-\u9fa5]{1,}[\u4e00-\u9fa5.·]{0,15}[\u4e00-\u9fa5]{1,}\s*$/;
            if (!patrn.exec(s)) {
                return false;
            }
            return true;
        }
    }
    var telCheckFun = function (data) {
        if ($(data).val() == "") {
            $(data).parents("p").addClass("pon");
            $(data).css({"color": "#F74A11"})
            return false;
        }

        if (!isMobile($(data).val())) {
            $(data).parents("p").addClass("pon");
            $(data).css({"color": "#F74A11"})
            return false;
        }
        return true;
        function isMobile(s) {
            var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
            if (!patrn.exec(s)) {
                return false;
            }
            return true;
        }
    }
    var weixinCheckFun = function (data) {
        if ($(data).val() == "") {
            $(data).parents("p").addClass("pon");
            $(data).css({"color": "#F74A11"})
            return false;
        }
        return true;
    }
    var yanCheckFun = function (data) {
        if ($(data).val() == "") {
            $(data).parents("p").addClass("yno");
            return false;
        }
        if (!isNumber($(data).val())) {
            $(data).parents("p").addClass("yno");
            return false;
        }
        return true;
        //检验汉字
        function isNumber(s) {
            var patrn = /^\d{4}$/;
            if (!patrn.exec(s)) {
                return false;
            }
            return true;
        }
    }
    $("#name").on("blur", function () {
        if (nameCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"})
        }
    });
    $("#tel").on("blur", function () {
        if (telCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"})
        }
    })
    $("#weixin").on("blur", function () {
        if (weixinCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"})
        }
    })
    $("#yan").on("blur", function () {
        if (yanCheckFun(this)) {
            $(this).parents("p").removeClass("yno");
        }
    });
    $("#yanzhengma").click(function () {
        if (!telCheckFun($("#tel"))) {
            return;
        }
        times();
        //$.ajax({
        //    type: "POST",
        //    url: path + "/binding/securityCode.do",
        //    data: "phone=" + $("#tel").val(),
        //    dataType: "Json",
        //    success: function (result) {
        //        if (result.msg) {
        //            alert("短信发送成功,请注意查收!");
        //        } else {
        //            alert("短信发送失败,请重试!");
        //        }
        //    }
        //});
    });
    var s = 60, t;

    function times() {
        s--;
        $("#yanzhengma").html("剩余" + s + "s");
        $("#yanzhengma").attr("disabled", "disabled");
        t = setTimeout(function () {
            times();
        }, 1000);
        if (s <= 0) {
            s = 60;
            $("#yanzhengma").removeAttr("disabled");
            $("#yanzhengma").html("重新获取验证码");
            clearTimeout(t);
        }
    }

    $(".sec2 p").on("click", function () {
        $(".sec2 p").removeClass("on")
        $(this).addClass("on");
    });
    $("#submit").click(function () {
        var n = 0;
        if ($(".sec2 .on label").html() == null || $(".sec2 .on [name='amount']").html() == null) {
            alert("请选择合伙人等级");
            n++;
        }
        if (!nameCheckFun($("#name"))) {
            n++;
        }
        if (!telCheckFun($("#tel"))) {
            n++;
        }
        if (!weixinCheckFun($("#weixin"))) {
            n++;
        }
        if (!yanCheckFun($("#yan"))) {
            n++;
        }
        if (n > 0) {
            return;
        }
        var paraData = {};
        paraData.name = $("#name").val();
        paraData.mobile = $("#tel").val();
        paraData.weixinId = $("#weixin").val();
        paraData.parentMobile = $("#parentMobile").val();
        paraData.skuId = skuId;
        paraData.skuName = skuName;
        paraData.levelId = $(".sec2 .on label").attr("levelId");
        paraData.levelName = $(".sec2 .on label").html();
        paraData.amount = $(".sec2 .on [name='amount']").html();
        paraData.yanzhengma = $("#yan").val();
        $.ajax({
            url: path + "border/registerConfirm/check.do",
            type: "POST",
            data: paraData,
            dataType: "json",
            success: function (data) {
                if (data && data.isError == false) {
                    var param = "?";
                    param += "name=" + paraData.name;
                    param += "&mobile=" + paraData.mobile;
                    param += "&weixinId=" + paraData.weixinId;
                    param += "&parentMobile=" + paraData.parentMobile;
                    param += "&skuId=" + paraData.skuId;
                    param += "&skuName=" + paraData.skuName;
                    param += "&levelId=" + paraData.levelId;
                    param += "&levelName=" + paraData.levelName;
                    param += "&amount=" + paraData.amount;
                    param += "&yanzhengma=" + paraData.yanzhengma;
                    window.location.href = path + "border/registerConfirm.shtml" + param;
                } else {
                    alert(data.message);
                }
            }
        });
    });
})