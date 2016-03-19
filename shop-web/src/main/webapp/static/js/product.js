(function () {
    window.productJS = window.productJS || {
            skuId: $("#skuId").val(),
            phone: null,
            initPage: function () {
                productJS.initContent();
            },
            initContent: function () {
                $(".back").attr("style", "display:none");
                $.ajax({
                    url: '/corder/isApplyTrial.do',
                    type: 'post',
                    async: false,
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
            }
        }
})();