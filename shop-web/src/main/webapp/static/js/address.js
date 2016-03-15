(function () {
    window.addressJS = window.addressJS || {
            init: function () {
                addressJS.initBody();
                addressJS.initInput();
                addressJS.initDetailAddress();
                addressJS.initError();
            },
            initBody: function () {
                $("body").on("click", function () {
                    $(".gao").hide();
                })
            },
            initInput: function () {
                $("input").on("focus", function () {
                    $(this).parents("p").removeClass("pon");
                    $(".no").hide();
                    $(this).css("color", "#333333")
                })
            },
            checkAddress: function () {
                var options = $(".sel:eq(0) option:selected").val();
                var option2 = $(".sel:eq(1) option:selected").val();
                var option3 = $(".sel:eq(2) option:selected").val();
                if (options == -1 || option2 == -1 || option3 == -1) {
                    $(".sel").css("border", "1px solid red")
                    return false;
                } else if (options != -1  || option2 != -1 || option3 != -1) {
                    $(".sel").css("border", "none")
                    return true;
                }
            },
            initDetailAddress: function () {
                //获取选中的项
                $(".dizhi").on("focus", function () {
                    addressJS.checkAddress();
                })
            },
            checkDetailAddress: function (paramJson) {
                if (paramJson.detailAddress == "") {
                    $(".dizhi").next().show()
                    /*.html("手机号不能为空")*/
                    $(".dizhi").focus();
                    return false;
                } else {
                    $(".onc").hide();
                    return true;
                }
            },
            initError: function () {
                $(".onc").eq(0).on("click", function (event) {
                    var event = event || event.window;
                    event.stopPropagation();
                    $(".onc").eq(0).next().show().html("姓名格式不正确");
                })
                $(".onc").eq(1).on("click", function (event) {
                    var event = event || event.window;
                    event.stopPropagation();
                    $(".onc").eq(1).next().show().html("手机号输入错误");
                })
                $(".onc").eq(2).on("click", function (event) {
                    var event = event || event.window;
                    event.stopPropagation();
                    $(".onc").eq(2).next().show().html("详细地址不能为空");
                })
            },
            getJsonParam: function () {
                var addressId = $("#addressId").val();
                var name = $("#name").val();
                var phone = $("#phone").val();
                var postcode = $("#postcode").val();
                var provinceId = $("#s_province").val();
                var provinceName = $("#s_province  option:selected").text();
                var cityId = $("#s_city").val();
                var cityName = $("#s_city  option:selected").text();
                var countyId = $("#s_county").val();
                var countyName = $("#s_county  option:selected").text();
                var detailAddress = $("#detailAddress").val();
                var isDefault = $("#isDefaultId").val();
                var operateType = $("#operateTypeId").val();

                var paramJson = {
                    "id": addressId,
                    "name": name,
                    "phone": phone,
                    "postcode": postcode,
                    "provinceId": provinceId,
                    "provinceName": provinceName,
                    "cityId": cityId,
                    "cityName": cityName,
                    "countyId": countyId,
                    "countyName": countyName,
                    "detailAddress": detailAddress,
                    "isDefault": isDefault,
                    "operateType": operateType
                }
                return paramJson;
            },
            checkName: function (paramJson) {
                if (paramJson.name == "") {
                    $(".name").next().show()
                    $(".name").focus();
                    return false;
                }
                if (!isCardName(paramJson.name)) {
                    $(".name").next().show()
                    /*.html("姓名格式错误")*/
                    $(".name").focus()
                    /*.css("color","red");*/
                    return false;
                } else {
                    $(".onc").hide();
                    return true;
                }
                //检验汉字
                function isChinese(s) {
                    var patrn = /^\s*[\u4e00-\u9fa5]{1,15}\s*$/;
                    if (!patrn.exec(s)) {
                        return false;
                    }
                    return true;
                }

                //检验姓名：姓名是2-15字的汉字
                function isCardName(s) {
                    var patrn = /^\s*[\u4e00-\u9fa5]{1,}[\u4e00-\u9fa5.·]{0,15}[\u4e00-\u9fa5]{1,}\s*$/;
                    if (!patrn.exec(s)) {
                        return false;
                    }
                    return true;
                }
            },
            checkPhone: function (paramJson) {
                if (paramJson.phone == "") {
                    $(".tel").next().show()
                    /*.html("手机号不能为空")*/
                    $(".tel").focus();
                    return false;
                }
                if (!isMobile(paramJson.phone)) {
                    $(".tel").next().show();
                    /*.html("手机号输入有误")*/
                    $(".tel").focus();
                    $(".name").focus()
                    /*.css("color","red");*/
                    return false;
                } else {
                    $(".onc").hide();
                    return true;
                }
                function isMobile(s) {
                    var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                    if (!patrn.exec(s)) {
                        $(".tel").next().show();
                        $(".tel").focus();
                        return false;
                    }
                    return true;
                }
            },
            validateAddressInfo: function (paramJson) {
                return addressJS.checkName(paramJson) ? (addressJS.checkPhone(paramJson) ? (addressJS.checkAddress() ? addressJS.checkDetailAddress(paramJson) : false) : false) : false;
            }
        }
})();