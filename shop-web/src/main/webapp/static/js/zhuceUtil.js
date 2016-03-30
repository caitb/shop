$(function () {
    var nameCheckFun = function (data) {
        if ($(data).val() == "") {
            $(data).parent().find("span").show();
            $(data).css({"color": "#F74A11"})
            $(data).parent().find("b").html("姓名不能为空");
            return false;
        }
        if (!isCardName($(data).val())) {
            $(data).parent().find("span").show();
            $(data).css({"color": "#F74A11"})
            $(data).parent().find("b").html("姓名是2-15字的汉字");
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
    var weixinCheckFun = function (data) {
        if ($(data).val() == "") {
            $(data).parent().find("span").show();
            $(data).css({"color": "#F74A11"})
            $(data).parent().find("b").html("微信号不能为空");
            return false;
        }
        return true;
    }
    var mobileCheckFun = function (data) {
        var bl = true;
        if ($('input[name="danx"]:checked').attr("class") == "shi") {
            if ($(data).val() == "") {
                $(data).parent().find("span").show();
                $(data).css({"color": "#F74A11"})
                $(data).parent().find("b").html("手机号不能为空");
                return false;
            }

            if (!isMobile($(data).val())) {
                $(data).parent().find("span").show();
                $(data).css({"color": "#F74A11"})
                $(data).parent().find("b").html("手机号格式不正确");
                return false;
            }
            var para = {};
            para.skuId = skuId;
            para.pMobile = $(data).val();
            $.ajax({
                url: path + "userApply/checkPMobile.do",
                data: para,
                dataType: "json",
                type: "POST",
                async: false,
                success: function (rdata) {
                    if (rdata && rdata.isError == false) {
                        pUserId = rdata.pUserId;
                        var levelId = rdata.levelId;
                        var checkLevelId = $(".dengji .on").attr("levelId");

                    } else {
                        $(data).parent().find("span").show();
                        $(data).css({"color": "#F74A11"})
                        $(data).parent().find("b").html(rdata.message);
                        bl = false;
                    }
                }
            });
        }
        if (bl) {
            return true;
        }
        else {
            return false;
        }
        function isMobile(s) {
            var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
            if (!patrn.exec(s)) {
                return false;
            }
            return true;
        }
    }
    $("#name").on("blur", function () {
        if (nameCheckFun(this)) {
            $(this).next().hide();
            $(this).css({"color": "black"})
        }
    });
    $("#weixin").on("blur", function () {
        if (weixinCheckFun(this)) {
            $(this).next().hide();
            $(this).css({"color": "black"})
        }
    })
    $("#pMobile").on("blur", function () {
        if (mobileCheckFun(this)) {
            $(this).next().hide();
            $(this).css({"color": "black"})
        }
    })

    $("#next").click(function () {
        var n = 0;
/*        if (!nameCheckFun($("#name"))) {
            n++;
        }*/
        if (!weixinCheckFun($("#weixin"))) {
            n++;
        }
        if (!mobileCheckFun($("#pMobile"))) {
            n++;
        }
        if (n > 0) {
            return;
        }
        var paraData = {};
        paraData.name = $("#name").val();
        paraData.weixinId = $("#weixin").val();
        paraData.skuId = skuId;
        paraData.levelId = $(".dengji .on").attr("levelId");
        if ($('input[name="danx"]:checked').attr("class") == "shi") {
            paraData.pMobile = $("#pMobile").val();
        } else {
            paraData.pMobile = "";
        }
        $.ajax({
            url: path + "userApply/registerConfirm/check.do",
            type: "POST",
            data: paraData,
            dataType: "json",
            success: function (data) {
                if (data && data.isError == false) {
                    $("#q_skuName").html(skuName);
                    $("#q_name").html($("#name").val());
                    $("#q_mobile").html(mobile);
                    $("#q_weixinId").html($("#weixin").val());
                    if ($('input[name="danx"]:checked').attr("class") == "shi") {
                        $("#q_pMobile").html($("#pMobile").val());
                    } else {
                        $("#q_pMobile").html("");
                    }
                    $("#q_levelName").html($(".dengji .on [name='levelName']").html());
                    $("#q_amount").html($(".dengji .on [name='amount']").html());
                    $(".back_que").show();
                    $(".back").show();
                } else {
                    alert(data.message);
                }
            }
        });
    });
    $("#submit").click(function (event) {
        var event = event || event.window;
        event.stopPropagation();
        var paraData = {};
        paraData.realName = $("#q_name").html();
        paraData.weixinId = $("#q_weixinId").html();
        paraData.skuId = skuId;
        paraData.levelId = $(".dengji .on").attr("levelId");
        paraData.pUserId = pUserId;
        $.ajax({
            url: path + "border/addBOrder.do",
            type: "post",
            data: paraData,
            dataType: "json",
            success: function (data) {
                if (data.isError == false) {
                    var param = "?";
                    param += "bOrderId=" + data.bOrderId;
                    window.location.href = path + "border/payBOrder.shtml" + param;
                }
                else {
                    alert(data.message);
                }
            }
        });
    });

    $("[name='danx']").on("click", function () {
        if ($(this).attr("class") == "shi") {
            $(".dengji input").attr("disabled", false)
            $("#hehuo").show();
        } else {
            $("#hehuo").hide();
        }
    });
    $(".dengji p").on("click", function () {
        $(this).addClass("on").siblings().removeClass("on")
    })
    $(".onc").on("click", function (event) {
        var event = event || event.window;
        event.stopPropagation();
        $(this).next().show();
    })
    $("body").on("click", function () {
        $(".gao").hide();
    })
    $("#getBack").on("click", function (event) {
        var event = event || event.window;
        event.stopPropagation();
        $(".back_que").hide();
        $(".back").hide();
    })
})