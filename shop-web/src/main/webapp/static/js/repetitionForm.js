"use strict";
var CommonPerson = {};
CommonPerson.Base = {};
CommonPerson.Base.LoadingPic = {
    operation: {
        timeTest: null,                     //延时器
        loadingCount: 0,                    //计数器 当同时被多次调用的时候 记录次数
        loadingImgUrl: "img/loading.gif",  //默认load图地址
        loadingImgHeight: 24,               //Loading图的高
        loadingImgWidth: 24,                 //Loading图的宽
        locked:true                         //loading加载开关
    },

    //显示全屏Loading图
    FullScreenShow: function (msg) {
        if (msg === undefined) {
            msg = "加载中...";
        }

        if ($("#div_loadingImg").length == 0) {
            $("body").append("<div id='div_loadingImg'></div>");
        }
        if (this.operation.loadingCount < 1) {
            this.operation.timeTest = setTimeout(function () {
                $("#div_loadingImg").append("<div id='loadingPage_bg' class='loadingPage_bg1'></div><div id='loadingPage'><p>" + msg + "</p></div>");
                $("#loadingPage_bg").height($(top.window.document).height()).width($(top.window.document).width());
            }, 100);
        }
        this.operation.loadingCount += 1;
    },

    //隐藏全屏Loading图
    FullScreenHide: function () {
        this.operation.loadingCount -= 1;
        if (this.operation.loadingCount <= 0) {
            clearTimeout(this.operation.timeTest);
            $("#div_loadingImg").empty();
            $("#div_loadingImg").remove();
            this.operation.loadingCount = 0;
        }
    }

}
function fullHide() {
    CommonPerson.Base.LoadingPic.FullScreenHide();
}

function fullShow() {
    CommonPerson.Base.LoadingPic.FullScreenShow();
}