$(function () {
    var weixinCheckFun = function (data) {
        if ($(data).val() == "") {
            alert("微信号不能为空");
            return false;
        }
        return true;
    }
    var mobileCheckFun = function (data) {
        var bl = false;
        if ($('input[name="danx"]:checked').attr("class") == "shi") {
            if ($(data).val() == "") {
                alert("手机号不能为空");
                return false;
            }

            if (!isMobile($(data).val())) {
                alert("手机号格式不正确");
                return false;
            }
            var para = {};
            var checkLevel = $(".on");
            if (checkLevel != null) {
                para.agentLevel = checkLevel.attr("levelId");
            }
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
                        if (sendType == 0) {
                            sendType = rdata.sendType;
                        }
                        bl = true;
                    } else {
                        alert(rdata.message);
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
    //$("#weixin").on("blur", function () {
    //    if (weixinCheckFun(this)) {
    //        $(this).next().hide();
    //        $(this).css({"color": "black"})
    //    }
    //})
    //$("#pMobile").on("blur", function () {
    //    if (mobileCheckFun(this)) {
    //        $(this).next().hide();
    //        $(this).css({"color": "black"})
    //    }
    //})
    $("#next").click(function () {
        var n = 0;
        if (!weixinCheckFun($("#weixin"))) {
            n++;
        }
        if ($("#q").prop("checked") == true) {
            if (!mobileCheckFun($("#pMobile"))) {
                n++;
            }
            $("#q_pMobile").html($("#pMobile").val());
            $("#q_pMobile").parent().css("display", "-webkit-box");
        } else {
            $("#q_pMobile").parent().css("display", "none");
        }
        if (n > 0) {
            return;
        }
        // 获取微信号
        $("#q_weixinId").html($("#weixin").val());
        // 获取合伙人等级
        $("#q_levelName").html($(".on label").html());
        // 获取所缴纳货款
        $("#q_amount").html($(".on").attr("agentFee"));

        // 弹出确认框
        $(".back_que").css("display", "-webkit-box");
        $(".back").show();
    });
    $("#submit").click(function (event) {
        var thisObj = $(this);
        if (thisObj.html() == "正在提交...") {
            return;
        }
        var event = event || event.window;
        event.stopPropagation();
        //还没有选择拿货方式
        if (sendType == 0) {
            var paraData = "?";
            paraData += "skuId=" + skuId;
            paraData += "&levelId=" + $(".on").attr("levelId");
            paraData += "&weixinId=" + $("#q_weixinId").html();
            paraData += "&pUserId=" + pUserId;
            window.location.href = path + "border/setUserSendType.shtml" + paraData;
        } else {
            var paraData = "?";
            paraData += "orderType=0";
            paraData += "&skuId=" + skuId;
            paraData += "&levelId=" + $(".on").attr("levelId");
            paraData += "&weixinId=" + $("#q_weixinId").html();
            paraData += "&pUserId=" + pUserId;
            window.location.href = path + "border/confirmBOrder.shtml" + paraData;
        }
    });
    /*
     * 是否有推荐人
     * */
    $("[name='danx']").on("click", function () {
        if ($(this).attr("class") == "shi") {
            $(".dengji input").attr("disabled", false)
            $("#hehuo").show();
        } else {
            $("#hehuo").hide();
        }
    });
    /*
     * 选择合伙人等级
     * */
    $(".dengji p").on("click", function () {
        $(this).addClass("on").siblings().removeClass("on")
    })
    /*
     * 返回修改
     * */
    $("#getBack").on("click", function (event) {
        var event = event || event.window;
        event.stopPropagation();
        $(".back_que").hide();
        $(".back").hide();
    })

});