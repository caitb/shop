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
    <script type="application/javascript">
        function onBridgeReady(){
            WeixinJSBridge.invoke(
                    'getBrandWCPayRequest',
                    {
                        "appId":"wxd5afa1deb29c6197",     //公众号名称，由商户传入
                        "timeStamp":"1457513229909",         //时间戳，自1970年以来的秒数
                        "nonceStr":"sddfs22dsdf5ssdfa53wq3", //随机串
                        "package":"prepay_id=wx2016030916391321529474ea0694275688",
                        "signType":"MD5",         //微信签名方式：
                        "paySign":"71BAB9387931ADEB2859BE2D9B66C6A6" //微信签名
                    },
                    function(res){
                        alert(res);
                        if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                            //跳转成功页面
                            alert("ok:true");
                        } else {
                            // 跳转失败或者取消页面
                            alert();
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

</body>
</html>
