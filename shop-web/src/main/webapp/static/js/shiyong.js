$(function () {
    var nameCheckFun = function (data) {
        if ($(data).val() == "") {
            $(data).parents("p").addClass("pon");
            $(data).css({"color": "#F74A11"});
            return false;
        }
        if (!isCardName($(data).val())) {
            $(data).parents("p").addClass("pon");
            $(data).css({"color": "#F74A11"});
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
    $("#nameId").on("blur", function () {
        if (nameCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"});
        }
    });
    $("#phoneId").on("blur", function () {
        if (telCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"});
        }
    })


    var yanCheckFun = function (data) {
        var verificationCode = $(data).val();
        var phone = $("#phoneId").val();
        var bl = false;
        $.ajax({
            type:"POST",
            async:false,
            url : "/binding/verificationCode.do",
            data:"verificationCode="+verificationCode+"&phone="+phone,
            dataType:"Json",
            success:function(result){
                if(result){
                    bl = true;
                }else{
                    bl = false;
                    $(data).parents("p").addClass("yno");
                }
            }
        });
        return bl;
    }
    $("#yanzhengma").click(function () {
        if (!telCheckFun($("#phoneId"))) {
            return;
        }
        times();
        $.ajax({
            type: "POST",
            url: "/binding/securityCode.do",
            data: "phone=" + $("#phoneId").val(),
            dataType: "Json",
            success: function (result) {
                if (result.msg) {
                    alert("短信发送成功,请注意查收!");
                } else {
                    alert("短信发送失败,请重试!");
                }
            }
        });
    });
    var s = 60, t;

    function times() {
        s--;
        $("#yanzhengma").html("剩余" + s + "s");
        $("#yanzhengma").attr("disabled", true);
        t = setTimeout(function () {
            times();
        }, 1000);
        if (s <= 0) {
            s = 60;
            $("#yanzhengma").attr("disabled", false);
            $("#yanzhengma").html("重新获取验证码");
            clearTimeout(t);
        }
    }
    $("#wechatId").on("blur", function () {
        if (weixinCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"});
        }
    })

    $("#apply").click(function () {
        //if ($(".pon").length==0&&yanCheckFun($("#codeValueId"))){
        if ($(".pon").length==0){
            var spuId = $("#spuId").val();
            var skuId = $("#skuId").val();
            var applyReason = $("#applyReasonId").val();
            var name = $("#nameId").val();
            var phone = $("#phoneId").val();
            var wechat = $("#wechatId").val();
            $.post("/corder/trialApply.do",
                {
                    "spuId":spuId,
                    "skuId":skuId,
                    "applyReason":applyReason,
                    "name" : name,
                    "phone" : phone,
                    "wechat" : wechat
                },function(data) {
                    if(data!=null&&data!=""){
                        window.location.href = "/corder/confirmOrder.do?orderId="+data;
                    }else{
                        alert("逻辑出错");
                    }
                });
        }
    })
})