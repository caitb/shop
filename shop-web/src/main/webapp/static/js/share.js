/*
 * 注意：
 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
 * 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 *
 * 如有问题请通过以下渠道反馈：
 * 邮箱地址：weixin-open@qq.com
 * 邮件主题：【微信JS-SDK反馈】具体问题
 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
 */
wx.ready(function () {


    var shareData1 = {
        title: '微信JS-SDK Demo',
        desc: '微信JS-SDK,帮助第三方为用户提供更优质的移动web服务',
        link: 'http://demo.open.weixin.qq.com/jssdk/',
        imgUrl: 'http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0'
    };

    var shareData2 = {
        title: '互联网之子',
        desc: '在长大的过程中，我才慢慢发现，我身边的所有事，别人跟我说的所有事，那些所谓本来如此，注定如此的事，它们其实没有非得如此，事情是可以改变的。更重要的是，有些事既然错了，那就该做出改变。',
        link: 'http://movie.douban.com/subject/25785114/',
        imgUrl: 'http://img3.douban.com/view/movie_poster_cover/spst/public/p2166127561.jpg',
        trigger: function (res) {
            alert('用户点击分享到QQ');
        },
        complete: function (res) {
            alert(JSON.stringify(res));
        },
        success: function (res) {
            alert('已分享');
        },
        cancel: function (res) {
            alert('已取消');
        },
        fail: function (res) {
            alert(JSON.stringify(res));
        }
    };

    var shareData3 = {
        title: '贾浩田的QQ空间',
        desc: '他曾说过:真有点坚持不下去了!',
        link: 'http://user.qzone.qq.com/274810545/infocenter?ptsig=ly5J*Fro-yTys6XvrBrNEvDr57kQOQpT*l0Hzqjyhjs_',
        imgUrl: 'http://qlogo2.store.qq.com/qzone/274810545/274810545/100?1263182533',
        trigger: function (res) {
            alert('用户点击分享到QQ');
        },
        complete: function (res) {
            alert(JSON.stringify(res));
        },
        success: function (res) {
            alert('已分享');
        },
        cancel: function (res) {
            alert('已取消');
        },
        fail: function (res) {
            alert(JSON.stringify(res));
        }
    };

    window.console.log('shareData: ' + shareData);
    wx.onMenuShareAppMessage(shareData);
    wx.onMenuShareTimeline(shareData);
    wx.onMenuShareQQ(shareData);
    wx.onMenuShareQZone(shareData);
    wx.onMenuShareWeibo(shareData);

    $('#sharePY').on('click', function(){
        if(typeof WeixinJSBridge == 'undefined'){
            alert("请先关注麦链社交商城，通过微信分享文章 ");
        }else{
            WeixinJSBridge.invoke('shareTimeline',shareData);
        }
    });

    function decryptCode(code, callback) {
        $.getJSON('/jssdk/decrypt_code.php?code=' + encodeURI(code), function (res) {
            if (res.errcode == 0) {
                codes.push(res.code);
            }
        });
    }
});

wx.error(function (res) {
    alert(res.errMsg);
});
