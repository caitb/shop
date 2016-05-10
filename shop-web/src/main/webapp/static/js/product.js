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
                        if (dataObj!=null&&dataObj!="") {
                            if (dataObj[0].payStatus==1){
                                //已支付的订单不能申请试用。
                                // 未支付的订单可以继续申请支付，支付时支付的之前的订单，不再重新下单
                                $(".first_p").attr("style", "display:none");
                                $(".first_p2").attr("style", "display:block");
                            }else{
                                $(".first_p").attr("style", "display:block");
                                $(".first_p2").attr("style", "display:none");
                            }
                        }else{
                            $(".first_p").attr("style", "display:block");
                            $(".first_p2").attr("style", "display:none");
                        }
                    }
                });
            }
        }
})();