/**
 * 隐藏微信分享
 */
function hideWXShare(config){
    if((typeof config)=='string'){
        config = window.eval('('+config+')');
    }
    if((typeof config)!='object'){
        console.log('参数格式不对!');
        return;
    }
    wx.config({
        debug: false,
        appId: config.appId,
        timestamp: config.timestamp,
        nonceStr: config.nonceStr,
        signature: config.signature,
        jsApiList: [
            'checkJsApi',
            'hideOptionMenu',
        ]
    });

    wx.hideOptionMenu();
}