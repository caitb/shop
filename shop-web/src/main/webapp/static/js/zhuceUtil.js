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
    var weixinCheckFun = function (data) {
        if ($(data).val() == "") {
            $(data).parents("p").addClass("pon");
            $(data).css({"color": "#F74A11"})
            return false;
        }
        return true;
    }
    $("#name").on("blur", function () {
        if (nameCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"})
        }
    });
    $("#weixin").on("blur", function () {
        if (weixinCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"})
        }
    })
    $("#submit").click(function () {
        var n = 0;
        if ($(".sec2 .on label").html() == null || $(".sec2 .on [name='amount']").html() == null) {
            alert("请选择合伙人等级");
            n++;
        }
        if (!nameCheckFun($("#name"))) {
            n++;
        }
        if (!weixinCheckFun($("#weixin"))) {
            n++;
        }
        if (n > 0) {
            return;
        }
        var paraData = {};
        paraData.name = $("#name").val();
        paraData.weixinId = $("#weixin").val();
        paraData.skuId = skuId;
        paraData.levelId = $(".sec2 .on label").attr("levelId");
        paraData.amount = $(".sec2 .on [name='amount']").html();
        paraData.parentMobile = $("#parentMobile").val();
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
                    param += "&skuName=" + skuName;
                    param += "&levelId=" + paraData.levelId;
                    param += "&levelName=" + paraData.levelName;
                    param += "&amount=" + paraData.amount;
                    param += "&yanzhengma=" + paraData.yanzhengma;
                    window.location.href = path + "userApply/registerConfirm.shtml" + param;
                } else {
                    alert(data.message);
                }
            }
        });
    });

    $("[name='danx']").on("click", function () {
        if ($(this).attr("class") == "shi") {
            alert("shi");
        } else {
            alert("fou");
        }
    });
})