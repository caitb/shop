<%@ page language="java" import="java.util.*" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>微信支付</title>
    <script type="application/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
    <script type="application/javascript">
        /*wx.config({
            debug:true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId:'wxd5afa1deb29c6197', // 必填，公众号的唯一标识
            timestamp: '1457513229909', // 必填，生成签名的时间戳
            nonceStr: 'sddfs22dsdf5ssdfa53wq3', // 必填，生成签名的随机串
            signature: 'A8274EB44C6F613BC4D99EA4DFFB2E6A60514110',// 必填，签名，见附录1
            jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        wx.ready(function () {
            /!*wx.chooseWXPay({
                //"appId":"wxd5afa1deb29c6197",     //公众号名称，由商户传入
                "timeStamp":"1457513229909",         //时间戳，自1970年以来的秒数
                "nonceStr":"sddfs22dsdf5ssdfa53wq3", //随机串
                "package":"prepay_id=wx2016030916391321529474ea0694275688",
                "signType":"MD5",         //微信签名方式：
                "paySign":"DAC5089DFDC98694DDD5D67B77E872BA", //微信签名
                success: function (res) {
                    alert("success:" + res.err_code + res.err_desc + res.err_msg);
                },
                cancel: function (res) {
                    alert("fail:" + res.errCode + res.errDesc + res.errMsg);
                },
                fail:function(res){
                    alert("error:" + res.errCode + res.errDesc + res.errMsg);
                    console.log(res);
                }
            });*!/
            pay();
        });*/
        function onBridgeReady(){
            WeixinJSBridge.invoke(
                    'getBrandWCPayRequest',
                    {
                        "appId":"${req.appId}",     //公众号名称，由商户传入
                        "timeStamp":"${req.timeStamp}",         //时间戳，自1970年以来的秒数
                        "nonceStr":"${req.nonceStr}", //随机串
                        "package":"${req.packages}",
                        "signType":"${req.signType}",         //微信签名方式：
                        "paySign":"${req.paySign}" //微信签名
                    },
                    function(res){
                        if (res.err_msg == "get_brand_wcpay_request:ok" ) {
                            //跳转成功页面
                            window.location.href = "${successUrl}";
                        } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                            window.history.go(-1);
                        } else {
                            // 跳转失败或者取消页面
                            alert("fail:" + res.err_code + "||" + res.err_desc + "||" + res.err_msg);
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
        pay();
    </script>
</head>
<body>
    <div class="index_box">
        <div class="apply_name">微信js支付测试</div>


        <div class="branch_con">
            <ul>
                <li><span class="name">测试支付信息</span></li>
            </ul>
            <p class="cz_btn"><a href="javascript:void(0);" onclick="pay();" class="btn_1">立即支付</a></p>
        </div>
    </div>
</body>
</html>
