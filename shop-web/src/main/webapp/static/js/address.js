(function () {
    window.addressJS = window.addressJS || {
            basePath : "http://"+ window.location.host,
            init: function () {
                addressJS.initBody();
                addressJS.initInput();
                addressJS.initDetailAddress();
                addressJS.initError();
                addressJS.bindChange();
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
                if (options == -1 || option2 == -1 || option3 == -2) {
                    $(".sel").css("border", "1px solid red")
                    return false;
                } else if (options != -1 || option2 != -1 || option3 != -2) {
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
            checkDetailAddress: function (detailAddress) {
                if (detailAddress == "") {
                    $(".dizhi").next().show()
                    $(".dizhi").focus();
                    $(".onc").eq(3).next().html("详细地址不能为空");
                    return false;
                } else if (addressJS.getStrLen(detailAddress) > 60) {
                    $(".dizhi").next().show()
                    $(".dizhi").focus();
                    $(".onc").eq(3).next().html("详细地址不能超过30个字");
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
                    $(".onc").eq(0).next().show();
                })
                $(".onc").eq(1).on("click", function (event) {
                    var event = event || event.window;
                    event.stopPropagation();
                    $(".onc").eq(1).next().show();
                })
                $(".onc").eq(2).on("click", function (event) {
                    var event = event || event.window;
                    event.stopPropagation();
                    $(".onc").eq(2).next().show();
                })
                $(".onc").eq(3).on("click", function (event) {
                    var event = event || event.window;
                    event.stopPropagation();
                    $(".onc").eq(3).next().show();
                })
            },
            bindChange: function () {
                $("#name").bind("change", function () {
                    addressJS.checkName($("#name").val());
                });
                $("#phone").bind("change", function () {
                    addressJS.checkPhone($("#phone").val());
                });
                $("#postcode").bind("change", function () {
                    addressJS.checkPostCode($("#postcode").val());
                });
                $("#detailAddress").bind("change", function () {
                    addressJS.checkDetailAddress($("#detailAddress").val());
                });
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
                var addAddressJumpType = $("#addAddressJumpTypeId").val();
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
                    "operateType": operateType,
                    "addAddressJumpType": addAddressJumpType
                }
                return paramJson;
            },
            checkName: function (name) {

                if (name == "") {
                    $(".name").next().show()
                    $(".name").focus();
                    $(".onc").eq(0).next().html("姓名不能为空");
                    return false;
                }
                if (addressJS.getStrLen(name) > 20) {
                    $(".name").next().show();
                    $(".name").focus();
                    $(".onc").eq(0).next().html("姓名不能超过10个字");
                    return false;
                } else {
                    $(".onc").hide();
                    return true;
                }
            },
            checkPhone: function (phone) {
                if (phone == "") {
                    $(".tel").next().show()
                    $(".tel").focus();
                    $(".onc").eq(1).next().html("手机号不能为空");
                    return false;
                }
                if (!isMobile(phone)) {
                    $(".tel").next().show();
                    $(".tel").focus();
                    $(".onc").eq(1).next().html("手机号格式不正确");
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
                        $(".onc").eq(1).next().html("手机号格式不正确");
                        return false;
                    }
                    return true;
                }
            },
            checkPostCode: function (postcode) {
                if (postcode == "") {
                    $(".postcode").next().show();
                    $(".postcode").focus();
                    $(".onc").eq(2).next().html("邮政编码不能为空");
                    return false;
                } else {
                    if (!/^[0-9][0-9]{5}$/.test(postcode)) {
                        $(".postcode").next().show()
                        $(".postcode").focus();
                        $(".onc").eq(2).next().html("邮政编码格式不正确");
                        return false;
                    }
                    $(".onc").hide();
                    return true;
                }
            },
            getStrLen: function (str) {
                var len = 0;
                if (str != null && str != "") {
                    for (var i = 0; i < str.length; i++) {
                        var c = str.charCodeAt(i);
                        if (c > 127 || c == 94) {
                            len += 2;
                        } else {
                            len++;
                        }
                    }
                }
                return len;
            },
            validateAddressInfo: function (paramJson) {
                return addressJS.checkName(paramJson.name) ? ( addressJS.checkPhone(paramJson.phone) ? (addressJS.checkPostCode(paramJson.postcode) ? (addressJS.checkAddress() ? addressJS.checkDetailAddress(paramJson.detailAddress) : false) : false) : false ) : false;
            },
            updateAddress: function () {
                var manageAddressJumpType = $("#manageAddressJumpTypeId").val();
                var addAddressJumpType = $("#addAddressJumpTypeId").val();
                var paramJson = addressJS.getJsonParam();
                if (addressJS.validateAddressInfo(paramJson)) {
                    $.post("/userAddress/addOrUpdateAddress.do",
                        paramJson, function (data) {
                            if (data == "success") {
                                window.location.href = addressJS.basePath + "/userAddress/toManageAddressPage.html?manageAddressJumpType=" + manageAddressJumpType + "&addAddressJumpType=" + addAddressJumpType;
                            }
                        });
                }
            }
        }
})();