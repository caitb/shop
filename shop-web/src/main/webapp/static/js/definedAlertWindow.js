(function () {
  window.alert = function(msg){
    generateHtml(msg);
    loadCssFile();
    $(".alert").show();
    hideWindow();
  }
  //生成Html
  var generateHtml = function (msg) {
    var _html = "";
    _html += '<div class="alert" style="display: none"><h1><img src="http://'+ window.location.host+'/static/images/alert.png" alt="">提示</h1>';
    _html += '<h2>' + msg + '</h2></div>';
    $("body").append(_html);
  }
  var hideWindow = function(){
        $('.alert').fadeIn("slow").delay(2000).fadeOut("slow", function(){
          $('.alert').remove();
        });

      }
  var loadCssFile = function(){
        $("<link>").attr({
          rel:"stylesheet",
          type:"text/css",
          href:"http://"+window.location.host+"/static/css/alert.css"
        }).appendTo("head");
  }
})();