(function ($) {
    //这里放入插件代码
    $.fn.initWxPay = function(param, urlBasePath){
        var wxConfig = {
            "appId":"sss",     //公众号名称，由商户传入
            "timeStamp":"",         //时间戳，自1970年以来的秒数
            "nonceStr":"", //随机串
            "package":"",
            "signType":"",         //微信签名方式：
            "paySign":"" //微信签名
        };

        var wxResConfig = {
            successUrl : "",
            cancelUrl : "",
            errUrl : ""
        };

        $(this).unbind("click").on("click", function () {
            var options = {
                type: "POST",
                url : urlBasePath + "wxpay/aspay",
                data: {
                    param:param
                },
                dataType:"json",
                success:function(res){
                    if(res != undefined && res.appId != undefined
                    && res.timeStamp != undefined && res.nonceStr != undefined
                    && res.package != undefined && res.signType != undefined
                    && res.paySign != undefined) {
                        wxConfig.appId = res.appId;
                        wxConfig.timeStamp = res.timeStamp;
                        wxConfig.nonceStr = res.nonceStr;
                        wxConfig.package = res.package;
                        wxConfig.signType = res.signType;
                        wxConfig.paySign = res.paySign;

                        wxResConfig.successUrl = res.successUrl;
                        wxResConfig.cancelUrl = res.cancelUrl;
                        wxResConfig.errUrl = res.errUrl;

                        pay();
                    } else {
                        alert("网络错误");
                    }
                },
                error:function(){
                    alert("网络错误");
                }
            };

            $.ajax(options);
        });

        function onBridgeReady(){
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest',
                wxConfig,
                function(res){
                    if (res.err_msg == "get_brand_wcpay_request:ok" ) {
                        //跳转成功页面
                        window.location.href = wxResConfig.successUrl;
                    } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                        // 支付取消
                    } else {
                        // 跳转失败或者取消页面
                        if(wxResConfig.errUrl != "" && wxResConfig.errUrl != null) {
                            window.location.href = wxResConfig.errUrl;
                        } else {

                        }
                    }
                }
            );
        }
        function pay() {
            if (typeof WeixinJSBridge == "undefined") {
                if (document.addEventListener) {
                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                } else if (document.attachEvent) {
                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                }
            } else {
                onBridgeReady();
            }
        }
    }
})(jQuery);