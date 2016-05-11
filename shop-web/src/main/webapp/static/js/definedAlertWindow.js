(function () {
    window.alert = function(msg){
        generateHtml(msg);
        loadCssFile();
        $(".alert").show();
        $(".layer").show();
        //hideWindow();
        initClick();
    }
    //生成Html
    var generateHtml = function (msg) {
        var _html = "";
        _html += '<div class="alert" style="display: none"><h1>' + msg + '</h1>';
        _html += '<h2><button name="closeWindow">确定</button></h2></div>';
        var _back="";
        _back +='<div class="layer"></div>'
        $("body").append(_html);
        $("body").append(_back);
    }
    var initClick = function(){
        $("button[name='closeWindow']").bind("click",function(){
            $('.alert').remove();
            $('.layer').remove();
        })
    }

    //var hideWindow = function(){
    //    $('.alert').fadeIn("slow").delay(2000).fadeOut("slow", function(){
    //     $('.alert').remove();
    //     });
    //    $(".layer").fadeIn("slow").delay(2000).fadeOut("slow", function(){
    //     $('.layer').remove();
    //     });
    //}
    var loadCssFile = function(){
        $("<link>").attr({
            rel:"stylesheet",
            type:"text/css",
            href:"http://"+window.location.host+"/static/css/alert.css"
        }).appendTo("head");
    }
})();
