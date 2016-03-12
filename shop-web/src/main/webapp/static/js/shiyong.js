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

    $("#nameId").on("blur", function () {
        if (nameCheckFun(this)) {
            $(this).parents("p").removeClass("pon");
            $(this).css({"color": "black"})
        }
    });
    $("#phoneId").on("blur", function () {
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
})