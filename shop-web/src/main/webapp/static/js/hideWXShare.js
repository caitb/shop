/**
 * 隐藏微信分享功能
 * @config json字符串或json对象
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
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone',
            'hideOptionMenu',
            'hideAllNonBaseMenuItem',
        ]
    });

    wx.ready(function() {
        wx.hideAllNonBaseMenuItem();
    });
}

$(function(){
    $.ajax({
        url: '/hideWXShare',
        data: {hideUrl: window.location.href},
        success: function(config){
            hideWXShare(config);
        }
    })
});